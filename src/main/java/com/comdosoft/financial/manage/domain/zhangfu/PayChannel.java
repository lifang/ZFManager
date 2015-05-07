package com.comdosoft.financial.manage.domain.zhangfu;

import java.util.Date;
import java.util.List;

public class PayChannel {

    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.name
	 * @mbggenerated
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.status
	 * @mbggenerated
	 */
	private Byte status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.support_cancel_flag
	 * @mbggenerated
	 */
	private Boolean supportCancelFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.opening_cost
	 * @mbggenerated
	 */
	private Integer openingCost;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.need_preliminary_verify
	 * @mbggenerated
	 */
	private Boolean needPreliminaryVerify;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.factory_id
	 * @mbggenerated
	 */
	private Integer factoryId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.created_user_id
	 * @mbggenerated
	 */
	private Integer createdUserId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.created_user_type
	 * @mbggenerated
	 */
	private Byte createdUserType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.created_at
	 * @mbggenerated
	 */
	private Date createdAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.updated_at
	 * @mbggenerated
	 */
	private Date updatedAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.support_type
	 * @mbggenerated
	 */
	private Boolean supportType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.support_requirement_type
	 * @mbggenerated
	 */
	private Byte supportRequirementType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.opening_requirement
	 * @mbggenerated
	 */
	private String openingRequirement;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.opening_datum
	 * @mbggenerated
	 */
	private String openingDatum;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pay_channels.opening_protocol
	 * @mbggenerated
	 */
	private String openingProtocol;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.id
	 * @return  the value of pay_channels.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.id
	 * @param id  the value for pay_channels.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.name
	 * @return  the value of pay_channels.name
	 * @mbggenerated
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.name
	 * @param name  the value for pay_channels.name
	 * @mbggenerated
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.status
	 * @return  the value of pay_channels.status
	 * @mbggenerated
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.status
	 * @param status  the value for pay_channels.status
	 * @mbggenerated
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.support_cancel_flag
	 * @return  the value of pay_channels.support_cancel_flag
	 * @mbggenerated
	 */
	public Boolean getSupportCancelFlag() {
		return supportCancelFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.support_cancel_flag
	 * @param supportCancelFlag  the value for pay_channels.support_cancel_flag
	 * @mbggenerated
	 */
	public void setSupportCancelFlag(Boolean supportCancelFlag) {
		this.supportCancelFlag = supportCancelFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.opening_cost
	 * @return  the value of pay_channels.opening_cost
	 * @mbggenerated
	 */
	public Integer getOpeningCost() {
		return openingCost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.opening_cost
	 * @param openingCost  the value for pay_channels.opening_cost
	 * @mbggenerated
	 */
	public void setOpeningCost(Integer openingCost) {
		this.openingCost = openingCost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.need_preliminary_verify
	 * @return  the value of pay_channels.need_preliminary_verify
	 * @mbggenerated
	 */
	public Boolean getNeedPreliminaryVerify() {
		return needPreliminaryVerify;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.need_preliminary_verify
	 * @param needPreliminaryVerify  the value for pay_channels.need_preliminary_verify
	 * @mbggenerated
	 */
	public void setNeedPreliminaryVerify(Boolean needPreliminaryVerify) {
		this.needPreliminaryVerify = needPreliminaryVerify;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.factory_id
	 * @return  the value of pay_channels.factory_id
	 * @mbggenerated
	 */
	public Integer getFactoryId() {
		return factoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.factory_id
	 * @param factoryId  the value for pay_channels.factory_id
	 * @mbggenerated
	 */
	public void setFactoryId(Integer factoryId) {
		this.factoryId = factoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.created_user_id
	 * @return  the value of pay_channels.created_user_id
	 * @mbggenerated
	 */
	public Integer getCreatedUserId() {
		return createdUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.created_user_id
	 * @param createdUserId  the value for pay_channels.created_user_id
	 * @mbggenerated
	 */
	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.created_user_type
	 * @return  the value of pay_channels.created_user_type
	 * @mbggenerated
	 */
	public Byte getCreatedUserType() {
		return createdUserType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.created_user_type
	 * @param createdUserType  the value for pay_channels.created_user_type
	 * @mbggenerated
	 */
	public void setCreatedUserType(Byte createdUserType) {
		this.createdUserType = createdUserType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.created_at
	 * @return  the value of pay_channels.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.created_at
	 * @param createdAt  the value for pay_channels.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.updated_at
	 * @return  the value of pay_channels.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.updated_at
	 * @param updatedAt  the value for pay_channels.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.support_type
	 * @return  the value of pay_channels.support_type
	 * @mbggenerated
	 */
	public Boolean getSupportType() {
		return supportType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.support_type
	 * @param supportType  the value for pay_channels.support_type
	 * @mbggenerated
	 */
	public void setSupportType(Boolean supportType) {
		this.supportType = supportType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.support_requirement_type
	 * @return  the value of pay_channels.support_requirement_type
	 * @mbggenerated
	 */
	public Byte getSupportRequirementType() {
		return supportRequirementType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.support_requirement_type
	 * @param supportRequirementType  the value for pay_channels.support_requirement_type
	 * @mbggenerated
	 */
	public void setSupportRequirementType(Byte supportRequirementType) {
		this.supportRequirementType = supportRequirementType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.opening_requirement
	 * @return  the value of pay_channels.opening_requirement
	 * @mbggenerated
	 */
	public String getOpeningRequirement() {
		return openingRequirement;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.opening_requirement
	 * @param openingRequirement  the value for pay_channels.opening_requirement
	 * @mbggenerated
	 */
	public void setOpeningRequirement(String openingRequirement) {
		this.openingRequirement = openingRequirement;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.opening_datum
	 * @return  the value of pay_channels.opening_datum
	 * @mbggenerated
	 */
	public String getOpeningDatum() {
		return openingDatum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.opening_datum
	 * @param openingDatum  the value for pay_channels.opening_datum
	 * @mbggenerated
	 */
	public void setOpeningDatum(String openingDatum) {
		this.openingDatum = openingDatum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pay_channels.opening_protocol
	 * @return  the value of pay_channels.opening_protocol
	 * @mbggenerated
	 */
	public String getOpeningProtocol() {
		return openingProtocol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pay_channels.opening_protocol
	 * @param openingProtocol  the value for pay_channels.opening_protocol
	 * @mbggenerated
	 */
	public void setOpeningProtocol(String openingProtocol) {
		this.openingProtocol = openingProtocol;
	}

	public static final byte STATUS_WAITING_FIRST_CHECK = 1;    // 待审核
    public static final byte STATUS_FIRST_UN_CHECKED = 2;    // 初审不通过
    public static final byte STATUS_FIRST_CHECKED = 3;        // 初审通过
    public static final byte STATUS_UN_CHECKED = 4;            // 审核不通过
    public static final byte STATUS_CHECKED = 5;                // 正常
    public static final byte STATUS_STOP = 6;                // 已停用

    private Factory factory;
    private List<City> areas;
    private List<PayChannelStandardRate> standardRates;
    private List<PayChannelBillingCycle> billingCycles;
    private List<OpeningRequirement> openingRequirements;
    private List<OtherRequirement> cancelRequirements;
    private List<OtherRequirement> updateRequirements;
    private List<SupportTradeType> supportTradeTypes;

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public List<City> getAreas() {
        return areas;
    }

    public void setAreas(List<City> areas) {
        this.areas = areas;
    }

    public List<PayChannelStandardRate> getStandardRates() {
        return standardRates;
    }

    public void setStandardRates(List<PayChannelStandardRate> standardRates) {
        this.standardRates = standardRates;
    }

    public List<PayChannelBillingCycle> getBillingCycles() {
        return billingCycles;
    }

    public void setBillingCycles(List<PayChannelBillingCycle> billingCycles) {
        this.billingCycles = billingCycles;
    }

    public List<OpeningRequirement> getOpeningRequirements() {
        return openingRequirements;
    }

    public void setOpeningRequirements(List<OpeningRequirement> openingRequirements) {
        this.openingRequirements = openingRequirements;
    }

    public List<OtherRequirement> getCancelRequirements() {
        return cancelRequirements;
    }

    public void setCancelRequirements(List<OtherRequirement> cancelRequirements) {
        this.cancelRequirements = cancelRequirements;
    }

    public List<OtherRequirement> getUpdateRequirements() {
        return updateRequirements;
    }

    public void setUpdateRequirements(List<OtherRequirement> updateRequirements) {
        this.updateRequirements = updateRequirements;
    }

    public List<SupportTradeType> getSupportTradeTypes() {
        return supportTradeTypes;
    }

    public void setSupportTradeTypes(List<SupportTradeType> supportTradeTypes) {
        this.supportTradeTypes = supportTradeTypes;
    }
}