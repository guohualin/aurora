package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConditionVO {

    private Long current;

    private Long size;

    private String keywords;

    private Integer categoryId;

    private Integer tagId;

    private Integer albumId;

    private Integer loginType;

    private Integer type;

    private Integer status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer isDelete;

    private Integer isReview;

    private Integer isTop;

    private Integer isFeatured;


}
