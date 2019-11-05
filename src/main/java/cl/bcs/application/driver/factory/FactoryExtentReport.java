package cl.bcs.application.driver.factory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.DateUtil;
import cl.bcs.application.suite.execute.ExtentReportUtiles;

public class FactoryExtentReport {
	
	public static void configuracionInicialER( ExtentReports extentReport) {
		extentReport = new ExtentReports(
				System.getProperty("user.dir") + "/test-output/OptimusER-"+DateUtil.fecha()+".html", true);
		extentReport.addSystemInfo("Encoding", "UTF-8");
		extentReport.addSystemInfo("Host Name", "Automatización OPTIMUS")
				.addSystemInfo("Environment", "http://bolsa.optimuscb.cl:9045").addSystemInfo("User Name", "Narveider");
		extentReport.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
	}

	public static void configuracionFinalER(ITestResult result,  Session session) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			session.logger.log(LogStatus.FAIL, "Caso fallado: " + result.getName());
			session.logger.log(LogStatus.FAIL, "Caso fallado: " + result.getThrowable());
			String screenshotPath = getScreenshot(session.getConfigDriver(),
					result.getName());
			session.logger.log(LogStatus.FAIL,
					session.logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			session.logger.log(LogStatus.SKIP, "Test saltado: " + result.getName());
		}
		ExtentReportUtiles.extentReport.endTest(session.logger);
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/testsScreenshots/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}
