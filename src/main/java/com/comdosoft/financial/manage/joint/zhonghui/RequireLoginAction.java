package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.zhonghui.LoginAction.LoginResult;
import com.google.common.collect.Maps;

public abstract class RequireLoginAction extends Action {
	
	private static final Logger LOG = LoggerFactory.getLogger(RequireLoginAction.class);
	
	private static final Map<String,LoginResult> loggedInfo = Maps.newConcurrentMap();
	
	private String phoneNum;
	private String password;
	private String position;

	public RequireLoginAction(String phoneNum, String password, String position) {
		this.phoneNum = phoneNum;
		this.password = password;
		this.position = position;
	}

	@Override
	protected Map<String, String> headers() {
		LoginResult result = loggedInfo.get(phoneNum);
		if(result == null) {
			try {
				LoginAction la = new LoginAction(phoneNum, password,position,
						Const.APP_VERSION,Const.PRODUCT);
				Action.acts(la.setHandler((r)->{
					LoginResult lr = (LoginResult)r;
					loggedInfo.put(phoneNum, lr);
				}));
			} catch (IOException e) {
				LOG.error("login error",e);
			}
		}
		Map<String,String> headers = Maps.newHashMap();
		headers.put("WSHSNO", loggedInfo.get(phoneNum).getSession());
		return headers;
	}

	@Override
	protected int getReqNo() {
		LoginResult result = loggedInfo.get(phoneNum);
		if(result!=null){
			return result.getNextReqNo();
		}
		return super.getReqNo();
	}

	@Override
	protected final Result parseResult(Map<String, String> headers, InputStream stream) {
		Result result = super.parseResult(headers, stream);
		if("SESSION_TIMEOUT".equals(result.getRespCode())){
			LOG.debug("[{}] session time out and try relogin.",phoneNum);
			loggedInfo.remove(phoneNum);
			try {
				Action.acts(this);
			} catch (IOException e) {
				LOG.error("login error",e);
			}
			return null;
		}
		return result;
	}
	
	

}
