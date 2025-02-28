package com.smartcommerce.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcommerce.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE username = #{username} AND is_deleted = 0")
    User findByUsername(@Param("username") String username);
    
    @Select("SELECT * FROM user WHERE email = #{email} AND is_deleted = 0")
    User findByEmail(@Param("email") String email);
    
    @Select("SELECT * FROM user WHERE mobile = #{mobile} AND is_deleted = 0")
    User findByMobile(@Param("mobile") String mobile);
}