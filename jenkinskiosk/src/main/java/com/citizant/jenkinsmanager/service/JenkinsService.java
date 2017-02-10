package com.citizant.jenkinsmanager.service;

import java.util.List;

import com.citizant.jenkinsmanager.bean.JenkinsBuild;
import com.citizant.jenkinsmanager.bean.JenkinsJob;
import com.citizant.jenkinsmanager.bean.JenkinsNode;


public interface JenkinsService {
	
	public List<JenkinsNode> getJenkinsNodes();
	
	public List<JenkinsJob> getJobList(String projectId);
	
	public List<JenkinsBuild> getBuildHistory(String projectId, String jobName);
	
	public JenkinsBuild startBuild(String projectId, String jobName, int lastBuild);
	
	public JenkinsBuild getRunningBuild(String projectId, String jobName, int lastBuild);
	
	public JenkinsBuild getBuildDetail(String projectId, String jobName, int buildNumber);
	
}
