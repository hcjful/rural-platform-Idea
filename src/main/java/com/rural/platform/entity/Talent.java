package com.rural.platform.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "talents")
public class Talent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private String content;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "author_avatar")
    private String authorAvatar;

    @Column(name = "publish_date", nullable = false)
    private LocalDateTime publishDate;

    @Column(nullable = false)
    private Integer likes = 0;

    @Column(name = "is_liked", nullable = false)
    private Boolean isLiked = false;
}
