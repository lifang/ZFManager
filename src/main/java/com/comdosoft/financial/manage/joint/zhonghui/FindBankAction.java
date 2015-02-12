package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.List;
import java.util.Map;

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
	protected Map<String, String> params() {
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
	protected Class<? extends Result> getResultType() {
		return BankResult.class;
	}

	public static class BankResult extends Result{
		private int total;
		private List<Bank> banks;
		
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public List<Bank> getBanks() {
			return banks;
		}
		public void setBanks(List<Bank> banks) {
			this.banks = banks;
		}
	}
	
	public static class Bank{
		private String bankDeposit;
		private String unionBankNo;
		
		public String getBankDeposit() {
			return bankDeposit;
		}
		public void setBankDeposit(String bankDeposit) {
			this.bankDeposit = bankDeposit;
		}
		public String getUnionBankNo() {
			return unionBankNo;
		}
		public void setUnionBankNo(String unionBankNo) {
			this.unionBankNo = unionBankNo;
		}
	}
}
