package playwrightfactory;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlayWrightFactory {
	
	/**
	 * 	 
	Page page;
	Browser browser;
	Playwright playwright;
	BrowserContext browserContext;
	*/
	Properties prop;
	
	private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
	private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
	private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
	private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
	
	public static Playwright getPlaywright() {
		return tlPlaywright.get();
	}
	
	public static Browser getBrowser() {
		return tlBrowser.get();
	}
	
	public static BrowserContext getBrowserContext() {
		return tlBrowserContext.get();
	}
	
	public static Page getPage() {
		return tlPage.get();
	}
	
	public Page initBrowser(Properties prop) {
		System.out.println("Browser name is : " + prop.getProperty("browsername").toLowerCase());
		tlPlaywright.set(Playwright.create());
		
		switch(prop.getProperty("browsername").toLowerCase())
		{
		case "chromium":
			tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
		    break;
		case "firefox":
			tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
		case "chrome":
			tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break; 
		default:
			System.out.println("Pass the right Browser name");
			break; 
			
		}
		
		tlBrowserContext.set(getBrowser().newContext());
		tlPage.set(getBrowserContext().newPage());
		getPage().navigate(prop.getProperty("applicationurl").trim());	
		return getPage();
			
	}
	
	public Properties initProperties()
	{
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(ip);
			
		}
		catch(Exception e){
			System.out.print(e.getMessage());
		}
		return prop;
	}
	
	public static String takeScreenShot() {
		
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + System.currentTimeMillis() + ".png";		

		
		//byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(targetFilePath))
		//		.setFullPage(true));
		
		byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(targetFilePath))
				.setFullPage(true));
		String base64TargetFilePath = Base64.getEncoder().encodeToString(buffer);

		return base64TargetFilePath;
		
	}
	
}
