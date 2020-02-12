package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Epic - 101 : Create login page features")
@Feature("US - 501 : Create test for login page on hubspot")
public class LoginPageTest {
	
	WebDriver driver;	
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;

	@BeforeTest
	public void setUp() {
		//launch the browser..call init_driver
		//create an object of BasePage
		basePage = new BasePage();
		prop = basePage.init_properties();//this return Properties
		String browserName = prop.getProperty("browser");// accessed the browser property
		driver = basePage.init_driver(browserName);// passed the browserName
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(priority = 1, description = "verify Login Page Title Test...")
	@Description("verify Login Page Title Test")
	@Severity(SeverityLevel.NORMAL)
	public void verifyLoginPageTitleTest() throws InterruptedException {
		//Thread.sleep(5000);// to take this off we need to create one more method
		String title = loginPage.getPageTitle();
		System.out.println("login Page title is : "+ title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);		
	}
	
	@Test(priority = 2)
	@Description("verify SignUp Link Test")
	@Severity(SeverityLevel.CRITICAL)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}
	
	@Test(priority = 3)
	@Description("login Test")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		HomePage homePage = loginPage.doLogin(userCred);
		String title = homePage.getHomePageTitle();
		Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
		//String accountName = homePage.getLoggedInUserAccountName();
		//Assert.assertEquals(accountName, prop.getProperty(accountname));
		
	}
	//negative test case// no need of maintaining xlsheet,we can use data provider
	
//@Test(priority = 4)
//	
//	public void loginTest_WrongCredentials() {
//		HomePage homePage = loginPage.doLogin("test1111@gmail.com","test123");
//		String title = homePage.getHomePageTitle();
//		Assert.assertEquals(title, "HubSpot Login");	
//		//String accountName = homePage.getLoggedInUserAccountName();
//		//Assert.assertEquals(accountName, "crmpro");
//		
//	}
	
	@DataProvider
	public Object[][] getLoginInvalidData() {
		Object data[][] = {
				{"test111@gmail.com","test123"},
				{"test112@gmail.com","test1234"},
				{"test113@gmail.com","test1235"},
				{" ","test1235"},
				{"test113@gmail.com"," "},
				{"test","test"},
				{" "," "}
				};
		return data;
		}
	
	@Test(priority =4, dataProvider = "getLoginInvalidData", enabled = false )
	public void login_InvalidTestCases(String username, String pwd) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMsg());
	}
	
		
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
