package com.codedata.rbac.view;

public class ForecastView {

    private String rawText;
    private String month;
    private String date;
    private String hours;

    public ForecastView() {
    }

    public ForecastView(String rawText, String month, String date, String hours) {
        this.rawText = rawText;
        this.month = month;
        this.date = date;
        this.hours = hours;
    }

    /**
     * 获取
     * @return rawText
     */
    public String getRawText() {
        return rawText;
    }

    /**
     * 设置
     * @param rawText
     */
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    /**
     * 获取
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 设置
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
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
     * @return hours
     */
    public String getHours() {
        return hours;
    }

    /**
     * 设置
     * @param hours
     */
    public void setHours(String hours) {
        this.hours = hours;
    }

    public String toString() {
        return "ForecastView{rawText = " + rawText + ", month = " + month + ", date = " + date + ", hours = " + hours + "}";
    }
}
