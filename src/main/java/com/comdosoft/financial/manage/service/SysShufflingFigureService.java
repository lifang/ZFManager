package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.SysShufflingFigure;
import com.comdosoft.financial.manage.mapper.zhangfu.SysShufflingFigureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by quqiang on 15/3/20.
 */
@Service
public class SysShufflingFigureService {

    @Autowired
    private SysShufflingFigureMapper sysShufflingFigureMapper;

    public List<SysShufflingFigure> findSysShufflingFigures(){
        return  sysShufflingFigureMapper.selectAll();
    }
}
