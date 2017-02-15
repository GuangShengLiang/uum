package com.github.uum.service.domain.bo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by user on 2016/11/4.
 */
@Data
public class MenuNode {

    private Integer id;

    private Integer parentId;

    private String name;

    private String tpl;

    private String icon;

    private Integer sort;

    private String css;

    private Boolean status;

    private List<MenuNode> children = Lists.newLinkedList();
}
