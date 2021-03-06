package com.citizant.jenkinsmanager.service;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.citizant.jenkinsmanager.bean.BuildStatistics;
import com.citizant.jenkinsmanager.bean.Dashboard;
import com.citizant.jenkinsmanager.bean.JenkinsNode;
import com.citizant.jenkinsmanager.dao.JenkinsNodesDao;
import com.citizant.jenkinsmanager.domain.ValueStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

public class JenkinsReportServiceImpl implements JenkinsReportService {

	@Autowired
	private JenkinsNodesDao jenkinsNodesDao;

	@Autowired
	private JenkinsService jenkinsService;

	private SimpleDateFormat SF = new SimpleDateFormat("MM/dd/yyyy");

	public List<JenkinsNode> getJenkinsNodes(String configFile) {
		List<JenkinsNode> nodes = new ArrayList<JenkinsNode>();
		try {
			List<com.citizant.jenkinsmanager.domain.JenkinsNode> jenkinsNodesList = jenkinsNodesDao.getNodes();

			// Check if the servers are running
			nodes.clear();
			for (com.citizant.jenkinsmanager.domain.JenkinsNode jenkinsNode : jenkinsNodesList) {
				JenkinsNode node = mapJenkinsNodeToUINode(jenkinsNode);
				nodes.add(node);
				JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(),
						node.getPassword());
				node.setRunning(js.isRunning());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nodes;
	}

	public Dashboard getCloudDashboard(List<JenkinsNode> nodes) {

		Dashboard ds = null;
		// Retrieve from database first
		String json = jenkinsNodesDao.getValue("dashboard");

		if (json != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {

				ds = mapper.readValue(json, new TypeReference<Dashboard>() {
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ds = getAllJenkinsStatistics(nodes);
		}
		return ds;

	}

	public Dashboard getNodeDashboard(String projectId) {
		Dashboard dashboard = new Dashboard();
		JenkinsNode node = jenkinsService.getJenkinsNodeById(projectId);
		HashMap<String, BuildStatistics> bumap = new HashMap<String, BuildStatistics>();
		countJenkins(dashboard, bumap, node);
		// Process results to list

		List<BuildStatistics> buildscs = new ArrayList<BuildStatistics>();
		buildscs.addAll(bumap.values());

		// Sort by time stamp
		Collections.sort(buildscs, new Comparator<BuildStatistics>() {
			public int compare(BuildStatistics b1, BuildStatistics b2) {
				if (b1.getDatestamp() > b2.getDatestamp())
					return 1;
				else
					return -1;
			}
		});
		dashboard.setBuildscs(buildscs);
		return dashboard;
	}

	public Dashboard getAllJenkinsStatistics(List<JenkinsNode> nodes) {
		Dashboard dashboard = new Dashboard();
		HashMap<String, BuildStatistics> bumap = new HashMap<String, BuildStatistics>();

		for (JenkinsNode node : nodes) {
			dashboard.setNumOfNodes(dashboard.getNumOfNodes() + 1);
			if (node.isRunning()) {
				dashboard.setNumOfLiveNodes(dashboard.getNumOfLiveNodes() + 1);
				countJenkins(dashboard, bumap, node);
			}
		}

		// Process results to list

		List<BuildStatistics> buildscs = new ArrayList<BuildStatistics>();
		buildscs.addAll(bumap.values());

		// Sort by time stamp
		Collections.sort(buildscs, new Comparator<BuildStatistics>() {
			public int compare(BuildStatistics b1, BuildStatistics b2) {
				if (b1.getDatestamp() > b2.getDatestamp())
					return 1;
				else
					return -1;
			}
		});
		dashboard.setBuildscs(buildscs);

		return dashboard;
	}

	private void countJenkins(Dashboard ds, HashMap<String, BuildStatistics> bumap, JenkinsNode node) {
		try {
			JenkinsServer js = new JenkinsServer(new URI(node.getServerUrl()), node.getUsername(), node.getPassword());

			Map<String, Job> jobMap = js.getJobs();
			Iterator<String> keys = jobMap.keySet().iterator();

			while (keys.hasNext()) {
				try{
				JobWithDetails jd = jobMap.get(keys.next()).details();
				List<Build> builds = jd.getAllBuilds();
				for (Build b : builds) {
					BuildWithDetails bwd = b.details();
					long dstamp = getBuildDate(bwd.getTimestamp());
					String bdate = getBuildDateStr(bwd.getTimestamp());

					BuildStatistics bds = bumap.get(bdate);
					if (bds == null) {
						bds = new BuildStatistics();
						bds.setDatestamp(dstamp);
						bds.setDate(bdate);
					}

					if (bwd.getResult().equals(BuildResult.SUCCESS)) {
						bds.setSucessful(bds.getSucessful() + 1);
						ds.setNumOfSuccessful(ds.getNumOfSuccessful() + 1);
					} else {
						bds.setFailed(bds.getFailed() + 1);
						ds.setNumOfFailed(ds.getNumOfFailed() + 1);
					}
					bumap.put(bdate, bds);
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private long getBuildDate(long timestamp) {
		String dd = SF.format(new Date(timestamp));
		try {
			return SF.parse(dd).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	private String getBuildDateStr(long timestamp) {
		return SF.format(new Date(timestamp));
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

	@Scheduled(fixedRate = 60000)
	public void generateCloudStatistics() {
		List<JenkinsNode> nodes = getJenkinsNodes(null);
		Dashboard dbs = getAllJenkinsStatistics(nodes);

		// Save results to database
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			String json = mapper.writeValueAsString(dbs);
			ValueStore vs = new ValueStore();
			vs.setJson(json);
			vs.setProperty("dashboard");
			jenkinsNodesDao.save(vs);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
