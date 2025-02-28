package com.smartcommerce.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcommerce.user.entity.RolePermission;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends BaseMapper<RolePermission> {
}