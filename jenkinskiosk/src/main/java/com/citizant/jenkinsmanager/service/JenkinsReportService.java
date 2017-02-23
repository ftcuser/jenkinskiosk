package com.citizant.jenkinsmanager.service;

import java.util.List;

import com.citizant.jenkinsmanager.bean.Dashboard;
import com.citizant.jenkinsmanager.bean.JenkinsNode;

public interface JenkinsReportService {
	
	public List<JenkinsNode> getJenkinsNodes(String configFile);
	
	public Dashboard getCloudDashboard(List<JenkinsNode> nodes);
	
	public Dashboard getNodeDashboard(String projectId);

}
