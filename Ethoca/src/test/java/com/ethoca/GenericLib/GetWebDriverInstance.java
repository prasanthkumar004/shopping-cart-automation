package com.ethoca.GenericLib;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;



//=========================================================CLASS & METHODS =============================================================================================
public class GetWebDriverInstance extends Extent_Reports {
	public static String env;
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	
	private static String  genericPath=System.getProperty("user.dir").concat("/src/test/resources/DriverExecutable/");
	protected WebDriver driver;
	public static  String USERNAME="" ;
	public static  String AUTOMATE_KEY="" ;
	public static  String URL="" ;
	DesiredCapabilities caps ;
	//@SuppressWarnings("deprecation")
	@BeforeClass
	
	@Parameters(value={"environment"})
	public   void getBrowser(String environment) throws MalformedURLException 
	{
		
		env = environment;
		driver = null;
		
		

		switch (environment.toUpperCase()) 
		{
		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", genericPath+"geckodriver.exe");
			

			driver = drivers.get("Firefox");
			if (driver == null) 
			{
				driver = new FirefoxDriver();
				
			}
			break;
		case "IE":
			driver = drivers.get("IE");
			if (driver == null) 
			{
				System.setProperty("webdriver.ie.driver", genericPath+"IEDriverServer.exe");

				driver = new InternetExplorerDriver();
				drivers.put("IE", driver);
			}
			break;
		case "CHROME":
			driver = drivers.get("Chrome");
			if (driver == null) 
			{
				System.out.println(genericPath);
				
				System.setProperty("webdriver.chrome.driver",genericPath+"chromedriver.exe");
				
				
				
				ChromeOptions options = new ChromeOptions(); 
				options.addArguments("disable-infobars"); 
				options.setExperimentalOption("useAutomationExtension", false);
				options.addArguments("start-maximized");
				driver = new ChromeDriver(options);
				driver.manage().deleteAllCookies();
				

			}
			break;
			
		


		}
		
	}
	
	
	@AfterMethod(alwaysRun = true)
	public  void closeAllDriver() 
	{

		try
		{
			driver.quit();
		}
		catch(WebDriverException exc)
		{
			System.out.println("WebDriver Exception in CloseAllDriver " +exc.getMessage());
		}

	}



}
