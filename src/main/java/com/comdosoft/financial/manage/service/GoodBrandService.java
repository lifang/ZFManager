/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年3月23日 下午9:33:17
 */
package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.GoodBrand;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodBrandMapper;

@Service
public class GoodBrandService {

	@Autowired
	private GoodBrandMapper goodBrandMapper;
	
	
	public List<GoodBrand> selectAll(){
		return goodBrandMapper.selectAll();
	}
}
