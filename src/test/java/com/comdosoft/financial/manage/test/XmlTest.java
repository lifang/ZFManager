package com.comdosoft.financial.manage.test;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;

import com.comdosoft.financial.manage.joint.hanxin.LoginRequest;

public class XmlTest {
	
	public static void main(String[] args){
		Writer writer = new StringWriter();
		LoginRequest request = new LoginRequest();
		request.setAccountName("account name");
		JAXB.marshal(request, writer);
		System.out.println(writer.toString());
	}

}
