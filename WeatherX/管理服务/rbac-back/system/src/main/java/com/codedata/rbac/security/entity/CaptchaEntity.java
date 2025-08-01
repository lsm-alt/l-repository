package com.codedata.rbac.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@TableName("sys_captcha")
@ApiModel("验证码实体")
public class CaptchaEntity {
    @TableId(type = IdType.INPUT)
    /**
     * 主键  手动输入
     */
    private String uuid;
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private Date expireTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "CaptchaEntity{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
