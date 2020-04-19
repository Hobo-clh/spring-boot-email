package com.example.springboot;

import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.User;
import com.example.springboot.service.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SpringBootEmailApplicationTests {

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RegisterService registerService;

    @Test
    void contextLoads() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("今晚开会");
        message.setText("大家，好！\n今晚7:30在教学楼201开班委会，请各位班委准时参加！ \n谢谢！");
        message.setTo("3067332706@qq.com");
        message.setFrom("longhuahobo@foxmail.com");
        javaMailSender.send(message);

    }

    @Test
    void test(){
        User user = new User();
        user.setEmail("807255841@qq.com");
        user.setPassword("123456");
        userMapper.insert(user);
    }
    @Test
    void select(){
        User user = new User();
        user.setId(1L);
        user.setEmail("807255841@qq.com");
        User user1 = userMapper.selectById(user.getId());
        System.out.println(user1);
        System.out.println("------------");
        User user2 = userMapper.selectByEmail(user.getEmail());
        System.out.println(user2);
    }

    @Test
    void test3(){
        User user = new User();
        user.setEmail("80725@qq.com");
        User user1 = userMapper.selectByEmail(user.getEmail());
        if (user1==null){
            System.out.println("空的");
        }else {
            System.out.println("不是空的");
        }
    }

    @Test
    void test4(){
        String email = "3067332706@qq.com";
//        Boolean flag = registerService.sendCode(email);
        Boolean flag = true;
        System.out.println(email);
        if (flag){
            registerService.removeCode(email);
        }
    }

}
