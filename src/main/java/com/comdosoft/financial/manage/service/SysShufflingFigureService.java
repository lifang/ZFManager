package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.SysShufflingFigure;
import com.comdosoft.financial.manage.mapper.zhangfu.SysShufflingFigureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional("transactionManager")
    public void edit(Integer id, String pictureUrl, String webSiteUrl){
        SysShufflingFigure sysShufflingFigure = sysShufflingFigureMapper.selectByPrimaryKey(id);
        if(sysShufflingFigure == null){
            sysShufflingFigure = new SysShufflingFigure();
            sysShufflingFigure.setId(id);
            sysShufflingFigure.setPictureUrl(pictureUrl);
            sysShufflingFigure.setWebsiteUrl(webSiteUrl);
            sysShufflingFigure.setCreatedAt(new Date());
            sysShufflingFigure.setUpdatedAt(new Date());
            sysShufflingFigureMapper.insert(sysShufflingFigure);
        }else {
            sysShufflingFigure.setPictureUrl(pictureUrl);
            sysShufflingFigure.setWebsiteUrl(webSiteUrl);
            sysShufflingFigure.setUpdatedAt(new Date());
            sysShufflingFigureMapper.updateByPrimaryKey(sysShufflingFigure);
        }
    }
}