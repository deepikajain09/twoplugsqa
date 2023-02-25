package com.twoplugs.test;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InitialTest { 
	WebDriver driver;
	String rootPath;
	Logger log = LogManager.getLogger(InitialTest.class);
	
	@BeforeSuite 
	public void loadLoginPage()  {
		System.out.println("Test1");
		//Set root path
		rootPath = System.getProperty("user.dir");
		//System.setProperty("webdriver.chrome.driver", rootPath+"\\driver\\chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://qatest.twoplugs.com/login");
		driver.manage().window().maximize();
		log.info("Initiated The Suite");

		

	}
	  
	@Test
	public void testTitle() {
		System.out.println("Test Title function");
		String expectedTitle = "twoPLUGS - A plug for your Service and another for your Need";
		String actualTitle = driver.getTitle();
		assertEquals(actualTitle,expectedTitle);

	}
	


	@AfterSuite
	public void closeApplication() throws InterruptedException {
		
		System.out.println("Close Browser");
		Thread.sleep(3000);
		driver.quit();
		
	}
}
