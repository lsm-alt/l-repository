package com.codedata.rbac.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codedata.rbac.security.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 令牌操作DAO
 */
@Mapper
public interface TokenDao extends BaseMapper<TokenEntity> {
    TokenEntity getByToken(String token);
    TokenEntity getByUserId(Long userId);
}
