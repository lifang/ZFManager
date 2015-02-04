package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.mapper.zhangfu.CityMapper;

@Service
public class CityService {
	
	@Autowired
	private CityMapper cityMapper;

	public List<City> provinces(){
		return cities(0);
	}
	
	public List<City> cities(Integer parentId){
		return cityMapper.selectWithParentId(parentId);
	}
	
	public City city(Integer id){
		return cityMapper.selectByPrimaryKey(id);
	}
}
