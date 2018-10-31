package com.heritage;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.heritage.base.DriverManager;
import com.heritage.base.EnvironmentPropertyReader;
import com.heritage.pages.LogInPage;
import com.heritage.pages.ProfilePage;

public class LogInTest {

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
	public void positiveLogInTest() {
				
		String expectedPageTitle   = "Profile";
		String expectedProfileName = "John Maw";
		String expectedNickname    = "OscarJohn";
		
		// Open Bishopsteignton Heritage login page - https://www.bishopsteigntonheritage.co.uk/wp-admin
		LogInPage logInPage = new LogInPage(driver);
		logInPage.openLogInPage();

		// Enter Valid Login Details and wait for profile page to load
		ProfilePage profilePage = logInPage.enterValidLoginDetails(heritage_username, heritage_password);
		profilePage.waitForProfilePageToLoad();


		// Verifications
		assertTrue("Profile Page Title not as expected", profilePage.getTitle().contains(expectedPageTitle));
		assertTrue("Profile Name not as expected", profilePage.getUserProfileLoaded().equals(expectedProfileName));
		assertTrue("Profile Nickname not as expected", profilePage.getUserNickname().equals(expectedNickname));
		assertTrue("Email Address has Invalid Format", profilePage.getUserEmail().matches("^(.*)@(.*)\\.(.*)$"));
	}
	
	@Test
	public void negativeLogInTests() {
		String invalidUsername         = "Joe Bloggs";
		String invalidPassword         = "Password";
		String nullUsername            = "";
		String nullPassword            = "";
		String expectedInvalidUsername = "ERROR: Invalid username";
		String expectedInvalidPassword = "ERROR: The password you entered for the username John Maw is incorrect. ";
		String expectedEmptyUsername   = "ERROR: The username field is empty";
		String expectedEmptyPassword   = "ERROR: The password field is empty";
		
		LogInPage logInPage = new LogInPage(driver);
		logInPage.openLogInPage();

		// Run Invalid Username and Password Tests
		
		logInPage.enterInvalidLoginDetails(invalidUsername, heritage_password);		
		assertTrue("Incorrect Error Message for Invalid Username", logInPage.getLoginErrorMessage().contains(expectedInvalidUsername));
		logInPage.clearLoginFields();
		
		logInPage.enterInvalidLoginDetails(invalidUsername, invalidPassword);		
		assertTrue("Incorrect Error Message for Invalid Username and Password", logInPage.getLoginErrorMessage().contains(expectedInvalidUsername));
		logInPage.clearLoginFields();
		
		logInPage.enterInvalidLoginDetails(heritage_username, invalidPassword);		
		assertTrue("Incorrect Error Message for Invalid Password", logInPage.getLoginErrorMessage().contains(expectedInvalidPassword));
		logInPage.clearLoginFields();
		
		logInPage.enterInvalidLoginDetails(heritage_username, nullPassword);
		assertTrue("Incorrect Error Message for Null Password", logInPage.getLoginErrorMessage().contains(expectedEmptyPassword));
		logInPage.clearLoginFields();
		
		logInPage.enterInvalidLoginDetails(nullUsername, heritage_password);		
		assertTrue("Incorrect Error Message for Null Username", logInPage.getLoginErrorMessage().contains(expectedEmptyUsername));
		logInPage.clearLoginFields();
	}

		@After
		public void tearDownDriver() {
			driver.quit();
		}
		
//	@AfterClass
//	public static void methodTearDown() {
//		DriverManager.quit();
//	}
}
