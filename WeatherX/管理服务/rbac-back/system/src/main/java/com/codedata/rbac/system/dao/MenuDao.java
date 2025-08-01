package com.codedata.rbac.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codedata.rbac.system.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDao extends BaseMapper<MenuEntity> {
    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    MenuEntity getMenuById(@Param("id") Long id);
    /**
     * 根据类型查询菜单
     * @param type
     * @return
     */
    List<MenuEntity> getMenuByType(@Param("type") Integer type);

    /**
     * 获取用户菜单列表
     * @param userId
     * @param type
     * @return
     */
    List<MenuEntity> getUserMenuList(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 查询用户权限列表
     * @param userId  用户ID
     */
    List<String> getUserPermissionsList(Long userId);

    /**
     * 查询所有权限列表
     */
    List<String> getPermissionsList();

    /**
     * 根据父菜单，查询子菜单
     * @param pid  父菜单ID
     */
    List<MenuEntity> getMenuListByPid(Long pid);
}
