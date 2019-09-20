package com.chao.cloud.admin.vue.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.api.R;
import com.chao.cloud.admin.vue.dal.entity.SysMenu;
import com.chao.cloud.admin.vue.service.SysMenuService;
import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.exception.BusinessException;

/**
 * 
 * @功能：
 * @author： 薛超
 * @时间：2019年5月8日
 * @version 2.0
 */
@RequestMapping("/sys/menu")
@Controller
@Validated
public class MenuController extends BaseController {

	String prefix = "sys/menu";

	@Autowired
	private SysMenuService sysMenuService;

	@RequestMapping("/list")
	@ResponseBody
	R<List<SysMenu>> list() {
		return R.ok(sysMenuService.list());
	}

	/**
	 * 菜单选择
	 * @return
	 */
	@RequestMapping("/choose")
	@ResponseBody
	public R<List<SysMenu>> choose() {
		// 获取当前角色的权限
		// UserDTO user = getUser();
		// Set<String> perms = getUser().getPerms();
		List<SysMenu> list = sysMenuService.list();
		// list.stream().filter(l ->
		// perms.contains(l.getPerms())).collect(Collectors.toList());
		return R.ok(list);
	}

	@RequestMapping("/add/{pId}")
	String add(Model model, @PathVariable("pId") Long pId) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", sysMenuService.getById(pId).getName());
		}
		return prefix + "/add";
	}

	@RequestMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Integer id) {
		SysMenu mdo = sysMenuService.getById(id);
		Integer pId = mdo.getParentId();
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", sysMenuService.getById(pId).getName());
		}
		model.addAttribute("menu", mdo);
		return prefix + "/edit";
	}

	@RequestMapping("/save")
	@ResponseBody
	Response<String> save(SysMenu menu) {
		if (sysMenuService.save(menu)) {
			return Response.ok();
		}
		throw new BusinessException("保存失败");

	}

	@RequestMapping("/update")
	@ResponseBody
	Response<String> update(SysMenu menu) {
		if (sysMenuService.updateById(menu)) {
			return Response.ok();
		}
		throw new BusinessException("更新失败");
	}

	@RequestMapping("/remove")
	@ResponseBody
	Response<String> remove(@NotNull Integer id) {
		// 递归删除
		if (sysMenuService.recursionRemove(id)) {
			return Response.ok();
		}
		throw new BusinessException("删除失败");
	}

}
