package com.codedata.rbac.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.system.entity.UserEntity;
import com.codedata.rbac.system.entity.MenuEntity;
import com.codedata.rbac.system.view.MenuView;

import java.util.List;

public interface MenuService extends IService<MenuEntity> {
    MenuView detail(Long id);
    void save(MenuView view);
    void update(MenuView view);
    void delete(Long id);
    /**
     * 菜单列表
     *
     * @param type 菜单类型
     */
    List<MenuView> getMenuByType(Integer type);
    /**
     * 用户菜单列表
     *
     * @param user  用户
     * @param type 菜单类型
     */
    List<MenuView> getUserMenuList(UserEntity user, Integer type);

    /**
     * 根据父菜单，查询子菜单
     * @param pid  父菜单ID
     */
    List<MenuView> getMenuListByPid(Long pid);

    /**
     * 获取权限字符串列表
     * @return
     */
    List<String> getPermissionsList();

    /**
     * 获取用户权限字符串列表
     * @return
     */
    List<String> getUserPermissionsList(Long userId);
}
