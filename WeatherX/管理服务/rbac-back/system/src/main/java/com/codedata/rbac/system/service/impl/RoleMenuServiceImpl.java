package com.codedata.rbac.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.system.dao.RoleMenuDao;
import com.codedata.rbac.system.entity.RoleMenuEntity;
import com.codedata.rbac.system.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuEntity> implements RoleMenuService {

    @Override
    public List<Long> getMenuIdListByRoleId(Long roleId) {
        return getBaseMapper().getMenuIdListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色菜单关系
        deleteByRoleIds(new Long[]{roleId});

        //角色没有一个菜单权限的情况
        if(menuIdList==null||menuIdList.isEmpty()){
            return;
        }

        //保存角色菜单关系
        for(Long menuId : menuIdList){
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setMenuId(menuId);
            roleMenuEntity.setRoleId(roleId);
            this.save(roleMenuEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleIds(Long[] roleIds) {
        getBaseMapper().deleteByRoleIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByMenuId(Long menuId) {
        getBaseMapper().deleteByMenuId(menuId);
    }
}
