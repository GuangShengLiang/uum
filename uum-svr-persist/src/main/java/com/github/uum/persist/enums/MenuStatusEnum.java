package com.github.uum.persist.enums;

/**
 * Created by lgs on 16-5-31.
 */
public enum MenuStatusEnum {
    None(-1),
    正常(1),
    删除(0);

    public final int code;

    MenuStatusEnum(int code) {
        this.code = code;
    }

    public static MenuStatusEnum getByCode(int code) {
        for (MenuStatusEnum e : MenuStatusEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return None;
    }

}
