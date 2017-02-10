package com.citizant.jenkinsmanager.bean;

import java.util.ArrayList;
import java.util.List;

/*
 * The dash board info 
 * 
 */
public class Dashboard {
	private int numOfNodes = 0;
	private int numOfLiveNodes = 0;
	private int numOfSuccessful = 0;
	private int numOfFailed = 0;
	
	private List<BuildStatistics> buildscs = new ArrayList<BuildStatistics>(); //The build statistics in last 10 days


	public int getNumOfNodes() {
		return numOfNodes;
	}

	public void setNumOfNodes(int numOfNodes) {
		this.numOfNodes = numOfNodes;
	}

	public int getNumOfLiveNodes() {
		return numOfLiveNodes;
	}

	public void setNumOfLiveNodes(int numOfLiveNodes) {
		this.numOfLiveNodes = numOfLiveNodes;
	}

	public int getNumOfSuccessful() {
		return numOfSuccessful;
	}

	public void setNumOfSuccessful(int numOfSuccessful) {
		this.numOfSuccessful = numOfSuccessful;
	}

	public int getNumOfFailed() {
		return numOfFailed;
	}

	public void setNumOfFailed(int numOfFailed) {
		this.numOfFailed = numOfFailed;
	}

	public List<BuildStatistics> getBuildscs() {
		return buildscs;
	}

	public void setBuildscs(List<BuildStatistics> buildscs) {
		this.buildscs = buildscs;
	}
	
	

}
