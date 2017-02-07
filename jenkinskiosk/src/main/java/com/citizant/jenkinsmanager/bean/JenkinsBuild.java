package com.citizant.jenkinsmanager.bean;

import java.io.Serializable;

public class JenkinsBuild implements Serializable {
	
	private int buildNumber;
	private String buildDate;
	private String status;
	
	private String consoleOutput;
	private String processMessage;
	private boolean running = false;
	
	
	public int getBuildNumber() {
		return buildNumber;
	}
	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}
	public String getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConsoleOutput() {
		return consoleOutput;
	}
	public void setConsoleOutput(String consoleOutput) {
		this.consoleOutput = consoleOutput;
	}
	public String getProcessMessage() {
		return processMessage;
	}
	public void setProcessMessage(String processMessage) {
		this.processMessage = processMessage;
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
}
