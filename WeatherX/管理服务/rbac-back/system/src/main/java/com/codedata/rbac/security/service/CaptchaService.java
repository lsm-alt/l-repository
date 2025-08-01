package com.codedata.rbac.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.security.entity.CaptchaEntity;

import javax.servlet.http.HttpServletResponse;

public interface CaptchaService extends IService<CaptchaEntity> {
    void create(HttpServletResponse response, String uuid);
    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
