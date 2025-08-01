package com.codedata.rbac.security.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codedata.rbac.security.entity.CaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaptchaDao extends BaseMapper<CaptchaEntity> {

}
