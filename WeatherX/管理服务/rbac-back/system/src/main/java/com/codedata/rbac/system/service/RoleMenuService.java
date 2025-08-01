package com.codedata.rbac.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.system.entity.RoleMenuEntity;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenuEntity> {
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> getMenuIdListByRoleId(Long roleId);

    /**
     * 保存或修改
     * @param roleId      角色ID
     * @param menuIdList  菜单ID列表
     */
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色id，删除角色菜单关系
     * @param roleIds 角色ids
     */
    void deleteByRoleIds(Long[] roleIds);

    /**
     * 根据菜单id，删除角色菜单关系
     * @param menuId 菜单id
     */
    void deleteByMenuId(Long menuId);
}
