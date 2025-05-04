package com.rural.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("warning_info")
public class WarningInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String type;
    
    private String status;
    
    private String description;
    
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    private String publisher;
    
    private String area;
    
    private Integer urgency;

    private String createBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    private String updateBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
    
    private String remarks;
    
    private String delFlag;
    
    @TableField(exist = false)
    private List<WarningRecord> records;
}
