package com.ethoca.Test;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ethoca.GenericLib.GetWebDriverInstance;
import com.ethoca.Pages.AccessoriesPage;
import com.ethoca.Pages.CheckOutPage;
import com.ethoca.Pages.HomePage;

public class TCShoppingCartValidation extends GetWebDriverInstance
{
	@Test
	 public void Verify_TCShoppingCartValidation() throws Exception 
	 {
		try{

		HomePage objHP = new HomePage(driver);
		objHP.goToAccessoriesPageFromHomePage();
		
		AccessoriesPage objAP=new AccessoriesPage(driver);
		if(objAP.addToCart())
		{
			objAP.goToCheckOut();
		
		
			CheckOutPage objCP=new CheckOutPage(driver);
			if(objCP.verifyIfItemisAdded())
			{
				objCP.clickContinue();
				objCP.fillContactDetails();
				if(objCP.clickPurchase())
					objCP.checkIfSuccessfullPurchase();
			
			}
			
		
		}
		} catch (Exception exc) {
			
			Logger.getLogger(TCShoppingCartValidation.class.getName()).log(Level.FATAL,
					"Exception occurred in Verify_TCShoppingCartValidation", exc);
		}
		
		
		
	 }
} 