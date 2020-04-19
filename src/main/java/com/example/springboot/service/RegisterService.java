package com.example.springboot.service;

import com.example.springboot.dto.ResultDTO;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.exception.CustomizeErrorCode;
import com.example.springboot.exception.CustomizeException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.mapper.VerifyMapper;
import com.example.springboot.model.User;
import com.example.springboot.model.Verify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    VerifyMapper verifyMapper;

    /**
     * 检查邮箱号是否注册
     * @param email 邮箱号
     * @return
     */
    public boolean checkRegister(String email){
        User user1 = userMapper.selectByEmail(email);
        if (user1==null){
            return true;
        }
        return false;
    }

    /**
     * 注册
     * @param userDTO 该对象参数含有email、pwd、code
     * @return ResultDTO
     */
    public ResultDTO register(UserDTO userDTO) {
        //checkRegister()返回结果为true表示可以注册
        if (!checkRegister(userDTO.getEmail())){
            return ResultDTO.errorInfo(CustomizeErrorCode.EMAIL_IS_EXIST);
        }
        userDTO.getCode();
        Verify verify = verifyMapper.selectVerify(userDTO.getCode(),userDTO.getEmail());
        if (verify==null){
            //验证码错误
            return ResultDTO.errorInfo(CustomizeErrorCode.VERIFY_IS_ERROR);
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        int flag = userMapper.insert(user);
        //注册成功
        if (flag==1){
            //登录成功将验证码删除
            verifyMapper.deleteByEmail(userDTO.getEmail());
            return ResultDTO.info(200,"注册成功");
        }
        //注册失败
        return ResultDTO.errorInfo(CustomizeErrorCode.REGISTER_FAIL);
    }

    /**
     * 发送邮件
     * @param email 邮箱号
     * @return ResultDTO
     */
    public ResultDTO sendCode(String email){
        //随机六位数验证码
        if (!checkRegister(email)){
            return ResultDTO.errorInfo(CustomizeErrorCode.EMAIL_IS_EXIST);
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            int code = (int)((Math.random() * 9 + 1) * 100000);
            message.setSubject("Hobo社区验证码");
            message.setText("欢迎加入Hobo社区！ 您的验证码是："+ code + "，请在5分钟内完成注册。");
            message.setTo(email);
            message.setFrom("longhuahobo@foxmail.com");
            mailSender.send(message);
            Verify verify = new Verify();
            //
            verify.setCode(code);
            verify.setEmail(email);
            verifyMapper.insert(verify);
            return ResultDTO.info(200,"邮件发送成功");
        }catch (MailException e){
            log.error("邮件发送出错" + e);
            return ResultDTO.errorInfo(CustomizeErrorCode.INVALID_ADDRESSES);
        }

    }

    /**
     * 使用多线程五分钟后清除验证码数据
     * @param email email
     */
    @Async
    public void removeCode(String email){
        try {
            System.out.println("线程开始"+System.currentTimeMillis());
            Thread.sleep(1000*60*5);
            verifyMapper.deleteByEmail(email);
            System.out.println("线程结束"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
