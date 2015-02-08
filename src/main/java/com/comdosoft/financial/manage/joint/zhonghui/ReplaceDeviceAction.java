package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

import com.comdosoft.financial.manage.joint.AbstractHandler;

public class ReplaceDeviceAction extends RequireLoginAction {
	
	private String ksnNo;
	private String reqNo;
	
	public ReplaceDeviceAction(String ksnNo, String reqNo) {
		super();
		this.ksnNo = ksnNo;
		this.reqNo = reqNo;
	}

	@Override
	public Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("ksnNo", ksnNo);
		params.put("reqNo", reqNo);
		params.put("model","zfmini");
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
		return "/swiper/change";
	}

}
