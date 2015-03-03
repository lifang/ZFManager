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
		return new Page<Role>(request, roles, total);
	}
}
