package com.rural.platform.service;

import com.rural.platform.entity.Comment;
import java.util.List;

public interface CommentService {
    /**
     * 获取才艺展示的评论列表
     * @param talentId 才艺ID
     * @return 评论列表
     */
    List<Comment> getCommentsByTalentId(Long talentId);

    /**
     * 添加评论
     * @param comment 评论信息
     * @return 评论信息
     */
    Comment addComment(Comment comment);

    /**
     * 点赞评论
     * @param id 评论ID
     * @return 评论信息
     */
    Comment likeComment(Long id);
} 