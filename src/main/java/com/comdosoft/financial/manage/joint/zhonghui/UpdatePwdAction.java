package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

import com.comdosoft.financial.manage.joint.AbstractHandler;

public class UpdatePwdAction extends RequireLoginAction {
	
	private String newPassword;// 登录名或者手机号码
	private String password;
	private String reqNo;

	public UpdatePwdAction(String newPassword, String password, String reqNo) {
		super();
		this.newPassword = newPassword;
		this.password = password;
		this.reqNo = reqNo;
	}

	@Override
	public Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("password", password);
		params.put("newPassword", newPassword);
		params.put("reqNo", reqNo);
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
		return "/user/changePassword";
	}

}
