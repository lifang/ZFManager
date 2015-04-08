package com.comdosoft.financial.manage.mapper.zhangfu;

import org.apache.ibatis.annotations.Param;

public interface ReocrdOperateMapper {
	
	int save(@Param("operateUserId") int operateUserId,@Param("operateUserName") String operateUserName,@Param("operateUsreType") int operateUsreType,
			@Param("types") int types,@Param("content") String content,@Param("operateTargetId") int operateTargetId);
}
