package com.codedata.rbac.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.page.Query;
import com.codedata.rbac.common.utils.ConvertUtils;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.system.dao.LogDao;
import com.codedata.rbac.system.entity.LogEntity;
import com.codedata.rbac.system.service.LogService;
import com.codedata.rbac.system.view.LogView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class LogServiceImpl extends ServiceImpl<LogDao, LogEntity> implements LogService {
    @Override
    public PageData<LogView> query(Map<String, Object> params) {
        String username = (String) params.get("username");
        String result = (String) params.get("result");
        String dateStartStr = (String) params.get("dateStart");
        String dateEndStr = (String) params.get("dateEnd");
        Date dateStart = null;
        Date dateEnd = null;
        if (!StringUtils.isBlank(dateStartStr)) {
            dateStart = DateUtil.parse(dateStartStr, Constants.DATE_TIME_PATTERN);
        }
        if (!StringUtils.isBlank(dateEndStr)) {
            dateEnd = DateUtil.parse(dateEndStr, Constants.DATE_TIME_PATTERN);
        }
        IPage<LogEntity> page = this.page(new Query<LogEntity>().getPage(params),
                new QueryWrapper<LogEntity>()
                        .select("id", "username", "type", "time", "result", "ip", "create_date")
                        .eq(StringUtils.isNotBlank(result), "result", result)
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .ge(dateStart != null, "create_date", dateStart)
                        .le(dateEnd != null, "create_date", dateEnd));
        return new PageData<LogView>(page, LogView.class);
    }

    @Override
    public LogView detail(Long id) {
        LogEntity entity = this.getById(id);
        return ConvertUtils.sourceToTarget(entity, LogView.class);
    }
}
