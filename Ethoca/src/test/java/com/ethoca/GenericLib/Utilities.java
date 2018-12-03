package com.ethoca.GenericLib;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Utilities {

	public static WebElement returnElement(WebDriver driver,
			String locatorType, String locatorPath) {
			try {
				switch (locatorType.toLowerCase()) {
				case "id":
					return driver.findElement(By.id(locatorPath));
				case "idCollection":
					return (WebElement) driver.findElements(By.id(locatorPath));
				case "xpath":
					return driver.findElement(By.xpath(locatorPath));

				case "name":
					return driver.findElement(By.name(locatorPath));

				case "classname":
					return driver.findElement(By.className(locatorPath));

				case "classnamecollection":
					List<WebElement> labels = driver.findElements(By
							.className(locatorPath));
					return (WebElement) labels;
				case "cssselector":
					return driver.findElement(By.cssSelector(locatorPath));

				case "linktext":
					return driver.findElement(By.linkText(locatorPath));

				case "tagname":
					return driver.findElement(By.tagName(locatorPath));

				default:
					throw new RuntimeException("Unknown locator " + locatorType
							+ " : " + locatorPath);
				}
			} catch (Exception e) {
				ExceptionHandler.handleException(e,null,locatorType,locatorPath);
				return null;
			}
		
	}

	public static boolean isPageisLoaded(WebDriver driver, String pageName,
			String expectedTitle) throws Exception {

		try {
			Assert.assertEquals(expectedTitle, driver.getTitle());
			Extent_Reports.executionLog("PASS", Extent_Reports.logExpected
					+ pageName + " should be loaded" + Extent_Reports.logActual
					+ pageName + " is Loaded", driver);
			return true;
		} catch (Exception e) {

			Extent_Reports.executionLog("FAIL", Extent_Reports.logExpected
					+ pageName + " should be loaded" + Extent_Reports.logActual
					+ pageName + " is not Loaded", driver);
			Logger.getLogger(Utilities.class.getName()).log(Level.FATAL,
					pageName + " is not Loaded", e);
			return false;

		}

	}

	public static List<WebElement> returnElements(WebDriver driver,
			String locatorType, String locatorPath) {
		try{
			switch (locatorType.toLowerCase()) {
			case "id":
				return driver.findElements(By.id(locatorPath));

			case "xpath":
				return driver.findElements(By.xpath(locatorPath));

			case "name":
				return driver.findElements(By.name(locatorPath));

			case "classname":
				return driver.findElements(By.className(locatorPath));

			case "cssselector":
				return driver.findElements(By.cssSelector(locatorPath));

			case "linktext":
				return driver.findElements(By.linkText(locatorPath));

			case "tagname":
				return driver.findElements(By.tagName(locatorPath));

			default:
				throw new RuntimeException("Unknown locator " + locatorType
						+ " : " + locatorPath);
			}
			}catch (Exception e) {
				ExceptionHandler.handleException(e,null,locatorType,locatorPath);
				return null;
			}}

}
