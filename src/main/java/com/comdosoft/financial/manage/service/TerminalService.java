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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TerminalService {

    @Value("${page.size}")
    private Integer pageSize;
    @Value("${filePath}")
    private String filePath ;
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
    @Autowired
    private PayChannelMapper payChannelMapper;
    @Autowired
    private OpeningApplieMapper openingApplieMapper;
    @Autowired
    private OpeningVideoVerifyMapper openingVideoVerifyMapper;
    @Autowired
    private OpeningRequirementMapper openingRequirementMapper;

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
        Terminal t=terminalMapper.findTerminalInfo(id);
        List<Map<String, Object>> mapTemp=terminalMapper.getRate(id);
        
        t.setTradeTypeList(mapTemp);
        
        OpeningApplie applie = t.getOpeningApplie();
        if(applie != null){
            List<TerminalOpeningInfo>infos = applie.getTerminalOpeningInfos();
            for (TerminalOpeningInfo s : infos) {
                if(s.getTypes()==2){
                    s.setValue(filePath+s.getValue());
                }
            }
        }
        return t;
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
    public List<String> importTerminal(Integer goodId, String content) {
        List<String> errorCodes = new ArrayList<String>();
        List<String> rightCodes = new ArrayList<String>();
        Good good = goodMapper.selectByPrimaryKey(goodId);
        int quantity = 0;
        if(good.getQuantity() != null){
            quantity = good.getQuantity();
        }

        String[] codes = content.split("\n|\r\n|\r");
        for(String code : codes){
            code = code.trim();
            if(!Strings.isNullOrEmpty(code)){
                Terminal terminal = terminalMapper.findTerminalByNum(code);
                if(terminal != null
                        || errorCodes.contains(code)
                        || rightCodes.contains(code)){
                    errorCodes.add(code);
                    continue;
                }
                rightCodes.add(code);
            }
        }
        if(errorCodes.size() == 0){
            for(String code : rightCodes){
                Terminal terminal = new Terminal();
                terminal.setGoodId(good.getId());
                terminal.setSerialNum(code);
                terminal.setStatus(Terminal.STATUS_NO_OPEN);
                terminal.setType(Terminal.TYPE_SYSTEM);
                terminal.setCreatedAt(new Date());
                terminal.setUpdatedAt(new Date());
                terminal.setIsReturnCsDepots(Terminal.IS_RETURN_CS_DEPOTS);
                terminalMapper.insert(terminal);
                quantity++;
            }
            good.setQuantity(quantity);
            goodMapper.updateByPrimaryKey(good);
        }
        return errorCodes;
    }
    
    public Terminal findByNum(String num) {
    	return terminalMapper.findTerminalByNum(num);
    }


    public int videoStatus(Integer id){
        int status = Terminal.VIDEO_STATUS_1;
        OpeningApplie openingApplie = openingApplieMapper.selectOpeningApplie(id);
        if(openingApplie == null){
            return status;
        }
        List<OpeningVideoVerify> verifies = openingVideoVerifyMapper.selectByApplyId(openingApplie.getId());
        for(OpeningVideoVerify verify : verifies){
            if(verify.getStatus() == OpeningVideoVerify.STATUS_FALSE){
                status = Terminal.VIDEO_STATUS_3;
            }
            return status;
        }

        Terminal terminal = terminalMapper.findTerminalInfo(id);
        status = Terminal.VIDEO_STATUS_2;
        List<OpeningRequirement> requirements = openingRequirementMapper.selectOpeningRequirements(terminal.getPayChannelId());
        for (OpeningRequirement requirement : requirements){
            if(requirement.getHasVideoVerify() == true){
                break;
            }
        }
        return status;
    }

    public OpeningApplie getOpeningApplie(Integer id){
        return openingApplieMapper.selectOpeningApplie(id);
    }
    
    /**
	 * 获得终端开通图片资料
	 * @return
	 */
	public List<Map<Object, Object>> getTerminalOpen(int id,int type){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", id);
		map.put("type", type);
		return terminalMapper.getTerminalOpen(map);
	}
	
	public List<String> judgeRemoveStorage(String[] terminals){
		List<String> invalids = new ArrayList<String>();
		for(String termianl : terminals){
			Terminal t =terminalMapper.getTerminal(termianl);
			if(t == null){
				invalids.add(termianl);
			}
		}
		return invalids;
	}

	public void removeStorage(Integer goodId, String[] terminals) {
		terminalMapper.deleteTerminals(terminals);
		goodMapper.updateQuantity(goodId, terminals.length);
	}
}
