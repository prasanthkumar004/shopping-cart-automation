package com.ethoca.Pages;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.ethoca.GenericLib.*;

public class CheckOutPage {

	// =========================================Variables=================================================================================

	private WebDriver driver;
	String sql;
	protected static String showDetails;
	DatabaseFunction db = new DatabaseFunction();
	public List<String> lstObject, lstTestData;
	static String screenshotExtension;
	String sqlQry, Status, strResultText;
	final static Logger logger = Logger.getLogger(CheckOutPage.class.getName());

	// =================================================================================================================================================================================
	// Constructor to initialize all the Page Objects
	public CheckOutPage(WebDriver driver) throws Exception
	// public PGActivationPage(String Browser)
	{
		try {
			this.driver = driver;
			// this.driver = GetWebDriverInstance.getBrowser(Browser);
			lstTestData = db.getTestDataObject("Select * from CheckOut",
					"Input");
			lstObject = db.getTestDataObject("Select * from CheckOut",
					"ObjectRepository");
			logger.info("Creating CheckOutPage instance");
		} catch (Exception e) {
			Logger.getLogger(CheckOutPage.class.getName()).log(Level.FATAL,
				"Error instantiating CheckOutPage", e);

		}

	}

	// ========================================================================BUSINESS
	// VALIDATION LOGIC=================================================


	public boolean verifyIfItemisAdded() throws Exception {


		Synchronization.waitForPageLoad(driver);

		if (Utilities.isPageisLoaded(driver, "CheckOut", lstTestData.get(0))) {

			try {

				

				List<WebElement> tableRow =Utilities.returnElements(driver, lstObject.get(5), lstObject.get(4));

				if (tableRow.size() == 1) 
				{

					String productName = tableRow.get(0).findElement(By.xpath(lstObject.get(7))).getText();
					String productQty = tableRow.get(0).findElement(By.xpath(lstObject.get(10))).getAttribute("value");

					if (productName.equals(lstTestData.get(1))
							&& productQty.equals("1"))
					{
						Extent_Reports
						.executionLog(
								"PASS",
								Extent_Reports.logExpected
										+ "Magic Mouse should be available on the cart with one quantity "
										+ Extent_Reports.logActual
										+ "Magic Mouse is available on the cart with one quantity",
								driver);


						return true;

					}else{
						Extent_Reports
						.executionLog(
								"FAIL",
								Extent_Reports.logExpected
										+ "Magic Mouse should be available on the cart with one quantity "
										+ Extent_Reports.logActual
										+ "Magic Mouse is not available on the cart with one quantity",
								driver);

					}
				}

			} catch (Exception e) {
				ExceptionHandler.handleException(e,null,"","");	
			
						}
		}
		return false;

	}

	public void clickContinue() {
		WebElement contBtn=null;
		try{
			 contBtn=Utilities.returnElement(driver, lstObject.get(14), lstObject.get(13));
			contBtn.click();
			//Synchronization.implicitWait(driver, 2000);
			Synchronization.waitForPageLoad(driver);
		}
		catch(Exception e){
			ExceptionHandler.handleException(e,contBtn,"","");
			
		}

	}

	public void fillContactDetails() throws Exception {
		List<WebElement> contactDetails = null;
		WebElement currContactElement = null;
		String title = null;

		try {
			
			Synchronization.explicitWait(driver, Utilities.returnElement(driver, lstObject.get(23), lstObject.get(22)), "visible");
			// adding all the contact details element with the specified class and input tag to the list
			contactDetails= Utilities.returnElements(driver, lstObject.get(23), lstObject.get(22));

			// adding all the contact details element with the specified class and textarea tag to the list
			contactDetails.addAll(Utilities.returnElements(driver, lstObject.get(26), lstObject.get(25)));


			for (int i = 0; i < contactDetails.size(); i++) {
				currContactElement = contactDetails.get(i);
				title = currContactElement.getAttribute("title");
				
				if (null != title) {
					if (title.equals("billingemail")) {
						currContactElement.sendKeys("test@test.com");
					} 

					else if (title.equals("billingphone")) {
						currContactElement.sendKeys("1234567890");
					} 
					else if (title.startsWith("billing")) {
						currContactElement.sendKeys("test");
					}
				}
			}

			WebElement country=Utilities.returnElement(driver, lstObject.get(29), lstObject.get(28));
			Select dd=new Select(country);
			dd.selectByVisibleText("Canada");

			WebElement chkBx=Utilities.returnElement(driver, lstObject.get(32), lstObject.get(31));
			chkBx.click();
			//System.out.println("Filled all the contact details successfully");
			Extent_Reports.executionLog("PASS", Extent_Reports.logExpected
					+ "All the billing details should be entered "
					+ Extent_Reports.logActual
					+ "Able to enter the billing details ", driver);
		} catch (Exception e) {
				ExceptionHandler.handleException(e, currContactElement,"","");
				//e.printStackTrace();
				Extent_Reports.executionLog("FAIL", Extent_Reports.logExpected
						+ "All the billing details should be entered "
						+ Extent_Reports.logActual
						+ "Not able to enter the billing details ", driver);
				//Logger.getLogger(CheckOutPage.class.getName()).log(Level.FATAL,
					//	null, e);

		}
	}

	public boolean clickPurchase() {
		WebElement purchaseBtn=null;
		try {

		purchaseBtn=Utilities.returnElement(driver, lstObject.get(20), lstObject.get(19));
			/*driver.findElement(By.className("input-button-buy"))
			.click();
			 */
			purchaseBtn.click();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e, purchaseBtn, "", "");
			return false;
		}

	}

	public boolean checkIfSuccessfullPurchase() throws Exception {

			WebElement element = null;
		try {

			
			Synchronization.waitForPageLoad(driver);

			String successPurchaseResult = lstTestData.get(2);
			element = Utilities.returnElement(driver, lstObject.get(17), lstObject.get(16));
			String transResWrap = element.getText();
			Logger.getLogger(CheckOutPage.class.getName()).log(Level.INFO, "Purchase Results Text: " + transResWrap);
			
			if (transResWrap.contains(successPurchaseResult))
			{
				Extent_Reports
				.executionLog(
						"PASS",
						Extent_Reports.logExpected
								+ "Purchase confirmation message should be displayed "
								+ Extent_Reports.logActual
								+ "Purchase confirmation message is displayed ",
						driver);
				return true;
			}
		}
		catch (Exception e) {
			ExceptionHandler.handleException(e, element, "", "");
			Extent_Reports.executionLog("FAIL", Extent_Reports.logExpected
					+ "Should be navigated to purchase confirmation page "
					+ Extent_Reports.logActual
					+ "Purchase confirmation page not identified ", driver);
		}
		return false;
	}
}
