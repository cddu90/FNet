/**
 * @author apudur01..
 * @category testcase
 * @class UnAuthenicatedPageLinksTest
 * @Uses PageObject CitrixLandingPage , MemberWhyBlueShieldPage, memberFindAPlanPage
 * @since 3/24/2017
 * 
 */
package com.bsc.qa.web.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WebUtils.ExcelUtilities;
import WebUtils.TestFileUtil;
import WebUtils.WebUtils;

import com.bsc.qa.framework.base.BaseTest;
import com.bsc.qa.framework.factory.BrowserFactoryManager;
import com.bsc.qa.framework.factory.ReportFactory;
import com.bsc.qa.web.pages.BlueSquareLoginPage;
import com.bsc.qa.web.pages.FileNetCreateQueuePage;
import com.bsc.qa.web.pages.FileNetLoginPage;
import com.bsc.qa.web.pages.HomeSccfHistoryPage;

import com.bsc.qa.web.pages.MisRouteResponsePage;
import com.bsc.qa.web.pages.MisRoutedClaimIndexCorrection;
//import com.bsc.qa.web.pages.HostSccfHistoryPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


/**
 * To start off the FileNet regression scenarios
 *
 */
public class FileNetTest extends BaseTest implements IHookable {

	static SoftAssert softAssert = new SoftAssert();

	private static  String inputDataSheetPath;
	private static  String environment;
	// private static String sheetName;
		//private static DBUtils objDBUtility;
		//private static	Map<String,String>queryDataMap=new HashMap<String,String>();
		//private static	List<Map<String,String>>listOfRecords=new ArrayList<Map<String,String>>();
	private FileNetLoginPage objFileNetLoginPage;
	private FileNetCreateQueuePage<?> objFileNetCreateQueue;
	private MisRoutedClaimIndexCorrection objMisRoutedClaimIndexCorrection;
	private MisRouteResponsePage objMIsRouteResponsePage;
	
     private BlueSquareLoginPage objBlueSquareLoginPage;
     private HomeSccfHistoryPage objHomeSccfHistoryPage;
	
	private WebUtils utils= new WebUtils();
	
	//private HostSccfHistoryPage objHostSccfHistoryPage;
	
	
	public static String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss"));
	public static String resultsDestinationFolderPath = "target\\FileNetResults_" + timestamp;
	

	//HashMap<String, String> dataMap = new HashMap<String, String>();
	/**
	 * @param testCaseName
	 * @param testMethodName
	 */
	protected void initBrowser(String testCaseName, String testMethodName) {
		WebDriver driver = BrowserFactoryManager.getDriver();
		objFileNetLoginPage=PageFactory.initElements(driver, FileNetLoginPage.class);
		objFileNetLoginPage.setPage(driver, browser, environment, testCaseName);
		objFileNetCreateQueue=PageFactory.initElements(driver,FileNetCreateQueuePage.class);
		objMisRoutedClaimIndexCorrection=PageFactory.initElements(driver,MisRoutedClaimIndexCorrection.class);
		objMIsRouteResponsePage=PageFactory.initElements(driver,MisRouteResponsePage.class);
		
		objBlueSquareLoginPage=PageFactory.initElements(driver,	BlueSquareLoginPage.class);
		objBlueSquareLoginPage.setPage(driver, browser, environment, testCaseName);
		objHomeSccfHistoryPage=PageFactory.initElements(driver,	HomeSccfHistoryPage.class);
		objHomeSccfHistoryPage.setPage(driver, browser, environment, testCaseName);
		
		
	}
	

	
	

	/**
	 * To submit the MAPD and PDP flow request in DRX broker portal application
	 * @param data 
	 * 			- sending test data sheet values to methods
	 * @throws Exception
	 * 			- to capture the exception
	 */
	@Test(dataProvider = "FileNetDataProvider")
	public void CreateMessage(Map<String, String> data)
	{
		try
		{
		
			
			Thread.sleep(100);
			
			//Creating the Driver object to Launch the Web browser
			WebDriver driver = BrowserFactoryManager.getDriver();
			
			//Calling function to log into the Blue2 application
	objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
			
			
			Thread.sleep(1000);
			
			objFileNetCreateQueue.CreateQueuePage(data, driver,logger);
			
			if(objFileNetCreateQueue.IsErrorVisible(driver))
			{
				String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);
				logger.log(LogStatus.FAIL, "Failed to create Message : "+ErrorMessage+"  displayed");
				
			}
			else
			{
				logger.log(LogStatus.PASS, "New Message Created Successfully");
			}

		}
		catch (Exception E)
			
