package com.github.uum.web.intercepter;

import com.github.uum.service.sevice.AccountService;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

      for(Cookie cookie:request.getCookies()){
          if (cookie.getName().equals("token")){
              if (cookie.getValue().equals("token")){
                  return true;
              }
          }
      }
//        response.sendRedirect("http://dashboard.github.co/loginPage?");
        return false;
    }
}
