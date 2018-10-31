package com.heritage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.heritage.base.BasePageObject;

public class HomePage extends BasePageObject<HomePage> {
	private By homeTitleBar           = By.id("headerh1");
	private By homeDescription        = By.id("strap");
	private By homeHomeLink           = By.xpath("//ul[@id='top-menu']/li[1]/a");
	private By homePeopleLink         = By.xpath("//ul[@id='top-menu']/li[2]/a");
	private By homePlacesLink         = By.xpath("//ul[@id='top-menu']/li[3]/a");
	private By homeEventsLink         = By.xpath("//ul[@id='top-menu']/li[4]/a");
	private By homeEnvironmentLink    = By.xpath("//ul[@id='top-menu']/li[5]/a");
	private By homeResearchLink       = By.xpath("//ul[@id='top-menu']/li[6]/a");
	private By homeResearchHowToLink  = By.xpath("//ul[@id='top-menu']/li[6]/ul/li[1]/a");
	private By homeResearchAdviceLink = By.xpath("//ul[@id='top-menu']/li[6]/ul/li[2]/a");
	private By homeAboutUsLink        = By.xpath("//ul[@id='top-menu']/li[7]/a");
	private By homeContactLink        = By.xpath("//ul[@id='top-menu']/li[8]/a");
	
	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void waitForHomePageToLoad() {
		waitForVisibilityOf(homeTitleBar);
	}
	
	public String getHomeTitleBarText () {
		return getText(homeTitleBar);
	}
	
	public String getHomeDescriptionText() {
		return getText(homeDescription);
	}

	public String getHomeMenuItemText(String menuItem) {
		String menuItemText="NotFound";
		
		switch(menuItem) {
		case "Home":
			menuItemText = getText(homeHomeLink);
			break;	
		case "People":
			menuItemText = getText(homePeopleLink);
			break;
		case "Places":
			menuItemText = getText(homePlacesLink);
			break;
		case "Events":
			menuItemText = getText(homeEventsLink);
			break;
		case "Environment":
			menuItemText = getText(homeEnvironmentLink);
			break;
		case "Research":
			menuItemText = getText(homeResearchLink);
			break;
		case "Research_HowTo":
			menuItemText = getTextAttribute(homeResearchHowToLink);
			break;
		case "Research_Advice":
			menuItemText = getTextAttribute(homeResearchAdviceLink);
			break;
		case "About Us":
			menuItemText = getText(homeAboutUsLink);
			break;
		case "Contact":
			menuItemText = getText(homeContactLink);
		default:
			break;
		}
		return menuItemText;
	}

	public void clickHomeMenuItem(String menuItem) {
		switch(menuItem) {
		case "Home":
			click(homeHomeLink);
			break;	
		case "People":
			click(homePeopleLink);
			break;
		case "Places":
			click(homePlacesLink);
			break;
		case "Events":
			click(homeEventsLink);
			break;
		case "Environment":
			click(homeHomeLink);
			break;
		case "Research":
			click(homeResearchLink);
			break;
		case "Reseach_HowTo":
			click(homeResearchHowToLink);
			break;
		case "Research_Advice":
			click(homeResearchAdviceLink);
			break;
		case "About_Us":
			click(homeAboutUsLink);
			break;
		case "Contact":
			click(homeContactLink);
		default:
			break;
		}
	}
}
