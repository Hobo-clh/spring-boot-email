package com.example.springboot.controller;

import com.example.springboot.dto.ResultDTO;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.exception.CustomizeErrorCode;
import com.example.springboot.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class registerController {

    @Autowired
    RegisterService registerService;
    @Autowired
    JavaMailSender mailSender;

    /**
     * 注册
     * 将注册参数code、email、pwd封装带UserDTO中
     * @param userDTO
     * @return ResultDTO 返回json格式，带有code和message
     */
    @ResponseBody
    @PostMapping("/register")
    public ResultDTO register(UserDTO userDTO){
        System.out.println(userDTO);
        if (StringUtils.isBlank(userDTO.getEmail())&&StringUtils.isBlank(userDTO.getPassword())){
            return ResultDTO.errorInfo(CustomizeErrorCode.EMAIL_OR_PWD_BLANK);
        }
        //表示不能注册 邮箱名重复
        return registerService.register(userDTO);

    }

    /**
     * 发送验证码
     * @param email 邮箱号
     * @return ResultDTO 返回json格式，带有code和message
     */
    @ResponseBody
    @PostMapping("/sendCode")
    public ResultDTO sendCode(@RequestParam("email") String email){
        System.out.println(email);
        if (StringUtils.isBlank(email)){
            return ResultDTO.info(210,"邮箱不能为空");
        }
        ResultDTO resultDTO = registerService.sendCode(email);
        if (resultDTO.getCode()==200) {
            //多线程五分钟后删除
            registerService.removeCode(email);
        }
        return resultDTO;
    }
}
