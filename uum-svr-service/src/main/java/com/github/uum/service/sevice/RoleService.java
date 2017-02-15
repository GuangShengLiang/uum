package com.github.uum.service.sevice;

import com.github.uum.persist.mapper.RoleMapper;
import com.github.uum.persist.mapper.RoleMenuMapper;
import com.github.uum.persist.model.UumMenu;
import com.github.uum.persist.model.UumRole;
import com.github.uum.persist.model.UumRoleMenu;
import com.github.uum.service.domain.bo.RoleNode;
import com.github.uum.service.domain.bo.RoleTreeNode;
import com.github.uum.service.domain.dto.RoleDto;
import com.github.uum.service.domain.dto.RoleMenuDto;
import com.github.uum.service.enums.RoleEnum;
import com.github.easywork.utils.Asserts;
import com.github.easywork.utils.BeanMapper;
import com.github.easywork.utils.Datas;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by user on 2016/11/4.
 */
@Service
public class RoleService {

    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    AccountService accountService;
    @Resource
    MenuService menuService;

    public void save(RoleDto dto) {
        UumRole role = BeanMapper.map(dto, UumRole.class);
        roleMapper.insertSelective(role);

    }

    public void update(RoleDto dto) {
        UumRole role = BeanMapper.map(dto, UumRole.class);
        int result = roleMapper.updateByPrimaryKeySelective(role);
        Asserts.whenThrow(result != 1, "更新角色id:d%失败", dto.getId());
    }

    public void delete(int id) {
        int result = roleMapper.deleteByPrimaryKey(id);
        Asserts.whenThrow(result != 1, "删除角色id:d%失败", id);
        UumRoleMenu example = new UumRoleMenu();
        example.setRoleId(id);
        roleMenuMapper.delete(example);
    }
    public boolean isAdmin(Integer uid) {
        List<UumRole> roles = accountService.getRolesOfAccount(uid);
        for (UumRole role: roles){
            if(RoleEnum.getByCode(role.getId())==RoleEnum.系统管理员){
                return true;
            }
        }
        return false;
    }

    public Set<Integer> getMenuIdsOfAccount(Integer uid) {
        List<UumRole> roles = accountService.getRolesOfAccount(uid);
        return getMenuIds(roles);
    }

    public Set<Integer> getMenuIds(List<UumRole> roles) {
        Set<Integer> roleIds = Datas.newHashSet(roles, e -> e.getId());
        if (CollectionUtils.isEmpty(roleIds)) {
            return Sets.newLinkedHashSet();
        }
        Example example = new Example(UumRoleMenu.class);
        example.createCriteria().andIn("roleId", roleIds);
        return Datas.newHashSet(roleMenuMapper.selectByExample(example), e -> e.getMenuId());
    }

    public void addMenuAndParentMenuToRole(RoleMenuDto dto) {
        UumRoleMenu roleMenu = BeanMapper.map(dto, UumRoleMenu.class);
        UumRoleMenu record = roleMenuMapper.selectOne(roleMenu);
        Asserts.whenThrow(record != null, "菜单已存在");
        roleMenuMapper.insertSelective(roleMenu);
        List<UumMenu> parents = menuService.getParents(dto.getMenuId());
        parents.forEach(e -> {
            RoleMenuDto parent = new RoleMenuDto();
            parent.setRoleId(dto.getRoleId());
            parent.setMenuId(e.getId());
            addMenuToRole(parent);
        });
    }

    public void addMenuToRole(RoleMenuDto dto) {
        UumRoleMenu roleMenu = BeanMapper.map(dto, UumRoleMenu.class);
        UumRoleMenu record = roleMenuMapper.selectOne(roleMenu);
        if (record != null) {
            return;
        }
        roleMenuMapper.insertSelective(roleMenu);
    }

