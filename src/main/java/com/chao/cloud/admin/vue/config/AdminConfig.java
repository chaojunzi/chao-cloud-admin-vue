package com.chao.cloud.admin.vue.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import com.chao.cloud.admin.vue.domain.dto.UserDTO;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Assert;

/**
 * 用户管理
 * @author 薛超
 * @since 2019年9月17日
 * @version 1.0.7
 */
@Configuration
public class AdminConfig implements InitializingBean {

	private long userTime = 24 * 60 * 60 * 1000;// 1天
	private long verifyTime = 30 * 60 * 1000;// 30分钟
	private long clearUserTime = 60 * 60 * 1000;// 1 小时
	private long clearVerifyTime = 10 * 60 * 1000;// 10分钟
	/**
	 * 用户缓存
	 */
	private final TimedCache<String, UserDTO> userCache = CacheUtil.newTimedCache(userTime);
	/**
	 * 验证码缓存
	 */
	private final TimedCache<String, String> verifyCache = CacheUtil.newTimedCache(verifyTime);

	/**
	 * 获取用户
	 * @param token
	 * @return
	 */
	public UserDTO getUser(String token) {
		return userCache.get(token);
	}

	/**
	 * 更新用户
	 * @param token
	 * @param user
	 */
	public void updateUser(String token, UserDTO user) {
		Assert.notNull(user, "用户不能为空");
		userCache.put(token, user);
	}

	/**
	 * 验证码
	 * @param key
	 * @return
	 */
	public String getVerify(String key) {
		String verify = verifyCache.get(key);
		Assert.notEmpty(verify, "验证码已过期");
		return verify;
	}

	/**
	 * 添加验证码
	 * @param key
	 * @param verify
	 * @return
	 */
	public String setVerify(String key, String verify) {
		Assert.notEmpty(verify, "验证码不能为空");
		verifyCache.put(key, verify);
		return verify;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		userCache.schedulePrune(clearUserTime);
		verifyCache.schedulePrune(clearVerifyTime);
	}

}
