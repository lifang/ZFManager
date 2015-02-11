package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.comdosoft.financial.manage.joint.JointResult;
import com.google.common.collect.Maps;

public class MerchantAuthAction extends Action{
	
	private String companyName;
	private String regPlace;
	private String businessLicense;
	private File businessPlace;
	private File cashierDesk;

	public MerchantAuthAction(String companyName, String regPlace,
			String businessLicense, File businessPlace, File cashierDesk) {
		super();
		this.companyName = companyName;
		this.regPlace = regPlace;
		this.businessLicense = businessLicense;
		this.businessPlace = businessPlace;
		this.cashierDesk = cashierDesk;
	}

	@Override
	public Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("companyName", companyName);
		params.put("regPlace", regPlace);
		params.put("businessLicense", businessLicense);
		return params;
	}

	@Override
	public Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
		fileParams.put("businessPlace", businessPlace);
		fileParams.put("cashierDesk", cashierDesk);
		return fileParams;
	}

	@Override
	public String url() {
		return "/user/merchantAuth";
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
