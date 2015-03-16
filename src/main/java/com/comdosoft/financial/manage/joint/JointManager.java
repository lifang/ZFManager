package com.comdosoft.financial.manage.joint;


public interface JointManager {
	
	void acts(JointRequest request,JointHandler handler);
	
//	JointRequest createLogin(String phoneNum, String password, String position);
//	JointRequest createLogin(String phoneNum, String password);

}
