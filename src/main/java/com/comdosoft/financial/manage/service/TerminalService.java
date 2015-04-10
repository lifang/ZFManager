package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.mapper.zhangfu.*;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TerminalService {

    @Value("${page.size}")
    private Integer pageSize;
	@Autowired
	private TerminalMapper terminalMapper;
    @Autowired
    private TerminalMarkMapper terminalMarkMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private FactoryMapper factoryMapper;
    @Autowired
    private GoodMapper goodMapper;

	public long countCustomerTerminals(Integer customerId){
		return terminalMapper.countCustomerTerminals(customerId);
	}

	public Page<Terminal> customerTerminalPage(Integer customerId,Integer page){
		long count = terminalMapper.countCustomerTerminals(customerId);
        if (count == 0) {
            return new Page<>(new PageRequest(1, pageSize), new ArrayList<Terminal>(), count);
        }
        PageRequest request = new PageRequest(page, pageSize);
        List<Terminal> result = terminalMapper.selectCustomerTerminals(customerId, request);
        Page<Terminal> terminals = new Page<>(request, result, count);
        if (terminals.getCurrentPage() > terminals.getTotalPage()) {
            request = new PageRequest(terminals.getTotalPage(), pageSize);
            result = terminalMapper.selectCustomerTerminals(customerId, request);
            terminals = new Page<>(request, result, count);
        }
        return terminals;
	}

    public Page<Terminal> findPages(Integer customerId, Integer page, Byte status, String keys) {

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
            keys = "%"+keys+"%";
        }
        long count = terminalMapper.countByKeys(factoryId, status, keys);
        if (count == 0) {
            return new Page<>(new PageRequest(1, pageSize), new ArrayList<Terminal>(), count);
        }

        PageRequest request = new PageRequest(page, pageSize);
        List<Terminal> result = terminalMapper.selectPageTerminalsByKeys(request, factoryId, status, keys);
        Page<Terminal> terminals = new Page<>(request, result, count);
        if (terminals.getCurrentPage() > terminals.getTotalPage()) {
            request = new PageRequest(terminals.getTotalPage(), pageSize);
            result = terminalMapper.selectPageTerminalsByKeys(request, factoryId, status, keys);
            terminals = new Page<>(request, result, count);
        }
        return terminals;
    }

    public Terminal findTerminalInfo(Integer id) {
        return terminalMapper.findTerminalInfo(id);
    }

    public TerminalMark mark(Integer terminalId, Integer customerId, String content) {
        TerminalMark terminalMark = new TerminalMark();
        terminalMark.setTerminalId(terminalId);
        terminalMark.setCustomerId(customerId);
        terminalMark.setContent(content);
        terminalMark.setCreatedAt(new Date());
        terminalMarkMapper.insert(terminalMark);
        terminalMark = terminalMarkMapper.selectTerminalMark(terminalMark.getId());
        return terminalMark;
    }

    @Transactional("transactionManager")
    public void importTerminal(Integer goodId, String content) {
        Good good = goodMapper.selectByPrimaryKey(goodId);
        int quantity = 0;
        if(good.getQuantity() != null){
            quantity = good.getQuantity();
        }

        String[] codes = content.split("\n|\r\n|\r");
        for(String code : codes){
            if(!Strings.isNullOrEmpty(code)){
                Terminal terminal = new Terminal();
                terminal.setCreatedAt(new Date());
                terminal.setGoodId(good.getId());
            }
        }


        good.setQuantity(quantity);
        goodMapper.updateByPrimaryKey(good);
    }
}
