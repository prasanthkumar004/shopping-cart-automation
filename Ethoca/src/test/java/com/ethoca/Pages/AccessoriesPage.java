package com.ethoca.Pages;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ethoca.GenericLib.*;

public class AccessoriesPage {

	// =========================================Variables=================================================================================

	private WebDriver driver;
	String sql;
	protected static String showDetails;
	DatabaseFunction db = new DatabaseFunction();
	public List<String> lstObject, lstTestData;
	static String screenshotExtension;
	String sqlQry, Status, strResultText;
	final static Logger logger = Logger.getLogger(AccessoriesPage.class.getName());

	// =================================================================================================================================================================================
	// Constructor to initialize all the Page Objects
	public AccessoriesPage(WebDriver driver) throws Exception {
		try {
			this.driver = driver;
			// this.driver = GetWebDriverInstance.getBrowser(Browser);
			logger.info("Creating AccessoriesPage instance");
			lstTestData = db.getTestDataObject("Select * from Accessories",
					"Input");
			lstObject = db.getTestDataObject("Select * from Accessories",
					"ObjectRepository");
		} catch (Exception e) {
			Logger.getLogger(AccessoriesPage.class.getName()).log(Level.FATAL, null, e);
		}

	}

	public boolean addToCart() throws Exception {

		Synchronization.explicitWait(
				driver,
				Utilities.returnElement(driver, lstObject.get(2),
						lstObject.get(1)), "clickable");

		if (Utilities.isPageisLoaded(driver, "Accessories Page",
				lstTestData.get(0))) {

			try {
				List<WebElement> productTitles = Utilities.returnElements(
						driver, lstObject.get(2), lstObject.get(1));

				for (int i = 0; i < productTitles.size(); i++) {
					String prodTit = productTitles.get(i).getText();
					if (prodTit.equals(lstTestData.get(1))) {

						Extent_Reports
								.executionLog(
										"Pass",
										Extent_Reports.logExpected
												+ "Product Name "
												+ lstTestData.get(1)
												+ " should be available on the accessories page"
												+ Extent_Reports.logActual
												+ "Product Name "
												+ lstTestData.get(1)
												+ " is available on the accessories page",
										driver);

						// TO GET THE PARENT OF THE MAGIC MOUSE PRODUCT TITLE
						WebElement productCol = productTitles.get(i)
								.findElement(By.xpath("./.."));

						// CHILD ELEMENTS OF THE PARENT OF MAGIC MOUSE
						List<WebElement> childs = productCol.findElements(By
								.xpath(".//*"));
						for (int j = 0; j < childs.size(); j++) {
							// THEN CLICK ON ADD TO CART
							if (childs.get(j).getAttribute("value") != null
									&& childs.get(j).getAttribute("value")
											.equals("Add To Cart")) {

								Extent_Reports
										.executionLog(
												"PASS",
												Extent_Reports.logExpected
														+ lstTestData.get(1)
														+ " should be available on the Accessories page"
														+ Extent_Reports.logActual
														+ lstTestData.get(1)
														+ " available on the Accessories page",
												driver);
								childs.get(j).click();
								return true;

							}

						}

						break;
					}
				}
			} catch (Exception e) {
				
				ExceptionHandler.handleException(e,null,"","");	
				Extent_Reports.executionLog("FAIL", Extent_Reports.logExpected
						+ lstTestData.get(1)
						+ " should be available on the Accessories page"
						+ Extent_Reports.logActual + lstTestData.get(1)
						+ " is not available on the Accessories page", driver);

			}

		}
		return false;
	}

	public boolean goToCheckOut() throws Exception {
WebElement checkOut=null;
		try {
			Synchronization.explicitWait(
					driver,
					Utilities.returnElement(driver, lstObject.get(8),
							lstObject.get(7)), "clickable");
			 checkOut = Utilities.returnElement(driver,
					lstObject.get(8), lstObject.get(7));

			Extent_Reports
					.executionLog(
							"PASS",
							Extent_Reports.logExpected
									+ " Checkout option should be available on the Accessories page"
									+ Extent_Reports.logActual
									+ "Checkout option is  available on the Accessories page",
							driver);
			checkOut.click();
			return true;
		} catch (Exception e) {
			Extent_Reports
					.executionLog(
							"FAIL",
							Extent_Reports.logExpected
									+ " Checkout option should be available on the Accessories page"
									+ Extent_Reports.logActual
									+ "Checkout option is not available on the Accessories page",
							driver);
			ExceptionHandler.handleException(e,checkOut,"","");	
		}

		return false;

	}

}
