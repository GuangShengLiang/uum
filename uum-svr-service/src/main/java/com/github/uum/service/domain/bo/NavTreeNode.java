package com.github.uum.service.domain.bo;

import lombok.Data;

import java.util.List;

/**
 * @Author lgs
 * @Date 16-11-26 下午4:58
 */
@Data
public class NavTreeNode {

    private Integer id;

    private Integer parentId;

    private String name;

    private String tpl;

    private String icon;

    private Integer sort;

    private String css;

    private Boolean status;

    private String label;

    private List<NavTreeNode> children;


}
