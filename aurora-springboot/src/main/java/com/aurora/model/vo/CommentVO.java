package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    private Integer replyUserId;

    private Integer topicId;

    @NotBlank(message = "评论内容不能为空")
    private String commentContent;

    private Integer parentId;

    @NotNull(message = "评论类型不能为空")
    private Integer type;
}
