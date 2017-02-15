package com.github.uum.web.domain.req;

import lombok.Data;

/**
 * @Author lgs
 * @Date 16-11-23 下午10:53
 */
@Data
public class MenuReq {

    private Integer parentId;

    private String name;

    private String tpl;

    private String icon;

    private Integer sort;

    private String css;

    private Boolean status;
}
