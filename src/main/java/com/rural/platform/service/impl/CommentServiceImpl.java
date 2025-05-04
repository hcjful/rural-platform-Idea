package com.rural.platform.service.impl;

import com.rural.platform.entity.Comment;
import com.rural.platform.mapper.CommentMapper;
import com.rural.platform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByTalentId(Long talentId) {
        return commentMapper.selectCommentsByTalentId(talentId);
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setLikes(0);
        comment.setIsLiked(false);
        commentMapper.insertComment(comment);
        return comment;
    }

    @Override
    public Comment likeComment(Long id) {
        Comment comment = commentMapper.selectCommentById(id);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        comment.setIsLiked(!comment.getIsLiked());
        comment.setLikes(comment.getIsLiked() ? comment.getLikes() + 1 : comment.getLikes() - 1);
        commentMapper.updateComment(comment);
        return comment;
    }
} 