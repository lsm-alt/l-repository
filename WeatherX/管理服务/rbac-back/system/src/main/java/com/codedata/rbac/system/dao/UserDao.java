package com.codedata.rbac.system.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codedata.rbac.system.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
    UserEntity getUserByName(String username);
}
