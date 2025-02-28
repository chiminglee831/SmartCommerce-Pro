package com.smartcommerce.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcommerce.user.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends BaseMapper<Permission> {

    @Select("SELECT p.* FROM permission p " +
            "JOIN role_permission rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId} AND p.is_deleted = 0")
    List<Permission> findPermissionsByRoleId(@Param("roleId") Long roleId);
}