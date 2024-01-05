package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QQLoginVO {

    @NotBlank(message = "openId不能为空")
    private String openId;

    @NotBlank(message = "accessToken不能为空")
    private String accessToken;

}
