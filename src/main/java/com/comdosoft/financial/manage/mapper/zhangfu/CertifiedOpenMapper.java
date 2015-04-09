package com.comdosoft.financial.manage.mapper.zhangfu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comdosoft.financial.manage.domain.zhangfu.task.CertifiedOpen;
import com.comdosoft.financial.manage.domain.zhangfu.task.Mark;
import com.comdosoft.financial.manage.domain.zhangfu.task.Opendetailsinfo;
import com.comdosoft.financial.manage.domain.zhangfu.task.Showinfo;
import com.comdosoft.financial.manage.utils.page.PageRequest;



public interface CertifiedOpenMapper {


    Showinfo findInfo(Integer id);

    List<CertifiedOpen> findPageByKeys(@Param("pageRequest") PageRequest pageRequest,
            @Param("status") Byte status, @Param("keys") String keys);

    long countByKeys(@Param("status") Byte status, @Param("keys") String keys);

    List<Opendetailsinfo> getOpeningDetails(Integer id);

    List<Mark> getMark(Integer id);

    void addMark(Mark mark);

    int upVstatus(@Param("id")Integer id,@Param("status") Integer status);

    int upStatus(@Param("id")Integer id,@Param("status") Integer status);

    int videoStatus(int id);

    int upMstatus(@Param("id")Integer id,@Param("status") Integer status);

	

    
}