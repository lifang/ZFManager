package com.comdosoft.financial.manage.domain.zhangfu.task;

public class OutStore {
	//出库单号id
	private int id;
	private int processUserId;
	private String csApplyId;
	//订单号ids
	private int orderId;
	// 0待处理   1已取消  2已完成
	private int status;
	private int csApplyTypes;
	private String updatedAt;
	private int quantity;
	private String createdAt;
	private String processUserName;
	//物流公司名称
	private String wlCompanyName;
	//物流单号
	private String wlNum;
	//终端号集合（goodId_portNum|goodId_portNum）
	private String terminalNum;
	//备注
	private String remarkContent;
	
	private String orderNumber;
	
	
	
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getRemarkContent() {
		return remarkContent;
	}
	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}
	public String getWlCompanyName() {
		return wlCompanyName;
	}
	public void setWlCompanyName(String wlCompanyName) {
		this.wlCompanyName = wlCompanyName;
	}
	public String getWlNum() {
		return wlNum;
	}
	public void setWlNum(String wlNum) {
		this.wlNum = wlNum;
	}
	public String getTerminalNum() {
		return terminalNum;
	}
	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProcessUserId() {
		return processUserId;
	}
	public void setProcessUserId(int processUserId) {
		this.processUserId = processUserId;
	}
	public String getCsApplyId() {
		return csApplyId;
	}
	public void setCsApplyId(String csApplyId) {
		this.csApplyId = csApplyId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCsApplyTypes() {
		return csApplyTypes;
	}
	public void setCsApplyTypes(int csApplyTypes) {
		this.csApplyTypes = csApplyTypes;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getProcessUserName() {
		return processUserName;
	}
	public void setProcessUserName(String processUserName) {
		this.processUserName = processUserName;
	}
	
	
}
