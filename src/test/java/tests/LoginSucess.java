package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.LoginPage;
import basetest.BaseTest;

public class LoginSucess extends BaseTest{
	
	@Test
	void loginUser_Passed()
	{
		LoginPage loginPageObj= hp.navigatetoLoginPage();
		loginPageObj.enterEmail(prop.getProperty("username").trim());
		loginPageObj.enterPassword(prop.getProperty("password").trim());
		loginPageObj.clickLoginButton();
		Assert.assertEquals(loginPageObj.islogoutButtonVisible(),true);
	}

}
