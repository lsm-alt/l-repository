package com.codedata.rbac.utils;

import com.codedata.rbac.common.utils.DateUtil;
import com.codedata.rbac.entity.AreaWeatherJson;
import com.codedata.rbac.entity.ForecastResult;
import com.codedata.rbac.entity.TimeJson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForecastUtils {

    private static String extractNumber(String text) {
        Matcher matcher = Pattern.compile("\\d+").matcher(text);
        return matcher.find() ? matcher.group() : "";
    }

    public static ForecastResult buildJson(String rawText) {
        String[] lines = rawText.split("\n");
        List<TimeJson> timeList = new ArrayList<>();
        List<AreaWeatherJson> areaList = new ArrayList<>();

        int dividerIndex = findFirstEmptyLineIndex(lines);
        String date = DateUtil.getcurDate();
        // 1. 每日天气解析
        for (int i = 0; i < dividerIndex; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("[:：]", 2);
            if (parts.length < 2) continue;

            TimeJson time = new TimeJson();
            time.setTimeRange(parts[0].trim());
            //+1 存储的天气是第二天的信息 方便后面直接使用
            time.setDate(DateUtil.getDayStr(DateUtil.calDay(date, i + 1)));
            String[] segments = parts[1].split("[,，]");
            if (segments.length < 3) continue;

            time.setWeather(segments[0].trim());
            time.setWindDirection(joinSegments(segments, 1, segments.length - 1));
            String[] temps = segments[segments.length - 1].split("[~～]");
            if (temps.length == 2) {
                time.setTempLow(extractNumber(temps[0]));
                time.setTempHigh(extractNumber(temps[1]));
            }

            timeList.add(time);
        }

        // 2. 分区域天气解析
        int areaStart = findNextNonEmptyLineIndex(lines, dividerIndex);
        if (areaStart >= lines.length) {
            return new ForecastResult(timeList, areaList); // 无区域数据
        }

        String commonTimeRange = lines[areaStart].trim();

        for (int i = areaStart + 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("[:：]", 2);
            if (parts.length < 2) continue;

            AreaWeatherJson area = new AreaWeatherJson();
            area.setTimeRange(commonTimeRange);
            area.setArea(parts[0].trim());

            String[] segments = parts[1].split("[,，]");
            if (segments.length < 3) continue;

            area.setWeather(segments[0].trim());
            area.setWindDirection(joinSegments(segments, 1, segments.length - 1));

            String[] temps = segments[segments.length - 1].split("[~～]");
            if (temps.length == 2) {
                area.setTempLow(extractNumber(temps[0]));
                area.setTempHigh(extractNumber(temps[1]));
            }

            areaList.add(area);
        }

        return new ForecastResult(timeList, areaList);
    }

    private static int findFirstEmptyLineIndex(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().isEmpty()) return i;
        }
        return lines.length;
    }

    private static int findNextNonEmptyLineIndex(String[] lines, int startIndex) {
        for (int i = startIndex; i < lines.length; i++) {
            if (!lines[i].trim().isEmpty()) return i;
        }
        return lines.length;
    }

    private static String joinSegments(String[] segments, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(segments[i].trim());
            if (i < end - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public static String buildText(ForecastResult forecastResult) {
        StringBuilder sb = new StringBuilder();

        // 1. 拼接每日天气部分
        for (TimeJson time : forecastResult.getTimeList()) {
            sb.append(time.getTimeRange()).append("：")
                    .append(time.getWeather()).append("，")
                    .append(time.getWindDirection()).append("，")
                    .append(time.getTempLow()).append("～").append(time.getTempHigh()).append("℃")
                    .append("。\n");
        }

        // 添加空行分隔区域天气
        sb.append("\n");

        // 2. 拼接区域天气
        List<AreaWeatherJson> areaList = forecastResult.getAreaList();
        if (!areaList.isEmpty()) {
            // 所有区域天气的时间范围统一
            sb.append(areaList.get(0).getTimeRange()).append("\n");

            for (AreaWeatherJson area : areaList) {
                sb.append(area.getArea()).append("：")
                        .append(area.getWeather()).append("，")
                        .append(area.getWindDirection()).append("，")
                        .append(area.getTempLow()).append("～").append(area.getTempHigh()).append("℃")
                        .append("。\n");
            }
        }

        return sb.toString().trim();
    }
}
