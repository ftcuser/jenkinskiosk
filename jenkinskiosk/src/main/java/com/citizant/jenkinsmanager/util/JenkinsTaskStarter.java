package com.citizant.jenkinsmanager.util;

import hudson.cli.CLI;

public class JenkinsTaskStarter {
	
	public static void main(String[] args) {
		/*
		String[] commands = {"-s", "http://ec2-54-89-220-152.compute-1.amazonaws.com:8888/jenkins/", "login", "--username", "steven", "--password", "jenkins" };
		try{
			CLI._main(commands);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		String[] commands2 = {"-s", "http://localhost:8888/jenkins/", "list-jobs", "--username", "steven", "--password", "jenkins" };
		try{
			CLI._main(commands2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] commands3 = {"-s", "http://localhost:8888/jenkins/", "build", "User Manager Build", "-s", "-v", "--username", "steven", "--password", "jenkins" };
		try{
			CLI._main(commands3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
