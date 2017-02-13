package com.citizant.jenkinsmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.citizant.jenkinsmanager.bean.Dashboard;
import com.citizant.jenkinsmanager.bean.JenkinsNode;
import com.citizant.jenkinsmanager.service.JenkinsReportService;
import com.citizant.jenkinsmanager.service.JenkinsService;

@Controller
public class ReportController extends AbstractController{
	
	@Autowired	
	JenkinsService jenkinsService;
	
	@Autowired
	JenkinsReportService reportService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@RequestMapping("/dashbord")
	@ResponseBody
	public  Dashboard getDashboard(HttpServletRequest request){		
		String serverConfig = request.getSession().getServletContext().getRealPath("/WEB-INF/jenkins-servers.json");
		List<JenkinsNode> nodes = reportService.getJenkinsNodes(serverConfig);		
		return reportService.getAllJenkinsStatistics();
	}
}
