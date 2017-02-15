package com.github.uum.service.domain.bo;

import com.github.uum.persist.model.UumAccount;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lgs
 * @Date 16-11-23 下午10:23
 */
@Data
public class AccountBo extends UumAccount {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date getCtimeStr() {
        return this.getCtime();
    }

}
