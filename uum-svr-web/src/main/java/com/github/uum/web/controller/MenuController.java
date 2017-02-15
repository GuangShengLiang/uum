package com.github.uum.web.controller;

import com.github.uum.service.domain.dto.MenuDto;
import com.github.uum.service.sevice.MenuService;
import com.github.uum.web.domain.req.MenuReq;
import com.github.easywork.json.JsonResponse;
import com.github.easywork.utils.BeanMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by user on 2016/11/3.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    MenuService menuService;

    @RequestMapping(method = RequestMethod.GET)
    public Object getMenuTree() {
        return menuService.getMenuTreeForBootstrapTree();
    }

    @RequestMapping(value = "all_nav", method = RequestMethod.GET)
    public JsonResponse getNavMenuTree() {
        return JsonResponse.success(menuService.getNavMenuTree());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody MenuReq req) {
        menuService.save(BeanMapper.map(req, MenuDto.class));
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public void deleteMenu(@PathVariable int id) {
        menuService.deleteMenuAndChildren(id);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public void updateMenu(@PathVariable Integer id, @RequestBody MenuReq req) {
        MenuDto dto = BeanMapper.map(req, MenuDto.class);
        dto.setId(id);
        menuService.update(dto);
    }

    @RequestMapping(value = "role/{roleId:\\d+}", method = RequestMethod.GET)
    public Object getMenuTreeOfRole(@PathVariable int roleId) {
        return menuService.getMenuTreeOfRole(roleId);
    }

    @RequestMapping(value = "account/{uid:\\d+}", method = RequestMethod.GET)
    public Object getMenuTreeOfAccount(@PathVariable int uid) {
        return menuService.getMenuTreeOfAccount(uid);
    }
}
