package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.InputStream;
import java.util.Map;

import com.comdosoft.financial.manage.joint.JointResult;

public class ChangePwdAction extends RequireLoginAction {
	
	private String newPassword;// 登录名或者手机号码
	private String password;
	private String reqNo;

	public ChangePwdAction(String newPassword, String password, String reqNo) {
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
	public String url() {
		return "/user/changePassword";
	}

	@Override
	public void handle(JointResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	protected JointResult parseResult(Map<String, String> headers,
			InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
