package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import playwrightfactory.PlayWrightFactory;

public class ExtentReportListener implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;
	String pathofExtentReport;

	public void onStart(ITestContext testContext) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt = new Date();
		String currentdatetimestamp = df.format(dt);

		// String timeStamp =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new
		// Date());
		repName = "Test-Report" + currentdatetimestamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Path to report

		sparkReporter.config().setDocumentTitle("Automation Report"); // Title of report
		sparkReporter.config().setReportName("Automation Testing"); // Name of report
		sparkReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Practice Automation");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("Operating System", testContext.getCurrentXmlTest().getParameter("os"));
		extent.setSystemInfo("Browser", testContext.getCurrentXmlTest().getParameter("browser"));

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Test Groups Included", includedGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + " got sucessully executed");

	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = PlayWrightFactory.takeScreenShot();
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());

	}

	public void onFinish(ITestContext testContext) {
		extent.flush();

	    pathofExtentReport = System.getProperty("user.dir") + ".\\reports\\" + repName;
	    System.setProperty("extentReportName ", repName);
		File extentReport = new File(pathofExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
