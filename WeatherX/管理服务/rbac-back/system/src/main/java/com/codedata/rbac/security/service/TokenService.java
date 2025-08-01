package com.codedata.rbac.security.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codedata.rbac.security.entity.TokenEntity;


public interface TokenService extends IService<TokenEntity> {
	/**
	 * 根据token值来查询
	 * @param token
	 * @return
	 */
	TokenEntity queryByToken(String token);
	/**
	 * 生成token
	 */
	TokenEntity createToken(Long userId);

	/**
	 * 使token过期
	 */
	void expireToken(Long userId);
}
