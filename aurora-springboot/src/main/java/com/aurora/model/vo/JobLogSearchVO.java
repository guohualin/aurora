package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLogSearchVO {

    private Integer jobId;

    private String jobName;

    private String jobGroup;

    private Integer status;

    private String startTime;

    private String endTime;
}
