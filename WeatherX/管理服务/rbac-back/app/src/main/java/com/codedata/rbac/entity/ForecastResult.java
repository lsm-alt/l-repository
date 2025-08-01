package com.codedata.rbac.entity;

import java.util.List;

public class ForecastResult {
    private List<TimeJson> timeList;
    private List<AreaWeatherJson> areaList;

    public ForecastResult() {
    }

    public ForecastResult(List<TimeJson> timeList, List<AreaWeatherJson> areaList) {
        this.timeList = timeList;
        this.areaList = areaList;
    }

    /**
     * 获取
     * @return timeList
     */
    public List<TimeJson> getTimeList() {
        return timeList;
    }

    /**
     * 设置
     * @param timeList
     */
    public void setTimeList(List<TimeJson> timeList) {
        this.timeList = timeList;
    }

    /**
     * 获取
     * @return areaList
     */
    public List<AreaWeatherJson> getAreaList() {
        return areaList;
    }

    /**
     * 设置
     * @param areaList
     */
    public void setAreaList(List<AreaWeatherJson> areaList) {
        this.areaList = areaList;
    }

    public String toString() {
        return "ForecastResult{timeList = " + timeList + ", areaList = " + areaList + "}";
    }
}