    public void removeMenuFromRole(RoleMenuDto dto) {
        UumRoleMenu roleMenu = BeanMapper.map(dto, UumRoleMenu.class);
        UumRoleMenu record = roleMenuMapper.selectOne(roleMenu);
        Asserts.notNull(record, "不能删除子角色的菜单");
        roleMenuMapper.deleteByPrimaryKey(record.getId());
    }
    public void removeMenuAndChildrenFromRole(RoleMenuDto dto) {
        UumRoleMenu roleMenu = BeanMapper.map(dto, UumRoleMenu.class);
        UumRoleMenu record = roleMenuMapper.selectOne(roleMenu);
        Asserts.notNull(record, "不能删除子角色的菜单");
        roleMenuMapper.deleteByPrimaryKey(record.getId());
        List<UumMenu> children = menuService.getChildren(dto.getMenuId());
        children.forEach(e->{
            roleMenuMapper.deleteByPrimaryKey(e.getId());
        });
    }
    public List<RoleTreeNode> getRoleTree() {
        List<UumRole> roles = roleMapper.selectByExample(null);
        List<RoleNode> roleNodes = buildRoleTree(roles);
        return buildBootstrapNode(roleNodes);
    }

    public Set<Integer> getMenuIdsOfRole(Integer id) {
        UumRole role = roleMapper.selectByPrimaryKey(id);
        List<UumRole> roleList = getAllChildren(role);
        List<UumRoleMenu> roleMenus = getMenuListOfRole(Datas.newHashSet(roleList, e -> e.getId()));
        return Datas.newHashSet(roleMenus, e -> e.getMenuId());
    }

    public List<UumRoleMenu> getMenuListOfRole(Set roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newLinkedList();
        }
        Example example = new Example(UumRoleMenu.class);
        example.createCriteria().andIn("roleId", roleIds);
        return roleMenuMapper.selectByExample(example);
    }

    public List<UumRole> getRoleList(Set roleIds, Boolean userStatus) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newLinkedList();
        }
        Example example = new Example(UumRole.class);
        Example.Criteria criteria = example.createCriteria();

        if (!CollectionUtils.isEmpty(roleIds)) {
            criteria.andIn("id", roleIds);
        }
        Optional.ofNullable(userStatus).ifPresent(e -> criteria.andEqualTo("status", userStatus));

        return roleMapper.selectByExample(example);
    }

    private List<UumRole> getAllChildren(UumRole role) {

        if (role == null) {
            return Lists.newLinkedList();
        }
        List<UumRole> roles = Lists.newLinkedList();
        roles.add(role);
        UumRole example = new UumRole();
        example.setParentId(role.getId());
        List<UumRole> roleList = roleMapper.select(example);
        roleList.forEach(e -> {
            roles.addAll(getAllChildren(e));
        });
        return roles;
    }

    private List<RoleTreeNode> buildBootstrapNode(List<RoleNode> menuBos) {
        if (CollectionUtils.isEmpty(menuBos)) {
            return null;
        }
        List<RoleTreeNode> nodeList = Lists.newLinkedList();

        menuBos.forEach(e -> {
            RoleTreeNode node = BeanMapper.map(e, RoleTreeNode.class);
            nodeList.add(node);

            node.setText(e.getName());

            node.setNodes(buildBootstrapNode(e.getChildren()));

        });
        return nodeList;
    }

    private List<RoleNode> buildRoleTree(List<UumRole> roles) {
        List<RoleNode> root = Lists.newLinkedList();
        Map<Integer, RoleNode> roleMap = Maps.newHashMap();
        roles.forEach(e -> {
            roleMap.put(e.getId(), BeanMapper.map(e, RoleNode.class));
        });

        roleMap.forEach((k, v) -> {
            if (v.getParentId() == 0) {
                root.add(v);
            } else {
                if (roleMap.get(v.getParentId()) ==null){
                    root.add(v);
                }else {
                    roleMap.get(v.getParentId()).getChildren().add(v);
                }
            }
        });
        return root;
    }
}
