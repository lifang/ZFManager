package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.mapper.zhangfu.GoodCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by junxi.qu on 2015/2/8.
 */
@Service
public class GoodCommentService {
    @Autowired
    private GoodCommentMapper goodCommentMapper;

    public long countWaitComments(){

        return  0;
    }
}
