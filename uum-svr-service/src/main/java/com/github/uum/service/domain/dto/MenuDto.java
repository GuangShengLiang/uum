package com.github.uum.service.domain.dto;

import lombok.Data;

/**
 * @Author lgs
 * @Date 16-11-23 下午10:42
 */
@Data
public class MenuDto {

    private Integer id;

    private Integer parentId;

    private String name;

    private String tpl;

    private String icon;

    private Integer sort;

    private String css;

    private Boolean status;
}
