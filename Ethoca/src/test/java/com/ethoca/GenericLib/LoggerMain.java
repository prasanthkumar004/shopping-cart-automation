package com.ethoca.GenericLib;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggerMain {
    
    public static void initialize(){
        try{
        	String genericPath = System.getProperty("user.dir").concat(
    				"/src/test/resources/log4j.properties");
        	
        org.apache.log4j.PropertyConfigurator.configure(genericPath);
        }catch(Exception e){
            Logger.getLogger(LoggerMain.class.getName()).log(Level.FATAL, null, e);
        }
        
    }
    
}