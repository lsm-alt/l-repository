package com.codedata.rbac.system.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "菜单视图")
public class LogView {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "操作类型")
    private String type;
    @ApiModelProperty(value = "日志内容")
    private String  content;
    @ApiModelProperty(value = "消耗时间(ms)")
    private Long time;
    @ApiModelProperty(value = "操作结果 1为成功 0为失败")
    private Integer result;
    @ApiModelProperty(value = "ip地址")
    private String ip;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "LogView{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", result=" + result +
                ", ip='" + ip + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
