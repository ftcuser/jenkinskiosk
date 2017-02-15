package com.citizant.jenkinsmanager.service;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.citizant.jenkinsmanager.bean.JenkinsBuild;
import com.citizant.jenkinsmanager.bean.JenkinsJob;
import com.citizant.jenkinsmanager.bean.JenkinsNode;
import com.citizant.jenkinsmanager.bean.JenkinsView;
import com.citizant.jenkinsmanager.dao.JenkinsNodesDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.View;


public class JenkinsServiceImpl implements JenkinsService {
	
	@Autowired
	private JenkinsNodesDao jenkinsNodesDao;
	
	protected List<JenkinsNode> nodes = new ArrayList<JenkinsNode>();
	protected SimpleDateFormat fm  = new SimpleDateFormat("HH:mm a, MM/dd/yyyy");

	public List<JenkinsNode> getJenkinsNodes() {
		
		try {
			List<com.citizant.jenkinsmanager.domain.JenkinsNode> jenkinsNodesList = jenkinsNodesDao.getNodes();
			
			//Check if the servers are running
			nodes.clear();
			for(com.citizant.jenkinsmanager.domain.JenkinsNode jenkinsNode : jenkinsNodesList) {
				JenkinsNode node = mapJenkinsNodeToUINode(jenkinsNode);
				nodes.add(node);
				JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
				node.setRunning(js.isRunning());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
		return nodes;
	}
	
	public List<JenkinsView> getViewsOfNode(String projectId) {
		
		List<JenkinsView> views = new ArrayList<JenkinsView>();
		
		if(this.nodes != null) {
			for(JenkinsNode node : nodes) {
				if(projectId.equals(node.getId())) {
					try{
						JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());
			
						Map<String, View> jenkinsViews = js.getViews();
						int count = 0;
						for(View vw : jenkinsViews.values()){
							JenkinsView jvw = new JenkinsView();
							jvw.setName(vw.getName());
							jvw.setDescription(vw.getDescription());
							
							List<JenkinsJob> jobs = new ArrayList<JenkinsJob>();
							
							for(Job job : vw.getJobs()) {
								JobWithDetails jd = job.details();
								JenkinsJob jb = new JenkinsJob();
								jb.setName(jd.getName());
								jb.setDescription(jd.getDescription());
												
								JenkinsBuild lastBuild = new JenkinsBuild();
								BuildWithDetails bwd = jd.getLastBuild().details();
								lastBuild.setBuildNumber(bwd.getNumber());
								lastBuild.setBuildDate(formatDate(bwd.getTimestamp()));
								lastBuild.setStatus(bwd.getResult().name());
								jb.setLastBuild(lastBuild);
								jobs.add(jb);
							}
							jvw.setIndex(count);
							jvw.setJobs(jobs);
							views.add(jvw);
							count++;
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
			return views;
		}
		return views;
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
							BuildWithDetails bwd = jd.getLastBuild().details();
							lastBuild.setBuildNumber(bwd.getNumber());
							lastBuild.setBuildDate(formatDate(bwd.getTimestamp()));
							lastBuild.setStatus(bwd.getResult().name());
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

	@SuppressWarnings("rawtypes")
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
							jbd.setBuildDate(formatDate(bwd.getTimestamp()));
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
							build.setBuildDate(formatDate(bwd.getTimestamp()));
							if(bwd.isBuilding()) {
								build.setProcessMessage("Build is in progress");
								build.setRunning(true);
							} else {
								build.setProcessMessage("Build is completed");
								build.setRunning(false);
								build.setConsoleOutput(bwd.getConsoleOutputText());
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
						build.setConsoleOutput(bb.getConsoleOutputText());
						build.setBuildDate(formatDate(bb.getTimestamp()));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}		
		
		return build;
	}

	@Override
	public void updateNode(JenkinsNode uiNode) {
		com.citizant.jenkinsmanager.domain.JenkinsNode node = mapUINodeToJenkinsNode(uiNode);
		jenkinsNodesDao.save(node);
	}
	
	@Override
	public void deleteNode(JenkinsNode uiNode) {
		com.citizant.jenkinsmanager.domain.JenkinsNode node = mapUINodeToJenkinsNode(uiNode);
		node.setActive(false);
		jenkinsNodesDao.save(node);
		
	}

	private com.citizant.jenkinsmanager.domain.JenkinsNode mapUINodeToJenkinsNode(JenkinsNode uiNode) {
		com.citizant.jenkinsmanager.domain.JenkinsNode node = new com.citizant.jenkinsmanager.domain.JenkinsNode();
		if (uiNode.getId() != null && uiNode.getId().length() > 0) {
			node.setNodeId(uiNode.getId());
		}
		node.setProjectName(uiNode.getProjectName());
		node.setDescription(uiNode.getDescription());
		node.setServerUrl(uiNode.getServerUrl());
		node.setUsername(uiNode.getUsername());
		node.setPassword(uiNode.getPassword());
		node.setActive(uiNode.isActive());
		return node;
	}
	
	private JenkinsNode mapJenkinsNodeToUINode(com.citizant.jenkinsmanager.domain.JenkinsNode jenkinsNode) {
		JenkinsNode node = new JenkinsNode();
		node.setId(jenkinsNode.getNodeId());
		node.setProjectName(jenkinsNode.getProjectName());
		node.setDescription(jenkinsNode.getDescription());
		node.setServerUrl(jenkinsNode.getServerUrl());
		node.setUsername(jenkinsNode.getUsername());
		node.setPassword(jenkinsNode.getPassword());
		node.setActive(jenkinsNode.isActive());
		return node;
	}
	
	private String formatDate(long time) {
		return fm.format(new Date(time));
	}
}
