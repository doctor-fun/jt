package com.jt;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//启动时不加载数据源的配置
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@ComponentScan("com.jt.controller")
public class SpringBootRunWeb {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRunWeb.class,args);
	}
}
