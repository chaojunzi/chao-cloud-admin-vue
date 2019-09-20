package com.chao.cloud.admin.vue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.chao.cloud.common.extra.mybatis.annotation.EnableMybatisPlus;
import com.chao.cloud.common.web.annotation.EnableGlobalException;
import com.chao.cloud.common.web.annotation.EnableWeb;

/**
 * admin-后台管理系统-rest
 * @author 薛超
 * @since 2019年9月17日
 * @version 1.0.7
 */
@SpringBootApplication
@EnableWeb // web
@EnableGlobalException // 全局异常处理
@EnableMybatisPlus // 数据库插件-乐观锁
public class ChaoAdminVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaoAdminVueApplication.class, args);
	}

}
