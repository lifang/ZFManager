package com.comdosoft.financial.manage.domain.zhangfu;

import java.util.Date;

public class DictionaryTradeType {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_trade_types.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_trade_types.trade_type
	 * @mbggenerated
	 */
	private Byte tradeType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_trade_types.trade_value
	 * @mbggenerated
	 */
	private String tradeValue;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_trade_types.created_at
	 * @mbggenerated
	 */
	private Date createdAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column dictionary_trade_types.updated_at
	 * @mbggenerated
	 */
	private Date updatedAt;
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_trade_types.id
	 * @return  the value of dictionary_trade_types.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_trade_types.id
	 * @param id  the value for dictionary_trade_types.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_trade_types.trade_type
	 * @return  the value of dictionary_trade_types.trade_type
	 * @mbggenerated
	 */
	public Byte getTradeType() {
		return tradeType;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_trade_types.trade_type
	 * @param tradeType  the value for dictionary_trade_types.trade_type
	 * @mbggenerated
	 */
	public void setTradeType(Byte tradeType) {
		this.tradeType = tradeType;
	}
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_trade_types.trade_value
	 * @return  the value of dictionary_trade_types.trade_value
	 * @mbggenerated
	 */
	public String getTradeValue() {
		return tradeValue;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_trade_types.trade_value
	 * @param tradeValue  the value for dictionary_trade_types.trade_value
	 * @mbggenerated
	 */
	public void setTradeValue(String tradeValue) {
		this.tradeValue = tradeValue;
	}
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_trade_types.created_at
	 * @return  the value of dictionary_trade_types.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_trade_types.created_at
	 * @param createdAt  the value for dictionary_trade_types.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column dictionary_trade_types.updated_at
	 * @return  the value of dictionary_trade_types.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column dictionary_trade_types.updated_at
	 * @param updatedAt  the value for dictionary_trade_types.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public static final byte TYPE_TRADE = 1;	// 交易类型
    public static final byte TYPE_OTHER = 2;	// 其他类型
    
    public static final int ID_TRADE = 1;//1 消费 2 转账 3 还款 4 话费充值 5 生活充值
    public static final int ID_TRANSFER = 2;
    public static final int ID_REPAY = 3;
    public static final int ID_PHONE_RECHARGE = 4;
    public static final int ID_LIFE_RECHAGE = 5;
}