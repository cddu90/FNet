package com.bsc.qa.web.pages;



import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.time.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import WebUtils.WebUtils;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.framework.base.BasePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class FileNetCreateQueuePage<explicitWaitByVisibilityofElement> {
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//span[contains(text(),'Create')]") })
	@CacheLookup
	WebElement Createlink;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//span[contains(text(),'Search')]") })
	@CacheLookup
	WebElement Searchlink;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='AttachmentSearch:Search']") })
	@CacheLookup
	WebElement SecondSearchlink;
	
	@FindAll({
		@FindBy(how = How.XPATH, using = "//td[text()='MRC']/preceding-sibling::td//input[@type='radio']") })
public List<WebElement> requriedRadioforMRC;

	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='AttachmentSearch:launchBtn']") })
	@CacheLookup
	WebElement FinalLaunchButton;
	
	//input[@name='AttachmentSearch:launchBtn']
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='LaunchStep:MessageType']") })
	@CacheLookup
	WebElement MessageType;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:From_Date']") })
	@CacheLookup
	WebElement DateOfServiceFrom;
	
	@FindAll({@FindBy(how=How.XPATH,using="//input[@id='LaunchStep:To_Date']") })
	@CacheLookup
	WebElement DateOfServiceTo;
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='LaunchStep:originatingPlan']") })
	@CacheLookup
	WebElement OriginStnCode;
	
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='LaunchStep:DestinationPlan']") })
	@CacheLookup
	WebElement DestnStationCode;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:LastName']") })
	@CacheLookup
	WebElement SubLastName;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:FirstName']") })
	@CacheLookup
	WebElement SubFirstName;
	
	@FindAll({@FindBy(how=How.XPATH,using="//input[@id='LaunchStep:Subscriber']") })
	@CacheLookup
	WebElement SubscriberID;
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:PatientLastName']") })
	@CacheLookup
	WebElement PatientLastName;
	
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:FirstName']") })
	@CacheLookup
	WebElement PatientFirstName;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:PatientGender:0']") })
	@CacheLookup
	WebElement RadioBtn_Male;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:PatientGender:1']") })
	@CacheLookup
	WebElement RadioBtn_Female;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:PatientGender:2']") })
	@CacheLookup
	WebElement RadioBtn_Unknown;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:PatientDateOfBirth']") })
	@CacheLookup
	WebElement PatientDOB;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='LaunchStep:PatientRelationship']") })
	@CacheLookup
	WebElement SelectRelationship;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:ProviderName']") })
	@CacheLookup
	WebElement ProviderName;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:FederalTaxId']") })
	@CacheLookup
	WebElement FederalTaxId;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:ZipCode']") })
	@CacheLookup
	WebElement ZipCode;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:BCBSProviderNumber']") })
	@CacheLookup
	WebElement BCBSProviderNum;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='LaunchStep:ClaimType']") })
	@CacheLookup
	WebElement ClaimType;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:TotalCharges']") })
	@CacheLookup
	WebElement TotalCharges;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:ReciptDate']") })
	@CacheLookup
	WebElement ReceiptDate;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:ContactOnReceiptDetails_Name']") })
	@CacheLookup
	WebElement ContactName;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:ContactOnReceiptDetails_PNum']") })
	@CacheLookup
	WebElement  ContactPhone;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@name='LaunchStep:launchButton1']") })
	@CacheLookup
	WebElement  LaunchButton;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:contentkey_it']") })
	@CacheLookup
	WebElement  contentKeyTextBox;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:Search']") })
	@CacheLookup
	WebElement  SubmitButton;
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:button_addAttachment']") })
	@CacheLookup
	WebElement AddDocumentButton ;
	
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//table[@id='LaunchStep:searchResultsTable']/tbody/tr/td[1]/input") })
	@CacheLookup
	WebElement AttachmentCheckBox ;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='LaunchStep:Attach']") })
	@CacheLookup
	WebElement AttachButton ;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//div[@id='ErrorMessage']") })
	@CacheLookup
	WebElement ErrorMessage ;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//table[@id='LaunchStep:message1']/tbody/tr/td/span") })
	@CacheLookup
	WebElement ErrorMessage_FEP_ID ;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//table[@id='workListForm:message1']/tbody/tr/td/span") })
	@CacheLookup
	WebElement ErrorMessage_FEP_ID_queuePage ;
	
		

