package com.ethoca.GenericLib;
//====================================================== All Required Packages =======================================================================================

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

//import com.codoid.products.fillo.Recordset;



public class DatabaseFunction

{
	//==========================================Script Variables==============================================================================================================	
    public static Connection con;
    public static String computerName,sql,Path;
    public String sqlQuery;
    public static String fileType;
//==========================================================================================================================================================================
    public DatabaseFunction() //Constructor of the class.
    {
	    try
	    {
	        computerName = InetAddress.getLocalHost().getHostName();   // Name of the System where user is executing the Scripts.
	        Path=System.getProperty("user.dir");                       // Soruce Directory of the Source code
	        Class.forName("org.relique.jdbc.csv.CsvDriver");
	       
	    }
	    catch (Exception exc)
	    {
	        System.out.println("DBConnection Failed"+exc);
	        
	    }
    }
    
    //##################################################################################################################################
    /** Name of the Fuction: fnGetDbConnection
        Description:This function will  return the connection object for the file type.
        Input Type: Type of the file which needs to connect as Database. Ex: 'Object Repository','Input'
        Return Value:  connection object. */
    
    public Connection fnGetDbConnection(String FileType)
    {   
    	
    	String genericPath=System.getProperty("user.dir").concat("/src/main/resources/");
    	 
    	try
    	{
	    	if("Input".equals(FileType))
	    	   
	    	{
	    		con = DriverManager.getConnection("jdbc:relique:csv:"+genericPath+"TestData/");
	    		
	    	}
	    		    	
	    
	    	if("ObjectRepository".equals(FileType))
		    	   
	    	{
	    		
	    		con = DriverManager.getConnection("jdbc:relique:csv:"+genericPath+"ObjectRep/");
	    		
	    		
	    	}
	    	if("TestRunner".equals(FileType))
		    	   
	    	{
	    		
	    		con = DriverManager.getConnection("jdbc:relique:csv:"+genericPath+"TestRunner/");
	    		
	    		
	    	}
	         //System.out.println("Database connected Successfully.");
	       
    	}
    	catch(Exception exc)
    	{
    		
    		System.out.println("fnGetDbConnectionDBConnection Failed"+exc);
    	}
    	
    	return con;
     }
 //##########################################################################################################################################
  //#############################################################################################################
  	public List<String> getTestDataObject(String sqlQuery,String FileType)
  	{
  	   
  		List<String> list = new ArrayList<String>();
  //		ArrayList<String> colName;
	  	try
	  	{
	  		 fnGetDbConnection(FileType);
	  		 Statement stmt = con.createStatement();
	  		 ResultSet results = stmt.executeQuery(sqlQuery); //"SELECT * FROM BreakingNews"
	  		 
	  		 ResultSetMetaData meta = results.getMetaData();
	  		 while(results.next())
	  		 {
		    	for (int iLoop = 0; iLoop <  meta.getColumnCount(); iLoop++)
				{
				
					list.add( results.getString(iLoop+1));
					
					
				}
		          
		      }
		    
		   
	//	    boolean append = true;
		    
		  //  con.close();
	  	}
	  	catch (Exception exc)
	  	{       
	  	  System.out.println(exc);
	  	}
  
  	return list;
  	}
  	
  //#############################################################################################################


  //<summary>
  //Function to get the object details from database for the specified screen name.
  //</summary>
  //<param name="screenName">Screen Name</param>
  //<returns>True if Screen Name exists in database else returns false.</returns>

  	public Hashtable<String,String> getHashTestData(String sqlQuery,String FileType)
  	
  	{
  		Hashtable<String, String> htbl = new Hashtable<String,String>();
  		
  		try
  	{
  		fnGetDbConnection("TestRunner");
  		 Statement stmt = con.createStatement();
  		 ResultSet results = stmt.executeQuery(sqlQuery); //"SELECT * FROM BreakingNews"
  		 
  		 //ResultSetMetaData meta = results.getMetaData();

  	
  	    while(results.next())  
  		{

  	    	String TestClass = results.getString("TestClass").trim();
  	    	String Browser= results.getString("Browser").trim();
  	    	htbl.put(TestClass, Browser);
  		}
  	}
  	catch (Exception exc)
  	{       
  	  System.out.println(exc);
  	}
  	return htbl;

  	}
  //#############################################################################################################


}
