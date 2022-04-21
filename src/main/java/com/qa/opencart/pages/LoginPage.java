package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	
	
	
	//1. private by locators:
	
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@type = 'submit' and @value = 'Login']");
	private By forgotPwd = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By loginErrorMessg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	private By loginErrorMessg1 = By.cssSelector("div.alert.alert-danger.alert-dismissible");

	//2. public page constructor :
	
	public LoginPage(WebDriver driver) {
		
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}


	// 3. public page actions :
	@Step("getting login page title..")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIs(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_TITLE);

	}

	@Step("getting login url title..")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrl(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_FRACTION_URL);

	}

	@Step("checking that forgot pwd link is displayed or not...")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwd);
	}

	@Step("login to application with username {0} and pssword {1}")
	public AccountsPage doLogin(String un, String pwd) {

		eleUtil.waitForElementToBeVisible(emailId, Constants.DEFAULT_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	@Step("login to application with  incorrect username {0} and pssword {1}")
	public boolean doInvalidLogin(String un, String pwd) {

		WebElement email_ele = eleUtil.waitForElementToBeVisible(emailId, Constants.DEFAULT_TIME_OUT);
		email_ele.clear();
		email_ele.sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		String actualErrorMsg = eleUtil.doElementGetText(loginErrorMessg);
		if(actualErrorMsg.contains(Errors.LOGIN_PAGE_ERROR_MESSGE)) {
			return true;
		}
		return false;
	}

	@Step("checking that register link is exist or not...")
	public boolean isRegisterLinkExist() {
		return eleUtil.waitForElementToBeVisible(registerLink, Constants.DEFAULT_TIME_OUT).isDisplayed();
	}

	@Step("navigating to register page...")
	public RegistrationPage navigateToRegisterPage() {
		if (isRegisterLinkExist()) {
			eleUtil.doClick(registerLink);
			return new RegistrationPage(driver);
		}
		return null;
	}
}
