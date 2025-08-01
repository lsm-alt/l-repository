package com.codedata.rbac.security.service;
import com.codedata.rbac.security.entity.TokenEntity;
import com.codedata.rbac.system.entity.UserEntity;

import java.util.Set;

public interface ShiroService {
    /**
     * 获取权限字符串
     * @param entity
     * @return
     */
    Set<String> getUserPermissions(UserEntity entity);


    TokenEntity getByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    UserEntity getUser(Long userId);
}
