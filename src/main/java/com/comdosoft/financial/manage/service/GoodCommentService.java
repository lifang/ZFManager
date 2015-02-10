package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodCommentMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junxi.qu on 2015/2/8.
 */
@Service
public class GoodCommentService {
    @Autowired
    private GoodCommentMapper goodCommentMapper;

    public long countComments(Byte status){
        return goodCommentMapper.countByStatus(status);
    }

    public Page<GoodComment> findWaitingPages(Integer page, int pageSize){
        PageRequest request = new PageRequest(page, pageSize);
        long count = countComments(GoodComment.STATUS_WAITING);
        if (count == 0) {
            return new Page<GoodComment>(new PageRequest(1, pageSize), new ArrayList<GoodComment>(), count);
        }
        List<GoodComment> result = goodCommentMapper.findPageCommentsByStatus(request, GoodComment.STATUS_WAITING);
        Page<GoodComment> comments = new Page<GoodComment>(request, result, count);
        return comments;
    }

}
