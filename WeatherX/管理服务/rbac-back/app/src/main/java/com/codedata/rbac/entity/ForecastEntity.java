package com.codedata.rbac.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codedata.rbac.common.base.BaseEntity;

@TableName("weather_forecast")
public class ForecastEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String forecastDate;
    private String timeWith;
    private String districtWith;
    private String filePath;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateDate;

    public ForecastEntity() {
    }

    public ForecastEntity( Long id, String forecastDate, String timeWith, String districtWith, String filePath, Integer updateDate) {
        this.id = id;
        this.forecastDate = forecastDate;
        this.timeWith = timeWith;
        this.districtWith = districtWith;
        this.filePath = filePath;
        this.updateDate = updateDate;
    }

    /**
     * 获取
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     * @return forecastDate
     */
    public String getForecastDate() {
        return forecastDate;
    }

    /**
     * 设置
     * @param forecastDate
     */
    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    /**
     * 获取
     * @return timeWith
     */
    public String getTimeWith() {
        return timeWith;
    }

    /**
     * 设置
     * @param timeWith
     */
    public void setTimeWith(String timeWith) {
        this.timeWith = timeWith;
    }

    /**
     * 获取
     * @return districtWith
     */
    public String getDistrictWith() {
        return districtWith;
    }

    /**
     * 设置
     * @param districtWith
     */
    public void setDistrictWith(String districtWith) {
        this.districtWith = districtWith;
    }

    /**
     * 获取
     * @return filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取
     * @return updateDate
     */
    public Integer getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置
     * @param updateDate
     */
    public void setUpdateDate(Integer updateDate) {
        this.updateDate = updateDate;
    }

    public String toString() {
        return "ForecastEntity{ id = " + id + ", forecastDate = " + forecastDate + ", timeWith = " + timeWith + ", districtWith = " + districtWith + ", filePath = " + filePath + ", updateDate = " + updateDate + "}";
    }
}
