package com.codedata.rbac.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codedata.rbac.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao extends BaseMapper<RoleEntity> {
}
