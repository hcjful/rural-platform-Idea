package com.rural.platform.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NotificationSettingsDTO {
    @NotNull(message = "系统通知设置不能为空")
    private Boolean systemNotify;
    
    @NotNull(message = "活动通知设置不能为空")
    private Boolean activityNotify;
    
    @NotNull(message = "评论通知设置不能为空")
    private Boolean commentNotify;
    
    @NotNull(message = "消息通知设置不能为空")
    private Boolean messageNotify;
    
    private List<String> notifyMethods;
}