			{
			     logger.log(LogStatus.FAIL, E );
			     
			}
					
		
	}

	
	/**
	 * To submit the Medicare Supplement request in DRX broker portal application
	 * @param data
	 * 			- sending test data sheet values to methods
	 * @throws Exception
	 * 			- to capture the exception
	 */
	
	
	
	
	
	@Test(dataProvider = "FileNetDataProvider")
	public void ValidateError(Map<String, String> data)throws Exception {
		WebDriver driver = BrowserFactoryManager.getDriver();

		try
		{
		//Calling function to log into the Blue2 application
        objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		
		
		Thread.sleep(1000);
		
		objFileNetCreateQueue.CreateQueuePage(data, driver,logger);
		
		String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);
		
		String[] Errors=ErrorMessage.split("\n");
		if (Errors[1].contains(	"First 3 characters of Subscriber ID cannot contain 0 or 1"))
		{
		
			logger.log(LogStatus.PASS,"Error Message: " + Errors[1] + "  displayed successfully");
			
			
		}
		else
		{
			logger.log(LogStatus.FAIL,"Error Message not displayed successfully");
		}
		
		}
		catch (Exception E)
		
		{
		     logger.log(LogStatus.FAIL, E );
		   
		     
		}
		
		
			
	}
	
	@Test(dataProvider = "FileNetDataProvider")
	public void ValidateFEPIDError(Map<String, String> data)throws Exception {
		WebDriver driver = BrowserFactoryManager.getDriver();

		try
		{
		//Calling function to log into the Blue2 application
        objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		
		
		Thread.sleep(1000);
		
		objFileNetCreateQueue.CreateQueuePage(data, driver,logger);
		
		String ErrorMessage=objFileNetCreateQueue.getErrorMessage_FEP_ID(logger);
		boolean temp=ErrorMessage.contains("The first three characters of the Subscriber ID cannot match an FEP ID R22-R99");
		
		if(temp)
		{
			logger.log(LogStatus.PASS, "Error Message : "  +ErrorMessage + "  displayed successfully");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Error Message  not displayed ");
		}
		
		}
		catch (Exception E)
		
		{
		     logger.log(LogStatus.FAIL, E );
		   
		     
		}
		
		
			
	}
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubwithAlphaOnlyPrefix(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.CorrectSubId_AlphaOnlyPrefix(logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		
		if(driver.getTitle().equalsIgnoreCase("B2 WorkList"))
		{
			logger.log(LogStatus.PASS, "No other errors displayed");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Errors displayed");
		}
		
		
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_Num_Num_Alpha_Prefix(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.CorrectSubId_Num_Num_Alpha_Prefix(logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		utils.waitForPageLoaded(driver);
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

		if(driver.getTitle().equalsIgnoreCase("B2 WorkList"))
		{
			logger.log(LogStatus.PASS, "No other errors displayed");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Errors displayed");
		}
		
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	

	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_Alpha_Num_Alpha_Prefix(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.CorrectSubId_Alpha_Num_Alpha_Prefix(logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		utils.waitForPageLoaded(driver);
		
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

		if(driver.getTitle().equalsIgnoreCase("B2 WorkList"))
		{
			logger.log(LogStatus.PASS, "No other errors displayed");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Errors displayed");
		}
		
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	

	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_Alpha_Num_Num_Prefix(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.CorrectSubId_Alpha_Num_Num_Prefix(logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		Thread.sleep(1000);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		utils.waitForPageLoaded(driver);
		
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

		if(driver.getTitle().equalsIgnoreCase("B2 WorkList"))
		{
			logger.log(LogStatus.PASS, "No other errors displayed");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Errors displayed");
		}
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	

	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_Num_Alpha_Num_Prefix(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.CorrectSubId_Num_Alpha_Num_Prefix(logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		utils.waitForPageLoaded(driver);
		
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

		if(driver.getTitle().equalsIgnoreCase("B2 WorkList"))
		{
			logger.log(LogStatus.PASS, "No other errors displayed");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Errors displayed");
		}
		
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_Num_Alpha_Alpha_Prefix(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.CorrectSubId_Num_Alpha_Alpha_Prefix(logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		utils.waitForPageLoaded(driver);
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

		if(driver.getTitle().equalsIgnoreCase("B2 WorkList"))
		{
			logger.log(LogStatus.PASS, "No other errors displayed");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Errors displayed");
		}
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_1_atFirstChar(Map<String, String> data)throws Exception {
		
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.InCorrectSubId_Prefix_One(0,logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);
		
		//String[] Errors=ErrorMessage.split("\n");
		if (ErrorMessage.contains(	"First 3 characters of Subscriber ID cannot contain 0 or 1"))
		{
		
			logger.log(LogStatus.PASS,"Error Message: First 3 characters of Subscriber ID cannot contain 0 or 1  displayed successfully");
			
			
		}
		else
		{
			logger.log(LogStatus.FAIL,"Error Message not displayed successfully");
		}
		
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_1_atSecondChar(Map<String, String> data)throws Exception {
		
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.InCorrectSubId_Prefix_One(1,logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		
        String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);
		
		//String[] Errors=ErrorMessage.split("\n");
		if (ErrorMessage.contains(	"First 3 characters of Subscriber ID cannot contain 0 or 1"))
		{
		
			logger.log(LogStatus.PASS,"Error Message: First 3 characters of Subscriber ID cannot contain 0 or 1 displayed successfully");
			
			
		}
		else
		{
			logger.log(LogStatus.FAIL,"Error Message not displayed successfully");
		}
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_1_atThirdChar(Map<String, String> data)throws Exception {
		
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.InCorrectSubId_Prefix_One(2,logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		
		 String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);
			
			//String[] Errors=ErrorMessage.split("\n");
			if (ErrorMessage.contains(	"First 3 characters of Subscriber ID cannot contain 0 or 1"))
			{
			
				logger.log(LogStatus.PASS,"Error Message: First 3 characters of Subscriber ID cannot contain 0 or 1 displayed successfully");
				
				
			}
			else
			{
				logger.log(LogStatus.FAIL,"Error Message not displayed successfully");
			}
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}

	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_0_atFirstChar(Map<String, String> data)throws Exception {
		
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey1(driver, data.get("contentKey"), logger);
		//objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.InCorrectSubId_Prefix_zero(0,logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);
		
		
			
			//String[] Errors=ErrorMessage.split("\n");
			if (ErrorMessage.contains(	"First 3 characters of Subscriber ID cannot contain 0 or 1"))
			{
			
				logger.log(LogStatus.PASS,"Error Message: First 3 characters of Subscriber ID cannot contain 0 or 1 displayed successfully");
				
				
			}
			else
			{
				logger.log(LogStatus.FAIL,"Error Message not displayed successfully");
			}
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_0_atSecondChar(Map<String, String> data)throws Exception {
		
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.InCorrectSubId_Prefix_zero(1,logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		
        String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);
		
      //String[] Errors=ErrorMessage.split("\n");
		if (ErrorMessage.contains(	"First 3 characters of Subscriber ID cannot contain 0 or 1"))
		{
		
			logger.log(LogStatus.PASS,"Error Message: First 3 characters of Subscriber ID cannot contain 0 or 1 displayed successfully");
			
			
		}
		else
		{
			logger.log(LogStatus.FAIL,"Error Message not displayed successfully");
		}
			
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_0_atThirdChar(Map<String, String> data)throws Exception {
		
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.InCorrectSubId_Prefix_zero(2,logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		
		String ErrorMessage=objFileNetCreateQueue.getErrorMessage(logger);

		//String[] Errors=ErrorMessage.split("\n");
		if (ErrorMessage.contains(	"First 3 characters of Subscriber ID cannot contain 0 or 1"))
		{
		
			logger.log(LogStatus.PASS,"Error Message: First 3 characters of Subscriber ID cannot contain 0 or 1 displayed successfully");
			
			
		}
		else
		{
			logger.log(LogStatus.FAIL,"Error Message not displayed successfully");
		}
			
				
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	
	@Test(dataProvider = "FileNetDataProvider")
	public void CorrectSubIdWith_FEP_ID_Prefix(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
		
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		
		objMisRoutedClaimIndexCorrection.ClickOnMisroutedClaimIndexLink(logger);
		
		objMisRoutedClaimIndexCorrection.ClickOnContentKey(driver, data.get("contentKey"),logger);
		
		Thread.sleep(100);
		
		objMisRoutedClaimIndexCorrection.CorrectSubId_FEP_ID_Prefix(logger);
		objMisRoutedClaimIndexCorrection.IndexCorrection(driver, data);
		
		
		objMisRoutedClaimIndexCorrection.SelectWorkFlowResponse(logger);
		objMisRoutedClaimIndexCorrection.ClickOnSaveLink(logger);
		objMisRoutedClaimIndexCorrection.ClickOnCompleteLink(logger);
		utils.waitForPageLoaded(driver);
		
		String ErrorMessage=objFileNetCreateQueue.getErrorMessage_FEP_ID_queuepage(logger);
        boolean temp=ErrorMessage.contains("The first three characters of the Subscriber ID cannot match an FEP ID R22-R99");
		
		if(temp)
		{
			logger.log(LogStatus.PASS, "Error Message : The first three characters of the Subscriber ID cannot match an FEP ID R22-R99 displayed successfully");
		}
		else
		{
			logger.log(LogStatus.FAIL, "Error Message  not displayed ");
		}
		
						
		
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}
	
	
	@Test(dataProvider = "FileNetDataProvider")
	public void ValidateSFNResponseCode(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
				
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		objMIsRouteResponsePage.clickOnUserInbox(logger);
		objMIsRouteResponsePage.ClickOnContentKey_responsePage(driver, data.get("contentKey"), logger);
		String ResponseType=objMIsRouteResponsePage.getMisrouteResponseType(logger);
		if(ResponseType.equalsIgnoreCase("SFN"))
		{
			logger.log(LogStatus.INFO, "Misroute Response Type is : SFN");
			
		}
		else
		{
			logger.log(LogStatus.FAIL, "Misroute Response Type is Not SFN");
		}
						
		
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}

	

	@Test(dataProvider = "FileNetDataProvider")
	public void Validate_SFN_ActionCode(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
				
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		objMIsRouteResponsePage.clickOnUserInbox(logger);
		objMIsRouteResponsePage.ClickOnContentKey_responsePage(driver, data.get("contentKey"), logger);
		boolean temp=objMIsRouteResponsePage.validate_SFN_Action_CodeField(logger);
		if(temp)
		{
			logger.log(LogStatus.INFO, "New field  SFN Action code is available");
			
			String SFN_Action_Code=objMIsRouteResponsePage.get_SFN_Action_Code_text();
			
			if(SFN_Action_Code.equalsIgnoreCase("S09"))
			{
				logger.log(LogStatus.PASS, " SFN Action code is : "+SFN_Action_Code);
			}
			else
			{
				logger.log(LogStatus.FAIL, " SFN Action code is not available");
			}
			
			
		}
		else
		{
			logger.log(LogStatus.FAIL, "New field  SFN Action code is not available");
		}
		
		
						
		
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}


	@Test(dataProvider = "FileNetDataProvider")
	public void Validate_ReqInfo_field(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
				
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		objMIsRouteResponsePage.clickOnUserInbox(logger);
		objMIsRouteResponsePage.ClickOnContentKey_responsePage(driver, data.get("contentKey"), logger);
		boolean temp=objMIsRouteResponsePage.validate_ReqInformationField(logger);
		if(temp)
		{
			logger.log(LogStatus.INFO, "New field Required Claim Information is available");
			
		}
		else
		{
			logger.log(LogStatus.FAIL, "New field  Required Claim Information is not available");
		}
		
		
						
		
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}

	@Test(dataProvider = "FileNetDataProvider")
	public void Validate_ReqInfo_fieldcontent(Map<String, String> data)throws Exception {
		WebDriver driver=BrowserFactoryManager.getDriver();
		
		boolean flag;
				
		try{
		
		//Calling function to log into the Blue2 application
		objFileNetLoginPage.loginToFileNetApplication(data.get("FileNetLogin"),data.get("FileNetPassword"), data.get("Role"), logger);
		Thread.sleep(1000);
		
		objMIsRouteResponsePage.clickOnUserInbox(logger);
		objMIsRouteResponsePage.ClickOnContentKey_responsePage(driver, data.get("contentKey"), logger);
		boolean temp=objMIsRouteResponsePage.validate_ReqInformationField(logger);
		if(temp)
		{
			logger.log(LogStatus.INFO, "New field Required Claim Information field is available");
			

			String ReqInfo=objMIsRouteResponsePage.validate_ReqInformationFieldText();
			
			if(!(ReqInfo.isEmpty()))
			{
				logger.log(LogStatus.PASS, " Required Claim Information is : "+ReqInfo);
			}
			else
			{
				logger.log(LogStatus.FAIL, " Required Claim Information is not available");
			}
			
		}
		else
		{
			logger.log(LogStatus.FAIL, "New field  Required Claim Information is not available");
		}
		
		
						
		
		flag=true;
		}
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),6,"Passed");
				
	}

	
	

	@Test(dataProvider = "FileNetDataProvider")
	public void testBlueSquareDay2Validations(Map<String, String> data)throws Exception {
		
       boolean flag;
		
		try{
			
			Thread.sleep(10000);
			
			//Creating th Driver object to Launch the Web browser
			WebDriver driver = BrowserFactoryManager.getDriver();
			
			//Calling function to log into the Blue2 application
			objBlueSquareLoginPage.loginToBlue2Application(data.get("Blue2Login"),data.get("Blue2Password"),logger);
			
			Thread.sleep(10000);
			
			//Reading the SubscriberNumber from the Data sheet
			
			  System.out.println("Data from Excel#################");
			
			  String SubscriberNumber = data.get("SubscriberNumber");
			  System.out.println(SubscriberNumber);
			
			  String Format=data.get("Format");
			  System.out.println("Format:" + Format);
			 
			  String OcStatus=data.get("OCStatus");
			  System.out.println("OcStatus:" + OcStatus);
			  
			  String MsgStatus=data.get("MsgStatus");
			  System.out.println("MsgStatus:" + MsgStatus);
			  
			  String SubscriberID = data.get("SubscriberID");
			  System.out.println("SubscriberID" + SubscriberID);
			  
			  System.out.println("End of Data from Excel#################");
			
			//Entering the SubscriberNumber in the SCCF history page @ Home tab and hitting the search button.
			String applicationValue=objHomeSccfHistoryPage.validateMessagecode(driver,SubscriberNumber,Format,MsgStatus,OcStatus,SubscriberID,logger);
			
			//Reading the corresponding message code from the Data sheet
			String messageCode=data.get("MessageCode");
			
			//Validating the Message code against the Blue2 application
			FileNetTest.validation("Messagestatuscode", applicationValue, messageCode, logger);
			
			//Calling function to logout from the Blue2 application
			objBlueSquareLoginPage.logoutFromBlue2Application(logger);
		
			
			flag=true;
			
              }
		
		catch(Exception E){
			flag=false;
			logger.log(LogStatus.FAIL, E);
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		}
		
		String path=resultsDestinationFolderPath + "\\FileNetTestData.xlsx";
		if(!flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),4,"Failed");
		else if(flag)
			ExcelUtilities.setResultsValue(path, environment,data.get("TestCaseID"),4,"Passed");
	
	}
	
	

	@DataProvider(name = "FileNetDataProvider")
	private static Object[][] getData(Method method) {
		if(method.getName().equalsIgnoreCase("testDRXEnrollmentSubmission")){
			environment="BrokerMAPDPDP";
		}else if(method.getName().equalsIgnoreCase("testDRXMedicareSupplementEnrollmentSubmission")){
			environment="BrokerMedicareSupplement";
		}
		
		  Object[][] columnArray = ExcelUtilities.getColumnArray(inputDataSheetPath,environment);
			Object[][] testDataArray = ExcelUtilities.getTableArray(inputDataSheetPath,environment);
		  List<Object> list=new ArrayList<Object>();//list to store the executable rows in the test data sheet
		  int noOfTestCases=0;String runMode="Yes";//declaration and initialization of runMode flag
		  for(int row=0;row<=testDataArray.length-1;row++){//loop through the data in test data sheet
			  //below checking the method name with the keyword name in test data sheet
			  if(method.getName().equalsIgnoreCase(testDataArray[row][3].toString())&& runMode.equalsIgnoreCase(testDataArray[row][4].toString())){
				noOfTestCases++;//icrementing the number when ever the testcase in datasheet having Runmode as Yes and keyword and method name
				Map<String, String> rowDataMap = new HashMap<String, String>();//map to handle row data in datasheet
				for (int col=0;col<=columnArray[0].length-1;col++){//iterate through all columns and rows
					rowDataMap.put(columnArray[0][col].toString(), testDataArray[row][col].toString());
				}
				list.add(rowDataMap);//adding into list
			}
		}
		  
		  Object[][] data = new Object[noOfTestCases][1];//creating object array and populating data
		  for(int row=0;row<list.size();row++){
			  data[row][0]=list.get(row);
		  }
		
		return data;
		

	}

	/**
	 * setUp is a @BeforeMethod executed before execution of a @Test
	 * 
	 * @param browser_
	 * @param driver_
	 * @param env_
	 * @param url_
	 * @throws Exception
	 */
	@BeforeClass	
	@Parameters({ "Browser_", "Driver_", "Env_", "URL_","InputDataSheetPath_" })
	public void setUp(@Optional("browser") String browser_,
			@Optional("webDriver") String driver_,
			@Optional("Env_") String env_, @Optional("url") String url_, @Optional("InputDataSheetPath") String InputDataSheetPath_)
			throws Exception {
		// boolean error = false;
		browser = browser_;
		webDriver = driver_;
		environment = env_; // System.getenv("ENVNAME");
		//System.out.print(environment);
		url = url_;
		inputDataSheetPath=InputDataSheetPath_;
		// ExcelUtilities.setUpExcel("src/test/resources/UMTestCases.xlsx");
		try{
			
		Path path = Paths.get(resultsDestinationFolderPath);
		 if(!Files.exists(path)){//checking the existence of given path
			 	try {Files.createDirectories(path);//Creating directory
			 		} catch (IOException e) {	e.printStackTrace();}
		 	}
		 	Path testDataSheetSourcePath=Paths.get(inputDataSheetPath);//storing report source path in testDataSourcePath
			
		 	File srcdatasheetPath = new File(inputDataSheetPath);
		 	
		 	
		 	String dataSheetName = srcdatasheetPath.getName();
		 	
		 	Path testDataSheetDestinationPath=Paths.get(resultsDestinationFolderPath + "\\"+dataSheetName);//storing report source path in testDataDestinationPath
			TestFileUtil.copyFile(testDataSheetSourcePath,testDataSheetDestinationPath);//copy function
		
			
		
		} catch (IOException e) {
			e.printStackTrace();//Printing exception object 
		}
		
	}

	/**
	 * @Override run is a hook before @Test method
	 */
	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		initBrowser(testResult.getTestName(), testResult.getMethod().getMethodName());
		Map<String,String> map=(Map<String,String>) callBack.getParameters()[0];
		String testCaseName="TestCaseName: "+map.get("TestCaseName");
		reportInit(testResult.getTestContext().getName() ,testCaseName, testResult.getMethod().getMethodName());
		
		//reportInit(testResult.getTestContext().getName(), browser,testCaseName, testResult.getMethod().getMethodName());
		softAssert = new SoftAssert();
		logger.log(LogStatus.INFO, "Starting test " + testCaseName);
		callBack.runTestMethod(testResult);
		softAssert.assertAll();
		System.out.println("soft assert complete");
		

		//try {
			//ExcelUtilities.closefile();
		//} catch (Exception e) {
			
		//	e.printStackTrace();
		//}
	}

	
	
	//after exection of all the tests in the suit,results and input data will be stored in results folder
  /** Copying test data excel file and screenshots in Result folder
 * @throws Exception - capturing exceptions
 */
@AfterClass
  public void afterClass() throws Exception {
		//assigning the resultsDestinationPath from testNG.xml
		try {
			ReportFactory.closeReport();
			Path path = Paths.get(resultsDestinationFolderPath);
			 if(!Files.exists(path)){//checking the existence of given path
			 	try {Files.createDirectories(path);//Creating directory
			 		} catch (IOException e) {	e.printStackTrace();}
			 }
		
		//Copying Extent reports	
		Path extentReportSourcePath=Paths.get("test-output\\BSC-reports\\Report.html");//storing report source path in extentReportSourcePath
		Path extentReportDestinationPath=Paths.get(resultsDestinationFolderPath+"\\Report.html");//storing report source path in extentReportDestinationPath
		TestFileUtil.copyFile(extentReportSourcePath,extentReportDestinationPath);//copy function
		
		//Copying Screenshots folder
		String strScreenshotFolderPath = "test-output\\BSC-reports\\Screenshots";
		Path pathScreenshot = Paths.get(strScreenshotFolderPath);
		 if(!Files.exists(pathScreenshot)){//checking the existence of given path
		 	try {Files.createDirectories(pathScreenshot);//Creating directory
		 		} catch (IOException e) {	e.printStackTrace();}
		 }
		 
		File srcDir = new File("test-output\\BSC-reports\\Screenshots" + "\\");//Input Data folder path
				
		String strDestinationScreenFolderPath = resultsDestinationFolderPath + "\\Screenshots";
		Path pathDestinationScreenshot = Paths.get(strDestinationScreenFolderPath);
		if(!Files.exists(pathDestinationScreenshot)){//checking the existence of given path
		 	try {Files.createDirectories(pathDestinationScreenshot);//Creating directory
		 		} catch (IOException e) {	e.printStackTrace();}
	 	}
		 
		File destDir = new File(strDestinationScreenFolderPath);
		FileUtils.copyDirectory(srcDir, destDir);//copying directory
		//ExcelUtilities.closefile();
		
	} catch (IOException e) {
		e.printStackTrace();//Printing exception object 
	}

  }
  
  /**
 * Copying html file to result folder
 */
@AfterSuite
  public void afterSuite1(){
	  
	  try{
	//Copying Extent reports
		  Path extentReportSourcePath=Paths.get("test-output\\BSC-reports\\Report.html");//storing report source path in extentReportSourcePath
			Path extentReportDestinationPath=Paths.get(resultsDestinationFolderPath+"\\Report.html");//storing report source path in extentReportDestinationPath
			TestFileUtil.copyFile(extentReportSourcePath,extentReportDestinationPath);//copy function
	  } catch (IOException e) {
			e.printStackTrace();//Printing exception object 
		}
  }





public static void validation(String elementName,String ActualValue, String ExpectedlValue,ExtentTest logger) {
    //validation of the Actual and Expected Values
	softAssert.assertEquals(ActualValue, ExpectedlValue,"ActualValue:"+ActualValue+"| ExpectedlValue:"+ExpectedlValue);
    String status="Fail";     
    if(ActualValue.equalsIgnoreCase(ExpectedlValue)){
          status="Pass";
          logger.log(LogStatus.PASS,"" + elementName + "|---------- ActualValue: " +ActualValue+ "----------ExpectedlValue: " + ExpectedlValue+"|----------Status:"+status);
    }else{
          logger.log(LogStatus.FAIL,"" + elementName + "|---------- ActualValue: " +ActualValue+ "----------ExpectedlValue: " + ExpectedlValue+"|----------Status:"+status);
          }
    //logger for showing the results in extents report html file
    
    System.out.println(" Element Name: " + elementName + "|---------- ActualValue: " +ActualValue+ "----------ExpectedlValue: " + ExpectedlValue);
          
}
	

}
