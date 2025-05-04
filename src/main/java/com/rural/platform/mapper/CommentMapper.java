package com.rural.platform.mapper;

import com.rural.platform.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * 根据才艺ID查询评论列表
     */
    List<Comment> selectCommentsByTalentId(Long talentId);

    /**
     * 根据评论ID查询评论
     */
    Comment selectCommentById(Long id);

    /**
     * 插入新评论
     */
    int insertComment(Comment comment);

    /**
     * 更新评论信息
     */
    int updateComment(Comment comment);
} 