package com.twoplugs.test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TP016_AddServiceMatch {


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
		Thread.sleep(3000);
		
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
	
	
	@Test(priority=1)
	public void GoToSettings() throws InterruptedException
	{
		WebElement user = driver.findElement(By.linkText("Hi,Linet"));
		user.click();
		WebElement settings = driver.findElement(By.xpath(".//span[contains(text(), 'Settings')]"));
		settings.click();
		
		//Check the setting page is displayed
				Boolean isSettingPageVisible =  driver.findElement(By.xpath(".//span[@class='w-icons-settings dark']")).isDisplayed();
				assertTrue(isSettingPageVisible);
				System.out.println("Setting page is displayed");
		
	}
	
	@Test(priority=2)
	public void serviceMatch() throws InterruptedException{
		
		WebElement serviceMatch = driver.findElement(By.xpath(".//span[contains(text(), 'Service Match')]"));
		serviceMatch.click();
		Thread.sleep(2000);
		
		//Check the service match page is displayed
		Boolean isServiceMatchPageVisible =  driver.findElement(By.xpath(".//h5[@id='normalcase']")).isDisplayed();
		assertTrue(isServiceMatchPageVisible);
		System.out.println("Service match page is displayed");
		
		Select timeFrequency = new Select(driver.findElement(By.xpath(".//select[@id='frequency']")));
		System.out.println(timeFrequency.getOptions().size());
		timeFrequency.selectByIndex(0);
		Thread.sleep(2000);
		
		Select typeOfMatch = new Select(driver.findElement(By.xpath(".//select[@id='match_type']")));
		System.out.println(typeOfMatch.getOptions().size());
		typeOfMatch.selectByIndex(1);
		Thread.sleep(2000);
		
		Select categoryDropDown = new Select(driver.findElement(By.xpath(".//select[@id='category_id']")));
		System.out.println(categoryDropDown.getOptions().size());
		categoryDropDown.selectByIndex(5);
		Thread.sleep(2000);
		
		Select subCategoryDropDown = new Select(driver.findElement(By.xpath(".//select[@id='subcategory_id']")));
		System.out.println(subCategoryDropDown.getOptions().size());
		subCategoryDropDown.selectByIndex(2);
		
		WebElement add = driver.findElement(By.xpath(".//span[contains(text(), 'add')]"));
		add.click();

	}
	
	//Close the browser
	@AfterSuite
	public void tearDown() 
	{
	driver.close();
	}
	
}
