/**
 * @class BrowserFactoryManager
 * @author gholla01
 *  Manager class uses a concept in java called ThreadLocal variables.
 *  Useful in ensuring your parallel executions are thread safe
 */
package com.bsc.qa.framework.factory;
import org.openqa.selenium.WebDriver;
/**
 *@author gholla01
 *BrowserFactoryManager - Introduced this class to ensure webdriver doesn't overlap in a parallel execution
 *ThreadLocal provides thread-local variables to ensure thread safe executions.
 *NOTE: Please do not use global static variable across the class for driver. Use getDriver and setWebDriver functions to 
 *ensure thread safe parallelism.
 */
public class BrowserFactoryManager {
	 private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<WebDriver>();
	 
	 public static WebDriver getDriver() {
	        return webDriverThreadLocal.get();
	 }
	 
	 public static void setWebDriver(WebDriver driver) {
		 webDriverThreadLocal.set(driver);
	 }

}

