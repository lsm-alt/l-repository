package com.codedata.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codedata.rbac.entity.ForecastEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForecastDao extends BaseMapper<ForecastEntity> {
}
