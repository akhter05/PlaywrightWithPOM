package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import basetest.BaseTest;
import constants.AppConstants;

public class HomePageTest extends BaseTest{
	
	@Test
	public void validateTitle() {
		String title = hp.getHomePageTitle();
		System.out.print("Title is:" + title +"Home Page title is" + AppConstants.HOME_PAGE_TITLE);	
		Assert.assertEquals("test", title);
	}
	
	@DataProvider
	public Object[][] getProductData() {
			return new Object[][] {
				{"Macbook"},
				{"iMac"},
				{"Samsung"}
			};	
	}
	
	@Test(dataProvider = "getProductData")
	public void searchTest(String productname)
	{
		String pageHeading = hp.doSearch(productname);
		Assert.assertEquals(pageHeading, "Search - " + productname);
	}
}
