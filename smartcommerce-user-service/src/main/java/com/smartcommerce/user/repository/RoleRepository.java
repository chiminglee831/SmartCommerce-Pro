package com.smartcommerce.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcommerce.user.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends BaseMapper<Role> {

    @Select("SELECT r.* FROM role r " +
            "JOIN user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND r.is_deleted = 0")
    List<Role> findRolesByUserId(@Param("userId") Long userId);
}