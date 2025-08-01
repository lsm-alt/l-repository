package com.codedata.rbac.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.system.entity.LogEntity;
import com.codedata.rbac.system.view.LogView;

import java.util.Map;

public interface LogService extends IService<LogEntity> {
    PageData<LogView> query(Map<String, Object> params);
    LogView detail(Long id);
}
