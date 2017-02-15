package com.github.uum.service.sevice;

import com.github.uum.persist.mapper.AccountMapper;
import com.github.uum.persist.mapper.AccountRoleMapper;
import com.github.uum.persist.model.UumAccount;
import com.github.uum.persist.model.UumAccountRole;
import com.github.uum.persist.model.UumRole;
import com.github.uum.service.domain.bo.AccountBo;
import com.github.uum.service.domain.dto.AccountDto;
import com.github.uum.service.domain.dto.AccountRoleDto;
import com.github.uum.service.domain.qo.AccountPageQo;
import com.github.easywork.domain.page.PageResult;
import com.github.easywork.utils.Asserts;
import com.github.easywork.utils.BeanMapper;
import com.github.easywork.utils.Datas;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2016/11/4.
 */
@Service
public class AccountService {

    @Resource
    AccountMapper accountMapper;
    @Resource
    RoleService roleService;
    @Resource
    AccountRoleMapper accountRoleMapper;

    public UumAccount getByName(String name) {
        UumAccount example = new UumAccount();
        example.setName(name);
        return accountMapper.selectOne(example);
    }

    public UumAccount login(String name ,String password){
        UumAccount account = getByName(name);
        Asserts.notNull(account,"用户不存在");
        Asserts.whenThrow(!account.getPassword().equals(password),"密码不正确");
        return account;
    }

    public void save(AccountDto dto) {
        UumAccount acc = getByName(dto.getName());
        Asserts.notNull(acc, "用户%s已存在", dto.getName());
        UumAccount account = BeanMapper.map(dto, UumAccount.class);
        account.setCtime(new Date());
        accountMapper.insertSelective(account);
    }

    public void update(AccountDto dto) {
        UumAccount account = BeanMapper.map(dto, UumAccount.class);
        int result = accountMapper.updateByPrimaryKeySelective(account);
        Asserts.whenThrow(result != 1, "更新用户uid:d%失败", dto.getId());
    }

    public PageResult<AccountBo> queryPage(AccountPageQo qo) {
        UumAccount example = BeanMapper.map(qo, UumAccount.class);
        List<UumAccount> list = accountMapper.select(example);

        return new PageResult(BeanMapper.mapAsList(list, AccountBo.class), 10);
    }

    public void removeRoleFromAccount(AccountRoleDto dto) {
        UumAccountRole accountRole = BeanMapper.map(dto, UumAccountRole.class);
        UumAccountRole record = accountRoleMapper.selectOne(accountRole);
        Asserts.notNull(record, "你没有该角色");
        accountRoleMapper.deleteByPrimaryKey(record.getId());
    }

    public void addRoleToAccount(AccountRoleDto dto) {
        UumAccountRole accountRole = BeanMapper.map(dto, UumAccountRole.class);
        UumAccountRole record = accountRoleMapper.selectOne(accountRole);
        if (record != null) {
            return;
        }
        accountRoleMapper.insertSelective(accountRole);
    }

    public List<UumRole> getRolesOfAccount(Integer uid) {

        UumAccountRole example = new UumAccountRole();
        example.setUid(uid);
        List<UumAccountRole> accountRoles = accountRoleMapper.select(example);
        return roleService.getRoleList(Datas.newHashSet(accountRoles, e -> e.getRoleId()), true);
    }
}
