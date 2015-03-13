package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bppos")
public class LoginRequest extends RequestBean {
	
	private String terminalId;
	private String merchantId;
	private String accountName;
	private String accountPwd;
	
	public LoginRequest() {
		super("AccountLogIn.Req");
	}
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
	public Class<? extends ResponseBean> getResponseClass() {
		return LoginResponse.class;
	}
}
