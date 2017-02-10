package com.citizant.jenkinsmanager.dao;

import java.util.List;

import com.citizant.jenkinsmanager.domain.JenkinsNode;

public interface JenkinsNodesDao {
	
	public List<JenkinsNode> getNodes();

}
