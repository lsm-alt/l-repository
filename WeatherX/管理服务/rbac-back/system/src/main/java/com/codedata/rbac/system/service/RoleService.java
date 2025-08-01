package com.codedata.rbac.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.system.entity.RoleEntity;
import com.codedata.rbac.system.view.RoleView;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<RoleEntity> {
    PageData<RoleView> query(Map<String, Object> params);

    List<RoleView> select(Map<String, Object> params);

    RoleView detail(Long id);

    void save(RoleView view);

    void update(RoleView view);

    void delete(Long[] ids);
}
