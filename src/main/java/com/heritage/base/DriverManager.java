package com.heritage.base;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager extends Thread {

	private static WebDriver aDriver = null;
	private static boolean avoidRecursiveCall = false;
	public static final String BROWSER_PROPERTY_NAME = "selenium2basics.webdriver";
	public static final String SAUCELABS_URL = "SAUCELABS_URL";

//	final String Username = "";
//	final String AccessKey = "";
//	final String Hub = "https://" + Username + ":" + AccessKey + "@ondemand.saucelabs.com:443/wd/hub";
	
	//private static final String DEFAULT_BROWSER = "FIREFOX";
	// private static final String DEFAULT_BROWSER = "SAFARI";
	 private static final String DEFAULT_BROWSER = "CHROME";

	public enum BrowserName {FIREFOX, CHROME, SAUCELABS, IE, GHOST, EDGE}

	private static BrowserName useThisDriver = null;

	// default for browsermob localhost:8080
	// default for fiddler: localhost:8888
	public static String PROXYHOST = "localhost";
	public static String PROXYPORT = "8080";
	public static String PROXY = PROXYHOST + ":" + PROXYPORT;

	public static void set(BrowserName aBrowser) {
		useThisDriver = aBrowser;
		// close any existing driver
		if (aDriver != null) {
			aDriver.quit();
			aDriver = null;
		}
	}

	public static WebDriver get() {

		if (useThisDriver == null) {
			String defaultBrowser = System.getenv(BROWSER_PROPERTY_NAME);

			if ("FIREFOX".contentEquals(defaultBrowser))
				useThisDriver = BrowserName.FIREFOX;

			if ("CHROME".contentEquals(defaultBrowser))
				useThisDriver = BrowserName.CHROME;

			if ("IE".contentEquals(defaultBrowser))
				useThisDriver = BrowserName.IE;

			if ("EDGE".contentEquals(defaultBrowser))
				useThisDriver = BrowserName.EDGE;

			if ("SAUCELABS".contentEquals(defaultBrowser))
				useThisDriver = BrowserName.SAUCELABS;

			if ("GHOST".contentEquals(defaultBrowser))
				useThisDriver = BrowserName.GHOST;

			if (useThisDriver == null)
				throw new RuntimeException("Unknown Browser in " + BROWSER_PROPERTY_NAME + ": " + defaultBrowser);
		}

		if (aDriver == null) {

			switch (useThisDriver) {
			case FIREFOX:
				System.setProperty("webdriver.gecko.driver", "C:/webdrivers/geckodriver/geckodriver.exe");
				aDriver = new FirefoxDriver();
				break;

			case CHROME:
				System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver/chromedriver.exe");
				aDriver = new ChromeDriver();
				break;

			case IE:
				System.setProperty("webdriver.ie.driver", "C:/webdrivers/iedriver/IEDriverServer.exe");
				aDriver = new InternetExplorerDriver();
				break;
			case SAUCELABS:

				DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
				firefoxCapabilities.setCapability("version", "5");
				firefoxCapabilities.setCapability("platform", Platform.XP);
				try {
					// add url to environment variables to avoid releasing with source
					String sauceURL = System.getenv(SAUCELABS_URL);
					aDriver = new RemoteWebDriver(new URL(sauceURL), firefoxCapabilities);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.setProperty("webdriver.gecko.driver", "C:/webdrivers/geckodriver/geckodriver.exe");
				aDriver = new FirefoxDriver();
			}

			// Shutdown Hook
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					DriverManager.quit();
				}
			});

		} else {

			try {
				// is browser still alive
				if (aDriver.getWindowHandle() != null) {
				}
			} catch (Exception e) {
				if (avoidRecursiveCall) {
					throw new RuntimeException("something has gone wrong as we have been in Driver.get already");
				}
				quit();
				aDriver = null;
				avoidRecursiveCall = true;
				return get();
			}
		}
		avoidRecursiveCall = false;
		return aDriver;
	}

	public static WebDriver get(String aURL, boolean maximize) {
		get();
		aDriver.get(aURL);
		if (maximize) {
			try {
				aDriver.manage().window().maximize();
			} catch (UnsupportedCommandException e) {
				System.out.println("Remote Driver does not support maximise");
			} catch (WebDriverException e) {
				System.out.println("Unexpected exception on trying to maximise");
				System.out.println(e.getMessage());
			}
		}
		return aDriver;
	}
	
	public static WebDriver get(String aURL) {
		return get(aURL, true);
	}

	public static void quit() {
		if (aDriver != null) {
			try {
				aDriver.quit();
				aDriver = null;
			} catch (Exception e) {
			}

		}
	}

}
