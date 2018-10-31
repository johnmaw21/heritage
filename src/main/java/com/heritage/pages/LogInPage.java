package com.heritage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.heritage.base.BasePageObject;

public class LogInPage extends BasePageObject<LogInPage> {

	private static final String URL = "https://www.bishopsteigntonheritage.co.uk/wp-admin";

	private By userNameField = By.id("user_login");
	private By passwordField = By.id("user_pass");
	private By logInButton   = By.id("wp-submit");
	private By logInError    = By.id("login_error");
	
	public LogInPage(WebDriver driver) {
		super(driver);
	}

	public void openLogInPage() {
		getPage(URL);
	}

	public ProfilePage enterValidLoginDetails(String email, String password) {
		type(email, userNameField);
		type(password, passwordField);
		click(logInButton);
		return new ProfilePage(driver);
	}
	
	public void enterInvalidLoginDetails(String email, String password) {
		type(email, userNameField);
		type(password, passwordField);
		click(logInButton);
	}

	public String getLoginErrorMessage() {
		waitForVisibilityOf(logInError, 10);
		return getText(logInError);
	}

	public void clearLoginFields() {
		clear(userNameField);
		clear(passwordField);
	}
	
}
