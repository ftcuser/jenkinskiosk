package com.citizant.jenkinsmanager.bean;

import java.io.Serializable;
import java.util.List;

public class JenkinsView implements Serializable {
	
		private int index = 0;
		private String name;
		private String description;
		private List<JenkinsJob> jobs;
		
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
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
