package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long talentId;
    private String content;
    private String userName;
    private String userAvatar;
    private Integer likes;
    private Boolean isLiked;
    private LocalDateTime createTime;
} 