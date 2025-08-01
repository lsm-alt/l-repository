package com.codedata.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.common.utils.DateUtil;
import com.codedata.rbac.common.utils.DirUtil;
import com.codedata.rbac.common.utils.HttpUtil;
import com.codedata.rbac.common.utils.JsonUtils;
import com.codedata.rbac.dao.ForecastDao;
import com.codedata.rbac.dao.JujubeDao;
import com.codedata.rbac.entity.ForecastEntity;
import com.codedata.rbac.entity.PeriodEntity;
import com.codedata.rbac.entity.TimeJson;
import com.codedata.rbac.entity.WeatherDay;
import com.codedata.rbac.service.JujubeService;
import com.codedata.rbac.utils.JwtTokenUtils;
import com.codedata.rbac.utils.WeatherDescriptionUtils;
import com.codedata.rbac.utils.WordTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JujubeServiceImpl extends ServiceImpl<JujubeDao, PeriodEntity> implements JujubeService {
    private final static Logger logger = LoggerFactory.getLogger(JujubeServiceImpl.class);


    @Value("${weather.api}")
    private String api;

    @Value("${weather.jujube-file}")
    private String jujubeFile;

    private JwtTokenUtils jwtTokenUtils;
    private ForecastDao forecastDao;

    public JujubeServiceImpl(ForecastDao forecastDao, JwtTokenUtils jwtTokenUtils) {
        this.forecastDao = forecastDao;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public void build() {
        //获取天气数据
        List<WeatherDay> weatherDays = getWeatherApi();
        String content = WeatherDescriptionUtils.generateForecast(weatherDays);
        HashMap<String, String> map = new HashMap<>();

        PeriodEntity entity = new PeriodEntity();

        QueryWrapper<PeriodEntity> wrapper = new QueryWrapper<>();
        wrapper.select("max(period) as period"); // 别名为实体字段名
        PeriodEntity result = this.getOne(wrapper);
        if (result == null || result.getPeriod() == null) {
            entity.setPeriod(Constants.ONE);
        } else {
            entity.setPeriod(result.getPeriod() + 1);
        }

        String period = DateUtil.getCurYear() + "年第" + entity.getPeriod() + "期";
        entity.setFileName(period + ".docx");

        map.put("content", content);
        map.put("period", period);
        map.put("unit", Constants.UNIT);

        LambdaQueryWrapper<ForecastEntity> queryWrapper = new LambdaQueryWrapper<>();
        String filesDir = DateUtil.getCurTime().replace(":", "-") + ".pdf";
        queryWrapper.eq(ForecastEntity::getForecastDate, filesDir);
        ForecastEntity forecastEntity = forecastDao.selectOne(queryWrapper);
        if (forecastEntity == null){
            throw new ServiceException(ErrorCode.ERR_FORECAST_NOT_EXIST);
        }
        List<TimeJson> timeWith = JsonUtils.jsonToArrayList(forecastEntity.getTimeWith(), TimeJson.class);
        for (TimeJson wf : timeWith) {
            String output = String.format("%s日：%s，%s，%s～%s℃。\n",
                    wf.getDate(),
                    wf.getWeather(),
                    wf.getWindDirection(),
                    wf.getTempLow(),
                    wf.getTempHigh());
            map.put("timeWith", output);
        }

        //生成word
        WordTemplateUtils.replaceInDocument(
                jujubeFile,
                DirUtil.getJujubeDir(entity.getFileName()),
                map);

    }

    private List<WeatherDay> getWeatherApi() {
        String tooken = null;
        //生成tooken
        try {
            tooken = jwtTokenUtils.generateToken();
        } catch (Exception e) {
            logger.error(ErrorCode.get(ErrorCode.ERR_JWT), e);
            throw new ServiceException(ErrorCode.ERR_JWT, e.getMessage());
        }
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + tooken);
        HashMap<String, Object> params = new HashMap<>();
        params.put("location", Constants.LOCATION);
        String response = null;
        try {
            logger.info("api: {}", api);
            //获取api数据
            response = HttpUtil.httpsGet(api, headers, params);
        } catch (Exception e) {
            logger.error(ErrorCode.get(ErrorCode.ERR_HTTP_GET), e);
            throw new ServiceException(ErrorCode.ERR_HTTP_GET, e.getMessage());
        }
        if (response.contains("error")) {
            logger.error(ErrorCode.get(ErrorCode.ERR_WEATHER_API), response);
            throw new ServiceException(ErrorCode.ERR_WEATHER_API, response);
        }
        if (!response.contains("daily")) {
            logger.error(ErrorCode.get(ErrorCode.ERR_WEATHER_API), response);
            throw new ServiceException(ErrorCode.ERR_WEATHER_API, response);
        }
        Map<Object, Object> resMap = JsonUtils.jsonToMap(response);
        Object daity = resMap.get(Constants.DAILY);
        String dailyJson = JsonUtils.toJsonString(daity);
        List<WeatherDay> weatherDays = JsonUtils.jsonToArrayList(dailyJson, WeatherDay.class);

        return weatherDays;
    }
}
