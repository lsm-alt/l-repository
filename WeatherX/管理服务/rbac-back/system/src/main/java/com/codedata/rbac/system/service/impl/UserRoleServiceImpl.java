package com.codedata.rbac.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.system.dao.UserRoleDao;
import com.codedata.rbac.system.entity.UserRoleEntity;
import com.codedata.rbac.system.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除角色用户关系
        deleteByUserIds(new Long[]{userId});
        //角色列表为空时直接返回
        if(roleIdList==null||roleIdList.size()==0){
            return;
        }
        //保存角色用户关系
        for(Long roleId : roleIdList){
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            this.save(userRoleEntity);
        }
    }

    @Override
    public void deleteByRoleIds(Long[] roleIds) {
        getBaseMapper().deleteByRoleIds(roleIds);
    }

    @Override
    public void deleteByUserIds(Long[] userIds) {
        getBaseMapper().deleteByUserIds(userIds);
    }

    @Override
    public List<Long> getRoleIdList(Long userId) {
        return getBaseMapper().getRoleIdList(userId);
    }
}
