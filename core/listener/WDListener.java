
/**
 * 
 */
package com.bsc.qa.framework.listener;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.bsc.qa.framework.factory.BrowserFactory;
import com.bsc.qa.framework.factory.BrowserFactoryManager;

/**
 *@author gholla01
 *WebDriverListener class ensures driver is set in the BrowserFactoryManager before invocation of a test Method
 */
public class WebDriverListener implements IInvokedMethodListener {

	@Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("Browser_");
            String sdriver  = method.getTestMethod().getXmlTest().getLocalParameters().get("Driver_");
            String url = method.getTestMethod().getXmlTest().getLocalParameters().get("Url_");
        	url = url.replace("ENVNAME_WEB", System.getenv("ENVNAME_WEB"));
            String implicitWaitString = method.getTestMethod().getXmlTest().getLocalParameters().get("ImplicitWait_");
            String headlessString = method.getTestMethod().getXmlTest().getLocalParameters().get("Headless_");
            String browserDimension = method.getTestMethod().getXmlTest().getLocalParameters().get("BrowserDimensions_");
            
            WebDriver driver = BrowserFactory.startBrowser(browserName,sdriver,url, implicitWaitString, headlessString, browserDimension);
            BrowserFactoryManager.setWebDriver(driver);
        }
    }
 
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {        	
            /*
             * Future implementation if required. For now, we dont need to do anything in afterInvocation.
             */
        }
    }
}
