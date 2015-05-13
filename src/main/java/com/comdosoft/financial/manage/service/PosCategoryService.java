package com.comdosoft.financial.manage.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.PosCategoryMapper;

@Service
public class PosCategoryService {

	@Autowired
	private PosCategoryMapper posCategoryMapper;
    @Autowired
    private GoodMapper goodMapper;

    public List<PosCategory> listAll(){
		List<PosCategory> categories = posCategoryMapper.selectAll();
		return categories;
	}

	public Collection<PosCategory> listOrderAll() {
		List<PosCategory> categories = posCategoryMapper.selectAll();
		Multimap<Integer, PosCategory> myMultimap = ArrayListMultimap.create();
		if (categories != null) {
			for (PosCategory posCategory : categories) {
				if (posCategory.getParentId() == null || posCategory.getParentId() == 0) {
					myMultimap.put(posCategory.getId(), posCategory);
				}
			}
			for (PosCategory posCategory : categories) {
				if (posCategory.getParentId() != null && posCategory.getParentId() != 0) {
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
        List<PosCategory> children = posCategoryMapper.selectChildren(id);
        for(PosCategory child:children) {
        	long c = goodMapper.countByCategoryId(child.getId());
        	if(c >0) {
        		return false;
        	}
        }
        posCategoryMapper.deleteByPrimaryKey(id);
        for(PosCategory child:children) {
        	posCategoryMapper.deleteByPrimaryKey(child.getId());
        }
        return true;
    }

	@Transactional("transactionManager")
	public PosCategory create(Integer parentId, String name) {
		PosCategory posCategory = new PosCategory();
		posCategory.setName(name);
		if(parentId == null){
			posCategory.setParentId(parentId);
		} else {
			posCategory.setParentId(parentId);
		}
		posCategory.setCreatedAt(new Date());
        posCategoryMapper.insert(posCategory);
		return posCategory;
	}
	
	@Transactional("transactionManager")
	public PosCategory modify(Integer id, String name) {
		PosCategory posCategory = posCategoryMapper.selectByPrimaryKey(id);
		posCategory.setName(name);
        posCategoryMapper.updateByPrimaryKey(posCategory);
		return posCategory;
	}

}
