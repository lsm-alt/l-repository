package com.codedata.rbac.common.base;

import com.codedata.rbac.common.group.AddGroup;
import com.codedata.rbac.common.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础视图
 */
public class BaseView implements Serializable {
    @ApiModelProperty(value = "id")
    @Null(message = "添加操作时id应为空", groups = AddGroup.class)
    @NotNull(message = "更新时id不能为空", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
