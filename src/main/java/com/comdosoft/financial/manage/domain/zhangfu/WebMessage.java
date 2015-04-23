package com.comdosoft.financial.manage.domain.zhangfu;

import java.util.Date;

public class WebMessage {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column web_messages.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column web_messages.title
	 * @mbggenerated
	 */
	private String title;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column web_messages.is_placed_top
	 * @mbggenerated
	 */
	private Boolean isPlacedTop;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column web_messages.create_at
	 * @mbggenerated
	 */
	private Date createAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column web_messages.updated_at
	 * @mbggenerated
	 */
	private Date updatedAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column web_messages.content
	 * @mbggenerated
	 */
	private String content;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column web_messages.id
	 * @return  the value of web_messages.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column web_messages.id
	 * @param id  the value for web_messages.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column web_messages.title
	 * @return  the value of web_messages.title
	 * @mbggenerated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column web_messages.title
	 * @param title  the value for web_messages.title
	 * @mbggenerated
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column web_messages.is_placed_top
	 * @return  the value of web_messages.is_placed_top
	 * @mbggenerated
	 */
	public Boolean getIsPlacedTop() {
		return isPlacedTop;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column web_messages.is_placed_top
	 * @param isPlacedTop  the value for web_messages.is_placed_top
	 * @mbggenerated
	 */
	public void setIsPlacedTop(Boolean isPlacedTop) {
		this.isPlacedTop = isPlacedTop;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column web_messages.create_at
	 * @return  the value of web_messages.create_at
	 * @mbggenerated
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column web_messages.create_at
	 * @param createAt  the value for web_messages.create_at
	 * @mbggenerated
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column web_messages.updated_at
	 * @return  the value of web_messages.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column web_messages.updated_at
	 * @param updatedAt  the value for web_messages.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column web_messages.content
	 * @return  the value of web_messages.content
	 * @mbggenerated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column web_messages.content
	 * @param content  the value for web_messages.content
	 * @mbggenerated
	 */
	public void setContent(String content) {
		this.content = content;
	}
}