package Pages;

import com.microsoft.playwright.Page;

public class LoginPage {

	Page page;
	
	public LoginPage(Page page)
	{
		this.page = page;
	}
	

	String emailField = "//input[@id='input-email']";
	String pwdField = "//input[@id='input-password']";	
	String loginBtn = "//input[@value='Login']";
	String logoutlink = "//a[@class='list-group-item'][normalize-space()='Logout']";
	
	
	
	

	
	public void enterEmail(String email)
	{
		page.fill(emailField, email);
	}
	
	public void enterPassword(String pwd)
	{
		page.fill(pwdField, pwd);
	}
	
	public void clickLoginButton()
	{
		page.click(loginBtn);
	}
	
	public boolean islogoutButtonVisible()
	{
		return page.isVisible(logoutlink);
	}
	
}
