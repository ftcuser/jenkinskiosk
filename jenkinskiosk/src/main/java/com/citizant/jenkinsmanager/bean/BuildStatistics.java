package com.citizant.jenkinsmanager.bean;

import java.io.Serializable;

/*
 * This is the statistics of Jenkins server build.
 * This could be used to generate JSON data
 * for charting 
 *
 * 
 */

public class BuildStatistics implements Serializable {
	
	private String date;
	private int sucessful;
	private int failed;
	private long datestamp = 0L;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getSucessful() {
		return sucessful;
	}
	public void setSucessful(int sucessful) {
		this.sucessful = sucessful;
	}
	public int getFailed() {
		return failed;
	}
	public void setFailed(int failed) {
		this.failed = failed;
	}
	public long getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(long datestamp) {
		this.datestamp = datestamp;
	}
	
	
	
}
