package com.codedata.rbac.system.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codedata.rbac.common.base.BaseEntity;

/**
 * 用户角色中间表
 */
@TableName("sys_user_role")
public class UserRoleEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户ID
     */
    private Long userId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
