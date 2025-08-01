package com.codedata.rbac.entity;

public class TimeJson {
    private static final long serialVersionUID = 1L;
    private String date; //预报的目标日期
    private String timeRange; //时间范围
    private String weather; //	天气描述
    private String windDirection; //风向、雨

    private String tempLow; //最低温度（℃）
    private String tempHigh;  //最高温度（℃）

    public TimeJson() {
    }

    public TimeJson(String date, String timeRange, String weather, String windDirection, String tempLow, String tempHigh) {
        this.date = date;
        this.timeRange = timeRange;
        this.weather = weather;
        this.windDirection = windDirection;
        this.tempLow = tempLow;
        this.tempHigh = tempHigh;
    }

    /**
     * 获取
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取
     * @return timeRange
     */
    public String getTimeRange() {
        return timeRange;
    }

    /**
     * 设置
     * @param timeRange
     */
    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * 获取
     * @return weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * 设置
     * @param weather
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * 获取
     * @return windDirection
     */
    public String getWindDirection() {
        return windDirection;
    }

    /**
     * 设置
     * @param windDirection
     */
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * 获取
     * @return tempLow
     */
    public String getTempLow() {
        return tempLow;
    }

    /**
     * 设置
     * @param tempLow
     */
    public void setTempLow(String tempLow) {
        this.tempLow = tempLow;
    }

    /**
     * 获取
     * @return tempHigh
     */
    public String getTempHigh() {
        return tempHigh;
    }

    /**
     * 设置
     * @param tempHigh
     */
    public void setTempHigh(String tempHigh) {
        this.tempHigh = tempHigh;
    }

    public String toString() {
        return "TimeJson{ date = " + date + ", timeRange = " + timeRange + ", weather = " + weather + ", windDirection = " + windDirection + ", tempLow = " + tempLow + ", tempHigh = " + tempHigh + "}";
    }
}
