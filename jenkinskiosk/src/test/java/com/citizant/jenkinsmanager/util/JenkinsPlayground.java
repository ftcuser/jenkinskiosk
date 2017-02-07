package com.citizant.jenkinsmanager.util;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;
import com.offbytwo.jenkins.model.TestReport;

public class JenkinsPlayground {
	
	public static void main(String[] args){
		try {
			JenkinsServer jenkins = new JenkinsServer(new URI("http://localhost:8888/jenkins"), "admin", "c3381e7058f95115c72e2da032b60a78");
		
			//jenkins.
			
			Map<String, Job> jobs = jenkins.getJobs();
			
			Iterator<String> its = jobs.keySet().iterator();
			while(its.hasNext()) {
				String jobTitle = its.next();				
				System.out.println(jobTitle);
			}
			
			JobWithDetails job = jobs.get("User Manager Build").details();
			
			//Get the last build before start new 
			Build previousBuild = job.getLastBuild();
			
			System.out.println(previousBuild.getNumber());
			
			QueueReference qr = job.build();
			System.out.println(qr.getQueueItemUrlPart());
			
			//Loop and wait for the build finish
			while(true) {
				
				Build lastBuild = jobs.get("User Manager Build").details().getLastBuild();
				if(lastBuild.getNumber() > previousBuild.getNumber()) {
					//This is the new build.
					BuildWithDetails lb = lastBuild.details();
					if(!lb.isBuilding()) {
						System.out.println(lb.getConsoleOutputText());
						break;
					} else {
						System.out.println("Rnning......");
					}
				} else {
					System.out.println("Not Start yet......");
				}
				try{
					Thread.sleep(2000);
				}catch(Exception e){
					
				}
			}
			
			
			
			//Build b = job.getLastBuild();
			
			
			//Build b = job.getLastSuccessfulBuild();
			
			//System.out.println(b.getNumber());
			
			//System.out.println(b.details().toString());
			
			//TestReport r = b.getTestReport();
			
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
