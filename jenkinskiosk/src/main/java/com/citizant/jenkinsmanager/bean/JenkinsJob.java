package com.citizant.jenkinsmanager.bean;

import java.io.Serializable;
import java.util.List;

public class JenkinsJob implements Serializable {
	
	private String name;
	private String description;
	private List<JenkinsBuild> builds;
	private JenkinsBuild lastBuild;
	
	
	
	public JenkinsBuild getLastBuild() {
		return lastBuild;
	}
	public void setLastBuild(JenkinsBuild lastBuild) {
		this.lastBuild = lastBuild;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<JenkinsBuild> getBuilds() {
		return builds;
	}
	public void setBuilds(List<JenkinsBuild> builds) {
		this.builds = builds;
	}
	
	

}
