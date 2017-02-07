package com.citizant.jenkinsmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.citizant.jenkinsmanager.bean.JenkinsBuild;
import com.citizant.jenkinsmanager.bean.JenkinsJob;
import com.citizant.jenkinsmanager.bean.JenkinsNode;
import com.citizant.jenkinsmanager.service.JenkinsService;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;



@Controller
public class RestController extends AbstractController {
	
	@Autowired	
	JenkinsService jenkinsService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		return null;
	}
	
	@RequestMapping("/listnodes")
	@ResponseBody
	public  List<JenkinsNode> listJenkinsNodes(HttpServletRequest request){		
		String serverConfig = request.getServletContext().getRealPath("/WEB-INF/jenkins-servers.json");
		List<JenkinsNode> nodes = jenkinsService.getJenkinsNodes(serverConfig);
		return nodes;
	}

	
	@RequestMapping("/jenkinshome/{projectId}")
	@ResponseBody
	public List<JenkinsJob> getJenkinsInfo(@PathVariable String projectId){				
		return jenkinsService.getJobList(projectId);
	}
	
	@RequestMapping("/buildhistory/{projectId}/{jobName}")
	@ResponseBody
	public List<JenkinsBuild> getJenkinsBuilds(@PathVariable String projectId, @PathVariable String jobName){				
		return jenkinsService.getBuildHistory(projectId, jobName);
	}
	
	@RequestMapping("/startbuild/{projectId}/{jobName}/{lastBuild}")
	@ResponseBody
	public JenkinsBuild startJenkinsBuilds(@PathVariable String projectId, 
			@PathVariable String jobName,
			@PathVariable int lastBuild){		
		
		return jenkinsService.startBuild(projectId, jobName, lastBuild);
	}
	
	@RequestMapping("/getbuild/{projectId}/{jobName}/{lastBuild}")
	@ResponseBody
	public JenkinsBuild getJenkinsBuilds(@PathVariable String projectId, 
			@PathVariable String jobName,
			@PathVariable int lastBuild){		
		
		return jenkinsService.getRunningBuild(projectId, jobName, lastBuild);
	}
}
