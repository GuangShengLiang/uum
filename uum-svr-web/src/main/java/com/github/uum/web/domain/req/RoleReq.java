package com.github.uum.web.domain.req;

import lombok.Data;

/**
 * @Author lgs
 * @Date 16-11-23 下午10:53
 */
@Data
public class RoleReq {

    private Integer parentId;

    private String name;

    private Boolean status;
}
