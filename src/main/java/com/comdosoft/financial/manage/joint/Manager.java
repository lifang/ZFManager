package com.comdosoft.financial.manage.joint;

import java.io.IOException;

import com.comdosoft.financial.manage.joint.Action.Method;
import com.comdosoft.financial.manage.utils.HttpUtils;

public abstract class Manager {
	
	public void action(Action action) throws IOException {
		String url = baseUrl() + action.url();
		if(action.method() == Method.GET){
			HttpUtils.get(url, action.headers(), action.params(), action.handler());
		}else if(action.method() == Method.POST){
			HttpUtils.post(url, action.headers(), 
					action.params(), action.fileParams(), action.handler());
		}
		action.action();
	}
	
	public abstract String baseUrl();
}
