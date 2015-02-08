package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.PosCategoryMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class PosCategoryService {

	@Autowired
	private PosCategoryMapper posCategoryMapper;
    @Autowired
    private GoodMapper goodMapper;

    public Collection<PosCategory> listAll(){
		List<PosCategory> categories = posCategoryMapper.selectAll();
		Multimap<Integer, PosCategory> myMultimap = ArrayListMultimap.create();  
		if (categories != null) {
			for (PosCategory posCategory : categories) {
				if (posCategory.getParentId() == null) {
					myMultimap.put(posCategory.getId(), posCategory);
				}
			}
			for (PosCategory posCategory : categories) {
				if (posCategory.getParentId() != null) {
					myMultimap.put(posCategory.getParentId(), posCategory);
				}
			}
		}
		return myMultimap.values();
	}

    @Transactional("transactionManager")
    public boolean delete(Integer id) {
        long count = goodMapper.countByCategoryId(id);
        if (count > 0) {
            return false;
        }
        posCategoryMapper.deleteByPrimaryKey(id);
        return true;
    }

	@Transactional("transactionManager")
	public PosCategory create(Integer parentId, String name) {
		PosCategory posCategory = new PosCategory();
		posCategory.setName(name);
		if(parentId != null && parentId == 0){
			posCategory.setParentId(null);
		} else {
			posCategory.setParentId(parentId);
		}
		posCategory.setCreatedAt(new Date());
        posCategoryMapper.insert(posCategory);
		return posCategory;
	}

}
