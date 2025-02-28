package com.smartcommerce.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("permission")
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String permissionName;
    
    private String permissionDesc;
    
    private String permissionPath;
    
    @TableField("is_deleted")
    @TableLogic
    @Builder.Default
    private Boolean deleted = false;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}