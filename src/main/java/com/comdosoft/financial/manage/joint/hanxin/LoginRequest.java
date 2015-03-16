package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name="bppos")
public class LoginRequest extends RequestBean {
	
	private String merchantId;
	private String accountName;
	private String accountPwd;
	
	public LoginRequest() {
		super("AccountLogIn");
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	
	@Override
	public Class<? extends JointResponse> getResponseType() {
		return LoginResponse.class;
	}
	
	@XmlRootElement(name="bppos")
	public static class LoginResponse extends ResponseBean {
		
		private String terminalId;
		private String merchantId;
		private String merchantName;//商户名
		private String accountName;//账户名
		private String personalMerRegNo;//个体工商信息
		private String legalManIdcard;//法人身份证号
		private String settleAccountType;
		private String settleAccount;//结算账户名
		private String settleAccountNo;//结算账号
		private String accountStatus;//开户状态
		private String authStatus;//认证状态
		private String actionList;
		
		public String getTerminalId() {
			return terminalId;
		}
		public void setTerminalId(String terminalId) {
			this.terminalId = terminalId;
		}
		
		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		
		public String getMerchantName() {
			return merchantName;
		}
		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}
		
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		
		public String getPersonalMerRegNo() {
			return personalMerRegNo;
		}
		public void setPersonalMerRegNo(String personalMerRegNo) {
			this.personalMerRegNo = personalMerRegNo;
		}
		
		public String getLegalManIdcard() {
			return legalManIdcard;
		}
		public void setLegalManIdcard(String legalManIdcard) {
			this.legalManIdcard = legalManIdcard;
		}
		
		public String getSettleAccountType() {
			return settleAccountType;
		}
		public void setSettleAccountType(String settleAccountType) {
			this.settleAccountType = settleAccountType;
		}
		
		public String getSettleAccount() {
			return settleAccount;
		}
		public void setSettleAccount(String settleAccount) {
			this.settleAccount = settleAccount;
		}
		
		public String getSettleAccountNo() {
			return settleAccountNo;
		}
		public void setSettleAccountNo(String settleAccountNo) {
			this.settleAccountNo = settleAccountNo;
		}
		
		public String getAccountStatus() {
			return accountStatus;
		}
		public void setAccountStatus(String accountStatus) {
			this.accountStatus = accountStatus;
		}
		
		public String getAuthStatus() {
			return authStatus;
		}
		public void setAuthStatus(String authStatus) {
			this.authStatus = authStatus;
		}
		
		public String getActionList() {
			return actionList;
		}
		public void setActionList(String actionList) {
			this.actionList = actionList;
		}
		
	}
}
