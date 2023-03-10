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

public class TP014_SendEeds {
	
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
	public void loginTest(String userName, String password) throws InterruptedException {
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
	
	//Search for a user for transaction
	@Test
	public void searchForUser() throws InterruptedException {
		WebElement searchField = driver.findElement(By.xpath(".//input[@id='exampleInputAmount']"));
		searchField.sendKeys("testcanada");
		Thread.sleep(2000);
		
		WebElement user = driver.findElement(By.xpath(".//div[@data-href='/profile/testCanada']"));
		user.click();
		//Check the other user profile is visible
		Boolean isOtherUserProfileVisible =  driver.findElement(By.xpath(".//div[@class='profile-information']")).isDisplayed();
		assertTrue(isOtherUserProfileVisible);
		System.out.println("Other user profile is displayed");
		
	}
	
	//Check the transaction is complete
	@Test
	public void sendEedsToOtherUser() throws InterruptedException {
		WebElement sendEedsIcon = driver.findElement(By.xpath(".//span[@class='w-icons-profileCtrl2']"));
		sendEedsIcon.click();
		
		//Check the amount transfer page is visible
		Boolean isAmountTansferPageVisible =  driver.findElement(By.xpath(".//h2[@class='pushH2']")).isDisplayed();
		assertTrue(isAmountTansferPageVisible);
		System.out.println("Amount transfer page is displayed to transfer");
		
		driver.findElement(By.xpath(".//div[@class='container']"));
		WebElement amount = driver.findElement(By.xpath(".//input[@id='transferAmount']"));
		amount.sendKeys("1");
		Thread.sleep(5000);
		
		WebElement transferIcon = driver.findElement(By.xpath(".//span[contains(text(), 'TRANSFER')]"));
		transferIcon.click();
		Thread.sleep(5000);
		
		WebElement confirmTransfer = driver.findElement(By.xpath(".//a[@id='btn_transfer']"));
		confirmTransfer.click();
				
	}
	
	//Close the browser
	@AfterSuite
	public void tearDown() 
	{
	driver.close();
	}

}
