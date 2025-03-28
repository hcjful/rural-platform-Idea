package com.rural.platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("cultural_activity")
public class CulturalActivity {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String category;
    
    private String imageUrl;
    
    private String description;
    
    private String content;
    
    private String author;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    private Integer status;
    
    private Integer viewCount;
} 