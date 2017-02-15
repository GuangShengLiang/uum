package com.github.uum.persist.model;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by user on 2016/11/4.
 */
@Data
public class UumRole {

    @Id
    private Integer id;

    private String name;

    private Integer parentId;

    private Boolean status;

    private Date ctime;

    private Date mtime;
}
