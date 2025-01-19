package basetest;

import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Page;

import Pages.HomePage;
import Pages.LoginPage;
import playwrightfactory.PlayWrightFactory;

public class BaseTest {
	
	protected PlayWrightFactory playwrightFactory = new PlayWrightFactory();
	protected Page page;
	protected HomePage hp;
	protected Properties prop;
	
	@BeforeTest
	public void setup() {
		prop = playwrightFactory.initProperties();
		page = playwrightFactory.initBrowser(prop);
		hp = new HomePage(page);
	}
	
	@AfterTest
	public void teardown() {
		page.context().browser().close();
	}

}
