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

public class TP013_BidTheNeed {

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
	public void SearchForNeed() 
	{
		WebElement searchField = driver.findElement(By.xpath(".//span[@class='w-icons-search']"));
		searchField.click();
		
		WebElement needTab = driver.findElement(By.xpath(".//li[@id='tabn']"));
		needTab.click();
		
		WebElement searchInput = driver.findElement(By.xpath(".//input[@id='searchInput']"));
		searchInput.sendKeys("Needs22");
		
		WebElement searchButton = driver.findElement(By.xpath(".//button[@class='btn btn-filter']"));
		searchButton.click();
		
		//Check the list of need search
		Boolean isSearchNeedDisplayed =  driver.findElement(By.xpath(".//table[@class='result-table']")).isDisplayed();
		assertTrue(isSearchNeedDisplayed);
		System.out.println("List of search need is displayed");
	}
	
	//Bid the need using "I can do this"
	@Test(priority=2)
	public void BidTheNeed() throws InterruptedException
	{
		WebElement need = driver.findElement(By.xpath(".//a[contains(text(), 'Needs22')]"));
		need.click();
		
		//Check the need page is displayed to bid
		Boolean isNeedPageDisplayed =  driver.findElement(By.xpath(".//div[contains(text(),'Needs22')]")).isDisplayed();
		assertTrue(isNeedPageDisplayed);
		System.out.println("required need page is displayed to bid");
		
		WebElement iCanDoThisButton = driver.findElement(By.xpath(".//span[contains(text(),'I can do this')]"));
		iCanDoThisButton.click();
		Thread.sleep(3000);
		
		//Check the submit your page is displayed
		Boolean isSubmitYourPageDisplayed =  driver.findElement(By.xpath(".//h4[contains(text(),'SUBMIT YOUR BID')]")).isDisplayed();
		assertTrue(isSubmitYourPageDisplayed);
		System.out.println("Submit your page is displayed");
		
		WebElement priceToBid = driver.findElement(By.xpath(".//input[@id='price']"));
		priceToBid.clear();
		priceToBid.sendKeys("1");
		
		WebElement checkBox	= driver.findElement(By.xpath(".//div[@id='agreeterm-styler']"));
		checkBox.click();
		
		WebElement sendButton = driver.findElement(By.xpath(".//button[@id='contract_send']"));
		sendButton.click();
		
		//Check the message bidding sent
		boolean biddingSent = driver.findElement(By.xpath(".//h3[contains(text(),'Bidding Sent')]")).isDisplayed();
		assertTrue(biddingSent);
		System.out.println("Bid sent");
		
		WebElement okButton = driver.findElement(By.xpath(".//a[@class='btn btn-success pull-right']"));
		okButton.click();
	}
	
	
	//Close the browser
	@AfterSuite
	public void tearDown() 
	{
	driver.close();;
	}
}
