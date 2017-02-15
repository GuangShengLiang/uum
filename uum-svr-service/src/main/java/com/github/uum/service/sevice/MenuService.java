package com.github.uum.service.sevice;

import com.github.uum.persist.mapper.MenuMapper;
import com.github.uum.persist.model.UumMenu;
import com.github.uum.service.domain.bo.MenuNode;
import com.github.uum.service.domain.bo.MenuTreeNode;
import com.github.uum.service.domain.bo.NavTreeNode;
import com.github.uum.service.domain.dto.MenuDto;
import com.github.easywork.utils.Asserts;
import com.github.easywork.utils.BeanMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by user on 2016/11/4.
 */
@Service
@Slf4j
public class MenuService {

    @Resource
    AccountService accountService;
    @Resource
    MenuMapper menuMapper;
    @Resource
    RoleService roleService;

    public UumMenu get(int id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public void save(MenuDto dto) {
        UumMenu menu = BeanMapper.map(dto, UumMenu.class);
        menu.setCtime(new Date());
        menuMapper.insertSelective(menu);
    }

    public void update(MenuDto dto) {
        UumMenu menu = BeanMapper.map(dto, UumMenu.class);
        int result = menuMapper.updateByPrimaryKeySelective(menu);
        Asserts.whenThrow(result != 1, "更新菜单id:d%失败", dto.getId());
    }

    public void delete(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    public void deleteMenuAndChildren(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
        getChildren(id).forEach(e -> menuMapper.deleteByPrimaryKey(e.getId()));
    }

    public List<MenuNode> getMenuTree() {
        List<UumMenu> menuList = getMenuList(null, null);
        return buildMenuTree(menuList);
    }

    public List<MenuNode> getMenuTreeOfAccount(Integer uid) {
        if (roleService.isAdmin(uid)) {
            return getMenuTree();
        }
        Set<Integer> menuIds = roleService.getMenuIdsOfAccount(uid);
        List<UumMenu> menus = getMenuList(menuIds, true);
        return buildMenuTree(menus);
    }

    public List<UumMenu> getMenuList(Set<Integer> ids, Boolean userStatus) {
        Example example = new Example(UumMenu.class);
        Example.Criteria criteria = example.createCriteria();
        Optional.ofNullable(userStatus).ifPresent(e -> criteria.andEqualTo("status", userStatus));
        if (!CollectionUtils.isEmpty(ids)) {
            criteria.andIn("id", ids);
        }
        return menuMapper.selectByExample(example);
    }

    public List<UumMenu> getParents(int id) {
        List<UumMenu> menuList = Lists.newLinkedList();
        UumMenu menu = get(id);
        Optional.ofNullable(menu).ifPresent(e -> {
            menuList.add(menu);
            menuList.addAll(getParents(menu.getParentId()));
        });
        return menuList;
    }

    public List<UumMenu> getChildren(int id) {
        List<UumMenu> allChildren = Lists.newLinkedList();
        UumMenu example = new UumMenu();
        example.setParentId(id);
        List<UumMenu> children = menuMapper.select(example);
        children.forEach(e -> {
            allChildren.add(e);
            allChildren.addAll(getChildren(e.getId()));
        });
        return allChildren;
    }

    public List<MenuTreeNode> getMenuTreeOfRole(Integer roleId) {

        Set<Integer> menuIds = roleService.getMenuIdsOfRole(roleId);
        List<UumMenu> menuList = getMenuList(menuIds, true);
        List<MenuNode> menuNodes = buildMenuTree(menuList);
        return buildBootstrapNode(menuNodes);
    }

    public List<MenuTreeNode> getMenuTreeForBootstrapTree() {
        List<MenuNode> menuList = getMenuTree();
        return buildBootstrapNode(menuList);
    }


    private List<MenuTreeNode> buildBootstrapNode(List<MenuNode> menuBos) {
        if (CollectionUtils.isEmpty(menuBos)) {
            return null;
        }
        List<MenuTreeNode> nodeList = Lists.newLinkedList();

        menuBos.forEach(e -> {
            MenuTreeNode node = BeanMapper.map(e, MenuTreeNode.class);
            nodeList.add(node);

            node.setHref(e.getTpl());
            node.setText(e.getName());

            node.setNodes(buildBootstrapNode(e.getChildren()));

        });
        return nodeList;
    }

    private List<MenuNode> buildMenuTree(List<UumMenu> menus) {
        List<MenuNode> root = Lists.newLinkedList();
        Map<Integer, MenuNode> menuMap = Maps.newHashMap();
        menus.forEach(e -> {
            menuMap.put(e.getId(), BeanMapper.map(e, MenuNode.class));
        });

        menuMap.forEach((k, v) -> {
            if (v.getParentId() == 0) {
                root.add(v);
            } else {
                Optional.ofNullable(menuMap.get(v.getParentId())).ifPresent(e -> e.getChildren().add(v));
                /*if (menuMap.get(v.getParentId()) ==null){
                    root.add(v);
                }else {
                    menuMap.get(v.getParentId()).getChildren().add(v);
                }*/
            }
        });
        return root;
    }

    public List<NavTreeNode> getNavMenuTree() {
        List<MenuNode> menuList = getMenuTree();
        return buildNavNode(menuList);
    }

    private List<NavTreeNode> buildNavNode(List<MenuNode> menuBos) {
        if (CollectionUtils.isEmpty(menuBos)) {
            return Lists.newLinkedList();
        }
        List<NavTreeNode> nodeList = Lists.newLinkedList();

        menuBos.forEach(e -> {
            NavTreeNode node = BeanMapper.map(e, NavTreeNode.class);
            nodeList.add(node);

            node.setLabel(e.getName());
            node.setChildren(buildNavNode(e.getChildren()));

        });
        return nodeList;
    }
}
