package com.github.uum.persist.enums;

/**
 * Created by lgs on 16-5-31.
 */
public enum AccountStatusEnum {
    None(-1),
    正常(0),
    删除(1),
    离职(2);

    public final int code;

    AccountStatusEnum(int code) {
        this.code = code;
    }

    public static AccountStatusEnum getByCode(int code) {
        for (AccountStatusEnum e : AccountStatusEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return None;
    }

}
