package com.aurora.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO {

    private Integer id;

    @NotBlank(message = "角色名不能为空")
    private String roleName;

    private List<Integer> resourceIds;

    private List<Integer> menuIds;

}
