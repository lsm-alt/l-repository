package com.codedata.rbac.system.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.system.entity.UserEntity;
import com.codedata.rbac.system.view.PasswordView;
import com.codedata.rbac.system.view.UserView;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<UserEntity> {
    UserEntity getUserByName(String username);
    PageData<UserView> query(Map<String, Object> params);
    void save(UserView view);
    void update(UserView view);
    void delete(Long[] ids);
    UserView detail(Long id);
    List<UserView> select(Map<String, Object> params);
    void password(PasswordView view);
}
