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

public class MisRouteResponsePage {
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//a[@id='worklistRootDashboard:cmdid_PrivateInboxWorklistUi']") })
	@CacheLookup
	WebElement User_Inbox_Link;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:wlTable:deluxe1__pagerNext']") }) 
	WebElement NavigateNextButton;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='workListForm:respType_Val']") })
	@CacheLookup
	WebElement MisRouteResponseTypeTextbox;


	@FindAll({ @FindBy(how = How.XPATH, using = "//table[@id='workListForm:claimresp_sub_PanelGrid']/tbody/tr[4]/td[2]/span") })
	@CacheLookup
	WebElement SFNActionCode_field;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//table[@id='workListForm:claimresp_sub_PanelGrid']/tbody/tr[4]/td[3]/span") })
	@CacheLookup
	WebElement SFNActionCode_field_textBox;
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//table[@id='workListForm:claimresp_sub_PanelGrid']/tbody/tr[5]/td[2]/span") })
	@CacheLookup
	WebElement RequiredClaimInformation_Field;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//table[@id='workListForm:claimresp_sub_PanelGrid']/tbody/tr[5]/td[3]/span") })
	@CacheLookup
	WebElement RequiredClaimInformation_textBox;
	
	

	
	WebUtils utils=new WebUtils();
	
	
	
	
	public void clickOnUserInbox(ExtentTest logger)
	{
		User_Inbox_Link.click();
		logger.log(LogStatus.INFO, "Clicked on UserInbox");
	}
	
	
	public  void ClickOnContentKey_responsePage(WebDriver driver,String ContentKey,ExtentTest logger)
	{
		outerLoop:
			for(int k=1;k<=20;k++)
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


	public String getMisrouteResponseType(ExtentTest logger)
	{
		String ResponseType= MisRouteResponseTypeTextbox.getText();
		logger.log(LogStatus.INFO, "Misroute Response Type:"+ResponseType);
		return ResponseType;
	}
	
	public boolean validate_SFN_Action_CodeField(ExtentTest logger)
	{
		return SFNActionCode_field.getText().equalsIgnoreCase("SFN Action code");
	}
	public String get_SFN_Action_Code_text()
	{
		return SFNActionCode_field_textBox.getText();
	}
	
	public boolean validate_ReqInformationField(ExtentTest logger)
	{
		return RequiredClaimInformation_Field.getText().equalsIgnoreCase("Required Claim Information");
	}
	public String validate_ReqInformationFieldText()
	{
		return RequiredClaimInformation_textBox.getText();
	}

}
