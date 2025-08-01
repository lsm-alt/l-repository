package com.codedata.rbac.entity;

public class WeatherTable {
    public static final String YINYU_GUOZHAO = "阴雨/寡照";
    public static final String GANRE_RIRAO = "干热/日灼";
    public static final String DAFENG_BINGBAO = "大风/冰雹";
    public static final String GANHAN_ZHILAO = "干旱/渍涝";
    public  static final  String SHUANGDONG_DIWEN = "霜冻/低温";

    public static final String LOW = "低";
    public static final String MIDDLE = "中";
    public static final String HIGH = "高";


    private String name;       // 现象名称
    private String timePeriod; // 发生时间段
    private String probability; // 发生概率

    public WeatherTable() {
    }

    public WeatherTable(String name, String timePeriod, String probability) {
        this.name = name;
        this.timePeriod = timePeriod;
        this.probability = probability;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return timePeriod
     */
    public String getTimePeriod() {
        return timePeriod;
    }

    /**
     * 设置
     *
     * @param timePeriod
     */
    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    /**
     * 获取
     *
     * @return probability
     */
    public String getProbability() {
        return probability;
    }

    /**
     * 设置
     *
     * @param probability
     */
    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String toString() {
        return "WeatherDisasterProbability{name = " + name + ", timePeriod = " + timePeriod + ", probability = " + probability + "}";
    }
}
