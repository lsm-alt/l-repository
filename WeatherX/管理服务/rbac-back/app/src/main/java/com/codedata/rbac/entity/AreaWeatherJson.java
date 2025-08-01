package com.codedata.rbac.entity;

public class AreaWeatherJson {
    private String timeRange;
    private String area;
    private String weather;
    private String windDirection;
    private String tempLow;
    private String tempHigh;

    public AreaWeatherJson() {
    }

    public AreaWeatherJson(String timeRange, String area, String weather, String windDirection, String tempLow, String tempHigh) {
        this.timeRange = timeRange;
        this.area = area;
        this.weather = weather;
        this.windDirection = windDirection;
        this.tempLow = tempLow;
        this.tempHigh = tempHigh;
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
     * @return area
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
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
        return "AreaWeatherJson{timeRange = " + timeRange + ", area = " + area + ", weather = " + weather + ", windDirection = " + windDirection + ", tempLow = " + tempLow + ", tempHigh = " + tempHigh + "}";
    }
}
