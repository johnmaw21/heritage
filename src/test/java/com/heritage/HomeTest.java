package com.heritage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.heritage.base.DriverManager;
import com.heritage.base.EnvironmentPropertyReader;
import com.heritage.pages.HomePage;
import com.heritage.pages.LogInPage;
import com.heritage.pages.ProfilePage;


public class HomeTest {

	public static WebDriver driver;
	final public String heritage_username = EnvironmentPropertyReader.getPropertyOrEnv("heritage.user", "Default");
	final public String heritage_password = EnvironmentPropertyReader.getPropertyOrEnv("heritage.password", "Default");
	
//	@BeforeClass
//	public static void setUpClass() throws Exception {
//		driver = DriverManager.get();
//	}
	
	@Before
	public void setUpDriver()  {
		System.setProperty("webdriver.gecko.driver", "C:/webdrivers/geckodriver/geckodriver.exe");
		driver = new FirefoxDriver();
	}
	
	@Test
	public void homePageValidationTest() {
		LogInPage logInPage = new LogInPage(driver);
		String expectedTitleBarText    = "Bishopsteignton Heritage";
		String expectedDescriptionText = "The Story of Bishopsteignton: A Project for the Whole Community";
		String[] homeMenusTopLevel     = {"Home", "People", "Places", "Events", "Environment", "Research", "About Us", "Contact"};
		String researchHowToText       = "How to upload research to the website";
		String researchAdviceText      = "Research Advice";
		
		// Log into Bishopsteignton Heritage website and wiat for Profile Page to Load
		logInPage.openLogInPage();

		ProfilePage profilePage = logInPage.enterValidLoginDetails(heritage_username, heritage_password);
		profilePage.waitForProfilePageToLoad();

		
		// Navigate to Bishopsteignton Home Page
		HomePage homePage = profilePage.navigateToHomePage();
		homePage.waitForHomePageToLoad();
		
		// Verifications
		// Verify Home Title Bar Text and Description
		Assert.assertEquals(homePage.getHomeTitleBarText(), expectedTitleBarText);
		Assert.assertEquals(homePage.getHomeDescriptionText(), expectedDescriptionText);
		
		// Verify Home Menu Items
		for (String menuItem : homeMenusTopLevel) {
			Assert.assertEquals(homePage.getHomeMenuItemText(menuItem), menuItem);
		}
		Assert.assertEquals(homePage.getHomeMenuItemText("Research_HowTo"), researchHowToText);
		Assert.assertEquals(homePage.getHomeMenuItemText("Research_Advice"), researchAdviceText);
	}
	
	@After
	public void tearDownDriver() {
		driver.quit();
	}
	
//@AfterClass
//public static void methodTearDown() {
//	DriverManager.quit();
//}
	
}
