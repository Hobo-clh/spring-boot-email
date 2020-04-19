package com.example.springboot.mapper;

import com.example.springboot.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 插入用户表
     * @param user 用户，带有email、password参数
     * @return
     */
    @Insert("insert into user(email,password) values(#{email},#{password})")
    int insert(User user);

    /**
     * 使用id查找用户
     * @param id 用户id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User selectById(Long id);

    /**
     * 使用邮箱号和密码查找用户
     * @param email 邮箱号
     * @param password  密码
     * @return
     */
    @Select("select * from user where email = #{email} and password = #{password} ")
    User selectByEmailPwd(String email, String password);

    /**
     * 使用邮箱号查找用户
     * @param email 邮箱号
     * @return
     */
    @Select("select * from user where email = #{email}")
    User selectByEmail(String email);
}
