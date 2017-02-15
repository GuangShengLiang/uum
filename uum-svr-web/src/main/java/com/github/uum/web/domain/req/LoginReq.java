package com.github.uum.web.domain.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by user on 2016/11/3.
 */
@Data
public class LoginReq {
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    private String returnUrl;
}
