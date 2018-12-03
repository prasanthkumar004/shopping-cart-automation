package com.ethoca.GenericLib;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Synchronization 
{   

	static WebDriverWait wait;
	static WebElement elements;	
	private enum  Property 
	{
		clickable, visible,display;
	}

	public static boolean  explicitWait(WebDriver driver,WebElement objectID,String objectProperty)
	{

		wait = new WebDriverWait(driver, 90);
		Property objProp = Property.valueOf(objectProperty.toLowerCase()); 
		switch(objProp)
		{
		case clickable:
			elements = wait.until(ExpectedConditions.elementToBeClickable(objectID));
			break;

		case visible:
			elements = wait.until(ExpectedConditions.visibilityOf(objectID));
			break;
		default:
			break;
		
		}
		return elements != null;

	}
	//================================================================================================================================	
	public static void implicitWait(WebDriver driver,int Secs)
	{

		driver.manage().timeouts().implicitlyWait(Secs, TimeUnit.SECONDS);
	}

	//================================================================================================================================
	public static void waitForPageLoad(WebDriver driver) 
	{

		WebDriverWait wait = new WebDriverWait(driver, 50);

		wait.until(new ExpectedCondition<Boolean>() 
				{
			public Boolean apply(WebDriver wdriver) 
			{
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
				});



	}
}
