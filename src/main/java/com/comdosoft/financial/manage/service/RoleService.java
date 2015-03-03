package com.comdosoft.financial.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.Menu;
import com.comdosoft.financial.manage.domain.zhangfu.Role;
import com.comdosoft.financial.manage.domain.zhangfu.RoleMenu;
import com.comdosoft.financial.manage.mapper.zhangfu.MenuMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.RoleMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.RoleMenuMapper;

@Service
public class RoleService {
	
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	public List<Menu> menuList(){
		List<Menu> allMenu = menuMapper.selectOrderedAll();
		return allMenu;
	}
	
	@Transactional("transactionManager")
	public void create(String name,Integer[] roles){
		Role role = new Role();
		role.setCreatedAt(new Date());
		role.setRoleName(name);
		roleMapper.insert(role);
		for(int i=0;i<roles.length;++i) {
			RoleMenu rm = new RoleMenu();
			rm.setMenuId(roles[i]);
			rm.setRoleId(role.getId());
			roleMenuMapper.insert(rm);
		}
	}

}
