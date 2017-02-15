package com.github.uum.persist.model;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by user on 2016/11/4.
 */
@Data
public class UumRoleMenu {

    @Id
    private Integer id;

    private Integer menuId;

    private Integer roleId;

}
