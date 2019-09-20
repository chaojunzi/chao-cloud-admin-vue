package com.chao.cloud.admin.vue.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.chao.cloud.admin.vue.config.AdminConfig;

import cn.hutool.core.collection.CollUtil;

public abstract class BaseController {

	@Autowired
	protected AdminConfig adminConfig;

	public List<Integer> removeEmpty(List<Integer> list) {
		if (CollUtil.isNotEmpty(list)) {
			return list.stream().distinct().filter(l -> l != null).collect(Collectors.toList());
		}
		return null;
	}
}