/**
 * @author gholla01
 * @category base class for test
 * BaseTest
 * This class provides factory methods to close and flush reports. Might be used to extend default behaviors for tests here 
 */
package com.bsc.qa.facets.ffpojo.factory;


import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/*
 * This is a base Test class. All testng tests needs to inherited from
 * this class. It contains common reporting infrastructure
 * @author gholla01
 */
public class sampleTest  {
	public static ExtentTest logger;
	public ExtentReports report;
	private String testCaseName;
	private String testMethodName;	
	public SoftAssert softAssert = null;
	//public String testValuables;

	/**
	 * @return the testMethodName
	 */
	public String getTestMethodName() {
		return testMethodName;
	}

	/**
	 * 
	 * @param testMethodName
	 */
	public void setTestMethodName(String testMethodName) {
		this.testMethodName = testMethodName;
	}

	/**
	 * @return the testCaseName
	 */ 
	public String getTestCaseName() {
		return testCaseName;
	}

	/**
	 * @param testCaseName the testCaseName to set
	 */
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	
	@AfterMethod
	public void afterMethod(Method caller) {
		ReportFactory.closeTest(caller.getName());
	}
	
	
	/*
	 * After suite will be responsible to close the report properly at the end
	 * You an have another afterSuite as well in the derived class and this one
	 * will be called in the end making it the last method to be called in test execution.
	 */
	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		ReportFactory.closeReport();
	}

	/**
	 * Initialize extent logger and report.
	 * 
	 * @param tc name
	 *
	 */
	@Deprecated
	protected void reportInit(String testContextName) {
		String envName = System.getenv("ENVNAME");
		logger = ReportFactory.getTest();
		report = ReportFactory.getExtentReport();
		logger = report.startTest(testContextName);
		loggerAssignCategory(testContextName, envName);
	}


	/**
	 * Initialize extent logger and report based on context and method name.
	 * 
	 * @param testContextName
	 * @param testMethodName
	 */
	protected void reportInit(String testContextName, String testMethodName) {
		String envName = System.getenv("ENVNAME");
		logger = ReportFactory.getTest();
		report = ReportFactory.getExtentReport();
		logger = report.startTest(testContextName + ": " + testMethodName);
		loggerAssignCategory(testContextName, envName);
	}

	/**
	 * Tag test suite category in the report depending on name of the environment.
	 * 
	 * @param testContextName
	 * @param envName
	 */
	private void loggerAssignCategory(String testContextName, String envName) {
		if(testContextName.toLowerCase().contains("smoke")){			
			logger.assignCategory("Smoke-"+envName);	
		}else if(testContextName.toLowerCase().contains("reg")){
			logger.assignCategory("Regression-"+envName);
		}else if(testContextName.toLowerCase().contains("func")){
			logger.assignCategory("Functional-"+envName);
		}else{
			logger.assignCategory("Automation-"+envName);	
		}
	}

	/**
	 * @AfterMethod gets called automatically after every @Test execution.
	 * This is framework provided method. Ensure this method exists in every Tests class
	 * 
	 * @param result
	 */
	@AfterMethod
	public void logResult(ITestResult result) {
		if(ITestResult.FAILURE==result.getStatus())
		{
			try 
			{
				System.setProperty("org.uncommons.reportng.escape-output", "false");

				logger.log(LogStatus.ERROR,result.getThrowable());
				logger.log(LogStatus.FAIL, result.getName() + "_" + testCaseName + " failed");				
			} 
			catch (Exception e)
			{		 
				System.out.println("Exception while logging results "+e.getMessage());
			} 
		}
		if(ITestResult.SUCCESS==result.getStatus()){
			logger.log(LogStatus.PASS, result.getName() +" verified successfully");
		}
	}

	
	public void printMapVals(Map<String, String> mapOfFileValues, Map<String, Object> dBmap, String keyName ){

		System.out.println("-------------------------------------------------------------------------------------------------------");
		
		if (mapOfFileValues != null) {
		    
			String Filevalue = mapOfFileValues.get(keyName).toString().trim();
			
			if(Filevalue.equals(null) || Filevalue.equals("")){
				
		    	Filevalue = "Empty or Blank";
  
			}
			 //key exists
	        System.out.println("File Key <<"+ keyName + ">>"  + " <<" + Filevalue + ">>" );
			
			
		}
		
		
		if (dBmap != null) {
			
			String DBValue = dBmap.get(keyName).toString().trim();
		    
			if(DBValue.equals(null) || DBValue.equals("")){
				
				DBValue = "Empty or Blank";
  
			}
			 //key exists
	        System.out.println("Database Key <<"+ keyName + ">>"  + " <<" + DBValue + ">>" );
			
			
		}

		
	}
	
	//method tests for null or empty value 
	public static boolean isNullOrBlank(String param) { 
		
	    return param == null || param.trim().length() == 0;
	    
	}
	
	
	//method will trim first two digits from RELATIONSHIP_CODE
	public static String trimRelCodeVal(String strValue){
		
		String subValue = null; 
		
		if(strValue.length()==3){
			
			 subValue = strValue.substring(1, 2);
			
		}else{
		
			 System.out.println("BAD RELATIONSHIP_CODE VALUE");
			
		}


		return subValue;
		
		
	}
	

}
