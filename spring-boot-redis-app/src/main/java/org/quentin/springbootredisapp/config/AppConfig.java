package org.quentin.springbootredisapp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = {ApiRuleProperties.class})
public class AppConfig {
}
