package com.heritage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

import com.heritage.base.BasePageObject;

public class ProfilePage extends BasePageObject<ProfilePage> {
	private By profileForm      = By.id("your-profile");
	private By profileFirstName = By.id("first_name");
	private By profileLastName  = By.id("last_name");
	private By profileNickname  = By.id("nickname");
	private By profileEmail     = By.id("email");
	private By homePageButton   = By.xpath("//li[@id='wp-admin-bar-site-name']");

	public ProfilePage(WebDriver driver) {
		super(driver);
	}

	public void waitForProfilePageToLoad() {
		waitForVisibilityOf(profileForm);
	}
	
	public String getUserProfileLoaded() {
		return getValue(profileFirstName) + " " + getValue(profileLastName);
	}
	
	public String getUserNickname() {
		return getValue(profileNickname);
	}
	
	public String getUserEmail() {
		return getValue(profileEmail);
	}
	
	public HomePage navigateToHomePage() {
		click(homePageButton);
		return new HomePage(driver);
	}
	public void waitForProfilePageTitle() {
		wait.until(titleContains("Profile"));
	}
	
}
