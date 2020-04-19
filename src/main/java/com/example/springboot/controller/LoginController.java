package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    public String goLogin(){
        return "loginRegisterForm";
    }

    /**
     * 登录
     * @param user 将登录的参数-email、pwd封装到User
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public Object login(User user){
        return loginService.login(user);
    }
}
