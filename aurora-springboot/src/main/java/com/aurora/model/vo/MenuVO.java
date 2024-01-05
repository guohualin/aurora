package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuVO {

    private Integer id;

    @NotBlank(message = "菜单名不能为空")
    private String name;

    @NotBlank(message = "菜单icon不能为空")
    private String icon;

    @NotBlank(message = "路径不能为空")
    private String path;

    @NotBlank(message = "组件不能为空")
    private String component;

    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    private Integer parentId;

    private Integer isHidden;

}
