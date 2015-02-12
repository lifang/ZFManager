package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodCommentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by junxi.qu on 2015/2/8.
 */
@Service
public class GoodCommentService {
    @Autowired
    private GoodCommentMapper goodCommentMapper;
    @Autowired
    private GoodMapper goodMapper;

    /**
     * 待审核列表分页
     * @param page
     * @param pageSize
     * @return
     */
    public Page<GoodComment> findWaitingPages(Integer page, int pageSize){
        PageRequest request = new PageRequest(page, pageSize);
        long count = goodCommentMapper.countByStatus(GoodComment.STATUS_WAITING);
        if (count == 0) {
            return new Page<>(new PageRequest(1, pageSize), new ArrayList<>(), count);
        }
        List<GoodComment> result = goodCommentMapper.findPageCommentsByGoodIdAndStatus(request, GoodComment.STATUS_WAITING, null);
        Page<GoodComment> comments = new Page<>(request, result, count);
        if (comments.getCurrentPage() > comments.getTotalPage()) {
            request = new PageRequest(comments.getTotalPage(), pageSize);
            result = goodCommentMapper.findPageCommentsByGoodIdAndStatus(request, GoodComment.STATUS_WAITING, null);
            comments = new Page<>(request, result, count);
        }
        return comments;
    }

    /**
     * 商品评论列表分页
     * @param goodId
     * @param page
     * @param pageSize
     * @return
     */
    public Page<GoodComment> findCommentPages(Integer goodId, Integer page, int pageSize){
        PageRequest request = new PageRequest(page, pageSize);
        long count = goodCommentMapper.countByStatus(GoodComment.STATUS_CHECKED);
        if (count == 0) {
            return new Page<>(new PageRequest(1, pageSize), new ArrayList<>(), count);
        }
        List<GoodComment> result = goodCommentMapper.findPageCommentsByGoodIdAndStatus(request, GoodComment.STATUS_CHECKED, goodId);
        Page<GoodComment> comments = new Page<>(request, result, count);
        if (comments.getCurrentPage() > comments.getTotalPage()) {
            request = new PageRequest(comments.getTotalPage(), pageSize);
            result = goodCommentMapper.findPageCommentsByGoodIdAndStatus(request, GoodComment.STATUS_CHECKED, goodId);
            comments = new Page<>(request, result, count);
        }
        return comments;
    }

    /**
     * 审核
     * @param customerId
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public GoodComment check(Integer customerId, Integer id){
        GoodComment comment = goodCommentMapper.selectByPrimaryKey(id);
        if(comment.getStatus() == GoodComment.STATUS_WAITING){
            comment.setVerifyUserId(customerId);
            comment.setVerifiedAt(new Date());
            comment.setStatus(GoodComment.STATUS_CHECKED);
            comment.setUpdatedAt(new Date());
            goodCommentMapper.updateByPrimaryKey(comment);
        }
        return comment;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public GoodComment delete(Integer id){
        GoodComment comment = goodCommentMapper.selectByPrimaryKey(id);
        if(comment.getStatus() != GoodComment.STATUS_DELETE){
            comment.setStatus(GoodComment.STATUS_DELETE);
            comment.setUpdatedAt(new Date());
            goodCommentMapper.updateByPrimaryKey(comment);
        }
        return comment;
    }
}
