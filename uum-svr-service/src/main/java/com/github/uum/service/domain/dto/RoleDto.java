package com.github.uum.service.domain.dto;

import lombok.Data;

/**
 * @Author lgs
 * @Date 16-11-23 下午11:04
 */
@Data
public class RoleDto {

    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;
}
