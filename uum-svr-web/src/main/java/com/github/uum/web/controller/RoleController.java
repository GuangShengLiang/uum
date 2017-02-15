package com.github.uum.web.controller;

import com.github.uum.service.domain.dto.RoleDto;
import com.github.uum.service.domain.dto.RoleMenuDto;
import com.github.uum.service.sevice.RoleService;
import com.github.uum.web.domain.req.RoleMenuReq;
import com.github.uum.web.domain.req.RoleReq;
import com.github.easywork.utils.BeanMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by user on 2016/11/3.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public Object getRoleTree() {
        return roleService.getRoleTree();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveRole(@RequestBody RoleReq req) {
        roleService.save(BeanMapper.map(req, RoleDto.class));
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public void deleteRole(@PathVariable int id) {
        roleService.delete(id);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public void updateRole(@PathVariable Integer id, @RequestBody RoleReq req) {
        RoleDto dto = BeanMapper.map(req, RoleDto.class);
        dto.setId(id);
        roleService.update(dto);
    }

    @RequestMapping(value = "menu", method = RequestMethod.POST)
    public void addMenuToRole(@RequestBody RoleMenuReq req) {

        roleService.addMenuAndParentMenuToRole(BeanMapper.map(req, RoleMenuDto.class));
    }

    @RequestMapping(value = "{roleId:\\d+}/menu/{menuId:\\d+}", method = RequestMethod.DELETE)
    public void removeMenuFromRole(@PathVariable Integer roleId, @PathVariable Integer menuId) {
        RoleMenuDto dto = new RoleMenuDto();
        dto.setMenuId(menuId);
        dto.setRoleId(roleId);
        roleService.removeMenuAndChildrenFromRole(dto);
    }

   /* @RequestMapping(value = "account/{uid}", method = RequestMethod.GET)
    public Object getAccountRoleTree(@PathVariable int uid) {
        return roleService.getMenuTree(uid);
    }*/
}
