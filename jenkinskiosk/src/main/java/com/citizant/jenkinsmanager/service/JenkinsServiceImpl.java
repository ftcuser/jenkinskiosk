package com.citizant.jenkinsmanager.service;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.citizant.jenkinsmanager.bean.JenkinsBuild;
import com.citizant.jenkinsmanager.bean.JenkinsJob;
import com.citizant.jenkinsmanager.bean.JenkinsNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;


public class JenkinsServiceImpl implements JenkinsService {
	
	protected List<JenkinsNode> nodes = null;

	public List<JenkinsNode> getJenkinsNodes(String configFile) {
		
		ObjectMapper mapper = new ObjectMapper();
		String json;
		
		try {
			json = new String(Files.readAllBytes(Paths.get(configFile)));
			nodes = mapper.readValue(json, new TypeReference<List<JenkinsNode>>(){});
			
			//Check if the servers are running
			for(JenkinsNode node : nodes) {
				JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
				node.setRunning(js.isRunning());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
		return nodes;
	}
	
	public List<JenkinsJob> getJobList(String projectId) {
		List<JenkinsJob> jobs = new ArrayList<JenkinsJob>();
		
		if(this.nodes != null) {
			for(JenkinsNode node : nodes) {
				if(projectId.equals(node.getId())) {
					try{
						JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
						Map<String, Job> jobMap = js.getJobs();
						Iterator<String> keys = jobMap.keySet().iterator();
						
						while(keys.hasNext()) {
							JobWithDetails jd = jobMap.get(keys.next()).details();
							JenkinsJob jb = new JenkinsJob();
							jb.setName(jd.getName());
							jb.setDescription(jd.getDescription());
											
							JenkinsBuild lastBuild = new JenkinsBuild();
							lastBuild.setBuildNumber(jd.getLastBuild().getNumber());
							lastBuild.setBuildDate((new Date(jd.getLastBuild().details().getTimestamp())).toString());
							lastBuild.setStatus(jd.getLastBuild().details().getResult().name());
							jb.setLastBuild(lastBuild);
							jobs.add(jb);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
			
		}
		return jobs;
	}

	public List<JenkinsBuild> getBuildHistory(String projectId, String jobName) {
		List<JenkinsBuild> bds = new ArrayList<JenkinsBuild>();
		if(this.nodes != null) {
			for(JenkinsNode node : nodes) {
				if(projectId.equals(node.getId())) {
					try{
						JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
						JobWithDetails jd = js.getJob(jobName);
						for(Build bd : jd.getAllBuilds()) {
							BuildWithDetails bwd = bd.details();
							JenkinsBuild jbd = new JenkinsBuild();
							jbd.setBuildNumber(bd.getNumber());
							jbd.setBuildDate((new Date(bwd.getTimestamp())).toString());
							jbd.setStatus(bwd.getResult().name());
							bds.add(jbd);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		return bds;
	}
	
	public JenkinsBuild getRunningBuild(String projectId, String jobName, int lastBuild){
		JenkinsBuild build = new JenkinsBuild();
		if(this.nodes != null) {
			for(JenkinsNode node : nodes) {
				if(projectId.equals(node.getId())) {
					try{
						JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
						JobWithDetails jd = js.getJob(jobName);
						Build bd = jd.getLastBuild();
						//System.out.println(bd.getNumber());
						if(bd.getNumber() > lastBuild) {
							BuildWithDetails bwd = bd.details();
							build.setBuildNumber(bd.getNumber());
							build.setBuildDate((new Date(bwd.getTimestamp()).toString()));
							if(bwd.isBuilding()) {
								build.setProcessMessage("Build is in progress");
								build.setRunning(true);
							} else {
								build.setProcessMessage("Build is completed");
								build.setRunning(false);
								build.setConsoleOutput(bwd.getConsoleOutputHtml());
							}
						} else {
							build.setBuildNumber(0);
							build.setBuildDate("");
							build.setProcessMessage("Build is not Start yet");
							build.setRunning(true);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		
		return build;
	}
	
	public JenkinsBuild startBuild(String projectId, String jobName, int lastBuild){
		JenkinsBuild build = new JenkinsBuild();
		if(this.nodes != null) {
			for(JenkinsNode node : nodes) {
				if(projectId.equals(node.getId())) {
					try{
						JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
						JobWithDetails jd = js.getJob(jobName);
						jd.build();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}		
		return build;
	}
	
	public JenkinsBuild getBuildDetail(String projectId, String jobName, int buildNumber) {
		JenkinsBuild build = new JenkinsBuild();
		
		if(this.nodes != null) {
			for(JenkinsNode node : nodes) {
				if(projectId.equals(node.getId())) {
					try{
						JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
						JobWithDetails jd = js.getJob(jobName);
						BuildWithDetails bb = jd.getBuildByNumber(buildNumber).details();
						build.setBuildNumber(buildNumber);
						build.setStatus(bb.getResult().name());
						build.setConsoleOutput(bb.getConsoleOutputHtml());
						build.setBuildDate((new Date(bb.getTimestamp()).toString()));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}		
		
		return build;
	}
}
