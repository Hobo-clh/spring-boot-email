package com.example.springboot.mapper;

import com.example.springboot.model.Verify;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VerifyMapper {

    /**
     * 插入验证表
     * @param verify 带有email和code参数
     * @return
     */
    @Insert("insert into verify(email,code) values(#{email},#{code})")
    int insert(Verify verify);

    /**
     * 使用验证码和邮箱号查找验证表
     * @param code 验证码
     * @param email 邮箱号
     * @return
     */
    @Select("select * from verify where code = #{code} and email = #{email}")
    Verify selectVerify(String code,String email);

    /**
     * 使用邮箱号删除验证表
     * @param email 邮箱号
     * @return
     */
    @Delete("delete from verify where email = #{email}")
    int deleteByEmail(String email);
}
