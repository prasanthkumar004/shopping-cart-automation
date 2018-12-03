package com.ethoca.GenericLib;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ExceptionHandler {
	
	private static WebElement element;
	private static String locatorType;
	private static String locatorPath;
	
	public static void handleException(Exception e,WebElement element, String locatorType, String locatorPath){
		ExceptionHandler.element=element;
		ExceptionHandler.locatorPath=locatorPath;
		ExceptionHandler.locatorType=locatorType;
		
		if(e instanceof NoSuchElementException){			
			Logger.getLogger(ExceptionHandler.class.getName()).log(Level.FATAL, "No Such Element Found Exception thrown for the following element: "+ExceptionHandler.generateElementDetails());
		}else if(e instanceof ElementNotInteractableException){		
			Logger.getLogger(ExceptionHandler.class.getName()).log(Level.FATAL,"This element is available in the page but it is currently not in interactable state: LocatorType: "+ExceptionHandler.generateElementDetails());
		}else{
			Logger.getLogger(ExceptionHandler.class.getName()).log(Level.FATAL,"Exception thrown while accessing this element: "+ExceptionHandler.generateElementDetails());
		}
		Logger.getLogger(ExceptionHandler.class.getName()).log(Level.FATAL, null, e);
		
	}
	
	private static String generateElementDetails(){
		try{
		String elementDet = "locatorType: "+locatorType+", locatorPath: "+locatorPath;
		if(element!=null)
			elementDet += ", Element Class: "+element.getAttribute("class")+", Element TagName: "+element.getTagName()+", Element Id: "+element.getAttribute("id")+", Element Name: "+element.getAttribute("name")+", Element Title: "+element.getAttribute("title");
		return elementDet;
		}catch(Exception e){
			//Logger.getLogger(ExceptionHandler.class.getName()).log(Level.FATAL, "Unable to fetch element details", e);
			return "UNABLE TO FETCH ELEMENT DETAILS";
		}
	}

}
