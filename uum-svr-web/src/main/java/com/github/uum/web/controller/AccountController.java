package com.github.uum.web.controller;

import com.github.uum.service.domain.dto.AccountDto;
import com.github.uum.service.domain.dto.AccountRoleDto;
import com.github.uum.service.domain.qo.AccountPageQo;
import com.github.uum.service.sevice.AccountService;
import com.github.uum.service.sevice.RoleService;
import com.github.uum.web.domain.req.AccountPageReq;
import com.github.uum.web.domain.req.AccountReq;
import com.github.uum.web.domain.req.AccountRoleReq;
import com.github.easywork.http.HttpDataGridPageResponse;
import com.github.easywork.utils.BeanMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by user on 2016/11/3.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    AccountService accountService;
    @Resource
    RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpDataGridPageResponse query(AccountPageReq req) {
        AccountPageQo qo = BeanMapper.map(req, AccountPageQo.class);
        qo.setPageSize(req.getRows());
        qo.setPageNo(req.getPage());
        return HttpDataGridPageResponse.success(accountService.queryPage(qo));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody AccountReq req) {
        accountService.save(BeanMapper.map(req, AccountDto.class));
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody AccountReq req) {
        AccountDto dto = BeanMapper.map(req, AccountDto.class);
        dto.setId(id);
        accountService.update(BeanMapper.map(req, AccountDto.class));
    }

    /* @RequestMapping(value = "/{id}", method = RequestMethod.GET)
     public JsonResponse get(@PathVariable Integer id) {
         UumAccount account = accountService.get(name);
         return account != null ? JsonResponse.success() : JsonResponse.fail("用户不存在");
     }*/

//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Object getByName(@PathVariable String name) {
        return accountService.getByName(name);
    }


    @RequestMapping(value = "role/{uid:\\d+}", method = RequestMethod.GET)
    public Object getRoleOfAccount(@PathVariable Integer uid) {
        return accountService.getRolesOfAccount(uid);
    }

    @RequestMapping(value = "role", method = RequestMethod.POST)
    public void addRoleToAccount(@RequestBody AccountRoleReq req) {
        accountService.addRoleToAccount(BeanMapper.map(req, AccountRoleDto.class));
    }

    @RequestMapping(value = "{uid:\\d+}/role/{roleId:\\d+}", method = RequestMethod.DELETE)
    public void removeRoleFromAccount(@PathVariable Integer uid, @PathVariable Integer roleId) {
        AccountRoleDto dto = new AccountRoleDto();
        dto.setRoleId(roleId);
        dto.setUid(uid);
        accountService.removeRoleFromAccount(dto);
    }
}
