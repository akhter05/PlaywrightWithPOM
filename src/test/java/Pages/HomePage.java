package Pages;

import com.microsoft.playwright.Page;

public class HomePage {
	
	private Page page;
		
	String searchFieldLocator ="//input[@placeholder='Search']";
	String searchbtnlocator = "//button[@class='btn btn-default btn-lg']";
	String searchResultPageHeaderLocator ="div[id='content'] h1";
	String accountMenuLocator = "//span[normalize-space()='My Account']";
	String logInMenuLocator = "//a[normalize-space()='Login']";
	
	public HomePage(Page page) {
		this.page = page;
	}
	
	public String getHomePageTitle() {
		return page.title();
	}
		
	public String doSearch(String productName) {
		 page.fill(searchFieldLocator, productName);
		 page.click(searchbtnlocator);
		 return page.textContent(searchResultPageHeaderLocator);
	}
	
	public LoginPage navigatetoLoginPage()
	{
		page.click(accountMenuLocator);
        page.click(logInMenuLocator);
        LoginPage loginPage = new LoginPage(page);
        return loginPage;
	}

}
