package com.bsc.qa.web.pages;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import WebUtils.WebUtils;

public class MisRoutedClaimIndexCorrection {

	@FindAll({ @FindBy(how = How.XPATH, using = "//a[@id='worklistRootDashboard:cmdid_MisroutedClaimIndexCorrectionWorklistUi']") })
	@CacheLookup
	WebElement MisroutedClaimLink;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:wlTable:deluxe1__pagerNext']") }) 
	WebElement NavigateNextButton;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:Subscriber']") })
	@CacheLookup
	WebElement Subscriber_ID;


	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:commandLink_Save1']") })
	@CacheLookup
	WebElement SaveButton;


	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='workListForm:WorkflowResponce']") })
	@CacheLookup
	WebElement WorkFlowResponsedrpdwn;


	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:commandLink_Complete']") })
	@CacheLookup
	WebElement CompleteButton;
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:From_Date']") })
	@CacheLookup
	WebElement IndexPage_DateOfServiceFrom;
	
	@FindAll({@FindBy(how=How.XPATH,using="//input[@id='workListForm:To_Date']") })
	@CacheLookup
	WebElement IndexPage_DateOfServiceTo;
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='LaunchStep:originatingPlan']") })
	@CacheLookup
	WebElement IndexPage_OriginStnCode;
	
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='workListForm:DestinationPlan']") })
	@CacheLookup
	WebElement IndexPage_DestnStationCode;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:LastName']") })
	@CacheLookup
	WebElement IndexPage_SubLastName;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:FirstName']") })
	@CacheLookup
	WebElement IndexPage_SubFirstName;
	
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:PatientLastName']") })
	@CacheLookup
	WebElement IndexPage_PatientLastName;
	
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:PatientFirstName']") })
	@CacheLookup
	WebElement IndexPage_PatientFirstName;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:PatientGender:0']") })
	@CacheLookup
	WebElement IndexPage_RadioBtn_Male;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:PatientGender:1']") })
	@CacheLookup
	WebElement IndexPage_RadioBtn_Female;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:PatientGender:2']") })
	@CacheLookup
	WebElement IndexPage_RadioBtn_Unknown;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:PatientDateOfBirth']") })
	@CacheLookup
	WebElement IndexPage_PatientDOB;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='workListForm:PatientRelationship']") })
	@CacheLookup
	WebElement IndexPage_SelectRelationship;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:ProviderName']") })
	@CacheLookup
	WebElement IndexPage_ProviderName;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:FederalTaxId']") })
	@CacheLookup
	WebElement IndexPage_FederalTaxId;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:TotalCharges']") })
	@CacheLookup
	WebElement IndexPage_ZipCode;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:BCBSProviderNumber']") })
	@CacheLookup
	WebElement IndexPage_BCBSProviderNum;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//select[@id='workListForm:ClaimType']") })
	@CacheLookup
	WebElement IndexPage_ClaimType;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:TotalCharges']") })
	@CacheLookup
	WebElement IndexPage_TotalCharges;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:ReciptDate']") })
	@CacheLookup
	WebElement IndexPage_ReceiptDate;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:ContactOnReceiptDetails_Name']") })
	@CacheLookup
	WebElement IndexPage_ContactName;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:ContactOnReceiptDetails_PNum']") })
	@CacheLookup
	WebElement  IndexPage_ContactPhone;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:wlTable:deluxe1__pagerLast']") })
	@CacheLookup
	WebElement  PagerLastButton;
	
	
	

	WebUtils utils=new WebUtils();

	public boolean flag;
	



	public void ClickOnMisroutedClaimIndexLink(ExtentTest logger) throws Exception
	{
		MisroutedClaimLink.click();
		logger.log(LogStatus.INFO, "Clicked on MisroutedClaimIndexLink");
	}

	public void ClickOnSaveLink(ExtentTest logger) throws Exception
	{
		SaveButton.click();
		logger.log(LogStatus.INFO, "Clicked on Save button");
	}

	public void ClickOnCompleteLink(ExtentTest logger) throws Exception
	{
		CompleteButton.click();
		logger.log(LogStatus.INFO, "Clicked on Complete button");
	}


	public void SelectWorkFlowResponse(ExtentTest logger) throws Exception
	{
		Select ResponseTypes= new Select(WorkFlowResponsedrpdwn);
		ResponseTypes.selectByValue("Revalidate Message");
		logger.log(LogStatus.INFO, "Selected WorkFlowResonse as Revalidate Message");

	}

	public  void ClickOnContentKey1(WebDriver driver,String ContentKey,ExtentTest logger) throws Exception
	{
		outerLoop:
			for(int k=1;k<=109;k++)
			{
				List<WebElement> listofRecords=driver.findElements(By.xpath("//table[@id='workListForm:wlTable']/tbody/tr"));

				for (int i=1;i<=listofRecords.size();i++)
				{
					WebElement ContentKeyLink=driver.findElement(By.xpath("//table[@id='workListForm:wlTable']/tbody/tr["+i+"]/td[2]"));
					String ContentKeytext=ContentKeyLink.getText();
					System.out.println(ContentKeytext);
					if (ContentKeytext.equalsIgnoreCase(ContentKey.trim()))
					{
						driver.findElement(By.xpath("//table[@id='workListForm:wlTable']/tbody/tr["+i+"]/td[2]/a")).click();
						logger.log(LogStatus.INFO, "Clicked on Content key: "+ContentKeytext);
						break outerLoop;

					}

				}



				NavigateNextButton.click();
				utils.explicitWaitForVisibilityofElement(driver, "//input[@id='workListForm:wlTable:deluxe1__pagerNext']");



			}

	}
	
	public  void ClickOnContentKey(WebDriver driver,String ContentKey,ExtentTest logger) throws Exception

	{
		//PagerLastButton.click();
		boolean hasNextPage=true;


		outerloop:
			while(hasNextPage){

				//If the Next button is enabled/available, then enabled_next_page_btn size will be one.
				// So,you can perform the click action and then do the action
				if(NavigateNextButton.isEnabled())
				{
					NavigateNextButton.click();

					List<WebElement> listofRecords=driver.findElements(By.xpath("//table[@id='workListForm:wlTable']/tbody/tr"));

					for (int i=1;i<=listofRecords.size();i++)
					{
						WebElement ContentKeyLink=driver.findElement(By.xpath("//table[@id='workListForm:wlTable']/tbody/tr["+i+"]/td[2]"));
						String ContentKeytext=ContentKeyLink.getText();
						System.out.println(ContentKeytext);
						if (ContentKeytext.equalsIgnoreCase(ContentKey.trim()))
						{
							driver.findElement(By.xpath("//table[@id='workListForm:wlTable']/tbody/tr["+i+"]/td[2]/a")).click();
							logger.log(LogStatus.INFO, "Clicked on Content key: "+ContentKeytext);
							break outerloop ;


						}

						hasNextPage=true;
					}
				}
				else
				{
					System.out.println("No more Pages Available");
					break outerloop;
				}
			}
	}



		




	public void CorrectSubId_AlphaOnlyPrefix(ExtentTest logger) throws Exception

	{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();
		for (int i=0;i<=Prefix.length-1;i++)
		{
			if(Character.isDigit(Prefix[i]))
			{
				SubPrefix=SubPrefix.replace(Prefix[i],'F');
			}

		}

		String text=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with Alpha only prefix : "+text);

		Subscriber_ID.clear();
		Subscriber_ID.sendKeys(text);



	}

	public void CorrectSubId_Num_Num_Alpha_Prefix(ExtentTest logger) throws Exception

	{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();
		for (int i=0;i<=Prefix.length-2;i++)
		{
			if(Character.isAlphabetic(Prefix[i]))
			{
				SubPrefix=SubPrefix.replace(Prefix[i],'5');
			}	
		}

		if(Character.isDigit(Prefix[2]))
		{
			SubPrefix=SubPrefix.replace(Prefix[2],'A');
		}	

		String NewSubId=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with Num/Num/Alpha prefix : "+NewSubId);
		Subscriber_ID.clear();

		Subscriber_ID.sendKeys(NewSubId);



	}

	public void CorrectSubId_Alpha_Num_Alpha_Prefix(ExtentTest logger) throws Exception

	{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();

		if(Character.isAlphabetic(Prefix[0]))
		{

			logger.log(LogStatus.INFO, "First characteris Alpha");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[0],'X');
		}

		if(Character.isDigit(Prefix[1]))
		{

			logger.log(LogStatus.INFO, "second characteris Numeric");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[1],'6');
		}

		if(Character.isAlphabetic(Prefix[2]))
		{

			logger.log(LogStatus.INFO, "Third characteris Alpha");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[2],'G');
		}


		String NewSubId=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with Alpha/Num/Alpha prefix : "+NewSubId);
		Subscriber_ID.clear();

		Subscriber_ID.sendKeys(NewSubId);



	}



	public void CorrectSubId_Alpha_Num_Num_Prefix(ExtentTest logger) throws Exception

	{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();

		if(Character.isAlphabetic(Prefix[0]))
		{

			logger.log(LogStatus.INFO, "First characteris Alpha");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[0],'D');
		}

		if(Character.isDigit(Prefix[1]))
		{

			logger.log(LogStatus.INFO, "second characteris Numeric");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[1],'6');
		}

		if(Character.isDigit(Prefix[2]))
		{

			logger.log(LogStatus.INFO, "Third characteris Numeric");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[2],'6');
		}


		String NewSubId=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with Alpha/Num/Num prefix : "+NewSubId);
		Subscriber_ID.clear();

		Subscriber_ID.sendKeys(NewSubId);


	}



	public void CorrectSubId_Num_Alpha_Num_Prefix(ExtentTest logger) throws Exception

	{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();

		if(Character.isDigit(Prefix[0]))
		{

			logger.log(LogStatus.INFO, "First characteris Numeric");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[0],'6');
		}

		if(Character.isAlphabetic(Prefix[1]))
		{

			logger.log(LogStatus.INFO, "second characteris Alpha");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[1],'D');
		}



		if(Character.isDigit(Prefix[2]))
		{

			logger.log(LogStatus.INFO, "Third characteris Numeric");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[2],'6');
		}


		String NewSubId=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with Num/Alpha/Num prefix : "+NewSubId);
		Subscriber_ID.clear();

		Subscriber_ID.sendKeys(NewSubId);


	}

	public void InCorrectSubId_Prefix_One(Integer positionofOne,ExtentTest logger) throws Exception
	{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();


		SubPrefix=SubPrefix.replace(Prefix[positionofOne],'1');

		String NewSubId=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with 1 at position  :" +(positionofOne+1)+ " in prefix : "+NewSubId);
		Subscriber_ID.clear();

		Subscriber_ID.sendKeys(NewSubId);
	}

	public void InCorrectSubId_Prefix_zero(Integer positionofOne,ExtentTest logger) 
	{
		try 
		
		{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();


		SubPrefix=SubPrefix.replace(Prefix[positionofOne],'0');
		String NewSubId=SubPrefix+SubSuffix;
		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);
		logger.log(LogStatus.INFO, "Corrected Subscriber id with 0 at position :" +(positionofOne+1)+ " in prefix : "+NewSubId);
		Subscriber_ID.clear();
		Subscriber_ID.sendKeys(SubPrefix+SubSuffix);
		}
		catch(Exception E)
		{
			
			logger.log(LogStatus.FAIL, "The operation has not been performed. The page may not have loaded or the web element is not present/has changed on the UI. Please check this manually. The above steps will indicate which page has been loaded.");
		
		}
		
	}



	public void CorrectSubId_Num_Alpha_Alpha_Prefix(ExtentTest logger) {
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();

		if(Character.isDigit(Prefix[0]))
		{

			logger.log(LogStatus.INFO, "First characteris Numeric");
		}
		else
		{
			SubPrefix=SubPrefix.replace(Prefix[0],'6');
		}

		for(int i=1;i<=Prefix.length-1;i++)
		{
			if(Character.isAlphabetic(Prefix[i]))
			{

				logger.log(LogStatus.INFO, "character is Alpha");
			}
			else
			{
				SubPrefix=SubPrefix.replace(Prefix[i],'D');
			}

		}


		String NewSubId=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with Num/Alpha/Alpha prefix : "+NewSubId);
		Subscriber_ID.clear();

		Subscriber_ID.sendKeys(NewSubId);

	}
	
	
	
	
	public void CorrectSubId_FEP_ID_Prefix(ExtentTest logger) throws Exception

	{
		String SubID= Subscriber_ID.getAttribute("value");

		String SubPrefix=SubID.substring(0,3);
		String SubSuffix=SubID.substring(3);

		char[] Prefix=SubPrefix.toCharArray();

		SubPrefix=SubPrefix.replace(Prefix[0],'R');
		

		logger.log(LogStatus.INFO, "First characteris R");
		
		
		SubPrefix=SubPrefix.replace(Prefix[1],'4');
		

		SubPrefix=SubPrefix.replace(Prefix[2],'5');


		String NewSubId=SubPrefix+SubSuffix;

		logger.log(LogStatus.INFO, "Subscriber id before correction : " +SubID);

		logger.log(LogStatus.INFO, "Corrected Subscriber id with FEP-ID prefix : "+NewSubId);
		Subscriber_ID.clear();

		Subscriber_ID.sendKeys(NewSubId);



	}

	public void IndexCorrection(WebDriver driver,Map<String, String> data) throws Exception
	
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//if(IndexPage_DateOfServiceFrom.getText().isEmpty())
		
			js.executeScript("document.getElementById('workListForm:From_Date').value='" + data.get("DateOfServiceFrom") + "';");
		
		
		//if(IndexPage_DateOfServiceTo.getAttribute("value").equalsIgnoreCase("08/16/1906"))
		
			js.executeScript("document.getElementById('workListForm:To_Date').value='" + data.get("DateOfServiceTo") + "';");
		
		
		String text=new Select(IndexPage_DestnStationCode).getFirstSelectedOption().getAttribute("value");
		boolean f=text.equalsIgnoreCase("select");
		if(f)
		{
			Select Index_DestnStnCodes=new Select(IndexPage_DestnStationCode);
			Index_DestnStnCodes.selectByValue(data.get("DestStatusCode"));
		}
		boolean g=(IndexPage_SubLastName.getAttribute("value").isEmpty());
		if(g)
		{
			IndexPage_SubLastName.sendKeys(data.get("SubLastName"));
		}
		if(IndexPage_SubFirstName.getAttribute("value").isEmpty())
		{
			IndexPage_SubFirstName.sendKeys(data.get("SubFirstName"));
		}
		if(IndexPage_PatientLastName.getAttribute("value").isEmpty())
		{
			IndexPage_PatientLastName.sendKeys(data.get("PtnLastName"));
		}
		if(IndexPage_PatientFirstName.getAttribute("value").isEmpty())
		{
			IndexPage_PatientFirstName.sendKeys(data.get("PtnFirstName"));
		}
		if(!(IndexPage_RadioBtn_Male.isSelected()||IndexPage_RadioBtn_Female.isSelected()||IndexPage_RadioBtn_Unknown.isSelected()))
		{
			switch(data.get("Gender"))
			{
			case "Male":
				IndexPage_RadioBtn_Male.click();
			case "Female":
				IndexPage_RadioBtn_Female.click();
			default:
				IndexPage_RadioBtn_Unknown.click();
				
			}

		}
		
		//if(IndexPage_PatientDOB.getAttribute("value").equalsIgnoreCase("08/16/1906"))
		
			js.executeScript("document.getElementById('workListForm:PatientDateOfBirth').value='" + data.get("PtnDOB") + "';");
		
		
		String text1=new Select(IndexPage_SelectRelationship).getFirstSelectedOption().getAttribute("value");
		if(text1.equalsIgnoreCase("select"))
		{
			Select Relationships=new Select(IndexPage_SelectRelationship);
			Relationships.selectByVisibleText(data.get("Relationship"));
		}
		boolean t=IndexPage_ProviderName.getAttribute("value").isEmpty();
		if(t)
		{
			IndexPage_ProviderName.sendKeys(data.get("PrvName"));
		}
		
		if(IndexPage_FederalTaxId.getAttribute("value").isEmpty())
		{
			IndexPage_FederalTaxId.sendKeys(data.get("FedTaxId"));
		}
		if(IndexPage_BCBSProviderNum.getAttribute("value").isEmpty())
		{
			IndexPage_BCBSProviderNum.sendKeys(data.get("BCBSPrvName"));
		}
		
		String text3=new Select(IndexPage_ClaimType).getFirstSelectedOption().getAttribute("value");
		if(text3.equalsIgnoreCase("select"))
		{
			Select CTypes=new Select(IndexPage_ClaimType);
			CTypes.selectByVisibleText(data.get("ClaimType"));
		}
		
		if(IndexPage_TotalCharges.getAttribute("value").equals("0.00"))
		{
			IndexPage_TotalCharges.clear();
			IndexPage_TotalCharges.sendKeys(data.get("TotCharges"));
		}
		
		//if(IndexPage_ReceiptDate.getAttribute("value").equalsIgnoreCase("08/16/1906"))
		
			js.executeScript("document.getElementById('workListForm:ReciptDate').value='" + data.get("RecieptDate") + "';");
		
		
		if(IndexPage_ContactName.getAttribute("value").isEmpty())
		{
			IndexPage_ContactName.sendKeys(data.get("CnctName"));
		}
		
		if(IndexPage_ContactPhone.getAttribute("value").isEmpty())
		{
			IndexPage_ContactPhone.sendKeys(data.get("CnctPhone"));
		}
		
		
	}
}





