package com.codedata.rbac.security.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.codedata.rbac.security.auth.AuthedUser;
import com.codedata.rbac.system.entity.UserEntity;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis数据自动填充
 *
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_DATE = "createDate";
    private final static String CREATOR = "creator";
    private final static String UPDATE_DATE = "updateDate";
    private final static String UPDATER = "updater";
    @Override
    public void insertFill(MetaObject metaObject) {
        UserEntity user = AuthedUser.getUser();
        Date date = new Date();
        //创建者
        strictInsertFill(metaObject, CREATOR, Long.class, user.getId());
        //创建时间
        strictInsertFill(metaObject, CREATE_DATE, Date.class, date);
        //更新者
        strictInsertFill(metaObject, UPDATER, Long.class, user.getId());
        //更新时间
        strictInsertFill(metaObject, UPDATE_DATE, Date.class, date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //刷新更新者
        strictUpdateFill(metaObject, UPDATER, Long.class, AuthedUser.getUserId());
        //刷新更新时间
        strictUpdateFill(metaObject, UPDATE_DATE, Date.class, new Date());
    }
}
