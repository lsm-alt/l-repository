package com.codedata.rbac.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.page.Query;
import com.codedata.rbac.common.utils.DateUtil;
import com.codedata.rbac.common.utils.DirUtil;
import com.codedata.rbac.common.utils.JsonUtils;
import com.codedata.rbac.dao.ForecastDao;
import com.codedata.rbac.entity.AreaWeatherJson;
import com.codedata.rbac.entity.ForecastEntity;
import com.codedata.rbac.entity.ForecastResult;
import com.codedata.rbac.entity.TimeJson;
import com.codedata.rbac.service.ForecastService;
import com.codedata.rbac.utils.ForecastUtils;
import com.codedata.rbac.view.ForecastView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ForecastServiceImpl extends ServiceImpl<ForecastDao, ForecastEntity> implements ForecastService {
    private final static Logger logger = LoggerFactory.getLogger(ForecastServiceImpl.class);

    @Override
    public void insert(String rawText, MultipartFile file) {
        String curTime = DateUtil.getCurTime();
        ForecastEntity forecastEntity = new ForecastEntity();
        try {
            //保存pdf文件
            String filesDir = curTime.replace(":", "-") + ".pdf";
            logger.debug("保存文件: {}", DirUtil.getForecastDir(filesDir));
            FileUtil.writeBytes(file.getBytes(),DirUtil.getForecastDir(filesDir));
            forecastEntity.setFilePath(DirUtil.getForecastDir(filesDir));
            forecastEntity.setForecastDate(filesDir);
        } catch (IOException e) {
            logger.error(ErrorCode.get(ErrorCode.ERR_FILE_ERROR), e);
            throw new ServiceException(ErrorCode.ERR_FILE_ERROR, e.getMessage());
        }
        //解析传入参数
        ForecastResult forecastResult = ForecastUtils.buildJson(rawText);
        forecastEntity.setTimeWith(JsonUtils.toJsonString(forecastResult.getTimeList()));
        forecastEntity.setDistrictWith(JsonUtils.toJsonString(forecastResult.getAreaList()));
        this.save(forecastEntity);

    }

    @Override
    public PageData<ForecastEntity> query(Map<String, Object> params) {
        String forecastDate = (String) params.get("forecastDate");
        IPage<ForecastEntity> page = this.page(
                new Query<ForecastEntity>().getPage(params),
                new LambdaQueryWrapper<ForecastEntity>()
                        .like(StrUtil.isNotBlank(forecastDate), ForecastEntity::getForecastDate, forecastDate));

        return new PageData<>(page, ForecastEntity.class);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            ForecastEntity forecastEntity = this.getById(id);
            FileUtil.del(forecastEntity.getFilePath());
        }
        this.removeBatchByIds(Arrays.asList(ids));
    }

    @Override
    public ForecastView detail(Long id) {
        ForecastEntity forecastEntity = this.getById(id);
        List<TimeJson> timeWith = JsonUtils.jsonToArrayList(forecastEntity.getTimeWith(), TimeJson.class);
        List<AreaWeatherJson> districtWith = JsonUtils.jsonToArrayList(forecastEntity.getDistrictWith(), AreaWeatherJson.class);
        ForecastView forecastView = new ForecastView();
        String buildText = ForecastUtils.buildText(new ForecastResult(timeWith, districtWith));
        forecastView.setRawText(buildText);
        //解析文件创建时间
        forecastView.setMonth(forecastEntity.getForecastDate().substring(5, 7));
        forecastView.setDate(forecastEntity.getForecastDate().substring(8, 10));
        forecastView.setHours(forecastEntity.getForecastDate().substring(11, 13));
        return forecastView;
    }

    @Override
    public void update(Long id, String rawText, MultipartFile file) {
        ForecastEntity forecastEntity = this.getById(id);
        ForecastResult forecastResult = ForecastUtils.buildJson(rawText);
        forecastEntity.setTimeWith(JsonUtils.toJsonString(forecastResult.getTimeList()));
        forecastEntity.setDistrictWith(JsonUtils.toJsonString(forecastResult.getAreaList()));
        try {
            logger.debug("保存文件: {}", forecastEntity.getFilePath());
            FileUtil.writeBytes(file.getBytes(),forecastEntity.getFilePath());
        } catch (IOException e) {
            logger.error(ErrorCode.get(ErrorCode.ERR_FILE_ERROR), e);
            throw new ServiceException(ErrorCode.ERR_FILE_ERROR, e.getMessage());
        }
        this.updateById(forecastEntity);
    }

    @Override
    public String download(Long id) {
        ForecastEntity forecastEntity = this.getById(id);
        return forecastEntity.getFilePath();
    }
}
