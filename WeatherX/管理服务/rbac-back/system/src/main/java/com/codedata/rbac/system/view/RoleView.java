package com.codedata.rbac.system.view;

import com.codedata.rbac.common.group.AddGroup;
import com.codedata.rbac.common.group.DefaultGroup;
import com.codedata.rbac.common.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "角色管理")
public class RoleView implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @Null(message="执行添加时主键应为空", groups = AddGroup.class)
    @NotNull(message="执行更新时主键不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message="角色名称不能为空", groups = DefaultGroup.class)
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String bak;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @ApiModelProperty(value = "菜单ID列表")
    private List<Long> menuIdList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

    @Override
    public String toString() {
        return "RoleView{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", bak='" + bak + '\'' +
                ", createDate=" + createDate +
                ", menuIdList=" + menuIdList +
                '}';
    }
}
