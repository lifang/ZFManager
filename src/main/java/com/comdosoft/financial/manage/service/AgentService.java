package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.domain.zhangfu.AgentProfitSetting;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.mapper.zhangfu.AgentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.AgentProfitSettingMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.DictionaryTradeTypeMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by junxi.qu on 2015/3/10.
 */
@Service
public class AgentService {

    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentProfitSettingMapper agentProfitSettingMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private DictionaryTradeTypeMapper dictionaryTradeTypeMapper;
    public Page<Agent> findPages(int page, Byte status, String keys){
        if (keys != null) {
            keys = "%"+keys+"%";
        }
        long count = agentMapper.countByKeys(status, keys);
        if (count == 0) {
            return new Page<Agent>(new PageRequest(1, pageSize), new ArrayList<Agent>(), count);
        }

        PageRequest request = new PageRequest(page, pageSize);
        List<Agent> result = agentMapper.findPageAgentByKeys(request, status, keys);
        Page<Agent> agent = new Page<>(request, result, count);
        if (agent.getCurrentPage() > agent.getTotalPage()) {
            request = new PageRequest(agent.getTotalPage(), pageSize);
            result = agentMapper.findPageAgentByKeys(request, status, keys);
            agent = new Page<>(request, result, count);
        }
        return agent;
    }
    
    public Page<Agent> findPages(int page, Byte status, String keys,Integer pageSize){
        if (keys != null) {
            keys = "%"+keys+"%";
        }
        long count = agentMapper.countByKeys(status, keys);
        if (count == 0) {
            return new Page<Agent>(new PageRequest(1, pageSize), new ArrayList<Agent>(), count);
        }

        PageRequest request = new PageRequest(page, pageSize);
        List<Agent> result = agentMapper.findPageAgentByKeys(request, status, keys);
        Page<Agent> agent = new Page<>(request, result, count);
        if (agent.getCurrentPage() > agent.getTotalPage()) {
            request = new PageRequest(agent.getTotalPage(), pageSize);
            result = agentMapper.findPageAgentByKeys(request, status, keys);
            agent = new Page<>(request, result, count);
        }
        return agent;
    }

    public Agent findAgentInfo(Integer id){
        return agentMapper.findAgentInfo(id);
    }

    @Transactional("transactionManager")
    public boolean update(Integer id, Integer types, String name, String cardId,
                       String companyName, String businessLicense, String phone, String email,
                       Integer cityId, String address, String username, String password, Byte accountType) {
        Agent agent = agentMapper.findAgentInfo(id);
        agent.setTypes(types);
        agent.setName(name);
        agent.setCardId(cardId);
        agent.setCompanyName(companyName);
        agent.setBusinessLicense(businessLicense);
        agent.setPhone(phone);
        agent.setEmail(email);
        agent.setAddress(address);
        Customer customer = agent.getCustomer();
        if (!customer.getUsername().equals(username)){
            Customer customer1 = customerMapper.selectByUsername(username);
            if (customer1 == null){
                customer.setUsername(username);
                customer.setAccountType(accountType);
            }else{
                return false;
            }
        }
        if (password != null && !password.equals("")){
            String md5Password = DigestUtils.md5Hex(password);
            customer.setPassword(md5Password);
        }
        customer.setCityId(cityId);
        customerMapper.updateByPrimaryKey(customer);
        agentMapper.updateByPrimaryKey(agent);
        return  true;
    }

    @Transactional("transactionManager")
    public boolean create(Integer types, String name, String cardId,
                          String companyName, String businessLicense, String phone, String email,
                          Integer cityId, String address, String username, String password, Byte accountType) {
        Customer customer = customerMapper.selectByUsername(username);
        if (customer != null){
            return false;
        }
        customer = new Customer();
        customer.setUsername(username);
        String md5Password = DigestUtils.md5Hex(password);
        customer.setPassword(md5Password);
        customer.setTypes(Customer.TYPE_AGENT);
        customer.setCityId(cityId);
        customer.setPhone(phone);
        customer.setIntegral(0);
        customer.setStatus(Customer.STATUS_NORMAL);
        customer.setAccountType(accountType);
        customer.setCreatedAt(new Date());
        customerMapper.insert(customer);

        Agent agent = new Agent();
        agent.setCustomerId(customer.getId());
        agent.setStatus(Agent.STATUS_WAITING_FIRST_CHECK);
        agent.setTypes(types);
        agent.setName(name);
        agent.setCardId(cardId);
        agent.setCompanyName(companyName);
        agent.setBusinessLicense(businessLicense);
        agent.setPhone(phone);
        agent.setEmail(email);
        agent.setAddress(address);
        String code = agentMapper.findMaxOneLevelAgentCode();
        if (code == null || code.equals("")){
            code = "001";
        } else{
            code = String.format("%03d", Integer.parseInt(code)+1);
        }
        agent.setParentId(0);
        agent.setCode(code);
        agent.setIsHaveProfit(true);
        agent.setFormTypes(Agent.FORMATTYPE_OP);
        agent.setCreatedAt(new Date());
        agent.setUpdatedAt(new Date());
        agentMapper.insert(agent);
        return true;
    }

