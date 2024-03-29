package com.chao.cloud.admin.vue.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chao.cloud.admin.vue.config.AdminConstant;
import com.chao.cloud.admin.vue.dal.entity.SysUser;
import com.chao.cloud.admin.vue.domain.dto.RoleDTO;
import com.chao.cloud.admin.vue.domain.dto.UserDTO;
import com.chao.cloud.admin.vue.service.SysRoleService;
import com.chao.cloud.admin.vue.service.SysUserService;
import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.exception.BusinessException;
import com.chao.cloud.common.extra.mybatis.generator.menu.MenuMapping;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;

@RequestMapping("/sys/user")
@Controller
@Validated
public class UserController extends BaseController {
	private String prefix = "sys/user";
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping
	String list(Model model) {
		return prefix + "/user";
	}

	@RequestMapping("/list")
	@ResponseBody
	Response<IPage<SysUser>> list(Page<SysUser> page, String username) {
		// 查询列表数据
		LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
		if (StrUtil.isNotBlank(username)) {
			queryWrapper.like(SysUser::getUsername, username);
		}
		IPage<SysUser> result = sysUserService.page(page, queryWrapper);
		if (CollUtil.isNotEmpty(result.getRecords())) {
			List<SysUser> records = result.getRecords().stream()
					.filter(u -> !AdminConstant.ADMIN_ID.equals(u.getUserId())).collect(Collectors.toList());
			result.setRecords(records);
		}
		return Response.ok(result);
	}

	@RequestMapping("/add")
	String add(Model model) {
		List<RoleDTO> roles = sysRoleService.list(StrUtil.EMPTY);
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@MenuMapping("编辑")
	@RequestMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Integer id) {
		UserDTO user = sysUserService.get(id);
		model.addAttribute("user", user);
		List<RoleDTO> roles = sysRoleService.list(user.getRoles());
		model.addAttribute("roles", roles);
		return prefix + "/edit";
	}

	@RequestMapping("/save")
	@ResponseBody
	Response<String> save(@Valid UserDTO user) {
		user.setRoleIds(super.removeEmpty(user.getRoleIds()));
		// 判断用户是否已经注册
		Integer userCount = sysUserService
				.count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, user.getUsername()));
		if (userCount > 0) {
			throw new BusinessException("用户已经注册:" + user.getUsername());
		}
		String password = DigestUtil.md5Hex(user.getUsername() + user.getPassword());
		user.setPassword(password);
		if (sysUserService.save(user) > 0) {
			return Response.ok();
		}
		throw new BusinessException("保存失败");
	}

	@RequestMapping("/update")
	@ResponseBody
	Response<String> update(UserDTO user) {
		user.setRoleIds(super.removeEmpty(user.getRoleIds()));
		if (sysUserService.update(user) > 0) {
			return Response.ok();
		}
		throw new BusinessException("更新失败");
	}

	@RequestMapping("/remove")
	@ResponseBody
	Response<String> remove(@NotNull Integer id) {
		boolean isAdmin = AdminConstant.ADMIN_ID.equals(id);
		if (isAdmin) {
			throw new BusinessException("admin 不可删除");
		}
		if (sysUserService.remove(id) > 0) {
			return Response.ok();
		}
		throw new BusinessException("删除失败");
	}

	@RequestMapping("/batchRemove")
	@ResponseBody
	Response<String> batchRemove(@RequestParam("ids[]") @Size(min = 1) Integer[] userIds) {
		boolean hasAdmin = ArrayUtil.contains(userIds, AdminConstant.ADMIN_ID);
		if (hasAdmin) {
			throw new BusinessException("admin 不可删除");
		}
		int r = sysUserService.removeBatch(userIds);
		if (r > 0) {
			return Response.ok();
		}
		throw new BusinessException("删除失败");
	}

	@RequestMapping("/resetPwd/page")
	String resetPwd() {
		return prefix + "/reset_pwd";
	}

}
