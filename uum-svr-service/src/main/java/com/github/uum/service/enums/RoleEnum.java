package com.github.uum.service.enums;

/**
 * Created by user on 2016/11/30.
 */
public enum  RoleEnum {

    系统管理员(1);

    public final Integer id;

    RoleEnum(Integer id){
        this.id = id;
    }
    public static RoleEnum getByCode(Integer id){
        for (RoleEnum value : values()){
            if (value.id.equals(id)){
                return value;
            }
        }
        return null;
    }
}