    /**
     * 初审不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Agent statusFirstUnCheck(Integer id) {
        Agent agent = agentMapper.findAgentInfo(id);
        if (agent.getStatus() == Agent.STATUS_WAITING_FIRST_CHECK) {
            agent.setStatus(Agent.STATUS_FIRST_UN_CHECKED);
            agentMapper.updateByPrimaryKey(agent);
        }
        return agent;
    }

    /**
     * 初审通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Agent statusFirstCheck(Integer id) {
        Agent agent = agentMapper.findAgentInfo(id);
        if (agent.getStatus() == Agent.STATUS_WAITING_FIRST_CHECK
                || agent.getStatus() == Agent.STATUS_FIRST_UN_CHECKED) {
            agent.setStatus(Agent.STATUS_FIRST_CHECKED);
            agentMapper.updateByPrimaryKey(agent);
        }
        return agent;
    }

    /**
     * 审核不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Agent statusUnCheck(Integer id) {
        Agent agent = agentMapper.findAgentInfo(id);
        if (agent.getStatus() == Agent.STATUS_WAITING_FIRST_CHECK
                || agent.getStatus() == Agent.STATUS_FIRST_CHECKED) {
            agent.setStatus(Agent.STATUS_UN_CHECKED);
            agentMapper.updateByPrimaryKey(agent);
        }
        return agent;
    }

    /**
     * 审核通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Agent statusCheck(Integer id) {
        Agent agent = agentMapper.findAgentInfo(id);
        if (agent.getStatus() == Agent.STATUS_WAITING_FIRST_CHECK
                || agent.getStatus() == Agent.STATUS_FIRST_UN_CHECKED
                || agent.getStatus() == Agent.STATUS_FIRST_CHECKED
                || agent.getStatus() == Agent.STATUS_UN_CHECKED) {
            agent.setStatus(Agent.STATUS_CHECKED);
            agentMapper.updateByPrimaryKey(agent);
            Customer customer = agent.getCustomer();
            customer.setStatus(Customer.STATUS_NORMAL);
            customerMapper.updateByPrimaryKey(customer);
        }
        return agent;
    }

    /**
     * 停止
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Agent statusStop(Integer id) {
        Agent agent = agentMapper.findAgentInfo(id);
        if (agent.getStatus() == Agent.STATUS_CHECKED) {
            agent.setStatus(Agent.STATUS_STOP);
            agentMapper.updateByPrimaryKey(agent);
            Customer customer = agent.getCustomer();
            customer.setStatus(Customer.STATUS_STOP);
            customerMapper.updateByPrimaryKey(customer);
        }
        return agent;
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Agent statusWaitingFirstCheck(Integer id) {
        Agent agent = agentMapper.findAgentInfo(id);
        if (agent.getStatus() == Agent.STATUS_STOP) {
            agent.setStatus(Agent.STATUS_WAITING_FIRST_CHECK);
            agentMapper.updateByPrimaryKey(agent);
        }
        return agent;
    }

    @Transactional("transactionManager")
    public void updateProfit(Integer id, Integer payChannelId, int newChannelId, List<Map<String, Object>> tradeTypeList) {
        agentProfitSettingMapper.deleteByAgentIdAndChannelId(id, payChannelId);
        for (Map<String, Object> t : tradeTypeList) {
            int tradeTypeId = (int) t.get("tradeTypeId");
            DictionaryTradeType tradeType = dictionaryTradeTypeMapper.selectByPrimaryKey(tradeTypeId);
            List<Map<String, String>> percents = (List) t.get("percents");
            for (Map<String, String> p : percents) {
                AgentProfitSetting agentProfitSetting = new AgentProfitSetting();
                agentProfitSetting.setAgentId(id);
                agentProfitSetting.setPayChannelId(newChannelId);
                agentProfitSetting.setTradeTypeId(tradeType.getId());
                if (tradeType.getTradeType() == DictionaryTradeType.TYPE_TRADE) {
                    agentProfitSetting.setTradeType(AgentProfitSetting.TYPE_TRADE);
                } else {
                    agentProfitSetting.setTradeType(AgentProfitSetting.TYPE_OTHER);
                }
                agentProfitSetting.setFloorNumber(Integer.parseInt(p.get("floorNumber")));
                agentProfitSetting.setPercent(Integer.parseInt(p.get("percent")));
                agentProfitSetting.setCreatedAt(new Date());
                agentProfitSetting.setUpdatedAt(new Date());
                agentProfitSettingMapper.insert(agentProfitSetting);
            }
        }
    }

    @Transactional("transactionManager")
    public void createProfit(Integer id, Integer channelId, List<Map<String, Object>> tradeTypeList) {
        for (Map<String, Object> t : tradeTypeList) {
            int tradeTypeId = (int) t.get("tradeTypeId");
            DictionaryTradeType tradeType = dictionaryTradeTypeMapper.selectByPrimaryKey(tradeTypeId);
            List<Map<String, Integer>> percents = (List) t.get("percents");
            for (Map<String, Integer> p : percents) {
                AgentProfitSetting agentProfitSetting = new AgentProfitSetting();
                agentProfitSetting.setAgentId(id);
                agentProfitSetting.setPayChannelId(channelId);
                agentProfitSetting.setTradeTypeId(tradeType.getId());
                if (tradeType.getTradeType() == DictionaryTradeType.TYPE_TRADE) {
                    agentProfitSetting.setTradeType(AgentProfitSetting.TYPE_TRADE);
                } else {
                    agentProfitSetting.setTradeType(AgentProfitSetting.TYPE_OTHER);
                }
                agentProfitSetting.setFloorNumber(p.get("floorNumber")*100);
                agentProfitSetting.setPercent(p.get("percent"));
                agentProfitSetting.setCreatedAt(new Date());
                agentProfitSetting.setUpdatedAt(new Date());
                agentProfitSettingMapper.insert(agentProfitSetting);
            }
        }
    }


}

