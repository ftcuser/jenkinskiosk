package com.citizant.jenkinsmanager.selenium;

	import java.io.IOException;
	import java.net.URL;

	import org.junit.AfterClass;
	import org.junit.Assert;
	import org.junit.BeforeClass;
	import org.junit.Test;
	import org.openqa.selenium.By;
	import org.openqa.selenium.Capabilities;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.remote.RemoteWebDriver;
	import org.openqa.selenium.remote.DesiredCapabilities;

	public class JenkinsManagerHomePage {
		private static String seleniumHub= "http://50.19.179.31:4444/wd/hub";
		private static String baseUrl = "http://50.19.179.31:9090/jenkinsmanager/index.html";
		private  static WebDriver driver;
		
		
	
		@BeforeClass
		public static void setUpDriver() throws IOException
		{	
			String hub =  System.getProperty("selenium.hub");
		if(hub == null) {
			hub  = seleniumHub;
		}
		
		String base = System.getProperty("app.baseurl");
		if(base == null) {
			base = baseUrl;
		}
		System.out.println("Selenium HUB : "  + hub);
		System.out.println("The app URL : "  + base);
		  
	    URL hubUrl = null;
	    try{
	    	hubUrl = new URL(hub);
	    }catch(Exception e){
	    	
	    }
		    Capabilities cap = DesiredCapabilities.firefox();	    
		    driver = new RemoteWebDriver(new URL(seleniumHub),cap);	   
		}
			   
		@Test	
		
				    
		    public void testSeleniumInfrastructure() throws IOException, InterruptedException {   
			driver.get(baseUrl);
			Assert.assertEquals(driver.getTitle(),"Jenkins Kiosk");
		    
		    /*
		    driver.findElement(By.id("email_id")).sendKeys("admin@kudo.com");
		    driver.findElement(By.id("password_id")).sendKeys("12345");
		    driver.findElement(By.id("btnSubmit")).click();
		    
		   // Assert.assertEquals(driver.getTitle(),"Flash");
		    //driver.findElement(By.linkText("Add a User")).click();
		    String URL = driver.getCurrentUrl();
		    Assert.assertEquals(URL, "http://50.19.150.209:8090/kudos/servlet/home/doLogin");
		    */
		}
		    
		    @AfterClass	    
			public static void  tearDownDriver(){	
				driver.quit();
				
		}
}

