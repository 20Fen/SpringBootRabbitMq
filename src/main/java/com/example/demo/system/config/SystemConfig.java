package com.example.demo.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 描述: 系统配置(获取properties文件配置)
 */
@Configuration
@PropertySource(value = "file:${user.dir}/conf/${runtime.conf.active}.properties", encoding = "utf-8")
public class SystemConfig {

}
