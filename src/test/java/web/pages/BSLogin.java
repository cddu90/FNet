package com.bsc.qa.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.framework.base.BasePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


/**
 * <description>
 * @param data - sending test data values to perform the operations
 * @param driver - To perform operations on browser
 * @param logger - to log the operation
 * @throws InterruptedExceptionaf
 */
public class BlueSquareLoginPage extends BasePage {

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='userNameFld']") })
	@CacheLookup
	WebElement usernameTextBox;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='passwordFld']") })
	@CacheLookup
	WebElement passwordTextbox;

	@FindAll({ @FindBy(how = How.XPATH, using = "//button[@id='loginBtn']") })
	@CacheLookup
	WebElement loginButton;
	
	//Below are the obejcts for HostSccfHistoryPage page
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='sccfHistoryTab']") })
	@CacheLookup
	WebElement sccfHistoryLink;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='sccfFld']") })
	@CacheLookup
	WebElement sccfnumberTextbox;

	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='listingSearchButton']") })
	@CacheLookup
	WebElement SearchButton;
	
	
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//a[@id='logout']") })
	@CacheLookup
	WebElement LogoutButton;
	
	
	
	

	public void loginToBlue2Application(String username, String pwd,ExtentTest logger)
			throws InterruptedException {
		logger.log(LogStatus.INFO, "Log into application");
		
		usernameTextBox.sendKeys(username);
		logger.log(LogStatus.INFO, "Entered username as " + username);
		
		String Password = AutomationStringUtilities.decryptValue(pwd);
		
		//entering without encryption
		//String Password = pwd;
		
		passwordTextbox.sendKeys(Password);
		
		logger.log(LogStatus.INFO,"Entered password");
		loginButton.click();
		logger.log(LogStatus.INFO, "clicked on login button");

	}
	
	
	
	
	
	
	
	
	
	

	public void logoutFromBlue2Application(ExtentTest logger)throws InterruptedException {
		
		LogoutButton.click();
		logger.log(LogStatus.INFO, "clicked on Logout button");
		Thread.sleep(1000);
		
	}
	
	
	
	
	
	public void EnterSccfAtSCCFHistoryPage_old(WebDriver driver, String sccfNumber, ExtentTest logger) throws InterruptedException{
		
		//Clicking on the history link
		sccfHistoryLink.click();
		logger.log(LogStatus.INFO, "Clicked on SCCF History link.");
		Thread.sleep(5000);

		//Entering the sccf number in the textbox
		sccfnumberTextbox.sendKeys(sccfNumber);
		logger.log(LogStatus.INFO, "SCCF Number entered: " + sccfNumber);
		Thread.sleep(5000);

		//Clicking on the search button
		SearchButton.click();
		logger.log(LogStatus.INFO, "Clicked on Search button.");
		Thread.sleep(5000);

	}	

	
	
}


