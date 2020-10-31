package org.litu.core.config;

import org.litu.core.mybatisplus.MybatisPlusSqlInject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
@MapperScan(value = { "org.litu.*.dao" })
public class MybatisConfig {

	/**
	 * <p>
	 * mp分页插件<br/>
	 * 参数 Page 即自动分页,必须放在第一位<br/>
	 * IPage<User> selectPage(Page page, @Param("state") Integer state);
	 * </p>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * sql注入器
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new MybatisPlusSqlInject();
	}

}
