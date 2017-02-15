package com.github.uum.web.domain.req;

import com.github.easywork.domain.page.GridPageQo;
import lombok.Data;

/**
 * @Author lgs
 * @Date 16-11-24 下午11:51
 */
@Data
public class AccountPageReq extends GridPageQo {

    private Integer id;

    private Integer status;
}
