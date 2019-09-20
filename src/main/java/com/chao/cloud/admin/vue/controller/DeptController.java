package com.chao.cloud.admin.vue.controller;

import java.util.List;

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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.chao.cloud.admin.vue.config.AdminConstant;
import com.chao.cloud.admin.vue.dal.entity.SysDept;
import com.chao.cloud.admin.vue.dal.entity.SysUser;
import com.chao.cloud.admin.vue.domain.dto.SelectTreeDTO;
import com.chao.cloud.admin.vue.service.SysDeptService;
import com.chao.cloud.admin.vue.service.SysUserService;
import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.exception.BusinessException;

import cn.hutool.core.collection.CollUtil;

/** 
 * 部门管理 
 * @功能：
 * @author： 薛超
 * @时间：2019年5月9日 
 * @version 2.0
 */
@RequestMapping("/sys/dept")
@Controller
@Validated
public class DeptController extends BaseController {
	private String prefix = "sys/dept";
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/list")
	@ResponseBody
	public R<List<SysDept>> list() {
		return R.ok(sysDeptService.list());
	}

	@RequestMapping("/add/{pId}")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总部门");
		} else {
			model.addAttribute("pName", sysDeptService.getById(pId).getName());
		}
		return prefix + "/add";
	}

	@RequestMapping("/edit/{deptId}")
	String edit(@PathVariable("deptId") Integer deptId, Model model) {
		SysDept sysDept = sysDeptService.getById(deptId);
		model.addAttribute("sysDept", sysDept);
		if (AdminConstant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
			model.addAttribute("parentDeptName", "无");
		} else {
			SysDept parDept = sysDeptService.getById(sysDept.getParentId());
			model.addAttribute("parentDeptName", parDept.getName());
		}
		return prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Response<String> save(SysDept sysDept) {
		if (sysDeptService.save(sysDept)) {
			return Response.ok();
		}
		return Response.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Response<String> update(SysDept sysDept) {
		if (sysDeptService.updateById(sysDept)) {
			return Response.ok();
		}
		return Response.error();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Response<String> remove(@NotNull Integer deptId) {
		int deptCount = sysDeptService.count(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getParentId, deptId));
		if (deptCount > 0) {
			throw new BusinessException("部门包含用户,不允许删除");
		}

		int userCount = sysUserService.count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getDeptId, deptId));
		if (userCount > 0) {
			throw new BusinessException("部门包含用户,不允许删除");
		}

		if (sysDeptService.removeById(deptId)) {
			return Response.ok();
		}
		return Response.error();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/batchRemove")
	@ResponseBody
	public Response<String> remove(@Size(min = 1) @RequestParam("ids[]") Integer[] deptIds) {
		sysDeptService.removeByIds(CollUtil.toList(deptIds));
		return Response.ok();
	}

	@RequestMapping("/choose")
	@ResponseBody
	public R<List<SelectTreeDTO>> choose() {
		return R.ok(sysDeptService.selectTree());
	}

}