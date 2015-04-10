package com.comdosoft.financial.manage.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
	
	@Cacheable("provinceMap")
	public Map<Integer,City> provinceMap(){
		List<City> provinces = provinces();
		return provinces.stream().collect(Collectors.toMap(City::getId, Function.identity()));
	}
}
