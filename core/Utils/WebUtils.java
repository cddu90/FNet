package WebUtils;

import static org.testng.Assert.assertFalse;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Sai Kiran Ayyagari
 * This class contains reusable methods to reduce the code from multiple lines to one line
 *
 */

public class WebUtils {

	/**
	 * Performs the double click operation
	 * 
	 * @param driver
	 *            - To perform double click operation on browser
	 * @param element
	 *            - The element on which double click needs to be performed
	 */
	public void doubleClick(WebDriver driver, WebElement element) {

		Actions action = new Actions(driver);

		action.moveToElement(element).doubleClick(element).perform();
	}

	/**
	 * Performs Right click operation
	 * 
	 * @param driver
	 *            - To perform right click operation on browser
	 * @param element
	 *            - The element on which Right click needs to be performed
	 */
	public void rightClick(WebDriver driver, WebElement element) {

		Actions action = new Actions(driver);

		action.contextClick(element).build().perform();
	}

	/**
	 * Performs explicit wait to check visibility of element for a particluar
	 * time period
	 * 
	 * @param driver
	 *            - To check the visibility of element on browser
	 * @param element
	 *            - Element for which driver should wait
	 * @return - returns webelement
	 */
	public  WebElement explicitWaitByVisibilityofElement(WebDriver driver,
			WebElement element) {

		WebElement locatedElement;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		locatedElement = wait.until(ExpectedConditions.visibilityOf(element));

		return locatedElement;
	}
	/**
	 * Performs explicit wait to check visibility of element for a particluar
	 * time period
	 * 
	 * @param driver
	 *            - To check the visibility of element on browser
	 * @param element
	 *            - Element for which driver should wait
	 * @return - returns webelement
	 */
	public void explicitWaitForVisibilityofElement(WebDriver driver,
			String xpath) {

		WebElement locatedElement;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));

	}

	/**
	 * To capture screenshots and save the files
	 * 
	 * @param webdriver
	 *            - To perform the screenshot operation on browser
	 * @param fileWithPath
	 *            - Destination path where file is saved
	 * @throws Exception
	 */
	public void takeSnapShot(WebDriver webdriver, String fileWithPath)
			throws Exception {

		// Convert web driver object to TakeScreenshot

		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

		// Call getScreenshotAs method to create image file

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination

		File DestFile = new File(fileWithPath);

		// Copy file at destination

		FileUtils.copyFile(SrcFile, DestFile);

	}

	/**
	 * Performs wait opeartion for a particular time till element is clickable
	 * 
	 * @param element
	 *            - The element on which wait operation needs to be performed
	 *            till it is clickable
	 */
	public void waitUntilElementclickable(WebElement element,WebDriver driver) {

		long currentTime = System.currentTimeMillis();
		long endTime = currentTime + 60000;
		for (int i = 0; currentTime < endTime; i++) {

			try {
				this.moveToClickableElement(element, driver);
				//element.click();
				break;

			} catch (Exception e) {

			}

			currentTime = System.currentTimeMillis();
		}

	}

	/**
	 * Waits for the element till it is clickable
	 * 
	 * @param driver
	 *            - To perform wait operation on the browser
	 * @param element
	 *            - The element for which wait is peformed
	 * @return - returns webElement
	 */
	public WebElement explicitWaitByElementToBeClickable(WebDriver driver,
			WebElement element) {

		WebElement locatedElement;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		locatedElement = wait.until(ExpectedConditions
				.elementToBeClickable(element));

		return locatedElement;
	}
	
	

	
	public void explicitWaitForTheElementToBeClickable(WebDriver driver,
			String xpath, int time) {

		WebDriverWait wait = new WebDriverWait(driver, time);

		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		
		
	} 
	
	
	 /* Waits for the element till it is clickable
	 * 
	 * @param driver
	 *            - To perform wait operation on the browser
	 * @param element
	 *            - The element for which wait is peformed
	 * @return - returns webElement
	 */
	public WebElement explicitWaitForTheElementToBeClickable(WebDriver driver,
			String xpath) {

		WebElement locatedElement;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		locatedElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		return locatedElement;
	}

	/**
	 * Waits till the element is present in the dom
	 * 
	 * @param driver
	 *            - To perform wait operation on the browser
	 * @param Xpath
	 *            - Xpath of the elemnt for which wait is performed
	 * @return - returns WebElement
	 */
	public WebElement explicitWaitByPresenceofElement(WebDriver driver,
			String Xpath) {

		WebElement locatedElement;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		locatedElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath(Xpath)));
		
		return locatedElement;
	}

	/**
	 * Waits for the list of elemnts till they are visible
	 * 
	 * @param driver
	 *            - performs wiat operation
	 * @param elements
	 *            - List of elements for which wait is performed
	 * @return - returns list of WebElements
	 */
	public List<WebElement> explicitWaitByVisibilityOfAllElements(
			WebDriver driver, List<WebElement> elements) {

		List<WebElement> locatedElements;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		locatedElements = wait.until(ExpectedConditions
				.visibilityOfAllElements(elements));

		return locatedElements;
	}

	/**
	 * Performs the click operation through Action class
	 * 
	 * @param element
	 *            - the element on which click operation needs to be performed
	 * @param driver
	 *            - To perform operations on the browser
	 */
	public void moveToClickableElement(WebElement element, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.build().perform();
	}
	
	/**
	 * Performs horizontal operation till particular element
	 * @param driver - performs operation on browser
	 * @param element 
	 */
	public void horizontalScroll(WebDriver driver,WebElement element){
		JavascriptExecutor jse = (JavascriptExecutor) driver;     
		jse.executeScript("arguments[0].scrollIntoView(true);",element);
	}

	/**
	 * Performs the mouseover operation
	 * 
	 * @param element
	 *            - the element on which mouseover is done
	 * @param driver
	 *            - To perform operations on browser
	 */
	public void mouseOver(WebElement element, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);

		actions.build().perform();
	}

	/**
	 * To click on particular element in the list
	 * 
	 * @param element
	 *            - the list of elements on which click is performed
	 * @param buttonName
	 *            - the name of the button,link or option in the dropdow to be
	 *            clicked
	 * @param logger
	 *            - to log the operation
	 * @param driver
	 *            - to perform operations on browser
	 * @throws InterruptedException
	 */
	public void clickButtonOrLink(List<WebElement> element, String buttonName,
			ExtentTest logger, WebDriver driver) throws InterruptedException {

		for (WebElement dropDownElement : element) {

			//System.out.println("The button name is !!!" + buttonName);
			//System.out.println("The dropdown element is "	+ dropDownElement.getText());
			try {

				if (!dropDownElement.isDisplayed()) {

					scrollDown(driver, dropDownElement);
				}

				if (dropDownElement.getText().trim()
						.equalsIgnoreCase(buttonName.trim())
						&& dropDownElement.isDisplayed()) {
					
					//this.waitUntilElementclickable(dropDownElement, driver);

					moveToClickableElement(dropDownElement, driver);

					break;
				}
			}

			catch (Exception E) {

				logger.log(LogStatus.FAIL, "Drop down element " + buttonName
						+ " is not found : ");
			}
		}
	}
	
	
	public void waitForPageLoaded(WebDriver driver)
	{
	    ExpectedCondition<Boolean> expectation = new
	ExpectedCondition<Boolean>() 
	    {
	        public Boolean apply(WebDriver driver)
	        {
	            return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	        }
	    };

	    
	}

	/**
	 * Performs scroll down operation
	 * 
	 * @param driver
	 *            - to perform operations on browser
	 * @param element
	 *            - Scroll down will be performed till the element
	 */
	public void scrollDown(WebDriver driver, WebElement element) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;

		executor.executeScript("arguments[0].scrollIntoView();", element);

	}

	/**
	 * Performs scroll up
	 * 
	 * @param driver
	 *            - to perform operations on browser
	 */
	public void scrollUp(WebDriver driver) {

		// WebElement element = driver.findElement(By.tagName("header"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		executor.executeScript("window.scrollTo(0, -document.body.scrollHeight);");

	}

	/**
	 * Performs click operation
	 * 
	 * @param driver
	 *            - to perform operations on browser
	 * @param element
	 *            - the element which is to be clicked
	 */
	public void javaScriptExecutorToClickElement(WebDriver driver,
			WebElement element) {

		JavascriptExecutor jse2 = (JavascriptExecutor) driver;
		jse2.executeScript("arguments[0].scrollIntoView()", element);
	}

	/**
	 * Performs wait till the element is invisible for particular tim period
	 * 
	 * @param driver
	 *            - to perform operation on browser
	 * @param Xpath
	 *            - the xpath of element for which wait is performed
	 */
	public void explicitwaitByinvisibilityOfElementLocated(WebDriver driver,
			String Xpath) {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.xpath(Xpath)));

	}

	/**
	 * Uploads the file using Robot class
	 * 
	 * @param docPath
	 *            - document path
	 */
	public void uploadFileWithRobot(String docPath) {
		StringSelection stringSelection = new StringSelection(docPath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		System.out.println("The document path is " + docPath);
		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(150);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * Downloads the file to a particular path(This is applicable to Auth Accel)
	 * 
	 * @param docPath
	 *            - document path
	 */
	public void downloadFileWithRobot(String docPath) {

		StringSelection stringSelection = new StringSelection(docPath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.delay(250);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(150);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}

	/**
	 * Copies the text from a text box and store it into string
	 * 
	 * @param element
	 *            - the textbox from which text needs to be copied
	 * @return - returns string
	 * @throws Exception
	 * @throws IOException
	 */
	public String copyTextFromTextBox(WebElement element) throws Exception,
			IOException {

		element.sendKeys(Keys.CONTROL + "a");
		Thread.sleep(5000);
		element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
		Thread.sleep(5000);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		String copiedString = (String) contents
				.getTransferData(DataFlavor.stringFlavor);
		return copiedString;

	}

	/**
	 * Performs wait operation atleast one elemnt in the list is present in the
	 * DOM
	 * 
	 * @param driver
	 *            - To perform operation on browser
	 * @param Xpath
	 *            - The xpath of the list of elements
	 * @return - returns list of WebElements
	 */
	public List<WebElement> explicitWaitByPresenceofAllElements(
			WebDriver driver, String Xpath) {
		List<WebElement> locatedElement;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		locatedElement = wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.xpath(Xpath)));
		

		return locatedElement;
	}

	
	/**
	 * To click on particular element in the list
	 * 
	 * @param element
	 *            - the list of elements on which click is performed
	 * @param buttonName
	 *            - the name of the button,link or option in the dropdow to be
	 *            clicked
	 * @param logger
	 *            - to log the operation
	 * @param driver
	 *            - to perform operations on browser
	 * @throws InterruptedException
	 */
	public void selectDropdownValueByVisibleText(WebElement element, String dropdownValue,
			ExtentTest logger, WebDriver driver) throws InterruptedException {

			try {

				if (!element.isDisplayed()) {

					scrollDown(driver, element);
				}
				Select oSelect = new Select(element);
   	        	oSelect.selectByVisibleText(dropdownValue);
   	        	logger.log(LogStatus.INFO, " Selected element value as '" + dropdownValue + "' in the dropdown");
				
			}

			catch (Exception E) {

				logger.log(LogStatus.FAIL, "Drop down element " + dropdownValue + " is not found : ");
			}
	}
	
	/**
	 * @param element
	 * @param logger
	 * @param driver
	 * @throws InterruptedException
	 */
	public void elementClickWithLogger(WebElement element, 
			ExtentTest logger, WebDriver driver) throws InterruptedException {
			try {
				element.click();
   	        	logger.log(LogStatus.PASS, " Clicked on " + element + " element ");
			}
			catch (Exception E) {
				logger.log(LogStatus.FAIL, " Failed to click on " + element + " element ");
			}
	}
	
	public void sendKeysWithLogger(WebElement element, String strValue,
			ExtentTest logger, WebDriver driver) throws InterruptedException {
			try {
				element.sendKeys(strValue.toString());
   	        	logger.log(LogStatus.PASS, " Clicked on " + element + " element ");
			}
			catch (Exception E) {
				logger.log(LogStatus.FAIL, " Failed to click on " + element + " element ");
			}
	}
	
	
	
	
}
