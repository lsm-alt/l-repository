package com.codedata.rbac.utils;

import com.codedata.rbac.entity.WeatherDay;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeatherDescriptionUtils {

    public static String generateForecast(List<WeatherDay> days) {
        StringBuilder report = new StringBuilder();
        report.append("预计未来一周我区以");

        boolean hasRain = false;
        boolean hasThunder = false;
        boolean mostlyCloudyOrOvercast = true;

        int maxTemp = Integer.MIN_VALUE;
        int minTemp = Integer.MAX_VALUE;

        double totalPrecip = 0.0;
        int rainDayCount = 0;
        int totalHumidity = 0;
        int humidityDays = 0;

        int maxWind = 0;
        String maxWindDir = "";
        String maxWindDate = "";

        for (WeatherDay day : days) {
            String dayText = day.getTextDay();
            String nightText = day.getTextNight();

            if (dayText.contains("雨") || nightText.contains("雨")) {
                hasRain = true;
                rainDayCount++;
            }

            if (dayText.contains("雷") || nightText.contains("雷")) {
                hasThunder = true;
            }

            if (!(dayText.contains("多云") || dayText.contains("阴") || nightText.contains("多云") || nightText.contains("阴"))) {
                mostlyCloudyOrOvercast = false;
            }

            try {
                int max = Integer.parseInt(day.getTempMax());
                int min = Integer.parseInt(day.getTempMin());
                maxTemp = Math.max(maxTemp, max);
                minTemp = Math.min(minTemp, min);
            } catch (NumberFormatException ignored) {}

            try {
                totalPrecip += Double.parseDouble(day.getPrecip());
            } catch (NumberFormatException ignored) {}

            try {
                totalHumidity += Integer.parseInt(day.getHumidity());
                humidityDays++;
            } catch (Exception ignored) {}

            try {
                String[] scale = day.getWindScaleDay().split("-");
                int scaleMax = Integer.parseInt(scale[scale.length - 1]);
                if (scaleMax > maxWind) {
                    maxWind = scaleMax;
                    maxWindDir = day.getWindDirDay();
                    maxWindDate = day.getFxDate();
                }
            } catch (Exception ignored) {}
        }

        // 天气主趋势
        if (hasRain) {
            report.append("阴雨天气为主");
        } else if (mostlyCloudyOrOvercast) {
            report.append("多云到阴天气为主");
        } else {
            report.append("晴好天气为主");
        }
        report.append("。\n");

        // 降水时段
        StringBuilder rainDays = new StringBuilder();
        for (WeatherDay day : days) {
            if (day.getTextDay().contains("雨") || day.getTextNight().contains("雨")) {
                rainDays.append(formatDate(day.getFxDate()))
                        .append(day.getTextDay())
                        .append("，夜间")
                        .append(day.getTextNight())
                        .append("；");
            }
        }

        if (rainDays.length() > 0) {
            report.append("预计降水时段为：").append(rainDays).append("\n");
        }

        if (hasThunder) {
            report.append("局地可能伴有雷电天气，请注意防范短时强降水或雷雨大风等强对流天气。\n");
        }

        // 风力分析
        if (maxWind >= 5) {
            report.append("部分时段风力较大，最大阵风预计达")
                    .append(maxWind)
                    .append("级（")
                    .append(maxWindDir)
                    .append("，出现在")
                    .append(formatDate(maxWindDate))
                    .append("），请注意防风。\n");
        }

        // 气温趋势
        report.append("本周白天气温在")
                .append(minTemp)
                .append("～")
                .append(maxTemp)
                .append("℃之间波动，夜间最低气温为")
                .append(minTemp)
                .append("℃左右。\n");

        // 紫外线提醒
        int maxUv = days.stream()
                .mapToInt(d -> {
                    try {
                        return Integer.parseInt(d.getUvIndex());
                    } catch (Exception e) {
                        return 0;
                    }
                }).max().orElse(0);
        if (maxUv >= 8) {
            report.append("紫外线强度较强，外出建议做好防晒措施。\n");
        }


        // 🔽 新增：降水量总结
        if (rainDayCount > 0) {
            report.append("本周共有").append(rainDayCount).append("天出现降水，总降水量约为").append(String.format("%.1f", totalPrecip)).append("毫米。\n");
        }

        return report.toString();
    }

    private static String formatDate(String fxDate) {
        try {
            LocalDate date = LocalDate.parse(fxDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月d日");
            return formatter.format(date);
        } catch (Exception e) {
            return fxDate;
        }
    }
}