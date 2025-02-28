package com.smartcommerce.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_role")
public class UserRole {

    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long roleId;
    
    private LocalDateTime createTime;
}