package com.comdosoft.financial.manage.domain.zhangfu;

import java.util.Date;

/**
 * 申请成为代理商，
 * 
 * @author Maple.liu
 *
 */
public class AgentJoin {
	private Integer id;
	private Integer process_user_id;
	private String type;
	private String address;
	private Integer status;
	private String name;
	private String processname;
	private String phone;
	private String date;
	private Integer checktype;

	public Integer getChecktype() {
		return checktype==null||checktype==0?2:checktype;
	}

	public void setChecktype(Integer checktype) {
		this.checktype = checktype;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProcess_user_id() {
		return process_user_id;
	}

	public void setProcess_user_id(Integer process_user_id) {
		this.process_user_id = process_user_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
