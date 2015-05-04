package com.comdosoft.financial.manage.domain.zhangfu.task;

public class Intention {
	
    private int id;
    private int status;
    private String name;
    private String processname;
    private String phone;
    private String content;
    private String date;
    private Integer checktype;
    
    public Integer getChecktype() {
		return checktype==null||checktype==0?1:checktype;
	}
    
    public void setChecktype(Integer checktype) {
		this.checktype = checktype;
	}
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProcessname() {
        return processname;
    }
    public void setProcessname(String processname) {
        this.processname = processname;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
   

	
	
	
}