//	public  void ClickOnCreateLink()
//	{
//		Createlink.click();
//
//	}
	
	public  void ClickOnSearchLink()
	{
		Searchlink.click();

	}
	
	public  void ClickOnSecondSearchLink()
	{
		SecondSearchlink.click();

	}

	
	public  void ClickOnRequriedRadioforMRC()
	{
		requriedRadioforMRC.get(0).click();

	}
	
	public  void ClickOnFinalLaunchButton()
	{
		FinalLaunchButton.click();

	}
	
	public void SelectMessageType(String MsgType)
	{
		Select MessageTypes= new Select(MessageType);
		MessageTypes.selectByValue(MsgType);

	}

	public void enterPlanDetails(String plancode,String DestCode)

	{
		
		Select OriginStnCodes=new Select(OriginStnCode);
		OriginStnCodes.selectByValue(plancode);

		Select OriginDstnCodes=new Select(DestnStationCode);
		OriginDstnCodes.selectByValue(DestCode);


	}

	public void enterSubscriberInfo(String LastName,String FirstName,String SubId)
	{
		SubLastName.sendKeys(LastName);
		SubFirstName.sendKeys(FirstName);
		SubscriberID.sendKeys(SubId);

	}


	public void enterPatientInfo(String LastName,String FirstName,String Gender,String Relationship)
	{
		PatientLastName.sendKeys(LastName);
		PatientFirstName.sendKeys(FirstName);
		if (Gender=="Male")
		{
			RadioBtn_Male.click();
		}
		else if(Gender=="Female")
		{
			RadioBtn_Female.click();
		}
		else 
		{
			RadioBtn_Unknown.click();
		}

	

		Select Relationships=new Select(SelectRelationship);
		Relationships.selectByVisibleText(Relationship);


	}

	public void enterProviderDetails(String PrvName,String FedTaxId,String BCBSPrv)
	{
		ProviderName.sendKeys(PrvName);
		FederalTaxId.sendKeys(FedTaxId);
		BCBSProviderNum.sendKeys(BCBSPrv);

	}

	public void enterClaimMisrouteDetails(String Tot_Charges,String Claim_Type)
	{
		TotalCharges.clear();//skatta05
		TotalCharges.sendKeys(Tot_Charges);
		Select CTypes=new Select(ClaimType);
		CTypes.selectByVisibleText(Claim_Type);


	}

	public void enterContactDetails(String Cnct_Name,String Phone_num)
	{
		ContactName.sendKeys(Cnct_Name);
		ContactPhone.sendKeys(Phone_num);

	}

	public void ClickonLaunch()
	{
		LaunchButton.click();
	}
	
	public void datePicker(WebDriver driver,String date,String element)
	{
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		if(element=="PlanDateOfServiceFrom")
		{
		js.executeScript("document.getElementById('LaunchStep:From_Date').value='" + date + "';");
		}
		else if(element=="PlanDateOfServiceTo")
		{
			js.executeScript("document.getElementById('LaunchStep:To_Date').value='" + date + "';");
		}
		else if(element=="Patient_DateOfBirth")
		{
			js.executeScript("document.getElementById('LaunchStep:PatientDateOfBirth').value='" + date + "';");
		}
		else if(element=="Receipt_date")
		{
			js.executeScript("document.getElementById('LaunchStep:ReciptDate').value='" + date + "';");
		}
	}
	


	public void AddAttachent(WebDriver driver,String contentKey)
	{
		AddDocumentButton.click();
//skatta05
		driver.switchTo().alert().accept();
		contentKeyTextBox.sendKeys(contentKey);
        //Select Record_types=new Select(MedicalRecordTypeDropDown);
		//Record_types.selectByVisibleText("ALL");
		SubmitButton.click();
		
		
		
		WebUtils utils=new WebUtils();
		
		WebElement checkbox=utils.explicitWaitByElementToBeClickable(driver, AttachmentCheckBox);
		checkbox.click();
		AttachButton.click();
		
	}
	
	public String getErrorMessage(ExtentTest logger)
	{
		String Error_Messsage=ErrorMessage.getText();
		
		logger.log(LogStatus.INFO, "ErrorMessage displayed is :"+Error_Messsage);
		
		return Error_Messsage;
	}
	
	public String getErrorMessage_FEP_ID(ExtentTest logger)
	{
		String Error_Messsage=ErrorMessage_FEP_ID.getText().trim();
		logger.log(LogStatus.INFO, "ErrorMessage displayed is :"+Error_Messsage);
		
		return Error_Messsage;
	}
	
	
	public String getErrorMessage_FEP_ID_queuepage(ExtentTest logger)
	{
		String Error_Messsage=ErrorMessage_FEP_ID_queuePage.getText().trim();
		logger.log(LogStatus.INFO, "ErrorMessage displayed is :"+Error_Messsage);
		
		return Error_Messsage;
	}
	
	
	
	public boolean IsErrorVisible(WebDriver driver)
	{
	   
		boolean present;
		try {
		   driver.findElement(By.xpath("//div[@id='ErrorMessage']"));
		   present = true;
		} catch (NoSuchElementException e) {
		   present = false;
		}
		return present;
	}
	
	
	
	public void CreateQueuePage( Map<String, String> data,WebDriver driver,ExtentTest logger)
	
	
	{
			
		
		ClickOnSearchLink();
		logger.log(LogStatus.INFO, "Clicked on SearchLink");
		
		ClickOnSecondSearchLink();
		logger.log(LogStatus.INFO, "Clicked on SecondSearchLink");
		
		
		
		ClickOnRequriedRadioforMRC();
		logger.log(LogStatus.INFO, "selected MRC record");
		
		ClickOnFinalLaunchButton();
		logger.log(LogStatus.INFO, "Clicked on Final launch button ");
		
		
		SelectMessageType(data.get("Message_Type"));
		logger.log(LogStatus.INFO, "Message Type selected:"+data.get("Message_Type"));
					
		
		datePicker(driver,data.get("DateOfServiceFrom"),"PlanDateOfServiceFrom");
		datePicker(driver,data.get("DateOfServiceTo"), "PlanDateOfServiceTo");
		enterPlanDetails(data.get("OriginStatusCode"), data.get("DestStatusCode"));
		logger.log(LogStatus.INFO, "Entered PlanDateOfServiceFrom :" +data.get("DateOfServiceFrom")+ "successfully");
		logger.log(LogStatus.INFO, "PlanDateOfServiceTo :" +data.get("DateOfServiceTo")+ "successfully");
		logger.log(LogStatus.INFO, "Entered OriginStatusCode :" +data.get("OriginStatusCode")+ "successfully");
		logger.log(LogStatus.INFO, "Entered DestinationStatusCode :" +data.get("DestStatusCode")+ "successfully");
		
		
		enterSubscriberInfo(data.get("SubLastName"), data.get("SubFirstName"), data.get("SubscriberID"));
		logger.log(LogStatus.INFO, "Entered Subscriber details successfully.Subscriber ID:"+data.get("SubscriberID"));
		
		
		enterPatientInfo(data.get("PtnLastName"), data.get("PtnFirstName"), data.get("Gender"), data.get("Relationship"));
		datePicker(driver,data.get("PtnDOB"),"Patient_DateOfBirth" );
		logger.log(LogStatus.INFO, "Entered Patient LastName:" +data.get("PtnLastName")+ "FirstName:  "+data.get("PtnFirstName")+"  successfully.");
		logger.log(LogStatus.INFO, "Selected Patient Relationship:" +data.get("Relationship")+" successfully.");
		logger.log(LogStatus.INFO, "Selected Patient DateOfBirth :" +data.get("PtnDOB")+" successfully.");
		
		
		enterProviderDetails(data.get("PrvName"),data.get("FedTaxId") , data.get("BCBSPrvName"));
		logger.log(LogStatus.INFO, "Entered Provider Name:"+data.get("PrvName"));
		logger.log(LogStatus.INFO, "Entered FederalTaxID Name:"+data.get("FedTaxId"));
		logger.log(LogStatus.INFO, "Entered BCBSProvider Name:"+data.get("BCBSPrvName"));
		

		enterContactDetails(data.get("CnctName"), data.get("CnctPhone"));
		logger.log(LogStatus.INFO, "Entered Contact person Name:   "+data.get("CnctName")+" and phone: "+data.get("CnctPhone"));
		
	
		datePicker(driver,data.get("RecieptDate"),"Receipt_date" );
		AddAttachent(driver, data.get("contentKey"));
		logger.log(LogStatus.INFO, "Attachments attached successfully.");
		
		
		enterClaimMisrouteDetails(data.get("TotCharges"), data.get("ClaimType"));
		logger.log(LogStatus.INFO, "Claim type:  "+data.get("ClaimType")+" selected successfully");
		
		ClickonLaunch();
		logger.log(LogStatus.INFO, "Clicked on Launch");
	
		

	}
	


}


