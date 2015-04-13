package com.comdosoft.financial.manage.domain.zhangfu;

import java.util.Date;

//积分兑换追踪记录
public class CustomerIntegralConvertMark {
	private Integer id;
	private Integer cicId;
	private String content;
	private Date createdAt;
	private Integer processUserId;
	private String processUserName;
	
	/**
	 * @return the cicId
	 */
	public Integer getCicId() {
		return cicId;
	}
	/**
	 * @param cicId the cicId to set
	 */
	public void setCicId(Integer cicId) {
		this.cicId = cicId;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * @return the processUserId
	 */
	public Integer getProcessUserId() {
		return processUserId;
	}
	/**
	 * @param processUserId the processUserId to set
	 */
	public void setProcessUserId(Integer processUserId) {
		this.processUserId = processUserId;
	}
	/**
	 * @return the processUserName
	 */
	public String getProcessUserName() {
		return processUserName;
	}
	/**
	 * @param processUserName the processUserName to set
	 */
	public void setProcessUserName(String processUserName) {
		this.processUserName = processUserName;
	}
	public CustomerIntegralConvertMark() {
		super();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerIntegralConvertMark [id=" + id + ", cicId=" + cicId
				+ ", content=" + content + ", createdAt=" + createdAt
				+ ", processUserId=" + processUserId + ", processUserName="
				+ processUserName + "]";
	}
 
 
}