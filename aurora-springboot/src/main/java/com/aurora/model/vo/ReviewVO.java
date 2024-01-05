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
public class ReviewVO {

    @NotNull(message = "id不能为空")
    private List<Integer> ids;

    @NotNull(message = "状态值不能为空")
    private Integer isReview;

}
