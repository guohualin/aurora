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
public class ResourceVO {

    private Integer id;

    @NotBlank(message = "资源名不能为空")
    private String resourceName;

    private String url;

    private String requestMethod;

    private Integer parentId;

    private Integer isAnonymous;

}
