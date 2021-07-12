
/**
 * @author gholla01
 */
package com.bsc.qa.framework.factory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
	
/**
 * @author gholla01
 * 
 * This class is used to instantiate the browsers in runtime. Especially useful when different browsers are required for different 
 * testcases and for parallel execution. 
 * Note: DO NOT CODE webDriver code directly in the testcase or in page objects. 
 */
public class BrowserFactory {		
	
	/**
	 * Factory method to create an instance of the browser web webDriver and pass the page url to the browser.
	 * 
	 * @param browserName	Browser name
	 * @param sdriver	Selenium driver
	 * @param url	URL
	 * @param implicitWait	Implicit wait
	 * @param headless	Headless - true or false
	 * @param browserDimensions	Browser dimensions
	 * @return
	 */
	public static WebDriver startBrowser(String browserName,String sdriver, String url, String implicitWait, String headless, String browserDimensions)
	{
		WebDriver webDriver = null;


		if(browserName.equalsIgnoreCase("firefox")){			
			webDriver = new FirefoxDriver();			
		}else if (browserName.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", sdriver);
            ChromeOptions chromeOptions = new ChromeOptions();
            // ChromeDriver is just AWFUL because every version or two it breaks unless you pass cryptic arguments
            //AGRESSIVE: options.setPageLoadStrategy(PageLoadStrategy.NONE); // https://www.skptricks.com/2018/08/timed-out-receiving-message-from-renderer-selenium.html
//            options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
            chromeOptions.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
    		if (headless != null && !"".equals(headless) && !"false".equals(headless) && "true".equals(headless)) {
                chromeOptions.addArguments("--headless"); // only if you are ACTUALLY running headless
    		}
    		if (browserDimensions != null && !"".equals(browserDimensions)) {
                chromeOptions.addArguments("--window-size=" + browserDimensions); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
    		}
            chromeOptions.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
            chromeOptions.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
            chromeOptions.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
            chromeOptions.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
            chromeOptions.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
            //driver = new ChromeDriver(options);
//			webDriver = new ChromeDriver(options);
            chromeOptions.addArguments("--incognito"); 
			DesiredCapabilities desiredCapabilitiesChrome = DesiredCapabilities.chrome();
			desiredCapabilitiesChrome.setCapability("applicationCacheEnabled", false);
			desiredCapabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			webDriver = new ChromeDriver(desiredCapabilitiesChrome);
    		if (browserDimensions == null && "".equals(browserDimensions)) {
    			webDriver.manage().window().maximize();
    		}
//			webDriver.get("chrome://settings/clearBrowserData");
//			webDriver.switchTo().activeElement();
//	        webDriver.findElement(By.cssSelector("#clearBrowsingDataConfirm")).click();
		}else if (browserName.equalsIgnoreCase("IE")){
			System.setProperty("webdriver.ie.driver", sdriver+"\\IEDriverServer.exe");
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("ignoreZoomSetting", true);
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			InternetExplorerDriverService.Builder ies=new InternetExplorerDriverService.Builder();
			File ie_temp=new File(sdriver);
			ies.withExtractPath(ie_temp);			
			InternetExplorerDriverService service=ies.build();
			webDriver = new InternetExplorerDriver(service,caps);		
		}else if (browserName.equalsIgnoreCase("safari")){			
			webDriver = new SafariDriver();
		}
		if (implicitWait == null || "".equals(implicitWait)) {
				webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				System.out.println("IMPLICIT WAIT: 20 seconds");
		}
		else {
			if (Boolean.parseBoolean(implicitWait)) {
				webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				System.out.println("IMPLICIT WAIT: 20 seconds");
			}
			else {
				System.out.println("IMPLICIT WAIT: No");
			}
		}
		webDriver.get(url);
		return webDriver;
	}
}

