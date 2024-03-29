package com.chao.cloud.admin.vue.service.impl;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.cloud.admin.vue.config.AdminConstant;
import com.chao.cloud.admin.vue.dal.entity.SysRole;
import com.chao.cloud.admin.vue.dal.entity.SysUser;
import com.chao.cloud.admin.vue.dal.mapper.SysRoleMapper;
import com.chao.cloud.admin.vue.dal.mapper.SysUserMapper;
import com.chao.cloud.admin.vue.domain.dto.UserDTO;
import com.chao.cloud.admin.vue.service.SysUserService;
import com.chao.cloud.common.util.RightsUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * @功能：
 * @author： 超君子
 * @时间：2019-05-28
 * @version 1.0.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public UserDTO get(Integer userId) {
		UserDTO user = BeanUtil.toBean(this.baseMapper.selectById(userId), UserDTO.class);
		if (RightsUtil.checkBigInteger(user.getRoles())) {
			LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery().select(SysRole::getRoleId);
			List<SysRole> list = sysRoleMapper.selectList(queryWrapper);
			if (CollUtil.isNotEmpty(list)) {
				user.setRoleIds(list.stream().filter(r -> RightsUtil.testRights(user.getRoles(), r.getRoleId()))
						.map(SysRole::getRoleId).collect(Collectors.toList()));
			}
		}
		if (ObjectUtil.isNull(user.getRoleIds())) {
			user.setRoleIds(Collections.emptyList());
		}
		return user;
	}

	@Override
	public int save(UserDTO user) {
		// 计算权限
		if (CollUtil.isNotEmpty(user.getRoleIds())) {
			BigInteger rights = RightsUtil.sumRights(user.getRoleIds());
			user.setRoles(rights.toString());
		}
		// 添加
		SysUser sysUser = BeanUtil.toBean(user, SysUser.class);
		int count = this.baseMapper.insert(sysUser);
		return count;
	}

	@Override
	public int update(UserDTO user) {
		// 计算权限
		if (CollUtil.isNotEmpty(user.getRoleIds())) {
			BigInteger rights = RightsUtil.sumRights(user.getRoleIds());
			user.setRoles(rights.toString());
		} else {
			user.setRoles(AdminConstant.RIGHTS_DEFAULT_VALUE);
		}
		int r = this.baseMapper.updateById(BeanUtil.toBean(user, SysUser.class));
		return r;
	}

	@Override
	public int remove(Integer userId) {
		return this.baseMapper.deleteById(userId);
	}

	@Override
	public int removeBatch(Integer[] userIds) {
		int count = this.baseMapper.deleteBatchIds(CollUtil.toList(userIds));
		return count;
	}

}
