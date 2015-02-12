package com.comdosoft.financial.manage.joint;

public interface JointResult {
	
	static final int RESULT_SUCCESS = 1;
	static final int RESULT_FAIL = 9;
	
	int getResult();
	
	boolean isSuccess();
	
	String getMsg();

}
