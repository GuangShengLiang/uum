package com.github.uum.service.domain.bo;

import lombok.Data;

import java.util.List;

/**
 * @Author lgs
 * @Date 16-11-26 下午4:58
 */
@Data
public class RoleTreeNode {

    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private String text;

    private List<RoleTreeNode> nodes;


}
