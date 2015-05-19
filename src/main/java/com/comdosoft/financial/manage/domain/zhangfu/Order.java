package com.comdosoft.financial.manage.domain.zhangfu;

import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class Order {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.id
	 * @mbggenerated
	 */
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.order_number
	 * @mbggenerated
	 */
	private String orderNumber;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.customer_id
	 * @mbggenerated
	 */
	private Integer customerId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.total_price
	 * @mbggenerated
	 */
	private Integer totalPrice;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.status
	 * @mbggenerated
	 */
	private Byte status;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.payed_at
	 * @mbggenerated
	 */
	private Date payedAt;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.types
	 * @mbggenerated
	 */
	private Byte types;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.customer_address_id
	 * @mbggenerated
	 */
	private Integer customerAddressId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.need_invoice
	 * @mbggenerated
	 */
	private Boolean needInvoice;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.actual_price
	 * @mbggenerated
	 */
	private Integer actualPrice;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.front_money
	 * @mbggenerated
	 */
	private Integer frontMoney;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.front_pay_status
	 * @mbggenerated
	 */
	private Byte frontPayStatus;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.pay_status
	 * @mbggenerated
	 */
	private Byte payStatus;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.created_user_id
	 * @mbggenerated
	 */
	private Integer createdUserId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.created_user_type
	 * @mbggenerated
	 */
	private Byte createdUserType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.process_user_id
	 * @mbggenerated
	 */
	private Integer processUserId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.process_user_name
	 * @mbggenerated
	 */
	private String processUserName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.created_at
	 * @mbggenerated
	 */
	private Date createdAt;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.updated_at
	 * @mbggenerated
	 */
	private Date updatedAt;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.belongs_to
	 * @mbggenerated
	 */
	private Integer belongsTo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.invoice_type
	 * @mbggenerated
	 */
	private Integer invoiceType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.comment
	 * @mbggenerated
	 */
	private String comment;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.total_quantity
	 * @mbggenerated
	 */
	private Integer totalQuantity;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.belongs_user_id
	 * @mbggenerated
	 */
	private Integer belongsUserId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column orders.invoice_info
	 * @mbggenerated
	 */
	private String invoiceInfo;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.id
	 * @return  the value of orders.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.id
	 * @param id  the value for orders.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.order_number
	 * @return  the value of orders.order_number
	 * @mbggenerated
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.order_number
	 * @param orderNumber  the value for orders.order_number
	 * @mbggenerated
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.customer_id
	 * @return  the value of orders.customer_id
	 * @mbggenerated
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.customer_id
	 * @param customerId  the value for orders.customer_id
	 * @mbggenerated
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.total_price
	 * @return  the value of orders.total_price
	 * @mbggenerated
	 */
	public Integer getTotalPrice() {
		return totalPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.total_price
	 * @param totalPrice  the value for orders.total_price
	 * @mbggenerated
	 */
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.status
	 * @return  the value of orders.status
	 * @mbggenerated
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.status
	 * @param status  the value for orders.status
	 * @mbggenerated
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.payed_at
	 * @return  the value of orders.payed_at
	 * @mbggenerated
	 */
	public Date getPayedAt() {
		return payedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.payed_at
	 * @param payedAt  the value for orders.payed_at
	 * @mbggenerated
	 */
	public void setPayedAt(Date payedAt) {
		this.payedAt = payedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.types
	 * @return  the value of orders.types
	 * @mbggenerated
	 */
	public Byte getTypes() {
		return types;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.types
	 * @param types  the value for orders.types
	 * @mbggenerated
	 */
	public void setTypes(Byte types) {
		this.types = types;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.customer_address_id
	 * @return  the value of orders.customer_address_id
	 * @mbggenerated
	 */
	public Integer getCustomerAddressId() {
		return customerAddressId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.customer_address_id
	 * @param customerAddressId  the value for orders.customer_address_id
	 * @mbggenerated
	 */
	public void setCustomerAddressId(Integer customerAddressId) {
		this.customerAddressId = customerAddressId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.need_invoice
	 * @return  the value of orders.need_invoice
	 * @mbggenerated
	 */
	public Boolean getNeedInvoice() {
		return needInvoice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.need_invoice
	 * @param needInvoice  the value for orders.need_invoice
	 * @mbggenerated
	 */
	public void setNeedInvoice(Boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.actual_price
	 * @return  the value of orders.actual_price
	 * @mbggenerated
	 */
	public Integer getActualPrice() {
		return actualPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.actual_price
	 * @param actualPrice  the value for orders.actual_price
	 * @mbggenerated
	 */
	public void setActualPrice(Integer actualPrice) {
		this.actualPrice = actualPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.front_money
	 * @return  the value of orders.front_money
	 * @mbggenerated
	 */
	public Integer getFrontMoney() {
		return frontMoney;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.front_money
	 * @param frontMoney  the value for orders.front_money
	 * @mbggenerated
	 */
	public void setFrontMoney(Integer frontMoney) {
		this.frontMoney = frontMoney;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.front_pay_status
	 * @return  the value of orders.front_pay_status
	 * @mbggenerated
	 */
	public Byte getFrontPayStatus() {
		return frontPayStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.front_pay_status
	 * @param frontPayStatus  the value for orders.front_pay_status
	 * @mbggenerated
	 */
	public void setFrontPayStatus(Byte frontPayStatus) {
		this.frontPayStatus = frontPayStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.pay_status
	 * @return  the value of orders.pay_status
	 * @mbggenerated
	 */
	public Byte getPayStatus() {
		return payStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.pay_status
	 * @param payStatus  the value for orders.pay_status
	 * @mbggenerated
	 */
	public void setPayStatus(Byte payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.created_user_id
	 * @return  the value of orders.created_user_id
	 * @mbggenerated
	 */
	public Integer getCreatedUserId() {
		return createdUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.created_user_id
	 * @param createdUserId  the value for orders.created_user_id
	 * @mbggenerated
	 */
	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.created_user_type
	 * @return  the value of orders.created_user_type
	 * @mbggenerated
	 */
	public Byte getCreatedUserType() {
		return createdUserType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.created_user_type
	 * @param createdUserType  the value for orders.created_user_type
	 * @mbggenerated
	 */
	public void setCreatedUserType(Byte createdUserType) {
		this.createdUserType = createdUserType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.process_user_id
	 * @return  the value of orders.process_user_id
	 * @mbggenerated
	 */
	public Integer getProcessUserId() {
		return processUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.process_user_id
	 * @param processUserId  the value for orders.process_user_id
	 * @mbggenerated
	 */
	public void setProcessUserId(Integer processUserId) {
		this.processUserId = processUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.process_user_name
	 * @return  the value of orders.process_user_name
	 * @mbggenerated
	 */
	public String getProcessUserName() {
		return processUserName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.process_user_name
	 * @param processUserName  the value for orders.process_user_name
	 * @mbggenerated
	 */
	public void setProcessUserName(String processUserName) {
		this.processUserName = processUserName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.created_at
	 * @return  the value of orders.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.created_at
	 * @param createdAt  the value for orders.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.updated_at
	 * @return  the value of orders.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.updated_at
	 * @param updatedAt  the value for orders.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.belongs_to
	 * @return  the value of orders.belongs_to
	 * @mbggenerated
	 */
	public Integer getBelongsTo() {
		return belongsTo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.belongs_to
	 * @param belongsTo  the value for orders.belongs_to
	 * @mbggenerated
	 */
	public void setBelongsTo(Integer belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.invoice_type
	 * @return  the value of orders.invoice_type
	 * @mbggenerated
	 */
	public Integer getInvoiceType() {
		return invoiceType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.invoice_type
	 * @param invoiceType  the value for orders.invoice_type
	 * @mbggenerated
	 */
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.comment
	 * @return  the value of orders.comment
	 * @mbggenerated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.comment
	 * @param comment  the value for orders.comment
	 * @mbggenerated
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.total_quantity
	 * @return  the value of orders.total_quantity
	 * @mbggenerated
	 */
	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.total_quantity
	 * @param totalQuantity  the value for orders.total_quantity
	 * @mbggenerated
	 */
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.belongs_user_id
	 * @return  the value of orders.belongs_user_id
	 * @mbggenerated
	 */
	public Integer getBelongsUserId() {
		return belongsUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.belongs_user_id
	 * @param belongsUserId  the value for orders.belongs_user_id
	 * @mbggenerated
	 */
	public void setBelongsUserId(Integer belongsUserId) {
		this.belongsUserId = belongsUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column orders.invoice_info
	 * @return  the value of orders.invoice_info
	 * @mbggenerated
	 */
	public String getInvoiceInfo() {
		return invoiceInfo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column orders.invoice_info
	 * @param invoiceInfo  the value for orders.invoice_info
	 * @mbggenerated
	 */
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	/**
	 * 用户订单
	 */
	public static final byte TYPE_CUSTOMER = 0;
	
	private Customer customer;
	private CustomerAddress customerAddress;
	private Agent agent;
	private Factory factory; 
	private Agent belongsAgent;
	
	private List<OrderGood> orderGoods;
//	private List<PayChannel> channels;
//	private List<GoodsPicture> pictures;
	private List<Good> goods;
	private List<OrderPayment> orderPayments;
	private List<OrderMark> orderMarks;
	private Integer orderPaymentTotal;
	
	private List<CsOutStorage> csOutStorages;
	private Integer totalOutQuantity;
	
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderGood> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGood> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public List<Good> getGoods() {
		return goods;
	}

	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}

	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public List<OrderPayment> getOrderPayments() {
		return orderPayments;
	}

	public void setOrderPayments(List<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}

	public List<OrderMark> getOrderMarks() {
		return orderMarks;
	}

	public void setOrderMarks(List<OrderMark> orderMarks) {
		this.orderMarks = orderMarks;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Integer getOrderPaymentTotal() {
		orderPaymentTotal=0;
		orderPayments=getOrderPayments();
		if(!CollectionUtils.isEmpty(orderPayments)){
			for(OrderPayment orderPayment:orderPayments){
				orderPaymentTotal+=orderPayment.getPrice();
			}
		}
		return orderPaymentTotal;
	}

	public void setOrderPaymentTotal(Integer orderPaymentTotal) {
		this.orderPaymentTotal = orderPaymentTotal;
	}

	public List<CsOutStorage> getCsOutStorages() {
		return csOutStorages;
	}

	public void setCsOutStorages(List<CsOutStorage> csOutStorages) {
		this.csOutStorages = csOutStorages;
	}

	public Integer getTotalOutQuantity() {
		totalOutQuantity=0;
		csOutStorages=getCsOutStorages();
		if(!CollectionUtils.isEmpty(csOutStorages)){
			for(CsOutStorage csOutStorage:csOutStorages){
				totalOutQuantity+=csOutStorage.getQuantity();
			}
		}
		return totalOutQuantity;
	}

	public void setTotalOutQuantity(Integer totalOutQuantity) {
		this.totalOutQuantity = totalOutQuantity;
	}

	public Agent getBelongsAgent() {
		return belongsAgent;
	}

	public void setBelongsAgent(Agent belongsAgent) {
		this.belongsAgent = belongsAgent;
	}
	
	public static final byte TRADE_UNPAID = 1;//1未付款
	public static final byte TRADE_PAID = 2;//2已付款
	public static final byte TRADE_SUCCESS = 3;//3已完成,已发货
	public static final byte TRADE_RATED = 4;//4已评价
	public static final byte TRADE_CANCELED = 5;//5已取消
	public static final byte TRADE_CLOSE = 6;//6交易关闭
	

}