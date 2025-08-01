package com.codedata.rbac.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.page.Query;
import com.codedata.rbac.common.utils.ConvertUtils;
import com.codedata.rbac.system.dao.RoleDao;
import com.codedata.rbac.system.entity.RoleEntity;
import com.codedata.rbac.system.service.RoleMenuService;
import com.codedata.rbac.system.service.RoleService;
import com.codedata.rbac.system.service.UserRoleService;
import com.codedata.rbac.system.view.RoleView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {
    private RoleMenuService roleMenuService;
    private UserRoleService userRoleService;
    public RoleServiceImpl(RoleMenuService roleMenuService,UserRoleService userRoleService){
        this.roleMenuService = roleMenuService;
        this.userRoleService = userRoleService;
    }

    @Override
    public PageData<RoleView> query(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        IPage<RoleEntity> page = this.page(new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>().like(StringUtils.isNotBlank(roleName), "role_name", roleName));
        return new PageData<RoleView>(page,RoleView.class);
    }

    @Override
    public List<RoleView> select(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        List<RoleEntity> roleEntityList =  this.list(new QueryWrapper<RoleEntity>().like(StringUtils.isNotBlank(roleName), "role_name", roleName));
        return ConvertUtils.sourceToTarget(roleEntityList,RoleView.class);
    }

    @Override
    public RoleView detail(Long id) {
        RoleEntity roleEntity = this.getById(id);
        return ConvertUtils.sourceToTarget(roleEntity,RoleView.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(RoleView view) {
        RoleEntity roleEntity = ConvertUtils.sourceToTarget(view,RoleEntity.class);
        //保存角色实体
        this.save(roleEntity);
        //更新角色菜单关系
        roleMenuService.saveOrUpdate(roleEntity.getId(), view.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleView view) {
        RoleEntity entity = ConvertUtils.sourceToTarget(view, RoleEntity.class);
        //更新角色
        this.updateById(entity);
        //更新角色菜单关系
        roleMenuService.saveOrUpdate(entity.getId(), view.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //删除角色
        this.removeBatchByIds(Arrays.asList(ids));
        //删除角色用户关系
        userRoleService.deleteByRoleIds(ids);
        //删除角色菜单关系
        roleMenuService.deleteByRoleIds(ids);
    }
}
