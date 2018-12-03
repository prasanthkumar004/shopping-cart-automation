package com.ethoca.TestRunner;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.ethoca.GenericLib.DatabaseFunction;

public class TestRunner {
	public Hashtable<String, String> htTestRunner;
	public List<String> lstBrowser, lstExecutionType, lstBrowserVersion, lstOS,
	lstOSVersion;
	public int poolSize = 0, executionCnt = 0;
	public String browsers;

	@Parameters({ "aut" })
	@Test
	public void tRun(String aut) {

		DatabaseFunction objDBF = new DatabaseFunction();
		try {
			switch (aut.toUpperCase()) {

			case "CHROME":
				lstExecutionType = objDBF
				.getTestDataObject(
						"Select ExecutionType from TestSuite Where Execution ='Yes' and Browser='Chrome'",
						"TestRunner");

				htTestRunner = objDBF.getHashTestData(
						"Select * from TestSuite Where Execution ='Yes' AND ExecutionType='"
								+ aut + "'", "TestRunner");
				poolSize = 1;
				executionCnt = htTestRunner.size();
				break;
			case "FIREFOX":
				lstExecutionType = objDBF
				.getTestDataObject(
						"Select Distinct(ExecutionType) from TestSuite Where Execution ='Yes' and Browser='Firefox'",
						"TestRunner");
				if (lstExecutionType.get(0).compareToIgnoreCase("Remote") == 0) {
					poolSize = 2;
					htTestRunner = objDBF
							.getHashTestData(
									"Select * from TestSuite Where Execution ='Yes' AND Browser='Chrome'",
									"TestRunner");
					aut = "Remote";
					executionCnt = htTestRunner.size();
				} else {
					htTestRunner = objDBF
							.getHashTestData(
									"Select * from TestSuite Where Execution ='Yes' AND Browser='Chrome'",
									"TestRunner");
					poolSize = 1;
					executionCnt = htTestRunner.size();
				}
				break;
			default:
				Logger.getLogger(TestRunner.class.getName()).log(Level.INFO,
						"Please Specify the Environment to Execute");

			}


			if (executionCnt > 0) {

				TestNG myTestNG = new TestNG();
				XmlSuite mySuite = new XmlSuite();
				mySuite.setName("Automation");

				XmlTest myTest = new XmlTest(mySuite);
				myTest.setName("Shopping Cart");
				List<XmlClass> myClasses = new ArrayList<XmlClass>();
				Set<Entry<String, String>> set = htTestRunner.entrySet();
				Iterator<Entry<String, String>> intIterator = set.iterator();
				while (intIterator.hasNext()) {

					Map.Entry<String, String> entry = ((Entry<String, String>) intIterator
							.next());

					myClasses.add(new XmlClass((String) entry.getKey()));

					myTest.setXmlClasses(myClasses);
					lstBrowserVersion = objDBF
							.getTestDataObject(
									"Select Distinct(BrowserVersion) from TestSuite Where Execution ='Yes' and  ExecutionType='"
											+ aut + "'", "TestRunner");
					lstOS = objDBF
							.getTestDataObject(
									"Select Distinct(OS) from TestSuite Where Execution ='Yes' and  ExecutionType='"
											+ aut + "'", "TestRunner");
					lstOSVersion = objDBF
							.getTestDataObject(
									"Select Distinct(OSVersion) from TestSuite Where Execution ='Yes' and  ExecutionType='"
											+ aut + "'", "TestRunner");
					myTest.addParameter("environment", (String) aut);
					//System.out.println(aut);
					myTest.addParameter("browser", (String) entry.getValue());
					myTest.addParameter("version",
							(String) lstBrowserVersion.get(0));
					myTest.addParameter("os", (String) lstOS.get(0));
					myTest.addParameter("osversion", (String) lstOSVersion.get(0));

				}
				List<XmlTest> myTests = new ArrayList<XmlTest>();
				myTests.add(myTest);
				mySuite.setTests(myTests);
				List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
				mySuites.add(mySuite);
				myTestNG.setXmlSuites(mySuites);
				myTestNG.run();
			} else {
							
				Logger.getLogger(TestRunner.class.getName()).log(Level.INFO,
						"Execution Flag Set as False in TestRunner");
			}
		} catch (Exception exc) {
			//System.out.println("Exception in TestRunner:" + exc.getMessage());
			Logger.getLogger(TestRunner.class.getName()).log(Level.FATAL,
					"Exception in TestRunner", exc);
		}
	}

}