package com.codedata.rbac.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.security.dao.CaptchaDao;
import com.codedata.rbac.security.entity.CaptchaEntity;
import com.codedata.rbac.security.service.CaptchaService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 验证码业务实现
 */
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaDao, CaptchaEntity> implements CaptchaService {
    //回收过期的验证码
    private void retrieveExpired() {
        try {
            this.remove(new QueryWrapper<CaptchaEntity>().le("expire_time", new Date()));
        } catch (Exception e) {

        }
    }

    @Override
    public void create(HttpServletResponse response, String uuid) {
        //回收已经过期的验证码
        retrieveExpired();
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //生成验证码
        SpecCaptcha captcha = new SpecCaptcha(150, 40);
        captcha.setLen(5);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        CaptchaEntity captchaEntity = new CaptchaEntity();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(captcha.text());
        captchaEntity.setExpireTime(DateUtils.addMinutes(new Date(),5));//验证码5分钟过期
        this.save(captchaEntity);//保存验证码
        try {
            captcha.out(response.getOutputStream());
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_CAPTCHA, "验证码生成失败:" + e.getMessage());
        }
    }

    @Override
    public boolean validate(String uuid, String code) {
        CaptchaEntity captchaEntity = this.getById(uuid);
        if (captchaEntity == null) {
            return false;
        }
        this.removeById(uuid);
        return captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis();
    }
}
