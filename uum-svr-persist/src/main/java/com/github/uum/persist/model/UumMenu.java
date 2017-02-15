package com.github.uum.persist.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by user on 2016/11/4.
 */
@Data
public class UumMenu {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer parentId;

    private String name;

    private String tpl;

    private String icon;

    private Integer sort;

    private String css;

    private Boolean status;

    private Date ctime;

    private Date mtime;
}
