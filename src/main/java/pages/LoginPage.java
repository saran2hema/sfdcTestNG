package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id="username")
	public WebElement username;

	@FindBy(id="password")
	public WebElement password;

	@FindBy(id="Login")
	public WebElement loginButton;

	@FindBy(xpath="//div[@id='error']")
	public WebElement loginErrorMessage;

	@FindBy(id="rememberUn")
	public WebElement rememberMe;
	
	@FindBy(xpath="//div[@id='userNav']")
	public WebElement userNavButton;
	
	@FindBy(xpath="//a[contains(text(),'Logout')]")
	public WebElement logoutButton;


	public void logintoSFDC(String email, String passWord) {
		logger.info("Logging in to application");
		this.username.sendKeys(email);
		this.password.sendKeys(passWord);
		this.loginButton.click();
		logger.info("Should be signed in to application");

	}
	public void rememberMe() throws InterruptedException {

		if(!rememberMe.isSelected()) {
			rememberMe.click();
			logger.info("Remember me is clicked");
		}

		Thread.sleep(3000);
	}
	
	public void logout() throws InterruptedException {
		Thread.sleep(2000);
	
		userNavButton.click();
logger.info("Usermenu is clicked");
		Thread.sleep(2000);
		
		logoutButton.click();
		logger.info("User is logged out");
		Thread.sleep(4000);
}
}
