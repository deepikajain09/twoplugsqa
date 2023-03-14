package com.twoplugs.test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TP017_FileAComplaint {

	WebDriver driver;
	String rootPath;
	
	//Launch two plugs login page
	@BeforeSuite
	public void logInPage() {
		rootPath = System.getProperty("user.dir");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://qatest.twoplugs.com/login");
		driver.manage().window().maximize();
					
	}

	//Login webelement
	@Test(dataProvider = "loginData")
	public void loginTest(String userName, String password) throws InterruptedException 
	{
		WebElement username = driver.findElement(By.xpath(".//input[@id = 'signInEmail']"));
		username.sendKeys(userName);
	
		WebElement passwordBox = driver.findElement(By.xpath(".//input[@id='signInPassword']"));
		passwordBox.sendKeys(password);
		Thread.sleep(2000);
		
		WebElement logIn = driver.findElement(By.xpath(" .//span[contains(text(),'LOG IN')]"));
		logIn.click();
		
		//Check the user profile page is displayed
		Boolean isUserProfileVisible =  driver.findElement(By.xpath(".//div[@class='profile-information']")).isDisplayed();
		assertTrue(isUserProfileVisible);
		System.out.println("User profile is displayed");
		
	}
	
	
	//Provide valid user data 
	@DataProvider(name = "loginData")
	String[][] loginData(){
		String[][]  data = {{"Linet","twoplugs2"}};
		return data;
	}
	
	
	//Search for a need to bid
	@Test(priority=1)
	public void GoToTransactionPage() throws InterruptedException 
	{
		WebElement transaction = driver.findElement(By.xpath(".//span[@class='w-icons-transactions']"));
		transaction .click();
		
		//Check the transaction page is displayed
		Boolean isTransactionPageDisplayed =  driver.findElement(By.xpath(".//span[@class='w-icons-transactions big']")).isDisplayed();
		assertTrue(isTransactionPageDisplayed );
		System.out.println("Transaction page and list of transactions history is displayed");
		Thread.sleep(5000);
	}
	
	//File a complaint after transaction
	@Test(priority=2)
	public void FileAaComplaint() throws InterruptedException
	{
		driver.navigate().to("https://qatest.twoplugs.com/complaintservice/37876");
		
		Boolean isFileaComplaintPageDisplayed =  driver.findElement(By.xpath(".//span[contains(text(),'File a Complaint')]")).isDisplayed();
		assertTrue(isFileaComplaintPageDisplayed);
		System.out.println("File a complaint page is displayed");
		
		WebElement subject = driver.findElement(By.xpath(".//input[@id='reportSubject']"));
		subject.sendKeys("Return");
		Thread.sleep(3000);
		
		WebElement content = driver.findElement(By.xpath(".//textarea[@name='content']"));
		content.sendKeys("Not Satisfied");
		Thread.sleep(3000);
				
		WebElement submitComplaint	= driver.findElement(By.xpath(".//span[contains(text(),'Submit complaint')]"));
		submitComplaint.click();
		
		System.out.println("File a complaint successfully");
		
	}
	
	
	//Close the browser
	@AfterSuite
	public void tearDown() 
	{
	driver.close();;
	}
	
}
