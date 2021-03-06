package WebUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
//import org.openqa.selenium.firefox.FirefoxOptions;

import com.bsc.qa.framework.factory.BrowserFactoryManager;

public class WebDriverListenerCustom implements IInvokedMethodListener {
	
	public static String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss"));
	public static String resultsDestinationFolderPath = "target\\FileNetResults_" + timestamp;
	
	    @Override
	    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	        if (method.isTestMethod()) {
	            String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("Browser_");
	            String sdriver  = method.getTestMethod().getXmlTest().getLocalParameters().get("Driver_");
	            String url = method.getTestMethod().getXmlTest().getLocalParameters().get("Url_");
	            String implicitWait = method.getTestMethod().getXmlTest().getLocalParameters().get("ImplicitWait_");
	           // String headlessString = method.getTestMethod().getXmlTest().getLocalParameters().get("Headless_");
	           // String browserDimensionsString = method.getTestMethod().getXmlTest().getLocalParameters().get("BrowserDimensions_");
	            
	            //WebDriver driver = startBrowser(browserName,sdriver,url, implicitWaitString, headlessString, browserDimensionsString);
	            
	           //String environment = "QA";
	            String Env = method.getTestMethod().getXmlTest().getLocalParameters().get("Env_");
	            String inputDatasheet = method.getTestMethod().getXmlTest().getLocalParameters().get("InputDataSheetPath_");
		           
	            WebDriver driver = startBrowser(sdriver,browserName,url, implicitWait, Env, inputDatasheet);
	            
	            BrowserFactoryManager.setWebDriver(driver);
	        }
	    }
	    
	    
	
		public static WebDriver startBrowser(String sdriver,String browserName, String url, String implicitWait, String Env, String inputDataSheetPath)
		{
			WebDriver webDriver = null;
System.out.println("inside browser init");
			if("Firefox".equalsIgnoreCase(browserName)){			
				//webDriver = new FirefoxDriver();	
				 System.setProperty("webdriver.gecko.driver", sdriver+"\\geckodriver.exe");
				//System.setProperty("webdriver.gecko.driver", sdriver);
				
//				FirefoxProfile ffprofile = new FirefoxProfile();
//				ffprofile.setPreference("dom.webnotifications.enabled", false);
//				WebDriver driver = new FirefoxDriver(ffprofile);
				
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("app.update.auto", false);
				profile.setPreference("app.update.enabled", false);
				WebDriver driver = new FirefoxDriver(profile);
				
				
//				FirefoxOptions options = new FirefoxOptions();
//				options.addPreference("dom.disable_beforeunload", true)
//				WebDriver driver = new FirefoxDriver(options);
				
			}else if ("chrome".equalsIgnoreCase(browserName)){
				System.setProperty("webdriver.chrome.driver", sdriver);
	           ChromeOptions options = new ChromeOptions();
	            // ChromeDriver is just AWFUL because every version or two it breaks unless you pass cryptic arguments
	            //AGRESSIVE: options.setPageLoadStrategy(PageLoadStrategy.NONE); // https://www.skptricks.com/2018/08/timed-out-receiving-message-from-renderer-selenium.html
//	            options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
//	            options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
//	    		if (headless != null && !"".equals(headless) && !"false".equals(headless) && "true".equals(headless)) {
//	                options.addArguments("--headless"); // only if you are ACTUALLY running headless
//	                options.addArguments("window-size=" + browserDimensions);
//	    		}
	            options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
	            options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
	            options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
	            options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
	            options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
	            //TODO: Add additional arguments here
				webDriver = new ChromeDriver(options);
				webDriver = new ChromeDriver();
			}else if ("IE".equalsIgnoreCase(browserName)){
				
				System.setProperty("webdriver.ie.driver", sdriver);
				//System.setProperty("webdriver.ie.driver", sdriver+"\\IEDriverServer.exe");
//				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
//				caps.setCapability("ignoreZoomSetting", true);
//				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
//				InternetExplorerDriverService.Builder ies=new InternetExplorerDriverService.Builder();
//				File ieTemp=new File(sdriver);
//				ies.withExtractPath(ieTemp);			
//				InternetExplorerDriverService service=ies.build();
				//webDriver = new InternetExplorerDriver(service,caps);
				webDriver = new InternetExplorerDriver();
				System.out.println(webDriver.toString());
			}else if ("safari".equalsIgnoreCase(browserName)){			
				webDriver = new SafariDriver();
			}
			else if ("edge".equalsIgnoreCase(browserName)){	
				System.setProperty("webdriver.edge.driver", sdriver+"\\msedgedriver.exe");
				webDriver = new EdgeDriver();
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
			try{
				
				Path path = Paths.get(resultsDestinationFolderPath);
				 if(!Files.exists(path)){//checking the existence of given path
					 	try {Files.createDirectories(path);//Creating directory
					 		} 
					 	catch(IOException e)
					 	{	
					 		e.printStackTrace();}
				 	}
				 	Path testDataSheetSourcePath=Paths.get(inputDataSheetPath);//storing report source path in testDataSourcePath
					
				 	File srcdatasheetPath = new File(inputDataSheetPath);
				 	
				 	
				 	String dataSheetName = srcdatasheetPath.getName();
				 	
				 	Path testDataSheetDestinationPath=Paths.get(resultsDestinationFolderPath + "\\"+dataSheetName);//storing report source path in testDataDestinationPath
					TestFileUtil.copyFile(testDataSheetSourcePath,testDataSheetDestinationPath);//copy function
				
					
				
				} catch (IOException e) {
					e.printStackTrace();//Printing exception object 
				}
			
			System.out.println(webDriver.toString());
			webDriver.manage().window().maximize();
			webDriver.get(url);
			return webDriver;
		}

		@Override
		public void afterInvocation(IInvokedMethod method,
				ITestResult testResult) {
			// TODO Auto-generated method stub
			
		}


}

