package com.chao.cloud.admin.vue.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chao.cloud.admin.vue.dal.entity.SysUser;
import com.chao.cloud.admin.vue.domain.dto.LeftMenuDTO;
import com.chao.cloud.admin.vue.domain.dto.UserDTO;
import com.chao.cloud.admin.vue.domain.vo.LoginVO;
import com.chao.cloud.admin.vue.service.SysMenuService;
import com.chao.cloud.admin.vue.service.SysUserService;
import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.exception.BusinessException;
import com.chao.cloud.common.util.HyalineCaptchaUtil;
import com.chao.cloud.common.util.HyalineCaptchaUtil.HyalineCircleCaptcha;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Validated
public class LoginController extends BaseController {

	@Autowired
	private SysUserService userService;
	@Autowired
	private SysMenuService menuService;

	@RequestMapping("/login")
	public Response<UserDTO> login(@Valid LoginVO vo) {
		String random = adminConfig.getVerify(vo.getVerifyKey());
		if (!vo.getVerify().equals(random)) {
			throw new BusinessException("请输入正确的验证码");
		}
		// 摘要算法
		String password = DigestUtil.md5Hex(vo.getUserName() + vo.getPassword());
		// 查询用户信息
		LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername,
				vo.getUserName());
		UserDTO user = BeanUtil.toBean(userService.getOne(queryWrapper), UserDTO.class);
		// 账号不存在
		if (BeanUtil.isEmpty(user) || !password.equals(user.getPassword())) {
			throw new BusinessException("账号或密码不正确");
		}
		// 账号锁定
		if (user.getStatus() == 0) {
			throw new BusinessException("账号已被锁定,请联系管理员");
		}
		// 获取菜单
		List<LeftMenuDTO> menus = menuService.listMenuLayuiTree(user.getUserId(), user.getRoles());
		user.setMenus(menus);
		// 添加缓存
		adminConfig.updateUser(DigestUtil.md5Hex(user.getUserId().toString()), user);
		return Response.ok(user);
	}

	/**
	 * 生成验证码
	 */
	@RequestMapping(value = "/getVerify")
	public void getVerify(@NotBlank String verifyKey, HttpServletResponse response) {
		try (OutputStream os = response.getOutputStream()) {
			response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			// 制作验证码
			HyalineCircleCaptcha captcha = HyalineCaptchaUtil.createCircleCaptcha(100, 42, 4, 3);
			String code = captcha.getCode();
			log.info("[验证码: {}]", code);
			// 存入缓存
			adminConfig.setVerify(verifyKey, code);
			// 输出
			captcha.write(os);
		} catch (Exception e) {
			log.error("获取验证码失败>>>> ", e);
		}
	}

}