package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

import com.comdosoft.financial.manage.joint.AbstractHandler;

public class LoginAction extends BaseAction {
	
	private String phoneNum;
	private String password;
	
	public LoginAction(String phone,String password) {
		this.phoneNum = phone;
		this.password = password;
	}

	@Override
	public Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("loginName", phoneNum);
		params.put("password", password);
		return params;
	}

	@Override
	public AbstractHandler handler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String url() {
		return "/user/login";
	}
}
