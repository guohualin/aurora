package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoVO {

    @NotNull(message = "相册id不能为空")
    private Integer albumId;

    private List<String> photoUrls;

    private List<Integer> photoIds;

}
