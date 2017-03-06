package com.citizant.jenkinsmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.citizant.jenkinsmanager.bean.JenkinsBuild;
import com.citizant.jenkinsmanager.bean.JenkinsJob;
import com.citizant.jenkinsmanager.bean.JenkinsNode;
import com.citizant.jenkinsmanager.bean.JenkinsView;
import com.citizant.jenkinsmanager.service.JenkinsService;




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
		List<JenkinsNode> nodes = jenkinsService.getJenkinsNodes();
		//List<JenkinsNode> nodes = jenkinsService.getLocalNodes(request.getSession()
		//		.getServletContext().getRealPath("/WEB-INF/jenkins-servers.json"));
		return nodes;
	}
	
	@RequestMapping("/updateNode")
	@ResponseBody
	public  List<JenkinsNode> updateJenkinsNode(@RequestBody JenkinsNode jenkinsNode){		
		jenkinsService.updateNode(jenkinsNode);
		return jenkinsService.getJenkinsNodes();
	}
	
	@RequestMapping("/deleteNode")
	@ResponseBody
	public  List<JenkinsNode> deleteJenkinsNode(@RequestBody JenkinsNode jenkinsNode){		
		jenkinsService.deleteNode(jenkinsNode);
		return jenkinsService.getJenkinsNodes();
	}
	
	@RequestMapping("/jenkinshome/{projectId}")
	@ResponseBody
	public List<JenkinsJob> getJenkinsInfo(@PathVariable String projectId){				
		return jenkinsService.getJobList(projectId);
	}
	
	@RequestMapping("/jenkinsviews/{projectId}")
	@ResponseBody
	public List<JenkinsView> getJenkinsViews(@PathVariable String projectId){				
		return jenkinsService.getViewsOfNode(projectId);
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
	
	@RequestMapping("/getbuilddetail/{projectId}/{jobName}/{buildNumber}")
	@ResponseBody
	public JenkinsBuild getJenkinsBuildDetail(@PathVariable String projectId, 
			@PathVariable String jobName,
			@PathVariable int buildNumber){		
		
		return jenkinsService.getBuildDetail(projectId, jobName, buildNumber);
	}
	
	@RequestMapping("/startpipline/{projectId}/{jobName}")
	@ResponseBody
	public JenkinsBuild startJenkinsPipeline(@PathVariable String projectId, 
			@PathVariable String jobName){		
		
		return jenkinsService.startBuild(projectId, jobName, 100);
	}
}
