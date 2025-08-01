package com.codedata.rbac.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codedata.rbac.common.utils.TokenGenerator;
import com.codedata.rbac.security.dao.TokenDao;
import com.codedata.rbac.security.entity.TokenEntity;
import com.codedata.rbac.security.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl extends ServiceImpl<TokenDao, TokenEntity> implements TokenService {
    /**
     * 默认12小时过期
     */
    private final static int EXPIRE = 3600 * 12;

    public TokenEntity queryByToken(String token) {
        return getBaseMapper().getByToken(token);
    }

    @Override
    public TokenEntity createToken(Long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        TokenEntity tokenEntity = getBaseMapper().getByUserId(userId);
        if(tokenEntity == null){
            tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateDate(now);
            tokenEntity.setExpireDate(expireTime);
            //保存token
            save(tokenEntity);
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setUpdateDate(now);
            tokenEntity.setExpireDate(expireTime);
            //更新token
            this.updateById(tokenEntity);
        }
        return tokenEntity;
    }
    
    @Override
    public void expireToken(Long userId) {
        String token = TokenGenerator.generateValue();
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}
