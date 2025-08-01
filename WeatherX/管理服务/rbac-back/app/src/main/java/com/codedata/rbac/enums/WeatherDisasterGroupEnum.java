package com.codedata.rbac.enums;

public enum WeatherDisasterGroupEnum {
    YINYU_GUOZHAO("阴雨/寡照"),
    GANRE_RIRAO("干热/日灼"),
    DAFENG_BINGBAO("大风/冰雹"),
    GANHAN_ZHILAO("干旱/渍涝"),
    SHUANGDONG_DIWEN("霜冻/低温");

    private final String label;         // 中文标签

    WeatherDisasterGroupEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}
