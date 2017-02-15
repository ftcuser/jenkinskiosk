package com.citizant.jenkinsmanager.selenium;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class JenkinsManagerHomePage {
	  private WebDriver driver;
	  private ScreenshotHelper screenshotHelper;
	  private WebDriverWait driverWait;
	  
	  private WebElement addUserButton;
	  private WebElement emailField;
	  private WebElement firstNameField;
	  private WebElement lastNameField;
	  private WebElement addButton;
	  
	  private String seleniumHub = "http://192.168.60.135:4444/wd/hub";
	  private String baseUrl =  "http://192.168.60.135:8080/usermanager";

	  
	  @Before
	  public void openBrowser() {
	  
	   // driver = new ChromeDriver();
	    //driver = new HtmlUnitDriver();
	    //((HtmlUnitDriver)driver).setJavascriptEnabled(true);
		  
		//try to get Selenium HUB and bas test URL from JVM parameters
		//This should set on Jenkins
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
	    
	    Capabilities cap = DesiredCapabilities.chrome();
	    driver = new RemoteWebDriver(hubUrl, cap);
	    driverWait = new WebDriverWait(driver, 30);
	    driver.get(base);
	   // screenshotHelper = new ScreenshotHelper();
	  }
	  
	  @After
	  public void saveScreenshotAndCloseBrowser() throws IOException {
	   // screenshotHelper.saveScreenshot("screenshot.png");
	    driver.quit();
	  }
	  
	  @Test
	  public void pageTitleAfterSearchShouldBeginWithDrupal() throws IOException {
		System.out.println("Page Title : " + driver.getTitle());
	    assertEquals("The page title should equal Jenkins Kiosk at the start of the test.", "Jenkins Kiosk", driver.getTitle());
	    
	
	    /*
	    WebElement searchField = driver.findElement(By.name("q"));
	    searchField.sendKeys("Drupal!");
	    searchField.submit();
	    */
	  }
	  
	  private class ScreenshotHelper {
	  
	    public void saveScreenshot(String screenshotFileName) throws IOException {
	      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	      FileUtils.copyFile(screenshot, new File(screenshotFileName));
	    }
	  }

}
