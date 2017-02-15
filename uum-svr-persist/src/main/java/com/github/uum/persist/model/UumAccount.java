package com.github.uum.persist.model;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by user on 2016/11/3.
 */
@Data
public class UumAccount {

    @Id
    private Integer id;

    private String name;

    private String password;

    private Integer status;

    private Date ctime;

    private Date mtime;

}
