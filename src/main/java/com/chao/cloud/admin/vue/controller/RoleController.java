package com.chao.cloud.admin.vue.controller;

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chao.cloud.admin.vue.dal.entity.SysRole;
import com.chao.cloud.admin.vue.domain.dto.RoleDTO;
import com.chao.cloud.admin.vue.service.SysRoleService;
import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.exception.BusinessException;

@RequestMapping("/sys/role")
@Controller
@Validated
public class RoleController extends BaseController {
	String prefix = "sys/role";
	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping("/list")
	@ResponseBody
	Response<IPage<SysRole>> list(Page<SysRole> page) {
		return Response.ok(sysRoleService.page(page));
	}

	@RequestMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model) {
		RoleDTO roleDO = sysRoleService.get(id);
		model.addAttribute("role", roleDO);
		return prefix + "/edit";
	}

	@RequestMapping("/save")
	@ResponseBody
	Response<String> save(RoleDTO role) {
		role.setMenuIds(super.removeEmpty(role.getMenuIds()));
		if (sysRoleService.save(role)) {
			return Response.ok();
		}
		throw new BusinessException("保存失败 ");
	}

	@RequestMapping("/update")
	@ResponseBody
	Response<String> update(RoleDTO role) {
		// 去重//去空
		role.setMenuIds(super.removeEmpty(role.getMenuIds()));
		if (sysRoleService.update(role)) {
			return Response.ok();
		}
		throw new BusinessException("更新失败 ");
	}

	@RequestMapping("/remove")
	@ResponseBody
	Response<String> save(@NotNull Integer id) {
		if (sysRoleService.remove(id)) {
			return Response.ok();
		}
		throw new BusinessException("删除失败");

	}

	@RequestMapping("/batchRemove")
	@ResponseBody
	Response<String> batchRemove(@Size(min = 1) @RequestParam("ids[]") Integer[] ids) {
		if (sysRoleService.batchRemove(ids)) {
			return Response.ok();
		}
		throw new BusinessException("删除失败");
	}
}
