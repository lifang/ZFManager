package com.comdosoft.financial.manage.domain.zhangfu.task;

public class Mark {
	private int applyid;
	private int cusid;
    private String created_at;
    private String content;
    private String name;
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getApplyid() {
        return applyid;
    }
    public void setApplyid(int applyid) {
        this.applyid = applyid;
    }
    public int getCusid() {
        return cusid;
    }
    public void setCusid(int cusid) {
        this.cusid = cusid;
    }
    
}
