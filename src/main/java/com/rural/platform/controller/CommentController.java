package com.rural.platform.controller;

import com.rural.platform.entity.Comment;
import com.rural.platform.service.CommentService;
import com.rural.platform.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/talent/{talentId}")
    public Result<List<Comment>> getCommentsByTalentId(@PathVariable Long talentId) {
        return Result.success(commentService.getCommentsByTalentId(talentId));
    }

    @PostMapping
    public Result<Comment> addComment(@RequestBody Comment comment) {
        return Result.success(commentService.addComment(comment));
    }

    @PostMapping("/{id}/like")
    public Result<Comment> likeComment(@PathVariable Long id) {
        return Result.success(commentService.likeComment(id));
    }
} 