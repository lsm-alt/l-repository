package com.codedata.rbac.system.view;

import com.codedata.rbac.common.group.AddGroup;
import com.codedata.rbac.common.group.DefaultGroup;
import com.codedata.rbac.common.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "用户视图")
public class UserView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @Null(message="添加操作时id应为空", groups = AddGroup.class)
    @NotNull(message="用户id不能为空", groups = UpdateGroup.class)
    private Long id;
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message="用户名不能为空", groups = DefaultGroup.class)
    private String username;
    @ApiModelProperty(value = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message="密码不能为空", groups = AddGroup.class)
    private String password;
    @ApiModelProperty(value = "姓名", required = true)
    private String realName;
    @ApiModelProperty(value = "头像")
    private String headUrl;
    @ApiModelProperty(value = "性别   0：男   1：女    2：保密", required = true)
    @Range(min=0, max=2, message = "性别数值非法", groups = DefaultGroup.class)
    private Integer gender;
    @ApiModelProperty(value = "邮箱")
    @Email(message="邮箱不能为空", groups = DefaultGroup.class)
    private String email;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "状态 0:停用 1:正常", required = true)
    @Range(min=0, max=1, message = "用户状态值非法", groups = DefaultGroup.class)
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;
    @ApiModelProperty(value = "超级管理员 0:否 1:是")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer superAdmin;
    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIdList;
    @ApiModelProperty(value = "部门编号")
    private Long deptId;
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "身份标识")
    private String identity;
    @ApiModelProperty(value = "身份标识类型")
    private Integer identityType;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    @Override
    public String toString() {
        return "UserView{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                ", superAdmin=" + superAdmin +
                ", roleIdList=" + roleIdList +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", identity='" + identity + '\'' +
                ", identityType=" + identityType +
                '}';
    }
}
