package com.codedata.rbac.system.view;

import com.codedata.rbac.common.base.BaseTreeNode;
import com.codedata.rbac.common.group.AddGroup;
import com.codedata.rbac.common.group.DefaultGroup;
import com.codedata.rbac.common.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "菜单视图")
public class MenuView extends BaseTreeNode<MenuView> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @Null(message="执行添加操作时id需要为空", groups = AddGroup.class)
    @NotNull(message="执行更新操作时id不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "上级ID")
    @NotNull(message="菜单上级ID不能为空", groups = DefaultGroup.class)
    private Long pid;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message="菜单名称不能为空", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "类型  0：菜单   1：按钮")
    @Range(min=0, max=1, message = "菜单类型不能为空", groups = DefaultGroup.class)
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：system:user:list,system:user:save)" )
    private String permissions;

    @ApiModelProperty(value = "排序")
    @Min(value = 0, message = "排序号不能为空", groups = DefaultGroup.class)
    private Integer sort;
    /**
     * 菜单详情
     */
    @ApiModelProperty(value = "菜单详情")
    private String detail;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @ApiModelProperty(value = "上级菜单名称")
    private String parentName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPid() {
        return pid;
    }

    @Override
    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "MenuView{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", icon='" + icon + '\'' +
                ", permissions='" + permissions + '\'' +
                ", sort=" + sort +
                ", detail='" + detail + '\'' +
                ", createDate=" + createDate +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
