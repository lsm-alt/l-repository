package com.codedata.rbac;

import com.codedata.rbac.entity.*;
import com.codedata.rbac.utils.ForecastUtils;

import java.util.ArrayList;
import java.util.List;

public class WordTemplateReplacer {
    String inputPath = "C:\\Users\\jklx\\Desktop\\KMC文件\\沾化冬枣成熟期气象服务专报202401.docx";   // 模板路径
    String outputPath = "C:\\Users\\jklx\\Desktop\\KMC文件\\沾化.docx";   // 输出路径
    public static String[] weatherLow = {"阵雨", "雷阵雨", "毛毛雨", "多云转雨", "阴转雨", "晴转雨","雷雨"};
    public static String[] weatherMid = {"小雨"};
    public static String[] weatherHigh = {"中雨", "大雨", "暴雨", "大暴雨", "特大暴雨"};
    public static void main(String[] args) throws Exception {
        String rawText = "今天夜间到明天白天：晴间多云，东风2～3级，25～37℃。\n" +
                "明天夜间-12日：晴间多云，东风转南风2～3级，26～37℃\n" +
                "后天夜间-13日：晴转阴有雷雨，南风转北风2～3级，雷雨时阵风7～9级，26～36℃。\n" +
                "14日：多云，东北风2～3级转东南风3～4级，24～36℃。\n" +
                "15日：多云转阴有暴雨雨，南风转北风3～4级，雨时阵风7～9级，28～39℃。\n" +
                "16日：阴转晴，北风2～3级，27～35℃。\n" +
                "17日：晴间多云，北风转南风2～3级，25～36℃。\n" +
                "\n" +
                "今天夜间到明天白天：\n" +
                "城区：晴间多云，东风2～3级，25～37℃。\n" +
                "中部：晴间多云，东风2～3级，24～36℃。\n" +
                "西部：晴间多云，东风2～3级，25～37℃。\n" +
                "南部：晴间多云，东风2～3级，25～37℃。\n" +
                "东部：晴间多云，东风2～3级，25～37℃。\n" +
                "北部：晴间多云，东风2～3级，25～37℃。\n" +
                "北部沿海：晴间多云，东风4～5级，26～36℃。\n" +
                "海防：晴间多云，东风4～5级，26～36℃。";
        ForecastResult forecastResult = ForecastUtils.buildJson(rawText);
        List<AreaWeatherJson> areaList = forecastResult.getAreaList();
        List<TimeJson> timeList = forecastResult.getTimeList();
        ArrayList<WeatherTable> list = new ArrayList<>();

        for (TimeJson timeJson : timeList) {
            boolean flag = false;
            WeatherTable weatherTable = new WeatherTable();
            for (String s : weatherHigh) {
                if (timeJson.getWeather().contains(s)){
                    weatherTable.setName(WeatherTable.YINYU_GUOZHAO);
                    weatherTable.setProbability(WeatherTable.HIGH);
                    weatherTable.setTimePeriod(timeJson.getDate());
                    flag = true;
                    list.add(weatherTable);
                    break;
                }
            }
            if (!flag){
                for (String s : weatherMid) {
                    if (timeJson.getWeather().contains(s)){
                        weatherTable.setName(WeatherTable.YINYU_GUOZHAO);
                        weatherTable.setProbability(WeatherTable.MIDDLE);
                        weatherTable.setTimePeriod(timeJson.getDate());
                        flag = true;
                        list.add(weatherTable);
                        break;
                    }
                }
            }
            if (!flag){
                for (String s : weatherLow) {
                    if (timeJson.getWeather().contains(s)){
                        weatherTable.setName(WeatherTable.YINYU_GUOZHAO);
                        weatherTable.setProbability(WeatherTable.LOW);
                        weatherTable.setTimePeriod(timeJson.getDate());
                        flag = true;
                        list.add(weatherTable);
                        break;
                    }
                }
            }
            
        }
        for (WeatherTable weatherTable : list) {
            System.out.println(weatherTable);
        }
    }


}
