package com.chao.cloud.admin.vue.domain.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginVO {
	@NotBlank(message = "请输入用户名")
	private String userName;
	@NotBlank(message = "请输入密码")
	private String password;
	@NotBlank(message = "请输入验证码")
	private String verify;
	@NotBlank(message = "请输入验证码 key")
	private String verifyKey;
}
