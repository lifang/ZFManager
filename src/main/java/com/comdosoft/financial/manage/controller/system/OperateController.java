package com.comdosoft.financial.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.comdosoft.financial.manage.domain.zhangfu.Menu;
import com.comdosoft.financial.manage.service.RoleService;

@Controller
@RequestMapping("/system/operate")
public class OperateController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/accounts",method=RequestMethod.GET)
	public String accountsList(){
		return "system/accounts_list";
	}

    @RequestMapping(value="/account/create",method=RequestMethod.GET)
    public String accountCreateGet(){
        return "system/account_create";
    }

	@RequestMapping(value="/roles",method=RequestMethod.GET)
	public String rolesList(Model model){
		return "system/roles_list";
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
}
