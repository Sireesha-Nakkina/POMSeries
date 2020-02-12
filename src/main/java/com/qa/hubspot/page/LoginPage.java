package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ElementUtil;
import com.qa.hubspot.util.JavaScriptUtil;

public class LoginPage extends BasePage{
	
	//this is called encapsulation.Page objects are a classic example of encapsulation - they hide the details of the UI structure
	
	WebDriver driver;
	ElementUtil elementUtil;
	JavaScriptUtil jsUtil;
	
	//1.locators = By
	By email = By.id("username");
	By password = By.id("password");
	By loginButton = By.id("loginBtn");
	By signUpLink = By.linkText("Sign up111");
	By loginErrorText = By.xpath("//div[@class='private-alert__inner']");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		
	}
	
	//page actions:
	
	public String getPageTitle() {
		elementUtil.waitForTitlePresent(AppConstants.LOGIN_PAGE_TITLE);
		return elementUtil.doGetPageTitle();
	}
	
	public String getPageTitleUsingJS() {
		return jsUtil.getTitleByJS();
	}
	
	public boolean checkSignUpLink() {
		elementUtil.waitForElementPresent(signUpLink);
		return elementUtil.doIsDisplayed(signUpLink);
	}
	
	//when we click on click on loginbutton it will land on HomePage
	//so we return homepage object -- linking
	
	//not a good practice to send multiple parameters
	
	//public HomePage doLogin(String username, String pwd) {
	
	public HomePage doLogin(Credentials userCred) {
		elementUtil.waitForElementPresent(email);
		elementUtil.doSendKeys(email, userCred.getAppUsername());
		elementUtil.doSendKeys(password, userCred.getAppPassword());
		elementUtil.doClick(loginButton);
	
		/*elementUtil.doSendKeys(email, username);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginButton);*/
		
		/*driver.findElement(email).sendKeys(username);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(loginButton).click();*/
		
		return new HomePage(driver);
	}
	
	
	public boolean checkLoginErrorMsg() {
		return elementUtil.doIsDisplayed(loginErrorText);
	}
	
	

}
