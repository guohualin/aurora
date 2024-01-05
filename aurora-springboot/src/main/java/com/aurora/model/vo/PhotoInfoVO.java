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
public class PhotoInfoVO {

    @NotNull(message = "照片id不能为空")
    private Integer id;

    @NotBlank(message = "照片名不能为空")
    private String photoName;

    private String photoDesc;

}
