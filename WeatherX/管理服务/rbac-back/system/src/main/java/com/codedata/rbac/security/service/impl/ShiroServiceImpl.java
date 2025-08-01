package com.codedata.rbac.security.service.impl;

import com.codedata.rbac.security.entity.TokenEntity;
import com.codedata.rbac.security.service.ShiroService;
import com.codedata.rbac.security.service.TokenService;
import com.codedata.rbac.system.entity.UserEntity;
import com.codedata.rbac.system.enums.AdminType;
import com.codedata.rbac.system.service.MenuService;
import com.codedata.rbac.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroServiceImpl implements ShiroService {

    private MenuService menuService;

    private UserService userService;

    private TokenService tokenService;

    public ShiroServiceImpl(MenuService menuService, UserService userService, TokenService tokenService) {
        this.menuService = menuService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public Set<String> getUserPermissions(UserEntity entity) {
        //系统管理员，拥有最高权限
        List<String> permissionsList;
        if(entity.getSuperAdmin() == AdminType.SUPER_ADMIN.value()) {
            permissionsList = menuService.getPermissionsList();
        }else{
            permissionsList = menuService.getUserPermissionsList(entity.getId());
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        for(String permissions : permissionsList){
            if(StringUtils.isBlank(permissions)){
                continue;
            }
            permsSet.addAll(Arrays.asList(permissions.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public TokenEntity getByToken(String token) {
        return tokenService.queryByToken(token);
    }

    @Override
    public UserEntity getUser(Long userId) {
        return userService.getById(userId);
    }
}
