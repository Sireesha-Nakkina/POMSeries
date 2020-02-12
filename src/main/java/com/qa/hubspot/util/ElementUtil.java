package com.qa.hubspot.util;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.hubspot.base.BasePage;

//according to best practices, what is missing, error handling.

public class ElementUtil extends BasePage{
	
	WebDriver driver;
	WebDriverWait wait;
	JavaScriptUtil jsUtil;
	Properties prop;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver,AppConstants.DEFAULT_TIMEOUT);
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public boolean waitForTitlePresent(String title) {
		wait.until(ExpectedConditions.titleIs(title));
		return true;
	}
	
	public boolean waitForElementPresent(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return true;
	}
	
	public boolean waitForElementVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return true;
	}
	public boolean waitForElement(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return true;
	}
	
	public String doGetPageTitle() {
		try {
			return driver.getTitle();
		}
		catch(Exception e) {
			System.out.println("Exception occured while getting the title..");			
		}
		return null;
	}

	
	/**
	 * This method is used to create the webelement on the basis of By locator
	 * @param locator
	 * @return
	 */
	
	public WebElement getElement(By locator) {
		WebElement element = null; // to handle it properly
	    try {
	    	//if(waitForElementPresent(locator));
	    element = driver.findElement(locator);
	    if(highlightElement) {
	    	jsUtil.flash(element);
	    }
	    }
	    catch(Exception e) {
	    	System.out.println("Some exception got occurred while creating the web element...");
	    }
		return element;		
	}
	
	public void doClick(By locator) {
		try {
		getElement(locator).click();
		}
		catch(Exception e) {
			System.out.println("Some exception occurred while clicking on the web element...");
		}
	}
	
	public void doActionClick(By locator) {
		try {
			WebElement element = getElement(locator);
			Actions action = new Actions(driver);
			action.click(element);
		}catch(Exception e) {
			System.out.println("Some exception got occurred while clicking on the web element..");
		}
	}
	
	public void doActionSendKeys(By locator,String value) {
		try {
			WebElement element = getElement(locator);
			Actions action = new Actions(driver);
			action.sendKeys(element,value);
		}catch(Exception e) {
			System.out.println("Some exception got occurred while entering value in the field..");
		}
	}
	
	public void doSendKeys(By locator,String value) {
		try {
		 WebElement element = getElement(locator);
		 element.clear();
		 element.sendKeys(value);
		}
		catch(Exception e) {
			System.out.println("some exception occurred while entering value in a field..");
		}
		
	}
	
	public boolean doIsDisplayed(By locator) {
		try {
		return getElement(locator).isDisplayed();
		}
		catch(Exception e) {
			System.out.println("some exception occured while element is displayed..");
		}
		return false;//method return
	}
	
	public String doGetText(By locator) {
		try {
		return getElement(locator).getText();
		}
		catch(Exception e) {
			System.out.println("Some exception occured while getting the text from a webelement..");
		}
		return null;
	}
	

}
