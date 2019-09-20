package com.chao.cloud.admin.vue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.cloud.admin.vue.dal.entity.SysDept;
import com.chao.cloud.admin.vue.domain.dto.SelectTreeDTO;

/**
 * @功能：
 * @author： 超君子
 * @时间：2019-05-28
 * @version 1.0.0
 */
public interface SysDeptService extends IService<SysDept> {
	/**
	 * 下拉树
	 * @return
	 */
	List<SelectTreeDTO> selectTree();

}
