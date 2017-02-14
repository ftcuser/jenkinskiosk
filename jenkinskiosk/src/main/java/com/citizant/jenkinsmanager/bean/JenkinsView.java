package com.citizant.jenkinsmanager.bean;

import java.io.Serializable;
import java.util.List;

public class JenkinsView implements Serializable {

		private String name;
		private String description;
		private List<JenkinsJob> jobs;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String discription) {
			this.description = discription;
		}
		public List<JenkinsJob> getJobs() {
			return jobs;
		}
		public void setJobs(List<JenkinsJob> jobs) {
			this.jobs = jobs;
		}
		
		
}
