package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.ContactsPage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ExcelUtil;

public class ContactsPageTest {
	
		
		WebDriver driver;	
		BasePage basePage;
		Properties prop;
		LoginPage loginPage;
		Credentials userCred;
		ContactsPage contactsPage;
		HomePage homePage;

		@BeforeMethod
		public void setUp() {
			
			basePage = new BasePage();
			prop = basePage.init_properties();//this return Properties
			String browserName = prop.getProperty("browser");// accessed the browser property
			driver = basePage.init_driver(browserName);// passed the browserName
			driver.get(prop.getProperty("url"));
			loginPage = new LoginPage(driver);			
			userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
			homePage = loginPage.doLogin(userCred);
			contactsPage = homePage.goToContactsPage();
		}
		
		@Test(priority = 1)
		public void verifyContactsPageTitle() throws InterruptedException {
			
			String title =contactsPage.getContactsPageTitle();
			System.out.println("contacts Page title is : "+ title);
			Assert.assertEquals(title, "Contacts");		
		}
		
		@DataProvider
		public Object[][] getContactsTestData(){
			//Object[][] data = ExcelUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);
			Object[][] data = ExcelUtil.getTestData("contacts");
			return data;
		}
		
		//For single set of data
		
		/*@Test(priority=2, enabled = false)
		public void createContactsTest() {
			contactsPage.createNewContact("siri@gmail.com","siri","N","QA Engineer");
		}*/
		
		
		@Test(priority=2, dataProvider = "getContactsTestData",enabled = true)
		public void createContactsTest(String email,String firstName,String lastName, String jobTitle) {
			contactsPage.createNewContact(email,firstName,lastName,jobTitle);
		}
		
		@AfterMethod
		public void tearDown() {
			driver.quit();
		}

}
