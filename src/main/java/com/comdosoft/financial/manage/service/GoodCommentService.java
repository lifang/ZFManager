package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodCommentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by junxi.qu on 2015/2/8.
 */
@Service
public class GoodCommentService {

    @Value("${page.comment.size}")
    private Integer pageSize;
    @Autowired
    private GoodCommentMapper goodCommentMapper;
    @Autowired
    private GoodMapper goodMapper;

    /**
     * 待审核列表分页
     * @param page
     * @return
     */
    public Page<GoodComment> findWaitingPages(Integer page){
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
     * @return
     */
    public Page<GoodComment> findCommentPages(Integer goodId, Integer page){
        PageRequest request = new PageRequest(page, pageSize);
        long count = goodCommentMapper.countByGoodIdAndStatus(goodId, GoodComment.STATUS_CHECKED);
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
     * 商品评论的总数加上goodId条件
     * @param goodId
     * @param page
     * @return
     */
    public Page<GoodComment> findCommentPagesByGoodId(Integer goodId, Integer page){
        PageRequest request = new PageRequest(page, pageSize);
        long count = goodCommentMapper.countByGoodIdAndStatus(goodId, GoodComment.STATUS_CHECKED);
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
    @Transactional(value = "transactionManager")
    public GoodComment check(Integer customerId, Integer id){
        GoodComment comment = goodCommentMapper.selectByPrimaryKey(id);
        if(comment.getStatus() == GoodComment.STATUS_WAITING){
            comment.setVerifyUserId(customerId);
            comment.setVerifiedAt(new Date());
            comment.setStatus(GoodComment.STATUS_CHECKED);
            comment.setUpdatedAt(new Date());
            goodCommentMapper.updateByPrimaryKey(comment);

            Good good = goodMapper.selectByPrimaryKey(comment.getGoodId());
            good.setTotalComment(good.getTotalComment() + 1);
            good.setTotalScore(good.getTotalScore() + comment.getScore());
            goodMapper.updateByPrimaryKey(good);
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
            if (comment.getStatus() == GoodComment.STATUS_CHECKED) {
                Good good = goodMapper.selectByPrimaryKey(comment.getGoodId());
                int totalComment = good.getTotalComment() - 1;
                int score = good.getTotalScore() - comment.getScore();
                if (totalComment <= 0 || score <= 0){
                    good.setTotalComment(0);
                    good.setTotalScore(0);
                }else {
                    good.setTotalComment(totalComment);
                    good.setTotalScore(good.getTotalScore() - comment.getScore());
                }
            goodCommentMapper.deleteByPrimaryKey(id);
            }
        return comment;
    }

    @Transactional("transactionManager")
    public GoodComment create(Integer goodId, Integer customerId, Integer score, String content){
        GoodComment comment = new GoodComment();
        comment.setGoodId(goodId);
        comment.setCustomerId(customerId);
        comment.setScore(score*10);
        comment.setContent(content);
        comment.setStatus(GoodComment.STATUS_WAITING);
        comment.setCreatedAt(new Date());
        goodCommentMapper.insert(comment);
        check(customerId, comment.getId());
        return comment;
    }
}
