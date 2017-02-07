package com.citizant.jenkinsmanager.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
	@Bean
	public JenkinsService jinkenService() {
		return new JenkinsServiceImpl();
	}
}
