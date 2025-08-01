package com.codedata.rbac.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDay {

    @JsonProperty("fxDate")
    private String fxDate;

    @JsonProperty("sunrise")
    private String sunrise;

    @JsonProperty("sunset")
    private String sunset;

    @JsonProperty("moonrise")
    private String moonrise;

    @JsonProperty("moonset")
    private String moonset;

    @JsonProperty("moonPhase")
    private String moonPhase;

    @JsonProperty("moonPhaseIcon")
    private String moonPhaseIcon;

    @JsonProperty("tempMax")
    private String tempMax;

    @JsonProperty("tempMin")
    private String tempMin;

    @JsonProperty("iconDay")
    private String iconDay;

    @JsonProperty("textDay")
    private String textDay;

    @JsonProperty("iconNight")
    private String iconNight;

    @JsonProperty("textNight")
    private String textNight;

    @JsonProperty("wind360Day")
    private String wind360Day;

    @JsonProperty("windDirDay")
    private String windDirDay;

    @JsonProperty("windScaleDay")
    private String windScaleDay;

    @JsonProperty("windSpeedDay")
    private String windSpeedDay;

    @JsonProperty("wind360Night")
    private String wind360Night;

    @JsonProperty("windDirNight")
    private String windDirNight;

    @JsonProperty("windScaleNight")
    private String windScaleNight;

    @JsonProperty("windSpeedNight")
    private String windSpeedNight;

    @JsonProperty("humidity")
    private String humidity;

    @JsonProperty("precip")
    private String precip;

    @JsonProperty("pressure")
    private String pressure;

    @JsonProperty("vis")
    private String vis;

    @JsonProperty("cloud")
    private String cloud;

    @JsonProperty("uvIndex")
    private String uvIndex;

    public WeatherDay() {
    }

    public WeatherDay(String fxDate, String sunrise, String sunset, String moonrise, String moonset, String moonPhase, String moonPhaseIcon, String tempMax, String tempMin, String iconDay, String textDay, String iconNight, String textNight, String wind360Day, String windDirDay, String windScaleDay, String windSpeedDay, String wind360Night, String windDirNight, String windScaleNight, String windSpeedNight, String humidity, String precip, String pressure, String vis, String cloud, String uvIndex) {
        this.fxDate = fxDate;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moonPhase = moonPhase;
        this.moonPhaseIcon = moonPhaseIcon;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.iconDay = iconDay;
        this.textDay = textDay;
        this.iconNight = iconNight;
        this.textNight = textNight;
        this.wind360Day = wind360Day;
        this.windDirDay = windDirDay;
        this.windScaleDay = windScaleDay;
        this.windSpeedDay = windSpeedDay;
        this.wind360Night = wind360Night;
        this.windDirNight = windDirNight;
        this.windScaleNight = windScaleNight;
        this.windSpeedNight = windSpeedNight;
        this.humidity = humidity;
        this.precip = precip;
        this.pressure = pressure;
        this.vis = vis;
        this.cloud = cloud;
        this.uvIndex = uvIndex;
    }

    /**
     * 获取
     * @return fxDate
     */
    public String getFxDate() {
        return fxDate;
    }

    /**
     * 设置
     * @param fxDate
     */
    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    /**
     * 获取
     * @return sunrise
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     * 设置
     * @param sunrise
     */
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * 获取
     * @return sunset
     */
    public String getSunset() {
        return sunset;
    }

    /**
     * 设置
     * @param sunset
     */
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    /**
     * 获取
     * @return moonrise
     */
    public String getMoonrise() {
        return moonrise;
    }

    /**
     * 设置
     * @param moonrise
     */
    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    /**
     * 获取
     * @return moonset
     */
    public String getMoonset() {
        return moonset;
    }

    /**
     * 设置
     * @param moonset
     */
    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    /**
     * 获取
     * @return moonPhase
     */
    public String getMoonPhase() {
        return moonPhase;
    }

    /**
     * 设置
     * @param moonPhase
     */
    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    /**
     * 获取
     * @return moonPhaseIcon
     */
    public String getMoonPhaseIcon() {
        return moonPhaseIcon;
    }

    /**
     * 设置
     * @param moonPhaseIcon
     */
    public void setMoonPhaseIcon(String moonPhaseIcon) {
        this.moonPhaseIcon = moonPhaseIcon;
    }

    /**
     * 获取
     * @return tempMax
     */
    public String getTempMax() {
        return tempMax;
    }

    /**
     * 设置
     * @param tempMax
     */
    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    /**
     * 获取
     * @return tempMin
     */
    public String getTempMin() {
        return tempMin;
    }

    /**
     * 设置
     * @param tempMin
     */
    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    /**
     * 获取
     * @return iconDay
     */
    public String getIconDay() {
        return iconDay;
    }

    /**
     * 设置
     * @param iconDay
     */
    public void setIconDay(String iconDay) {
        this.iconDay = iconDay;
    }

    /**
     * 获取
     * @return textDay
     */
    public String getTextDay() {
        return textDay;
    }

    /**
     * 设置
     * @param textDay
     */
    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    /**
     * 获取
     * @return iconNight
     */
    public String getIconNight() {
        return iconNight;
    }

    /**
     * 设置
     * @param iconNight
     */
    public void setIconNight(String iconNight) {
        this.iconNight = iconNight;
    }

    /**
     * 获取
     * @return textNight
     */
    public String getTextNight() {
        return textNight;
    }

    /**
     * 设置
     * @param textNight
     */
    public void setTextNight(String textNight) {
        this.textNight = textNight;
    }

    /**
     * 获取
     * @return wind360Day
     */
    public String getWind360Day() {
        return wind360Day;
    }

    /**
     * 设置
     * @param wind360Day
     */
    public void setWind360Day(String wind360Day) {
        this.wind360Day = wind360Day;
    }

    /**
     * 获取
     * @return windDirDay
     */
    public String getWindDirDay() {
        return windDirDay;
    }

    /**
     * 设置
     * @param windDirDay
     */
    public void setWindDirDay(String windDirDay) {
        this.windDirDay = windDirDay;
    }

    /**
     * 获取
     * @return windScaleDay
     */
    public String getWindScaleDay() {
        return windScaleDay;
    }

    /**
     * 设置
     * @param windScaleDay
     */
    public void setWindScaleDay(String windScaleDay) {
        this.windScaleDay = windScaleDay;
    }

    /**
     * 获取
     * @return windSpeedDay
     */
    public String getWindSpeedDay() {
        return windSpeedDay;
    }

    /**
     * 设置
     * @param windSpeedDay
     */
    public void setWindSpeedDay(String windSpeedDay) {
        this.windSpeedDay = windSpeedDay;
    }

    /**
     * 获取
     * @return wind360Night
     */
    public String getWind360Night() {
        return wind360Night;
    }

    /**
     * 设置
     * @param wind360Night
     */
    public void setWind360Night(String wind360Night) {
        this.wind360Night = wind360Night;
    }

    /**
     * 获取
     * @return windDirNight
     */
    public String getWindDirNight() {
        return windDirNight;
    }

    /**
     * 设置
     * @param windDirNight
     */
    public void setWindDirNight(String windDirNight) {
        this.windDirNight = windDirNight;
    }

    /**
     * 获取
     * @return windScaleNight
     */
    public String getWindScaleNight() {
        return windScaleNight;
    }

    /**
     * 设置
     * @param windScaleNight
     */
    public void setWindScaleNight(String windScaleNight) {
        this.windScaleNight = windScaleNight;
    }

    /**
     * 获取
     * @return windSpeedNight
     */
    public String getWindSpeedNight() {
        return windSpeedNight;
    }

    /**
     * 设置
     * @param windSpeedNight
     */
    public void setWindSpeedNight(String windSpeedNight) {
        this.windSpeedNight = windSpeedNight;
    }

    /**
     * 获取
     * @return humidity
     */
    public String getHumidity() {
        return humidity;
    }

    /**
     * 设置
     * @param humidity
     */
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    /**
     * 获取
     * @return precip
     */
    public String getPrecip() {
        return precip;
    }

    /**
     * 设置
     * @param precip
     */
    public void setPrecip(String precip) {
        this.precip = precip;
    }

    /**
     * 获取
     * @return pressure
     */
    public String getPressure() {
        return pressure;
    }

    /**
     * 设置
     * @param pressure
     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    /**
     * 获取
     * @return vis
     */
    public String getVis() {
        return vis;
    }

    /**
     * 设置
     * @param vis
     */
    public void setVis(String vis) {
        this.vis = vis;
    }

    /**
     * 获取
     * @return cloud
     */
    public String getCloud() {
        return cloud;
    }

    /**
     * 设置
     * @param cloud
     */
    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    /**
     * 获取
     * @return uvIndex
     */
    public String getUvIndex() {
        return uvIndex;
    }

    /**
     * 设置
     * @param uvIndex
     */
    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String toString() {
        return "WeatherDay{fxDate = " + fxDate + ", sunrise = " + sunrise + ", sunset = " + sunset + ", moonrise = " + moonrise + ", moonset = " + moonset + ", moonPhase = " + moonPhase + ", moonPhaseIcon = " + moonPhaseIcon + ", tempMax = " + tempMax + ", tempMin = " + tempMin + ", iconDay = " + iconDay + ", textDay = " + textDay + ", iconNight = " + iconNight + ", textNight = " + textNight + ", wind360Day = " + wind360Day + ", windDirDay = " + windDirDay + ", windScaleDay = " + windScaleDay + ", windSpeedDay = " + windSpeedDay + ", wind360Night = " + wind360Night + ", windDirNight = " + windDirNight + ", windScaleNight = " + windScaleNight + ", windSpeedNight = " + windSpeedNight + ", humidity = " + humidity + ", precip = " + precip + ", pressure = " + pressure + ", vis = " + vis + ", cloud = " + cloud + ", uvIndex = " + uvIndex + "}";
    }
}
