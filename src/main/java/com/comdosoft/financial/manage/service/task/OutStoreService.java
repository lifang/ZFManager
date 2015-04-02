package com.comdosoft.financial.manage.service.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.OutStore;
import com.comdosoft.financial.manage.mapper.zhangfu.OutStoreMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class OutStoreService {
	
	@Value("${page.outStore.size}")
    private int pageSize=2;
	
	@Autowired
	private OutStoreMapper outStoreMapper;
	
	public Page<OutStore> findPages(int page, Byte status, String keys){
		if (keys != null) {
			keys = "%"+keys+"%";
		}
		long count = outStoreMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<OutStore>(new PageRequest(1, pageSize), new ArrayList<OutStore>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<OutStore> result = outStoreMapper.findPageOutStoresByKeys(request, status, keys);
		Page<OutStore> outStores = new Page<>(request, result, count);
		if (outStores.getCurrentPage() > outStores.getTotalPage()) {
			request = new PageRequest(outStores.getTotalPage(), pageSize);
			result = outStoreMapper.findPageOutStoresByKeys(request, status, keys);
			outStores = new Page<>(request, result, count);
		}
		return outStores;
	}
}
