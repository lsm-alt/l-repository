package com.codedata.rbac.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codedata.rbac.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
//equals() / hashCode() 包含 父类（BaseEntity） 中的字段
@EqualsAndHashCode(callSuper = true)
@TableName("weather_period")
public class PeriodEntity extends BaseEntity {
    private Integer period;
    private String fileName;
    private String filePath;
    @TableField(fill = FieldFill.INSERT)
    private Date updateDate;

}
