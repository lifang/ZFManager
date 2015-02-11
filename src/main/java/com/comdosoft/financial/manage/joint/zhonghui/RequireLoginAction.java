package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.IOException;
import java.util.Map;

import com.comdosoft.financial.manage.joint.zhonghui.LoginAction.LoginResult;
import com.google.common.collect.Maps;

public abstract class RequireLoginAction extends Action {
	
	private static LoginResult loggedInfo = null;

	@Override
	public Map<String, String> headers() {
		if(loggedInfo == null) {
			try {
				LoginAction la = new LoginAction(null, null);
				Action.acts(la);
				loggedInfo = la.getLoginResult();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Map<String,String> headers = Maps.newHashMap();
		headers.put("WSHSNO", loggedInfo.getSession());
		return headers;
	}

}
