package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.task.Intention;
import com.comdosoft.financial.manage.domain.zhangfu.task.Mark;
import com.comdosoft.financial.manage.utils.page.PageRequest;



public interface IntentionMapper {


    Intention findInfo(Integer id);

    List<Intention> findPageByKeys(@Param("pageRequest") PageRequest pageRequest,
            @Param("status") Byte status, @Param("keys") String keys);

    long countByKeys(@Param("status") Byte status, @Param("keys") String keys);

    List<Mark> getMark(Integer id);

    void addMark(Mark mark);

    int upStatus(@Param("id")Integer id,@Param("status") Integer status);

    void dispatch(Map<String, Object> params);



	

    
}