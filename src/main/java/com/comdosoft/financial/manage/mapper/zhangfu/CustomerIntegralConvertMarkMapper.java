package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;

import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralConvertMark;

public interface CustomerIntegralConvertMarkMapper {

	List<CustomerIntegralConvertMark> findMarksById(Integer id);

	CustomerIntegralConvertMark createMark(Integer pid, String content,
			Integer id, String name);

}
