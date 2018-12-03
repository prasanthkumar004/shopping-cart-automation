package com.ethoca.Pages;

import org.testng.annotations.Test;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ethoca.GenericLib.*;

public class HomePage {

	// =========================================Variables=================================================================================

	private WebDriver driver;
	String sql;
	protected static String showDetails;
	DatabaseFunction db = new DatabaseFunction();
	public List<String> lstObject, lstTestData;
	static String screenshotExtension;
	String sqlQry, Status, strResultText;
	final static Logger logger = Logger.getLogger(HomePage.class.getName());

	// =================================================================================================================================================================================
	// Constructor to initialize all the Page Objects
	public HomePage(WebDriver driver) throws Exception {
		try {
			this.driver = driver;
			// this.driver = GetWebDriverInstance.getBrowser(Browser);
			logger.info("Creating HomePage instance");
			lstTestData = db.getTestDataObject("Select * from HomePage",
					"Input");
			lstObject = db.getTestDataObject("Select * from HomePage",
					"ObjectRepository");
		} catch (Exception e) {
			Logger.getLogger(HomePage.class.getName()).log(Level.FATAL,
					"Error instantiating HomePage", e);
		}

	}

	// ========================================================================

	@Test
	public void goToAccessoriesPageFromHomePage() throws Exception {

		driver.get(lstTestData.get(0));

		Synchronization.waitForPageLoad(driver);

		if (Utilities.isPageisLoaded(driver, "Home Page", lstTestData.get(1))) {
WebElement productCategoryTab=null;
			try {
				// Launching Browser with valid URL.

				 productCategoryTab = Utilities.returnElement(driver,
						lstObject.get(2), lstObject.get(1));

				if (productCategoryTab.isDisplayed()) {
					Extent_Reports
							.executionLog(
									"PASS",
									Extent_Reports.logExpected
											+ "Product Category Tab should be available on home page"
											+ Extent_Reports.logActual
											+ "Product Category Tab is available on home page",
									driver);
					Actions hover = new Actions(driver);

					hover.moveToElement(productCategoryTab)
							.moveToElement(
									Utilities.returnElement(driver,
											lstObject.get(5), lstObject.get(4)))
							.click().build().perform();

				}

			} catch (Exception e) {
				ExceptionHandler.handleException(e,null,"","");	
				Extent_Reports
						.executionLog(
								"FAIL",
								Extent_Reports.logExpected
										+ "Product Category Tab ->Accessories should be available on home page"
										+ Extent_Reports.logActual
										+ "Product Category Tab -> Accessories is not  available on home page",
								driver);

			}
		}

	}

}
