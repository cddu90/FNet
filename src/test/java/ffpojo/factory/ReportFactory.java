/**
 * @author gholla01
 */
package com.bsc.qa.facets.ffpojo.factory;
import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * Factory class to create an instance of the extent report.
 * 
 * @author gholla01
 * @category Helper
 * This class is used to instantiate the reporting in runtime. Especially useful when different browsers are required for different 
 * testcases and for parallel execution. 
 *  
 */
public class ReportFactory {

	public static ExtentReports reporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();

	public synchronized static ExtentReports getExtentReport() {
		if (reporter == null) {
			// you can get the file name and other parameters here from a
			// config file or global variables
			//reporter = new ExtentReports("test-output/BSC-reports/"+"Report.html", true, DisplayOrder.NEWEST_FIRST);
			reporter = new ExtentReports("target/BSC-reports/"+"Report.html", true, DisplayOrder.NEWEST_FIRST);			
		}
		return reporter;
	}


// WIP by gholla01
//	public synchronized static ExtentTest getTest(String testName, String testDescription) {
//		//Code used for troubleshooting
////		// if this test has already been created return
////		if (!nameToTestMap.containsKey(testName)) {
////			//Long threadID = Thread.currentThread().getId();
////			//ExtentTest test = getExtentReport().startTest(testName, testDescription);
////			//nameToTestMap.put(testName, test);
////			//threadToExtentTestMap.put(threadID, testName);
////		}
//		return nameToTestMap.get(testName);
//	}

	/**
	 * Get testName from nameToTestMap
	 * 
	 * @param testName
	 * @return testName from nameToTestMap
	 */
	public synchronized static ExtentTest getTest(String testName) {
		return nameToTestMap.get(testName);
	}

	/**
	 * At any given instance there will be only one test running in a thread. We
	 * are already maintaining a thread to extentest map. This method should be
	 * used after some part of the code has already called getTest with proper
	 * testcase name and/or description.
	 * 
	 * @return testName
	 */
	public synchronized static ExtentTest getTest() {
		Long threadID = Thread.currentThread().getId();

		if (threadToExtentTestMap.containsKey(threadID)) {
			String testName = threadToExtentTestMap.get(threadID);
			return nameToTestMap.get(testName);
		}	
		return null;
	}

	/**
	 * Close testName report after every test method 
	 * 
	 * @param testName
	 */
	public synchronized static void closeTest(String testName) {

		if (!testName.isEmpty()) {
			ExtentTest test = getTest(testName);
			getExtentReport().endTest(test);
		}
	}


// WIP by gholla01
//	public synchronized static void closeTest(ExtentTest test) {
//		if (test != null) {
//			getExtentReport().endTest(test);
//		}
//	}
//
//	public synchronized static void closeTest()  {
//		ExtentTest test = getTest();
//		closeTest(test);
//	}

	/**
	 * Close extent report after test suite execution is complete
	 * 
	 */
	public synchronized static void closeReport() {
		if (reporter != null) {
			reporter.flush();
		}
	}

}

