package com.rural.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "volunteer_activities") // JPA 注解（如果有用 JPA 可保留）
@TableName("volunteer_activities")
public class VolunteerActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(name = "image_url")
    @TableField("image_url")
    private String imageUrl;

    @Column(nullable = false)
    private String location;

    @Column(name = "start_date", nullable = false)
    @TableField("start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @TableField("end_date")
    private LocalDateTime endDate;

    @Column(name = "max_volunteers", nullable = false)
    @TableField("max_volunteers")
    private Integer maxVolunteers;

    @Column(name = "current_volunteers", nullable = false)
    @TableField("current_volunteers")
    private Integer currentVolunteers = 0;

    @Column(nullable = false)
    private Double duration;

    @Column(length = 5000)
    private String content;

    @Column(name = "review_content", length = 5000)
    @TableField("review_content")
    private String review;

    @Column(name = "review_images")
    @TableField("review_images")
    private String reviewImages;

    @Column(nullable = false)
    private String status = "recruiting"; // recruiting, ongoing, finished

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @TableField("created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @TableField("updated_at")
    private LocalDateTime updatedAt;
} 