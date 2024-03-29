package com.chao.cloud.admin.vue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.cloud.admin.vue.dal.entity.SysUser;
import com.chao.cloud.admin.vue.domain.dto.UserDTO;

/**
 * @功能：
 * @author： 超君子
 * @时间：2019-05-28
 * @version 1.0.0
 */
public interface SysUserService extends IService<SysUser> {

	UserDTO get(Integer userId);

	int save(UserDTO user);

	int update(UserDTO user);

	int remove(Integer userId);

	int removeBatch(Integer[] userIds);

}
