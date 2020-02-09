package com.jt.config;

import com.jt.Interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer{
	
	//开启匹配后缀型配置,任何后缀都被忽略，只看前缀,index.html就会被拦截到index+.jsp,而index.html
	//会被搜索引擎爬取，而index.jsp不能
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}
@Autowired
	private UserInterceptor userInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//将userInterceptor拦截器交给springmvc管理，指定拦截路径
		registry.addInterceptor(userInterceptor	).addPathPatterns(
				"/cart/**","/order/**"
		);
	}
}


