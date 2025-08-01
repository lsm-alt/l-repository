package com.codedata.rbac.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codedata.rbac.common.base.BaseEntity;

import java.util.Date;

/**
 * 角色实体类
 */
@TableName("sys_role")
public class RoleEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 备注
     */
    private String bak;
    /**
     * 更新用户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "roleName='" + roleName + '\'' +
                ", bak='" + bak + '\'' +
                ", updater=" + updater +
                ", updateDate=" + updateDate +
                '}';
    }
}
