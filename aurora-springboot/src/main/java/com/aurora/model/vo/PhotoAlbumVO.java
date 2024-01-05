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
public class PhotoAlbumVO {

    private Integer id;

    @NotBlank(message = "相册名不能为空")
    private String albumName;

    private String albumDesc;

    @NotBlank(message = "相册封面不能为空")
    private String albumCover;

    private Integer status;

}
