package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.InputStream;
import java.util.Map;

import com.comdosoft.financial.manage.joint.JointResult;

public class FindBankAction extends Action {
	
	private String keyword;
	private int max=20;
	private int p;

	public FindBankAction(String keyword, int max, int p) {
		this.keyword = keyword;
		this.max = max;
		this.p = p;
	}
	
	public FindBankAction(String keyword, int p) {
		this.keyword = keyword;
		this.p = p;
	}
	
	@Override
	public Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("p", Integer.toString(p));
		params.put("max", Integer.toString(max));
		params.put("keyword", keyword);
		return params;
	}

	@Override
	public String url() {
		return "/bank/query";
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
