package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

/**
 * 交易列表
 * @author wu
 *
 */
@XmlRootElement(name="bppos")
public class EnquiryListRequest extends RequestBean {

	public EnquiryListRequest(String application) {
		super("EnquiryList");
	}

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return EnquiryListResponse.class;
	}

	@XmlRootElement(name="bppos")
	public static class EnquiryListResponse extends ResponseBean {

	}
}
