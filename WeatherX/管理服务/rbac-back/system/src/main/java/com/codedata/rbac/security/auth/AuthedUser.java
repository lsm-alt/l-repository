package com.codedata.rbac.security.auth;
import com.codedata.rbac.system.entity.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 用来获取运行状态的用户信息
 */
public class AuthedUser {
    public static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取用户信息
     */
    public static UserEntity getUser() {
        Subject subject = getSubject();
        if(subject == null){
            return new UserEntity();
        }
        UserEntity user = (UserEntity)subject.getPrincipal();
        if(user == null){
            return new UserEntity();
        }
        return user;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return getUser().getId();
    }

}
