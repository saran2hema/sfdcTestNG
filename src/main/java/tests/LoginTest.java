package tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import constants.WaitConstants;
import listeners.TestListeners;
import pages.LoginPage;
import utils.CommonUtils;
import utils.DataUtils;

@Listeners(TestListeners.class)
public class LoginTest extends BaseTest{
	
	@BeforeMethod
	public void preConditions(Method name) {
		
		System.out.println("Preconditions: Launch chrome browser with headless=false");
		BaseTest.setDriver("chrome", false);
		
	}
	@AfterMethod
	public void postConditions() {
		System.out.println("Postconditions: Close the chrome browser");
		//driver.quit();
		//BaseTest.getDriver().close();
		
	}
	@AfterTest
	public void afterTest() {
		BaseTest.getDriver().quit();
	}
	
	@Test
	public void TC02_loginWithValidCredentials() throws IOException {
		test = BaseTest.threadExtentTest.get();
		WebDriver driver=BaseTest.getDriver();
		
		//Create object of page LoginPage
		LoginPage lp=new LoginPage(driver);
		
		BaseTest.test=BaseTest.extent.createTest("VerifyLoginwithcorrectCredentials1");
	
		//launch the SFDC app in browser
		driver.get(DataUtils.readLoginTestData("app.url"));
		driver.manage().timeouts().implicitlyWait(WaitConstants.IMPLICIT_WAIT_DURATION);
		driver.manage().timeouts().scriptTimeout(WaitConstants.IMPLICIT_WAIT_DURATION);
    driver.manage().timeouts().pageLoadTimeout(WaitConstants.IMPLICIT_WAIT_DURATION);
		//Enter valid username
		//lp.username.sendKeys(DataUtils.readLoginTestData("valid.username"));
		
		//login with valid credentials
		Assert.assertTrue((lp.logintoSFDC(DataUtils.readLoginTestData("valid.username"),
				DataUtils.readLoginTestData("valid.password"),driver)),"TC02: FAil unable to login");
		CommonUtils.captureScreenShot(driver);
	}
	
	//@Test
	public void TC01_verifyLoginErrorMessage1() throws IOException {
		WebDriver driver=BaseTest.getDriver();
		//Create object of page LoginPage
		LoginPage lp=new LoginPage(driver);
		
		//launch the SFDC app in browser
		driver.get(DataUtils.readLoginTestData("app.url"));
		driver.manage().timeouts().implicitlyWait(WaitConstants.IMPLICIT_WAIT_DURATION);
		
		//Enter valid username
		Assert.assertTrue(lp.enterUsername(DataUtils.readLoginTestData("valid.username")), "Issue in entering Username");
		
		//Clear password
		Assert.assertTrue(lp.clearPassword(), "Issue in clearing password field");

		
		//Click Log-In button and verify Login error
		Assert.assertTrue(lp.verifyLoginError(driver),"TC01--> Fail");
		CommonUtils.captureScreenShot(driver);
	}
	
	//@Test
	public void TC03_RememberUserName() throws IOException, InterruptedException {
		WebDriver driver=BaseTest.getDriver();
		//Create object of page LoginPage
		LoginPage lp=new LoginPage(driver);
		
		//launch the SFDC app in browser
		driver.get(DataUtils.readLoginTestData("app.url"));
		
		//Login using valid credentials
		//Enter valid username
				Assert.assertTrue(lp.enterUsername(DataUtils.readLoginTestData("valid.username")), "Issue in entering Username");
		
		//Enter valid pasword
		Assert.assertTrue(lp.enterPassword(DataUtils.readLoginTestData("valid.password")), "Issue in entering password");
		//Click Remember me
		Assert.assertTrue(lp.rememberMe(),"Issue in selecting rememberMe");
		//Click Log-In button
		lp.loginButton.click();
		//logout
		Assert.assertTrue(lp.logout(driver),"Issue in logging out");
		
		//verify username is retained
		Assert.assertTrue(lp.verifyUsernameRetained(DataUtils.readLoginTestData("valid.username")),"TC03-->Fail Valid username not retained");
			
		
	}

}
