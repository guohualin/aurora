package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkVO {

    private Integer id;

    @NotBlank(message = "链接名不能为空")
    private String linkName;

    @NotBlank(message = "链接头像不能为空")
    private String linkAvatar;

    @NotBlank(message = "链接地址不能为空")
    private String linkAddress;

    @NotBlank(message = "链接介绍不能为空")
    private String linkIntro;

    /**
     * 状态 0正常1失效
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
