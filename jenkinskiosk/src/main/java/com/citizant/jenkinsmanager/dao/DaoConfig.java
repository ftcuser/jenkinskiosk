package com.citizant.jenkinsmanager.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.citizant.jenkinsmanager.dao.impl.JenkinsNodesDaoImpl;

@Configuration
public class DaoConfig {

	@Bean
	public JenkinsNodesDao jenkinsNodesDao() {
		return new JenkinsNodesDaoImpl();
	}
}
