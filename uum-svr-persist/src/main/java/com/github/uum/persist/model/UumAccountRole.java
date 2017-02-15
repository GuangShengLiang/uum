package com.github.uum.persist.model;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by user on 2016/11/3.
 */
@Data
public class UumAccountRole {

    @Id
    private Integer id;

    private Integer uid;

    private Integer roleId;
}
