package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
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

@Epic("Epic - 102 : Create Home page features")
@Feature("US - 502 : Create test for Home page on hubspot")
public class HomePageTest {
	
	WebDriver driver;	
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	HomePage homePage;
	Credentials userCred;

	@BeforeTest
	public void setUp() throws InterruptedException {
		//launch the browser..call init_driver
		//create an object of BasePage
		basePage = new BasePage();
		prop = basePage.init_properties();//this return Properties
		String browserName = prop.getProperty("browser");// accessed the browser property
		driver = basePage.init_driver(browserName);// passed the browserName
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		homePage = loginPage.doLogin(userCred);
		//Thread.sleep(5000);
	}
	
	
	@Test(priority =1)
	@Description("verify Home Page Title Test")
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageTitleTest() {
		String title = homePage.getHomePageTitle();
		System.out.println("Home page title is : "+ title);
		Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
	}
	
	@Test(priority =2)
	@Description("verify Home Page Header Test")
	@Severity(SeverityLevel.NORMAL)
	public void verifyHomePageHeaderTest() {
		String header = homePage.getHomePageHeader();
		System.out.println("Home page header is : "+ header);
		Assert.assertEquals(header, AppConstants.HOME_PAGE_HEADER);
	}
	
	@Test(priority =3, enabled = false)
	@Description("verify LoggedInUserAccountNameTest")
	@Severity(SeverityLevel.CRITICAL)
	public void verifyLoggedInUserAccountName() {
		String accountName = homePage.getLoggedInUserAccountName();
		System.out.println("LoggedInUserAccountName is "+ accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
