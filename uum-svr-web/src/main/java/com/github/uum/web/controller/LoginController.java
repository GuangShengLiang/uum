package com.github.uum.web.controller;

import com.github.uum.service.sevice.AccountService;
import com.github.uum.web.domain.req.LoginReq;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by user on 2016/11/3.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Resource
    AccountService accountService;


    @RequestMapping(value = "account/login", method = RequestMethod.POST)
    public String login(LoginReq loginReq, HttpServletResponse response) throws UnsupportedEncodingException {


        accountService.login(loginReq.getName(),loginReq.getPassword());
        Cookie cookie = new Cookie("token", "token");
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        if (StringUtils.isEmpty(loginReq.getReturnUrl())){
            return "redirect:/index.html";
        }

        return URLDecoder.decode(loginReq.getReturnUrl(),"UTF-8");
    }

}
