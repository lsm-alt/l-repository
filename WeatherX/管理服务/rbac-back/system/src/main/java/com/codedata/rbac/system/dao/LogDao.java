package com.codedata.rbac.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codedata.rbac.system.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogDao extends BaseMapper<LogEntity> {
}
