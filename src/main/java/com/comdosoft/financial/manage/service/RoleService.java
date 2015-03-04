package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.Menu;
import com.comdosoft.financial.manage.domain.zhangfu.Role;
import com.comdosoft.financial.manage.domain.zhangfu.RoleMenu;
import com.comdosoft.financial.manage.mapper.zhangfu.MenuMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.RoleMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.RoleMenuMapper;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class RoleService {
	
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	public List<Menu> menuList(){
        return menuMapper.selectOrderedAll();
	}
	
	@Transactional("transactionManager")
	public void create(String name,Integer[] roles){
		Role role = new Role();
		role.setCreatedAt(new Date());
		role.setRoleName(name);
		roleMapper.insert(role);
		saveRoleMenus(role.getId(),roles);
	}
	
	/**
	 * 列表
	 * @param page 页数
	 * @param query 查询条件
	 * @return
	 */
	public Page<Role> listPage(Integer page,String query){
		PageRequest request = new PageRequest(page, Constants.PAGE_SIZE);
		List<Role> roles = roleMapper.selectPage(request,query);
		long total = roleMapper.countTotal(query);
		return new Page<>(request, roles, total);
	}

    public List<Role> allRoles(){
        return roleMapper.selectAll();
    }
	
	public Role role(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
	}

    public List<Role> customerRoles(Integer customerId){
        return roleMapper.customerRoles(customerId);
    }

    public List<List<Role>> customerRoles(List<Customer> customers) {
        return customers.stream().map(
                c -> customerRoles(c.getId())).collect(Collectors.toList());
    }
	
	/**
	 * 根据roleId查询出所有menuKey
	 * @param id roleId
	 * @return
	 */
	public List<String> roleKeys(Integer id) {
		return roleMenuMapper.selectRoleKeys(id);
	}
	
	@Transactional("transactionManager")
	public void modify(Integer id,String name,Integer[] roles){
		Role role = role(id);
		role.setRoleName(name);
		roleMapper.updateByPrimaryKey(role);
		roleMenuMapper.deleteByRoleId(id);
		saveRoleMenus(id, roles);
	}
	
	private void saveRoleMenus(Integer roleId,Integer[] roles){
        for (Integer role : roles) {
            RoleMenu rm = new RoleMenu();
            rm.setMenuId(role);
            rm.setRoleId(roleId);
            roleMenuMapper.insert(rm);
        }
	}
}
