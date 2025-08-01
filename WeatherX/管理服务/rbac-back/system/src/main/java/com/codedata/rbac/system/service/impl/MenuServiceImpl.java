package com.codedata.rbac.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.utils.ConvertUtils;
import com.codedata.rbac.common.utils.TreeUtils;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.system.dao.MenuDao;
import com.codedata.rbac.system.entity.MenuEntity;
import com.codedata.rbac.system.entity.UserEntity;
import com.codedata.rbac.system.enums.AdminType;
import com.codedata.rbac.system.service.MenuService;
import com.codedata.rbac.system.service.RoleMenuService;
import com.codedata.rbac.system.view.MenuView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {
    private RoleMenuService roleMenuService;

    public MenuServiceImpl(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @Override
    public MenuView detail(Long id) {
        MenuEntity menuEntity = getBaseMapper().getMenuById(id);
        MenuView view = ConvertUtils.sourceToTarget(menuEntity,MenuView.class);
        return view;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(MenuView view) {
        MenuEntity menuEntity =  ConvertUtils.sourceToTarget(view,MenuEntity.class);
        this.save(menuEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MenuView view) {
        MenuEntity menuEntity =  ConvertUtils.sourceToTarget(view,MenuEntity.class);
        //上级菜单不能为自身
        if(menuEntity.getId().equals(menuEntity.getPid())){
            throw new ServiceException(ErrorCode.ERR_MENU,"上级菜单不能为自身");
        }
        this.updateById(menuEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //删除菜单
        this.removeById(id);
        //删除角色菜单关系
        roleMenuService.deleteByMenuId(id);
    }

    @Override
    public List<MenuView> getMenuByType(Integer type) {
        List<MenuEntity> menuList = getBaseMapper().getMenuByType(type);
        List<MenuView> viewList = ConvertUtils.sourceToTarget(menuList, MenuView.class);
        return TreeUtils.build(viewList, Constants.MENU_ROOT);
    }

    @Override
    public List<MenuView> getUserMenuList(UserEntity user, Integer type) {

        List<MenuEntity> menuList;

        //系统管理员，拥有最高权限
        if(user.getSuperAdmin() == AdminType.SUPER_ADMIN.value()){
            menuList = getBaseMapper().getMenuByType(type);
        }else {
            menuList = getBaseMapper().getUserMenuList(user.getId(), type);
        }

        List<MenuView> viewList = ConvertUtils.sourceToTarget(menuList, MenuView.class);

        return TreeUtils.build(viewList);
    }

    @Override
    public List<MenuView> getMenuListByPid(Long pid) {
        List<MenuEntity> menuEntities = getBaseMapper().getMenuListByPid(pid);
        return ConvertUtils.sourceToTarget(menuEntities,MenuView.class);

    }

    @Override
    public List<String> getPermissionsList() {
        return getBaseMapper().getPermissionsList();
    }

    @Override
    public List<String> getUserPermissionsList(Long userId) {
        return getBaseMapper().getUserPermissionsList(userId);
    }
}
