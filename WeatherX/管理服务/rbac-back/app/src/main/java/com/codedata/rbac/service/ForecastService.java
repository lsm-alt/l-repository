package com.codedata.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.entity.ForecastEntity;
import com.codedata.rbac.view.ForecastView;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


public interface ForecastService extends IService<ForecastEntity> {
    void insert(String rawText, MultipartFile file);
    PageData<ForecastEntity> query(Map<String, Object> params);

    void delete(Long[] ids);

    ForecastView detail(Long id);
    void update(Long id,String rawText, MultipartFile file);

    String download(Long id);
}
