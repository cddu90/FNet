package com.bsc.qa.web.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import WebUtils.WebUtils;
import com.bsc.qa.framework.base.BasePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


/**
 * <description>
 * @param data - sending test data values to perform the operations
 * @param driver - To perform operations on browser
 * @param logger - to log the operation
 * @throws InterruptedException
 */
public class HomeSccfHistoryPage extends BasePage {
	
	//home claim elements
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='sccfHistoryTab']") })
	@CacheLookup
	WebElement sccfHistoryLink;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='listingSearchButton']") })
	@CacheLookup
	WebElement SearchButton;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='homeTabAnc']") })
	@CacheLookup
	WebElement Hometab;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='subscriberIdPrefix']") })
	@CacheLookup
	WebElement SubscriberIdPrefix;
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='subscriberIdSuffix']") })
	@CacheLookup
	WebElement SubscriberIdSuffix;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='listingSearch.resultsTable.tr.1']/child::td[text()='MISROUTE']") })
	@CacheLookup
	WebElement FormatValue;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='listingSearch.resultsTable.tr.1']/child::td[text()='Open']") })
	@CacheLookup
	WebElement OCStatusValue;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='listingSearch.resultsTable.tr.1']/child::td[text()='PRSD']") })
	@CacheLookup
	WebElement MsgStatusValue;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='listingSearch.resultsTable.tr.1']/td[8]") })
	@CacheLookup
	WebElement Date;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = ".//*[@id='listingSearch.resultsTable.tr.1']/td[10]") })
	@CacheLookup
	WebElement SubscriberIDNumber;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='claimMisrouteDetail.messageStatusCode']") })
	@CacheLookup
	WebElement MessageStatusCode;
	
	
	WebUtils webUtils=	new WebUtils();
	
	/**
	 * <description>
	 * This method is used to spit the SubscriberID
	 * @param SubscriberID - sending test data values to perform the operations
	 * returns arrOfStr 
	 */

	public String[] splitSubscriberId(String SubscriberId) 
	{ 
	   
	   String str = SubscriberId; 
	   String[] arrOfStr = str.split("-",2);
	  
	return arrOfStr;
	} 
	
/**
 * <description>
 * This method is used to search the Subscriber for the specific claims. 
 * @param driver - To perform operations on browser
 * @param SubscriberID - sending test data values to perform the operations
 * @param logger - to log the operation
 * @throws InterruptedException
 */
public void searchForSubscriber(WebDriver driver,String SubscriberId, ExtentTest logger)throws InterruptedException{

	
	String SubscriberIdPrefixValue,SubscriberIdSuffixValue;
	logger.log(LogStatus.INFO, "Logged into the BlueSquare application");
	
	//Click on SCCF History Link
	sccfHistoryLink.click();
	logger.log(LogStatus.INFO, "Clicked on SCCF History link.");

	Thread.sleep(10000);
	
	//Enter the SubscriberId in the SubscriberId Textbox
	String[] subscriberId=splitSubscriberId(SubscriberId);
	
	SubscriberIdPrefixValue=subscriberId[0]; 
	SubscriberIdSuffixValue =subscriberId[1]; 
	
	//Enter the SubscriberIdPrefixValue in the SubscriberId Textbox
	SubscriberIdPrefix.sendKeys(SubscriberIdPrefixValue);
	
	//Enter the SubscriberIdSuffixValue in the SubscriberId Textbox
	SubscriberIdSuffix.sendKeys(SubscriberIdSuffixValue);
	
	logger.log(LogStatus.INFO, "SubscriberIdPrefixValue entered: " + SubscriberIdPrefixValue);
	logger.log(LogStatus.INFO, "SubscriberIdSuffixValue entered: " + SubscriberIdSuffixValue);
	
	//Click on the Search button 
	SearchButton.click();
	logger.log(LogStatus.INFO, "Clicked on Search button.");
	
	Thread.sleep(10000);
	
}




/**
 * <description>
 * This method is used to get the Messagestatus code for the ITS Home claims-Subscriber details 
 * @param driver - To perform operations on browser
 * @param sccfNumber - sending test data values to perform the operations
 * @param logger - to log the operation
 * @throws InterruptedException
 * returns Messagestatus code
 */
	
 public String validateMessagecode(WebDriver driver, String SubscriberId,String Format,String Msgstatus,String OCStatus, String SubscriberIDNum,ExtentTest logger) throws InterruptedException{
		
	  String Messagestatuscode="";
	  
	  
		  //Click on the HomeTab
		  Hometab.click();
		  logger.log(LogStatus.INFO, "Clicked on Home tab.");
	

		  //Calling the method to search the Subscriber
		  searchForSubscriber(driver,SubscriberId,logger);
		  
         
		  //Formatvalue.
		  String format=FormatValue.getText();
		  System.out.println("format:" +format);
		  
		  String OcStatus=OCStatusValue.getText();
		  System.out.println("OcStatus:" + OcStatus);
		  
		  String MsgStatus=MsgStatusValue.getText();
		  System.out.println("MsgStatus:" + MsgStatus);
		  
		  String SubscriberIdNumber=SubscriberIDNumber.getText();
		  System.out.println("SubscriberIdNumber:" + SubscriberIdNumber);
		  
		  
		  if(Format.equalsIgnoreCase(format) && Msgstatus.equalsIgnoreCase(MsgStatus) && OCStatus.equalsIgnoreCase(OcStatus) && SubscriberIDNum.equalsIgnoreCase(SubscriberIdNumber))
			{
			  logger.log(LogStatus.INFO, "Format should be:."+format);  
			  logger.log(LogStatus.INFO, "Message Status should be:."+MsgStatus);
			  logger.log(LogStatus.INFO, "OcStatus should be:."+OcStatus); 
			  logger.log(LogStatus.INFO, "Satisfied all the conditions."); 
			//click on subscriber results
			 FormatValue.click();
		     logger.log(LogStatus.INFO, "Clicked on subscriber results.");
		     Thread.sleep(10000);
		
		   //Get the SFMessage code from the BlueSquare application
		   Messagestatuscode=MessageStatusCode.getText().toString().trim();
		   logger.log(LogStatus.INFO, "Got the MessageCode as."+Messagestatuscode);
		  
		}
		return Messagestatuscode;
	}

}

