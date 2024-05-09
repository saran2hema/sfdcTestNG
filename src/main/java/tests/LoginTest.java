package tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
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
		//Enter valid username
		lp.username.sendKeys(DataUtils.readLoginTestData("valid.username"));
		test.log(Status.INFO, "username is entered");
		
		//Enter valid password
		lp.password.sendKeys(DataUtils.readLoginTestData("valid.password"));
		test.log(Status.INFO, "password is entered");
		
		//Click Log-In button
		lp.loginButton.click();
		test.log(Status.INFO, "Log-in button is clicked");
	}
	
	@Test
	public void TC01_verifyLoginErrorMessage1() throws IOException {
		WebDriver driver=BaseTest.getDriver();
		//Create object of page LoginPage
		LoginPage lp=new LoginPage(driver);
		
		
		//driver.manage().timeouts().implicitlyWait(WaitConstants.IMPLICIT_WAIT_DURATION);	
		//launch the SFDC app in browser
		driver.get(DataUtils.readLoginTestData("app.url"));
		//Enter valid username
		lp.username.sendKeys(DataUtils.readLoginTestData("valid.username"));
		//Clear password
		lp.password.clear();
		//Click Log-In button
		lp.loginButton.click();
		//get the login error message
		String errorMessage1=lp.loginErrorMessage.getText();
		
		CommonUtils.captureScreenShot(driver);
	
		Assert.assertEquals(errorMessage1, "Please enter your password.", "TC01--> Fail");
		
	}
	
	@Test
	public void TC03_RememberUserName() throws IOException, InterruptedException {
		WebDriver driver=BaseTest.getDriver();
		//Create object of page LoginPage
		LoginPage lp=new LoginPage(driver);
		
		//launch the SFDC app in browser
		driver.get(DataUtils.readLoginTestData("app.url"));
		//Enter valid username
		lp.username.sendKeys(DataUtils.readLoginTestData("valid.username"));
		//Enter valid password
		lp.password.sendKeys(DataUtils.readLoginTestData("valid.password"));
		//Click Remember me
		lp.rememberMe();
		//Click Log-In button
		lp.loginButton.click();
		//logout
		lp.logout();
		
		//verify username is retained
		String unameTxtbox=lp.username.getAttribute("value");
		Assert.assertEquals(unameTxtbox, DataUtils.readLoginTestData("valid.username"),"Valid username not retained");
		
		
	}

}
