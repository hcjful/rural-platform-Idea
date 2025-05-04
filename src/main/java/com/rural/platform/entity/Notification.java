package com.rural.platform.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;
}
