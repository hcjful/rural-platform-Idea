package com.rural.platform.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CulturalActivityDTO {
    private Long id;
    
    @NotBlank(message = "活动标题不能为空")
    @Size(min = 2, max = 50, message = "标题长度必须在2-50个字符之间")
    private String title;
    
    @NotBlank(message = "活动分类不能为空")
    private String category;
    
    private String imageUrl;
    
    private String description;
    
    @NotBlank(message = "活动内容不能为空")
    private String content;
    
    private String author;
    
    private String createTime;
    
    private Integer viewCount;
} 