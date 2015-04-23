package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.mapper.zhangfu.*;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PayChannelService {

    @Value("${page.size}")
    private Integer pageSize;
    @Value("${filePath}")
    private String filePath ;
    @Autowired
    private PayChannelMapper payChannelMapper;
    @Autowired
    private SupportAreaMapper supportAreaMapper;
    @Autowired
    private PayChannelStandardRateMapper payChannelStandardRateMapper;
    @Autowired
    private PayChannelBillingCycleMapper payChannelBillingCycleMapper;
    @Autowired
    private SupportTradeTypeMapper supportTradeTypeMapper;
    @Autowired
    private OpeningRequirementMapper openingRequirementMapper;
    @Autowired
    private OpeningRequirementListMapper openingRequirementListMapper;
    @Autowired
    private OtherRequirementMapper otherRequirementMapper;
    @Autowired
    private DictionaryTradeTypeMapper dictionaryTradeTypeMapper;
    @Autowired
    private FactoryMapper factoryMapper;
    @Autowired
    private CustomerMapper customerMapper;

    public List<PayChannel> findCheckedChannelsLikeName(String name) {
        return payChannelMapper.selectByStatusAndName(PayChannel.STATUS_CHECKED, "%" + name + "%");
    }

    public List<PayChannel> findCheckedChannels() {
        return payChannelMapper.selectByStatusAndName(PayChannel.STATUS_CHECKED, null);
    }

    public Page<PayChannel> findPages(Integer customerId, int page, Byte status, String keys) {
        Integer factoryId = null;
        if(customerId != null){
            Factory factory = factoryMapper.findFactoryByCustomerId(customerId);
            if(factory != null) {
                factoryId = factory.getId();
            } else {
                Customer customer = customerMapper.selectByPrimaryKey(customerId);
                if (customer.getTypes() == Customer.TYPE_THIRD_PARTY){
                    factoryId = 0;
                }
            }
        }

        if (keys != null) {
            keys = "%" + keys + "%";
        }
        long count = payChannelMapper.countByKeys(factoryId, status, keys);
        if (count == 0) {
            return new Page<>(new PageRequest(1, pageSize), new ArrayList<PayChannel>(), count);
        }

        PageRequest request = new PageRequest(page, pageSize);
        List<PayChannel> result = payChannelMapper.findPageChannelsByKeys(request, factoryId, status, keys);
        Page<PayChannel> channels = new Page<>(request, result, count);
        if (channels.getCurrentPage() > channels.getTotalPage()) {
            request = new PageRequest(channels.getTotalPage(), pageSize);
            result = payChannelMapper.findPageChannelsByKeys(request, factoryId, status, keys);
            channels = new Page<>(request, result, count);
        }
        return channels;
    }

    /**
     * 初审不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public PayChannel statusFirstUnCheck(Integer id) {
        PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
        if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK) {
            channel.setStatus(PayChannel.STATUS_FIRST_UN_CHECKED);
            payChannelMapper.updateByPrimaryKey(channel);
        }
        return channel;
    }

    /**
     * 初审通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public PayChannel statusFirstCheck(Integer id) {
        PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
        if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK
                || channel.getStatus() == PayChannel.STATUS_FIRST_UN_CHECKED) {
            channel.setStatus(PayChannel.STATUS_FIRST_CHECKED);
            payChannelMapper.updateByPrimaryKey(channel);
        }
        return channel;
    }

    /**
     * 审核不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public PayChannel statusUnCheck(Integer id) {
        PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
        if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK
                || channel.getStatus() == PayChannel.STATUS_FIRST_CHECKED) {
            channel.setStatus(PayChannel.STATUS_UN_CHECKED);
            payChannelMapper.updateByPrimaryKey(channel);
        }
        return channel;
    }

    /**
     * 审核通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public PayChannel statusCheck(Integer id) {
        PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
        if (channel.getStatus() == PayChannel.STATUS_WAITING_FIRST_CHECK
                || channel.getStatus() == PayChannel.STATUS_FIRST_UN_CHECKED
                || channel.getStatus() == PayChannel.STATUS_FIRST_CHECKED
                || channel.getStatus() == PayChannel.STATUS_UN_CHECKED) {
            channel.setStatus(PayChannel.STATUS_CHECKED);
            payChannelMapper.updateByPrimaryKey(channel);
        }
        return channel;
    }

    /**
     * 停止
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public PayChannel statusStop(Integer id) {
        PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
        if (channel.getStatus() == PayChannel.STATUS_CHECKED) {
            channel.setStatus(PayChannel.STATUS_STOP);
            payChannelMapper.updateByPrimaryKey(channel);
        }
        return channel;
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public PayChannel statusWaitingFirstCheck(Integer id) {
        PayChannel channel = payChannelMapper.selectByPrimaryKey(id);
        if (channel.getStatus() == PayChannel.STATUS_STOP) {
            channel.setStatus(PayChannel.STATUS_WAITING_FIRST_CHECK);
            payChannelMapper.updateByPrimaryKey(channel);
        }
        return channel;
    }

    public PayChannel findChannelInfo(Integer id) {
        PayChannel channel = payChannelMapper.findChannelLazyInfo(id);
        List<OtherRequirement> l= channel.getCancelRequirements();
        for (OtherRequirement otherRequirement : l) {
            otherRequirement.setTempletFilePath(filePath+otherRequirement.getTempletFilePath());
        }
        List<OtherRequirement> l2= channel.getUpdateRequirements();
        for (OtherRequirement otherRequirement : l2) {
            otherRequirement.setTempletFilePath(filePath+otherRequirement.getTempletFilePath());
        }
        channel.getFactory().setLogoFilePath(filePath+channel.getFactory().getLogoFilePath());
        return channel;
    }

    @Transactional("transactionManager")
    public void update(Integer id, String name, Integer factoryId, Integer supportType,
                       Integer[] regions, Boolean supportCancel,
                       List<Map<String, Object>> standardRates, List<Map<String, Object>> billingCycles, List<Map<String, Object>> tradeTypes,
                       Float openingCost, Boolean preliminaryVerify, String openingRequirement, String openingDatum, String openingProtocol,
                       List<Map<String, Object>> openingRequirements, List<Map<String, Object>> cancelRequirements,
                       List<Map<String, Object>> updateRequirements) {

        PayChannel channel = payChannelMapper.findChannelLazyInfo(id);
        channel.setName(name);
        channel.setFactoryId(factoryId);
        channel.setSupportCancelFlag(supportCancel);
        if (openingCost == null){
            channel.setOpeningCost(null);
        } else {
           channel.setOpeningCost((int)(openingCost*100));
        }
        channel.setNeedPreliminaryVerify(preliminaryVerify);
        channel.setOpeningRequirement(openingRequirement);
        channel.setOpeningDatum(openingDatum);
        channel.setOpeningProtocol(openingProtocol);
        //支持区域
        supportAreaMapper.deleteChannelAreas(id);
        
        if(supportType == 2){
        	for (Integer regionId : regions){
                SupportArea supportArea = new SupportArea();
                supportArea.setPayChannelId(id);
                supportArea.setCityId(regionId);
                supportAreaMapper.insert(supportArea);
            }
            channel.setSupportType(false);
        } else if (supportType == 1){
        	for (Integer regionId : regions){
                SupportArea supportArea = new SupportArea();
                supportArea.setPayChannelId(id);
                supportArea.setCityId(regionId);
                supportAreaMapper.insert(supportArea);
            }
            channel.setSupportType(true);
        } else {
            SupportArea supportArea = new SupportArea();
            supportArea.setPayChannelId(id);
            supportArea.setCityId(0);
            supportAreaMapper.insert(supportArea);
            channel.setSupportType(true);
        }
        payChannelMapper.updateByPrimaryKey(channel);
        //刷卡交易标准手续费
        payChannelStandardRateMapper.deletePayChannelStandardRates(id);
        for (Map<String, Object> standardRateObject : standardRates){
            //id: id,rate: rate,description: description
            Object tradeStandardRateId = standardRateObject.get("id");
            if(tradeStandardRateId != null){
                PayChannelStandardRate payChannelStandardRate = new PayChannelStandardRate();
                payChannelStandardRate.setPayChannelId(id);
                payChannelStandardRate.setTradeStandardRateId(Integer.parseInt(tradeStandardRateId.toString()));
                Object standardRate = standardRateObject.get("rate");
                Object description = standardRateObject.get("description");
                payChannelStandardRate.setStandardRate(standardRate == null||standardRate.equals("") ? null:Integer.parseInt(standardRate.toString()));
                payChannelStandardRate.setDescription(description == null ? null : (String) description);
                payChannelStandardRate.setCreatedAt(new Date());
                payChannelStandardRateMapper.insert(payChannelStandardRate);
            }
        }

        //资金服务费
        payChannelBillingCycleMapper.deletePayChannelBillingCycles(id);
        for (Map<String, Object> payChannelBillingObject : billingCycles){
            //id: id,rate: rate,description: description
            Object billingCyclesId = payChannelBillingObject.get("id");
            if(billingCyclesId != null){
                PayChannelBillingCycle payChannelBillingCycle = new PayChannelBillingCycle();
                payChannelBillingCycle.setPayChannelId(id);
                payChannelBillingCycle.setBillingCyclesId(Integer.parseInt(billingCyclesId.toString()));
                Object rate = payChannelBillingObject.get("rate");
                Object description = payChannelBillingObject.get("description");
                payChannelBillingCycle.setRate(rate == null||rate.equals("") ? null : Integer.parseInt(rate.toString()));
                payChannelBillingCycle.setDescription(description == null ? null : (String) description);
                payChannelBillingCycleMapper.insert(payChannelBillingCycle);
            }
        }

        //其他交易类型
        List<SupportTradeType> supportTradeTypes = channel.getSupportTradeTypes();
        Map<String, SupportTradeType> supportTradeTypeMap = new HashMap<>();
        List<SupportTradeType> deleteSupportTradeTypes = new ArrayList<>();
        for (SupportTradeType supportTradeType : supportTradeTypes) {
            supportTradeTypeMap.put(supportTradeType.getTradeTypeId()+"", supportTradeType);
            if(supportTradeType.getTradeType() == SupportTradeType.TYPE_OTHER){
                deleteSupportTradeTypes.add(supportTradeType);
            }
        }
        for (int i = 0; i < tradeTypes.size() ; i++) {
            Map<String, Object> tradeTypeObject = tradeTypes.get(i);
            //id: id,rate: rate,description: description
            Object tradeTypeId = tradeTypeObject.get("id");
            if(tradeTypeId != null){
                SupportTradeType supportTradeType = supportTradeTypeMap.get(tradeTypeId);
                if(supportTradeType == null){
                    supportTradeType = new SupportTradeType();
                    supportTradeType.setTradeTypeId(Integer.parseInt(tradeTypeId.toString()));
                    supportTradeType.setPayChannelId(id);
                    supportTradeType.setTradeType(SupportTradeType.TYPE_OTHER);
                    supportTradeTypeMapper.insert(supportTradeType);
                } else {
                    deleteSupportTradeTypes.remove(supportTradeType);
                }
                supportTradeType.setSortIndex(i+1);
                Object rate = tradeTypeObject.get("rate");
                Object description = tradeTypeObject.get("description");
                supportTradeType.setTerminalRate(rate == null||rate.equals("") ? null : Integer.parseInt(rate.toString()));
                supportTradeType.setDescription(description == null ? null : (String) description);
                supportTradeTypeMapper.updateByPrimaryKey(supportTradeType);
            }
        }
        for (SupportTradeType supportTradeType : deleteSupportTradeTypes){
            supportTradeTypeMapper.deleteByPrimaryKey(supportTradeType.getId());
        }

        //开通所需材料
        List<OpeningRequirement> openingRequirementList = channel.getOpeningRequirements();
        Map<String, OpeningRequirement> openingRequirementsMap = new HashMap<>();
        List<OpeningRequirement> deleteOpeningRequirements = new ArrayList<>();
        for (OpeningRequirement oRequirement : openingRequirementList) {
            openingRequirementsMap.put(oRequirement.getId()+"", oRequirement);
            deleteOpeningRequirements.add(oRequirement);
        }
        for (Map<String, Object> openingRequirementObject : openingRequirements){
            //id: id, title: title, description: description, publicRequirements: publicRequirements, privateRequirements: privateRequirements
            Object openingRequirementId = openingRequirementObject.get("id");
            String hasVideoVerify = (String)openingRequirementObject.get("hasVideoVerify");
            Object title = openingRequirementObject.get("title");
            Object description = openingRequirementObject.get("description");
            Object publicRequirements = openingRequirementObject.get("publicRequirements");
            Object privateRequirements = openingRequirementObject.get("privateRequirements");
            OpeningRequirement requirement = openingRequirementsMap.get(openingRequirementId);
            if(requirement != null) {
                deleteOpeningRequirements.remove(requirement);
                requirement.setHasVideoVerify(Boolean.valueOf(hasVideoVerify));
                requirement.setLevelTitle(title == null ? null : (String) title);
                requirement.setLevelDescription(description == null ? null : (String) description);
                openingRequirementMapper.updateByPrimaryKey(requirement);
            } else {
                requirement = new OpeningRequirement();
                requirement.setPayChannelId(id);
                requirement.setHasVideoVerify(Boolean.valueOf(hasVideoVerify));
                requirement.setLevelTitle(title == null ? null : (String) title);
                requirement.setLevelDescription(description == null ? null : (String) description);
                openingRequirementMapper.insert(requirement);
            }
            openingRequirementListMapper.deleteOpeningRequirementLists(requirement.getId());
            if(publicRequirements != null){
                for (String settingId : (List<String>)publicRequirements){
                    OpeningRequirementList or = new OpeningRequirementList();
                    or.setOpeningRequirementsId(requirement.getId());
                    or.setRequirementSettingId(Integer.parseInt(settingId));
                    or.setRequirementType(OpeningRequirementList.TYPE_PUBLIC);
                    openingRequirementListMapper.insert(or);
                }
            }
            if(privateRequirements != null){
                for (String settingId : (List<String>)privateRequirements){
                    OpeningRequirementList or = new OpeningRequirementList();
                    or.setOpeningRequirementsId(requirement.getId());
                    or.setRequirementSettingId(Integer.parseInt(settingId));
                    or.setRequirementType(OpeningRequirementList.TYPE_PRIVATE);
                    openingRequirementListMapper.insert(or);
                }
            }
        }
        for (OpeningRequirement requirement : deleteOpeningRequirements){
            openingRequirementMapper.deleteByPrimaryKey(requirement.getId());
            openingRequirementListMapper.deleteOpeningRequirementLists(requirement.getId());
        }

        otherRequirementMapper.deleteOtherRequirements(id);
        //注销所需材料
        for (Map<String, Object> cancelRequirementObject : cancelRequirements){
            //name: name, description: description, fileUrl: fileUrl
            OtherRequirement otherRequirement = new OtherRequirement();
            otherRequirement.setPayChannelId(id);
            Object title = cancelRequirementObject.get("title");
            Object description = cancelRequirementObject.get("description");
            //Object fileUrl = cancelRequirementObject.get("fileUrl");
            String fileUrl = cancelRequirementObject.get("fileUrl").toString().replaceAll(filePath, "");
            otherRequirement.setTitle(title == null ? null : (String) title);
            otherRequirement.setDescription(description == null ? null : (String) description);
            otherRequirement.setTempletFilePath(fileUrl == null ? null :  fileUrl);
            otherRequirement.setTypes(OtherRequirement.TYPE_CANCEL);
            otherRequirementMapper.insert(otherRequirement);
        }
        //更新所需材料
        for (Map<String, Object> updateRequirementObject : updateRequirements){
            //name: name, description: description, fileUrl: fileUrl
            OtherRequirement otherRequirement = new OtherRequirement();
            otherRequirement.setPayChannelId(id);
            Object title = updateRequirementObject.get("title");
            Object description = updateRequirementObject.get("description");
           // Object fileUrl = updateRequirementObject.get("fileUrl");
            String fileUrl = updateRequirementObject.get("fileUrl").toString().replaceAll(filePath, "");
            otherRequirement.setTitle(title == null ? null : (String) title);
            otherRequirement.setDescription(description == null ? null : (String) description);
            otherRequirement.setTempletFilePath(fileUrl == null ? null :  fileUrl);
            otherRequirement.setTypes(OtherRequirement.TYPE_UPDATE);
            otherRequirementMapper.insert(otherRequirement);
        }
    }

    @Transactional("transactionManager")
    public void create(Integer createUserId, Byte userType, String name, Integer factoryId, Integer supportType,
                       Integer[] regions, Boolean supportCancel,
                       List<Map<String, Object>> standardRates, List<Map<String, Object>> billingCycles, List<Map<String, Object>> tradeTypes,
                       Float openingCost, Boolean preliminaryVerify, String openingRequirement, String openingDatum, String openingProtocol,
                       List<Map<String, Object>> openingRequirements, List<Map<String, Object>> cancelRequirements,
                       List<Map<String, Object>> updateRequirements) {
        PayChannel channel = new PayChannel();
        channel.setName(name);
        channel.setFactoryId(factoryId);
        channel.setSupportCancelFlag(supportCancel);
        if (openingCost == null){
            channel.setOpeningCost(null);
        } else {
            channel.setOpeningCost((int)(openingCost*100));
        }
        channel.setNeedPreliminaryVerify(preliminaryVerify);
        channel.setOpeningRequirement(openingRequirement);
        channel.setOpeningDatum(openingDatum);
        channel.setOpeningProtocol(openingProtocol);
        payChannelMapper.insert(channel);
        Integer id = channel.getId();
        //支持区域
        if(supportType == 2){
            for (Integer regionId : regions){
                SupportArea supportArea = new SupportArea();
                supportArea.setPayChannelId(id);
                supportArea.setCityId(regionId);
                supportAreaMapper.insert(supportArea);
            }
            channel.setSupportType(false);
        } else if (supportType == 1){
            for (Integer regionId : regions){
                SupportArea supportArea = new SupportArea();
                supportArea.setPayChannelId(id);
                supportArea.setCityId(regionId);
                supportAreaMapper.insert(supportArea);
            }
            channel.setSupportType(true);
        } else {
            SupportArea supportArea = new SupportArea();
            supportArea.setPayChannelId(id);
            supportArea.setCityId(0);
            supportAreaMapper.insert(supportArea);
            channel.setSupportType(true);
        }
        channel.setStatus(PayChannel.STATUS_WAITING_FIRST_CHECK);
        channel.setCreatedUserId(createUserId);
        channel.setCreatedUserType(userType);
        channel.setCreatedAt(new Date());
        payChannelMapper.updateByPrimaryKey(channel);
        //刷卡交易标准手续费
        for (Map<String, Object> standardRateObject : standardRates){
            //id: id,rate: rate,description: description
            Object tradeStandardRateId = standardRateObject.get("id");
            if(tradeStandardRateId != null){
                PayChannelStandardRate payChannelStandardRate = new PayChannelStandardRate();
                payChannelStandardRate.setPayChannelId(id);
                payChannelStandardRate.setTradeStandardRateId(Integer.parseInt(tradeStandardRateId.toString()));
                Object standardRate = standardRateObject.get("rate");
                Object description = standardRateObject.get("description");
                payChannelStandardRate.setStandardRate(standardRate == null||standardRate.equals("") ? null:Integer.parseInt(standardRate.toString()));
                payChannelStandardRate.setDescription(description == null ? null : (String) description);
                payChannelStandardRate.setCreatedAt(new Date());
                payChannelStandardRateMapper.insert(payChannelStandardRate);
            }
        }

        //资金服务费
        for (Map<String, Object> payChannelBillingObject : billingCycles){
            //id: id,rate: rate,description: description
            Object billingCyclesId = payChannelBillingObject.get("id");
            if(billingCyclesId != null){
                PayChannelBillingCycle payChannelBillingCycle = new PayChannelBillingCycle();
                payChannelBillingCycle.setPayChannelId(id);
                payChannelBillingCycle.setBillingCyclesId(Integer.parseInt(billingCyclesId.toString()));
                Object rate = payChannelBillingObject.get("rate");
                Object description = payChannelBillingObject.get("description");
                payChannelBillingCycle.setRate(rate == null||rate.equals("") ? null : Integer.parseInt(rate.toString()));
                payChannelBillingCycle.setDescription(description == null ? null : (String) description);
                payChannelBillingCycleMapper.insert(payChannelBillingCycle);
            }
        }

        //其他交易类型
        DictionaryTradeType dictionaryTradeType = dictionaryTradeTypeMapper.selectBaseTradeType();
        if (dictionaryTradeType != null){
            SupportTradeType baseSupportTradeType = new SupportTradeType();
            baseSupportTradeType.setTradeType(SupportTradeType.TYPE_TRADE);
            baseSupportTradeType.setPayChannelId(id);
            baseSupportTradeType.setTradeTypeId(dictionaryTradeType.getId());
            baseSupportTradeType.setSortIndex(0);
            supportTradeTypeMapper.insert(baseSupportTradeType);
        }

        for (int i = 0; i < tradeTypes.size() ; i++) {
            Map<String, Object> tradeTypeObject = tradeTypes.get(i);
            //id: id,rate: rate,description: description
            Object tradeTypeId = tradeTypeObject.get("id");
            if(tradeTypeId != null){
                SupportTradeType supportTradeType = new SupportTradeType();
                supportTradeType.setTradeTypeId(Integer.parseInt(tradeTypeId.toString()));
                supportTradeType.setPayChannelId(id);
                supportTradeType.setTradeType(SupportTradeType.TYPE_OTHER);
                supportTradeType.setSortIndex(i+1);
                Object rate = tradeTypeObject.get("rate");
                Object description = tradeTypeObject.get("description");
                supportTradeType.setTerminalRate(rate == null||rate.equals("") ? null : Integer.parseInt(rate.toString()));
                supportTradeType.setDescription(description == null ? null : (String) description);
                supportTradeTypeMapper.insert(supportTradeType);
            }
        }

        //开通所需材料
        for (Map<String, Object> openingRequirementObject : openingRequirements){
            //id: id, title: title, description: description, publicRequirements: publicRequirements, privateRequirements: privateRequirements
            Object openingRequirementId = openingRequirementObject.get("id");
            Object title = openingRequirementObject.get("title");
            Object description = openingRequirementObject.get("description");
            Object publicRequirements = openingRequirementObject.get("publicRequirements");
            Object privateRequirements = openingRequirementObject.get("privateRequirements");
            OpeningRequirement requirement = new OpeningRequirement();
            requirement.setPayChannelId(id);
            requirement.setLevelTitle(title == null ? null : (String) title);
            requirement.setLevelDescription(description == null ? null : (String) description);
            openingRequirementMapper.insert(requirement);
            if(publicRequirements != null){
                for (String settingId : (List<String>)publicRequirements){
                    OpeningRequirementList or = new OpeningRequirementList();
                    or.setOpeningRequirementsId(requirement.getId());
                    or.setRequirementSettingId(Integer.parseInt(settingId));
                    or.setRequirementType(OpeningRequirementList.TYPE_PUBLIC);
                    openingRequirementListMapper.insert(or);
                }
            }
            if(privateRequirements != null){
                for (String settingId : (List<String>)privateRequirements){
                    OpeningRequirementList or = new OpeningRequirementList();
                    or.setOpeningRequirementsId(requirement.getId());
                    or.setRequirementSettingId(Integer.parseInt(settingId));
                    or.setRequirementType(OpeningRequirementList.TYPE_PRIVATE);
                    openingRequirementListMapper.insert(or);
                }
            }
        }

        //注销所需材料
        for (Map<String, Object> cancelRequirementObject : cancelRequirements){
            //name: name, description: description, fileUrl: fileUrl
            OtherRequirement otherRequirement = new OtherRequirement();
            otherRequirement.setPayChannelId(id);
            Object title = cancelRequirementObject.get("title");
            Object description = cancelRequirementObject.get("description");
            String fileUrl = cancelRequirementObject.get("fileUrl").toString().replaceAll(filePath, "");
            otherRequirement.setTitle(title == null ? null : (String) title);
            otherRequirement.setDescription(description == null ? null : (String) description);
            otherRequirement.setTempletFilePath(fileUrl == null ? null : fileUrl );
            otherRequirement.setTypes(OtherRequirement.TYPE_CANCEL);
            otherRequirementMapper.insert(otherRequirement);
        }
        //更新所需材料
        for (Map<String, Object> updateRequirementObject : updateRequirements){
            //name: name, description: description, fileUrl: fileUrl
            OtherRequirement otherRequirement = new OtherRequirement();
            otherRequirement.setPayChannelId(id);
            Object title = updateRequirementObject.get("title");
            Object description = updateRequirementObject.get("description");
            String fileUrl = updateRequirementObject.get("fileUrl").toString().replaceAll(filePath, "");
            otherRequirement.setTitle(title == null ? null : (String) title);
            otherRequirement.setDescription(description == null ? null : (String) description);
            otherRequirement.setTempletFilePath(fileUrl == null ? null :  fileUrl);
            otherRequirement.setTypes(OtherRequirement.TYPE_UPDATE);
            otherRequirementMapper.insert(otherRequirement);
        }
    }
    
    @Transactional("transactionManager")
    public void updatePayChannelBillingCycle(Integer[] ids,Integer[] rates,Integer[] profits){
    	PayChannelBillingCycle payChannelBillingCycle = new PayChannelBillingCycle();
    	for(int i=0;i<ids.length;i++){
    		payChannelBillingCycle = payChannelBillingCycleMapper.selectByPrimaryKey(ids[i]);
    		payChannelBillingCycle.setRate(rates[i]);
    		payChannelBillingCycle.setProfit(profits[i]);
    		payChannelBillingCycleMapper.updateByPrimaryKey(payChannelBillingCycle);
    	}
    }

}
