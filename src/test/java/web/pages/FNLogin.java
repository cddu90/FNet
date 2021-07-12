package com.bsc.qa.web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.bsc.bqsa.AutomationStringUtilities;
import com.bsc.qa.framework.base.BasePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * <description>
 * @param logger - to log the operation
 * @throws InterruptedException
 */

public class FileNetLoginPage extends BasePage {
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='j_id_jsp_554479780_2:userName']") })
	@CacheLookup
	WebElement usernameTextBox;

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='j_id_jsp_554479780_2:password']") })
	@CacheLookup
	WebElement passwordTextbox;
	
	@FindAll({@FindBy(how=How.XPATH,using="//select[@id='j_id_jsp_554479780_2:roleType']") })
	@CacheLookup
	WebElement RoleTextbox;
	

	@FindAll({ @FindBy(how = How.XPATH, using = "//input[@id='j_id_jsp_554479780_2:j_id_jsp_554479780_15']") })
	@CacheLookup
	WebElement loginButton;
	
	
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//span[text()='Logout']") })
	@CacheLookup
	WebElement LogOutlink;
	
	
	
	
	
	
	
	public void loginToFileNetApplication(String username, String pwd,String Role,ExtentTest logger)
			throws InterruptedException {
		logger.log(LogStatus.INFO, "Log into FileNet application");
		
		String UserName = username;
		
		usernameTextBox.sendKeys(UserName);
		logger.log(LogStatus.INFO, "Entered username  " );
		
		String Password = AutomationStringUtilities.decryptValue(pwd);
		
		passwordTextbox.sendKeys(Password);
		
		
		
		logger.log(LogStatus.INFO,"Entered password");
		
		Select Role_types= new Select(RoleTextbox);
		Role_types.selectByValue(Role);
		logger.log(LogStatus.INFO,"Role Selected is:" +Role);
		
		loginButton.click();
		logger.log(LogStatus.INFO, "clicked on login button");
		
		

	}
	
public void logoutFromFileNetApplication(ExtentTest logger)throws InterruptedException {
		
	LogOutlink.click();
	logger.log(LogStatus.INFO, "clicked on Logout button");
	Thread.sleep(1000);

	}

 

}

