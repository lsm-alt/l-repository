package com.codedata.rbac.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.page.Query;
import com.codedata.rbac.common.utils.ConvertUtils;
import com.codedata.rbac.security.auth.AuthedUser;
import com.codedata.rbac.system.service.UserService;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.system.dao.UserDao;
import com.codedata.rbac.system.entity.UserEntity;
import com.codedata.rbac.system.enums.AdminType;
import com.codedata.rbac.system.service.UserRoleService;
import com.codedata.rbac.system.view.PasswordView;
import com.codedata.rbac.system.view.UserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    private UserRoleService userRoleService;

    public UserServiceImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public UserEntity getUserByName(String username) {
        return getBaseMapper().getUserByName(username);
    }

    private String buildPasswordHash(String username, String password) {
        String plain = username + password;
        return plain;
    }

    @Override
    public PageData<UserView> query(Map<String, Object> params) {
        String username = (String) params.get("username");
        IPage<UserEntity> page = this.page(new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>().like(StringUtils.isNotBlank(username), "username", username));
        return new PageData<UserView>(page, UserView.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserView view) {
        UserEntity entity = ConvertUtils.sourceToTarget(view, UserEntity.class);
        entity.setPassword(buildPasswordHash(view.getUsername(), view.getPassword()));
        entity.setSuperAdmin(AdminType.ORDINARY.value());
        if (view.getStatus() == null) {
            view.setStatus(1);//为空默认按正常状态处理
        }
        this.save(entity);
        userRoleService.saveOrUpdate(entity.getId(), view.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserView view) {
        UserEntity entity = ConvertUtils.sourceToTarget(view, UserEntity.class);
        if (StringUtils.isBlank(entity.getPassword())) {
            entity.setPassword(null);
        } else {
            entity.setPassword(buildPasswordHash(entity.getUsername(), entity.getPassword()));
        }
        this.updateById(entity);
        userRoleService.saveOrUpdate(entity.getId(), view.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //删除对应用户
        this.removeBatchByIds(Arrays.asList(ids));
        //删除关系
        userRoleService.deleteByUserIds(ids);
    }

    @Override
    public UserView detail(Long id) {
        UserEntity entity = this.getById(id);
        return ConvertUtils.sourceToTarget(entity, UserView.class);
    }

    @Override
    public List<UserView> select(Map<String, Object> params) {
        List<UserEntity> userEntities = this.list(new QueryWrapper<UserEntity>().select("id", "username"));
        return ConvertUtils.sourceToTarget(userEntities, UserView.class);
    }

    @Override
    public void password(PasswordView view) {
        UserEntity curr = AuthedUser.getUser();
        UserEntity entity = this.getById(curr.getId());
        String password = buildPasswordHash(entity.getUsername(), view.getPassword());
        if (!Arrays.equals(password.getBytes(), entity.getPassword().getBytes())) {
            throw new ServiceException(ErrorCode.ERR_PARAMETERS, "原密码不正确");
        }
        if (!Arrays.equals(view.getNewPassword().getBytes(), view.getConfirmPassword().getBytes())) {
            throw new ServiceException(ErrorCode.ERR_PARAMETERS, "新密码与确认密码不一致");
        }
        if (Arrays.equals(view.getNewPassword().getBytes(), view.getPassword().getBytes())) {
            throw new ServiceException(ErrorCode.ERR_PARAMETERS, "新密码与旧密码一致");
        }
        password = buildPasswordHash(entity.getUsername(), view.getNewPassword());
        entity.setPassword(password);
        this.updateById(entity);
    }
}