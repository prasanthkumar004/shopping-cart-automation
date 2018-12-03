package com.ethoca.GenericLib;


import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Extent_Reports extends TestListenerAdapter {
	public ExtentTest testReporter;
	public static ExtentReports extentReport;
	public static ExtentTest logger;
	static String screenshotExtension,reportFolder,destination,screenshotPath,screenshotExtensions;
	//public String screenshotExtensions;
	static File folder;
	static String browserType,ImagePath;
	static WebDriver drivers;
	static int iFlag=0,intPos=0;
	//static String appVersion;
	public static String logExpected="<br><b><font color=#0d0d0f>Expected Result: </font></b>" ;
	public static String logActual="<br><b><font color=#0d0d0f>Actual  Result: </font></b>" ;



	@BeforeMethod
	public void beforeMethod(Method caller) {
		logger=ExtentTestManager.startTest(browserType+"_"+this.getClass().getSimpleName());
		
	}

	
	public void getResult(ITestResult result) {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test Case Pass :"+result.getName());
			
		}
		else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Case Failed :"+result.getName());
		
		}
		else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP,"Test Case Skipped is :"+"_"+result.getName());
			
		}
		
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}
	//==============================================================='

		@BeforeClass
		@Parameters({"browser"})
		public String getBrowserType(String browser)
		{
		
			return browserType = browser;
		}
		//===============
		@BeforeSuite
		public void setup(ITestContext ctx) {
		    TestRunner runner = (TestRunner) ctx;
		    runner.setOutputDirectory(System.getProperty("user.dir").concat("/src/test/resources/") );
		}
//==================
	    //This method is to capture the screenshot and return the path of the screenshot.

			public static String getScreenshot(WebDriver driver) throws Exception 
			{
				
					//String dest;
					SimpleDateFormat sdfDateReport = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
					Date now = new Date();
					folder=ExtentTestManager.getFolder();
					
					screenshotExtensions=sdfDateReport.format(now) +"_"+System.currentTimeMillis();
		
					TakesScreenshot ts = (TakesScreenshot) driver;
					File source = ts.getScreenshotAs(OutputType.FILE);
				
					destination = folder+"/"+logger.getTest().getName()+"_"+screenshotExtensions+".png";
					File finalDestination = new File(destination);
			
					FileUtils.copyFile(source, finalDestination);
			
					ImagePath = logger.getTest().getName()+"_"+screenshotExtensions+".png";
			
				
				return ImagePath;
					
			}
//==========================================================================================================

	public static void executionLog(String status,String Description,WebDriver driver) throws Exception
	{
		drivers=driver;
		switch(status.toUpperCase())
		{
		case  "PASS":
		
				ExtentTestManager.getTest().log(LogStatus.PASS,driver.getCurrentUrl()+"\n"+Description+Extent_Reports.logger.addScreenCapture(Extent_Reports.getScreenshot(driver)));
				

					break;
		case  "FAIL":
			
				ExtentTestManager.getTest().log(LogStatus.FAIL,driver.getCurrentUrl()+"\n"+Description+Extent_Reports.logger.addScreenCapture(Extent_Reports.getScreenshot(driver)));
		
			break;
		case  "INFO":
		
				ExtentTestManager.getTest().log(LogStatus.INFO,driver.getCurrentUrl()+"\n"+Description+Extent_Reports.logger.addScreenCapture(Extent_Reports.getScreenshot(driver)));
		
			break;
		}

	}
	@AfterMethod
	public void afterMethod(ITestResult result)
	{
		getResult(result);
		
	
	}
//================================================================
	/*@AfterSuite
	public void endReport(){
	
		extentReport.endTest(logger);
		extentReport.flush();
		
		 
    }*/
	@AfterSuite
	public void afterSuite() {
		
		
		extentReport.endTest(logger);
		ExtentManager.getInstance().flush();
		
	}
}
