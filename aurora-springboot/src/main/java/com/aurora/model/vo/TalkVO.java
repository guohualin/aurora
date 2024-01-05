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
public class TalkVO {

    private Integer id;

    @NotBlank(message = "说说内容不能为空")
    private String content;

    private String images;

    @NotNull(message = "置顶状态不能为空")
    private Integer isTop;

    @NotNull(message = "说说状态不能为空")
    private Integer status;

}
