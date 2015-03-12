package com.comdosoft.financial.manage.controller.system;

import java.util.List;

import com.comdosoft.financial.manage.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Menu;
import com.comdosoft.financial.manage.domain.zhangfu.Role;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.RoleService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/system/operate")
public class OperateController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/accounts",method=RequestMethod.GET)
	public String accountsList(){
		return "system/accounts_list";
	}
	
	@RequestMapping(value="/accounts/page",method=RequestMethod.POST)
	public String accountsPage(String query,Byte status,Integer page,Model model){
        if(page==null){
            page = 0;
        }
		Page<Customer> customers = customerService.listOperatePage(page, query, status);
		model.addAttribute(customers);
        List<List<Role>> roles = roleService.customerRoles(customers.getContent());
        model.addAttribute("roles",roles);
		return "system/accounts_list_page";
	}

    @RequestMapping(value="/account/create",method=RequestMethod.GET)
    public String accountCreateGet(Model model){
        List<Role> roles = roleService.allRoles();
        model.addAttribute(roles);
        return "system/account_create";
    }

    @RequestMapping(value="/account/create",method=RequestMethod.POST)
    public String accountCreatePost(String account,String name,String password,
                                    @RequestParam("re_password") String rePassword,
                                    Integer[] roles){
        customerService.createOperate(account,name,password,roles);
        return "redirect:/system/operate/accounts";
    }
    
    @RequestMapping(value="/account/{id}/status",method=RequestMethod.POST)
    public String accountStatus(@PathVariable Integer id,Model model){
		Customer customer = customerService.updateStatus(id);
		model.addAttribute(customer);
        List<Role> roles = roleService.customerRoles(customer.getId());
        model.addAttribute("role", roles);
        return "system/accounts_list_page_row";
    }
    
    @RequestMapping(value="/account/{id}/edit",method=RequestMethod.GET)
    public String accountEdit(@PathVariable Integer id,Model model){
        List<Role> roles = roleService.allRoles();
        model.addAttribute(roles);
        Customer customer = customerService.customer(id);
        model.addAttribute(customer);
        List<Role> customerRoles = roleService.customerRoles(id);
        model.addAttribute("customerRoles",customerRoles);
        return "system/account_edit";
    }
    
    @RequestMapping(value="/account/{id}/edit",method=RequestMethod.POST)
    public String accountEditPost(@PathVariable Integer id,
    		String account,String name,String password,
            @RequestParam("re_password") String rePassword,
            Integer[] roles){
    	customerService.modifyOperate(id, account, name, password, roles);;
        return "redirect:/system/operate/accounts";
    }

	@RequestMapping(value="/roles",method=RequestMethod.GET)
	public String rolesList(){
		return "system/roles_list";
	}
	
	@RequestMapping(value="/roles/page",method=RequestMethod.POST)
	public String rolesPage(String query,Integer page,Model model){
		Page<Role> roles = roleService.listPage(page, query);
		model.addAttribute(roles);
		return "system/roles_list_page";
	}

    @RequestMapping(value="/role/create",method=RequestMethod.GET)
    public String roleCreateGet(Model model){
		List<Menu> menus = roleService.menuList();
		model.addAttribute(menus);
        return "system/role_create";
    }
    
    @RequestMapping(value="/role/create",method=RequestMethod.POST)
    public String roleCreatePost(@RequestParam("role_name") String name,Integer[] roles){
    	roleService.create(name, roles);
        return "redirect:/system/operate/roles";
    }
    
    @RequestMapping(value="/role/{id}/edit",method=RequestMethod.GET)
    public String roleEditGet(@PathVariable Integer id,Model model){
		List<Menu> menus = roleService.menuList();
		model.addAttribute(menus);
		List<String> keys = roleService.roleKeys(id);
		model.addAttribute("keys", keys);
		Role role = roleService.role(id);
		model.addAttribute(role);
        return "system/role_edit";
    }
    
    @RequestMapping(value="/role/{id}/edit",method=RequestMethod.POST)
    public String roleEditPost(@PathVariable Integer id,
    		@RequestParam("role_name") String name,Integer[] roles){
    	roleService.modify(id, name, roles);
        return "redirect:/system/operate/roles";
    }

    @RequestMapping(value="account/{id}/resetpwd",method=RequestMethod.POST)
    @ResponseBody
    public Response resetpwd(@PathVariable Integer id, String password){
        customerService.modifyPwd(id, password);
        return Response.getSuccess(null);
    }
}
