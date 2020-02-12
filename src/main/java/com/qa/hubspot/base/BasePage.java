package com.qa.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
//to do parallel testing we use Threadlocal from Java
public class BasePage {
	
	//public WebDriver driver;
	public Properties prop;
	public static boolean highlightElement;
	public OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	
	public static synchronized WebDriver getDriver() {
		return tldriver.get();
	}
	
	
	
	
	public WebDriver init_driver(String browserName) {
		
		highlightElement = prop.getProperty("highlight").equals("yes") ? true :false;
		
		System.out.println("browser name is: "+ browserName);
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equals("chrome")) {
			
			WebDriverManager.chromedriver().setup();
			
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sireesha\\Desktop\\driver\\chromedriver.exe");
			
//			if(prop.getProperty("headless").equals("yes")) {
//			ChromeOptions co = new ChromeOptions();// headless mode
//			co.addArguments("--headless");
//			driver = new ChromeDriver(co);
//			}
//			else {
//				driver = new ChromeDriver();
//			}
			
			//driver = new ChromeDriver(optionsManager.getChromeOptions());			
			tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			
		}else if(browserName.equals("Firefox")){
			//System.setProperty("webdriver.gecko.driver", "C:\\Users\\Sireesha\\Desktop\\driver\\geckodriver.exe");
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			WebDriverManager.firefoxdriver().setup();
//			if(prop.getProperty("headless").equals("yes")) {
//				FirefoxOptions fo = new FirefoxOptions();// headless mode
//				fo.addArguments("--headless");
//				driver = new FirefoxDriver(fo);
//				}
//				else {
//					driver = new FirefoxDriver();
//				}
		}else if(browserName.equals("Safari")){			
			//driver = new SafariDriver();
			tldriver.set(new SafariDriver());
	    }else if(browserName.equals("IE")){			
		//driver = new InternetExplorerDriver();	
	    	tldriver.set(new InternetExplorerDriver());
		}
		else {
			System.out.println("browser Name "+ browserName + " is not found, please pass the right browser");
		}
		
		//driver.manage().deleteAllCookies();
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//driver.manage().window().fullscreen();
		
		//driver.get(url);
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		getDriver().manage().window().fullscreen();
		
		//return driver;
		return getDriver();
		
	}
	
	//method to read the properties from config.properties
	
	public Properties init_properties() {
		prop = new Properties();
		//"C:\\Users\\Sireesha\\eclipse-workspace\\JavaTrainingNaveen\\POMSeries
		String path = null;
		String env = null;
		
		try {
			env = System.getProperty("env");
			if(env.equals("qa")) {
				path = ".\\src\\main\\java\\com\\qa\\hubspot\\config\\qa.config.properties";
			}else if (env.equals("stg")) {
				path = ".\\src\\main\\java\\com\\qa\\hubspot\\config\\stg.config.properties";
			}
			
		}catch(Exception e) {
			path = ".\\src\\main\\java\\com\\qa\\hubspot\\config\\config.properties";
		}
		
		try {
			FileInputStream ip = new FileInputStream(path);
			//load all the properties
			try {
				prop.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {			
			System.out.println("some issue with config properties ... Please correct your config..");
		}
		return prop;
		
	}	
	
	/**
	 * take screenshot
	 */
	
	public String getScreenshot(){
		
	File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	String path = System.getProperty("user.dir") + "\\screenshots" + System.currentTimeMillis()+ ".png";
	File destination = new File(path);
	try {
		FileUtils.copyFile(src, destination);	
	}catch(IOException e) {
		System.out.println("screenshot captured failed..");
	}
	return path;
		
	}

}
