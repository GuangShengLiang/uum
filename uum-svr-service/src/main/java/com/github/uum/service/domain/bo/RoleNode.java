package com.github.uum.service.domain.bo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Author lgs
 * @Date 16-11-23 下午11:05
 */
@Data
public class RoleNode {

    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private List<RoleNode> children = Lists.newLinkedList();
}
