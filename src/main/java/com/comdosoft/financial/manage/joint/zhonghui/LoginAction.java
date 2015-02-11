package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.comdosoft.financial.manage.joint.JointResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginAction extends Action {
	
	private String phoneNum;
	private String password;
	private LoginResult loginResult;
	
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
	public String url() {
		return "/user/login";
	}

	@Override
	public void handle(JointResult result) {
		this.loginResult = (LoginResult) result;
	}

	@Override
	protected JointResult parseResult(Map<String, String> headers,
			InputStream stream) {
		LoginResult result = null;
		String session = headers.get("WSHSNO");
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(stream, LoginResult.class);
			if(result.isSuccess){
				result.result = JointResult.RESULT_SUCCESS;
			}
			result.session = session;
		} catch (IOException e) {
			result = new LoginResult();
			e.printStackTrace();
		}
		return result;
	}
	
	public LoginResult getLoginResult() {
		return loginResult;
	}

	public static class LoginResult implements JointResult {
		
		private int result = JointResult.RESULT_FAIL;
		private String session;
		private String respTime;
		private boolean isSuccess;
		private String respCode;
		private String respMsg;
		private int respNo;
		private String status;
		private String name;
		private String cardTail;
		private String ksnNo;
		private String sessionKey;
		private String keyCheck;
		private String model;
		private String serialType;
		private int nextReqNo;

		@Override
		public int getResult() {
			return result;
		}

		public String getSession() {
			return session;
		}

		public String getRespTime() {
			return respTime;
		}

		public boolean isSuccess() {
			return isSuccess;
		}

		public String getRespCode() {
			return respCode;
		}

		public String getRespMsg() {
			return respMsg;
		}

		public int getRespNo() {
			return respNo;
		}

		public String getStatus() {
			return status;
		}

		public String getName() {
			return name;
		}

		public String getCardTail() {
			return cardTail;
		}

		public String getKsnNo() {
			return ksnNo;
		}

		public String getSessionKey() {
			return sessionKey;
		}

		public String getKeyCheck() {
			return keyCheck;
		}

		public String getModel() {
			return model;
		}

		public String getSerialType() {
			return serialType;
		}

		public int getNextReqNo() {
			return nextReqNo;
		}
		
	}

}
