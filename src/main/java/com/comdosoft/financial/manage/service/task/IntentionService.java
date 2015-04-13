package com.comdosoft.financial.manage.service.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.task.Intention;
import com.comdosoft.financial.manage.domain.zhangfu.task.Mark;
import com.comdosoft.financial.manage.mapper.zhangfu.IntentionMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class IntentionService {

    @Value("${page.size}")
    private Integer pageSize;

	@Autowired
	private IntentionMapper intentionMapper;
	
	public Page<Intention> findPages(int id,int page, Byte status, String keys){
		long count = intentionMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<Intention>(new PageRequest(1, pageSize), new ArrayList<Intention>(), count);
		}
		PageRequest request = new PageRequest(page, pageSize);
		List<Intention> result = intentionMapper.findPageByKeys(request, status, keys,id);
		
		Page<Intention> goods = new Page<>(request, result, count);
		if (goods.getCurrentPage() > goods.getTotalPage()) {
			request = new PageRequest(goods.getTotalPage(), pageSize);
			result = intentionMapper.findPageByKeys(request, status, keys,id);
			goods = new Page<>(request, result, count);
		}
		return goods;
	}

	public Intention findInfo(Integer id) {
		return intentionMapper.findInfo(id);
	}

    public List<Mark> getMark(Integer id) {
        return intentionMapper.getMark(id);
    }

    public void addMark(Mark mark) {
        intentionMapper.addMark(mark);
        
    }

    public int upStatus(Integer id, Integer status) {
        return  intentionMapper.upStatus(id,status);
    }

    public void dispatch(String ids, Integer customerId, String customerName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids.split(","));
        params.put("customerId", customerId);
        params.put("customerName", customerName);
        intentionMapper.dispatch(params);
    }
	
}
