package com.example.springboot.service;

import com.example.springboot.dto.ResultDTO;
import com.example.springboot.exception.CustomizeErrorCode;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

    /**
     * 登录
     * @param user 用户 参数为输入的email和pwd
     * @return
     */
    public Object login(User user){
        String email = user.getEmail();
        String password = user.getPassword();
        if (email==null||password==null){
            return ResultDTO.errorInfo(CustomizeErrorCode.EMAIL_OR_PWD_BLANK);
        }
        User login = userMapper.selectByEmailPwd(email, password);
        if (login==null){
            return ResultDTO.errorInfo(CustomizeErrorCode.EMAIL_OR_PWD_ERROR);
        }
        return ResultDTO.info(200,"登录成功");
    }
}
