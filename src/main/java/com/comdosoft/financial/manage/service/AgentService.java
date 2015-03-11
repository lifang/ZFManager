package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.AgentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
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
    private CustomerMapper customerMapper;
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

    public Agent findAgentInfo(Integer id){
        return agentMapper.findAgentInfo(id);
    }

    @Transactional("transactionManager")
    public boolean update(Integer id, Integer types, String name, String cardId,
                       String companyName, String businessLicense, String phone, String email,
                       Integer cityId, String address, String username, String password) {
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
                          Integer cityId, String address, String username, String password) {
        Customer customer = customerMapper.selectByUsername(username);
        if (customer != null){
            return false;
        }

        Agent agent = new Agent();
        agent.setTypes(types);
        agent.setName(name);
        agent.setCardId(cardId);
        agent.setCompanyName(companyName);
        agent.setBusinessLicense(businessLicense);
        agent.setPhone(phone);
        agent.setEmail(email);
        agent.setAddress(address);
        agent.setCreatedAt(new Date());
        agent.setCode("");
        customer = new Customer();
        customer.setUsername(username);
        String md5Password = DigestUtils.md5Hex(password);
        customer.setPassword(md5Password);
        customer.setTypes(Customer.TYPE_AGENT);
        customer.setCityId(cityId);
        customer.setPhone(phone);
        customer.setIntegral(0);
        customer.setCreatedAt(new Date());
        customer.setStatus(Customer.STATUS_NORMAL);
        customerMapper.insert(customer);
        agent.setCustomerId(customer.getId());
        agentMapper.insert(agent);
        return true;
    }
}
