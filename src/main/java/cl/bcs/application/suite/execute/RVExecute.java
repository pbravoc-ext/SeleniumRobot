package cl.bcs.application.suite.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.driver.factory.FactoryExcel;
import cl.bcs.application.driver.factory.FactoryExtentReport;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
import cl.bcs.application.file.util.DateUtil;
import cl.bcs.application.file.util.ExtentReportUtiles;
import cl.bcs.application.suite.RV;

public class RVExecute {
	List<RVExcel> rve = null;

	@SuppressWarnings({ "unchecked" })

	@BeforeSuite
	public void startReport() {
		DateUtil.fecha();
		FactoryExtentReport.configuracionInicialER("RV");
		FactoryExcel fe = new FactoryExcel();
		rve = (List<RVExcel>) fe.getReadExcel("rv");
		if (rve.isEmpty() || rve.size() == 0) {
			System.out.println("Datos Vacios");
		}
	}

	@Test(priority = 1)
	public void variacion1() {
		ExtentTest test = ExtentTestManager.startTest(rve.get(0).getVariacion());
		Session session = new Session(0,test);
		System.out.println(session.getEstadoFlujo());
		Assert.assertTrue(RV.suiteRV(rve.get(0), session));
		session.logger.log(LogStatus.PASS, rve.get(0).getVariacion() + " Completado", "");
	}
//
//		@Test(priority = 2)
//		public void variacion2() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(1).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(1), session));
//			session.logger.log(LogStatus.PASS, rve.get(1).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 3)
//		public void variacion3() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(2).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(2), session));
//			session.logger.log(LogStatus.PASS, rve.get(2).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 4)
//		public void variacion4() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(3).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(3), session));
//			session.logger.log(LogStatus.PASS, rve.get(3).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 5)
//		public void variacion5() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(4).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(4), session));
//			session.logger.log(LogStatus.PASS, rve.get(4).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 6)
//		public void variacion6() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(6).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(6), session));
//			session.logger.log(LogStatus.PASS, rve.get(6).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 7)
//		public void variacion7() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(7).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(7), session));
//			session.logger.log(LogStatus.PASS, rve.get(7).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 8)
//		public void variacion8() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(8).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(8), session));
//			session.logger.log(LogStatus.PASS, rve.get(8).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 9)
//		public void variacion9() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(9).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(9), session));
//			session.logger.log(LogStatus.PASS, rve.get(9).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 10)
//		public void variacion10() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(10).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(10), session));
//			session.logger.log(LogStatus.PASS, rve.get(10).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 11)
//		public void variacion11() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(11).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(11), session));
//			session.logger.log(LogStatus.PASS, rve.get(11).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 12)
//		public void variacion12() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(12).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(12), session));
//			session.logger.log(LogStatus.PASS, rve.get(12).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 13)
//		public void variacion13() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(13).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(13), session));
//			session.logger.log(LogStatus.PASS, rve.get(13).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 14)
//		public void variacion14() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(14).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(14), session));
//			session.logger.log(LogStatus.PASS, rve.get(14).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 15)
//		public void variacion15() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(15).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(15), session));
//			session.logger.log(LogStatus.PASS, rve.get(15).getVariacion() + " Completado", "");
//		}
//
//		@Test(priority = 16)
//		public void variacion16() {
//			Session session = new Session();
//			SessionRV.setEstadoFlujo(0);
//			session.logger = session.extent.startTest(rve.get(16).getVariacion());
//			Assert.assertTrue(RV.suiteRV(rve.get(17), session));
//			session.logger.log(LogStatus.PASS, rve.get(16).getVariacion() + " Completado", "");
//		}

//		  @AfterMethod
//		    public void afterEachTest(ITestResult result) {
//			  if (result.getStatus() == ITestResult.FAILURE) {
//		            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
//		        } else if (result.getStatus() == ITestResult.SKIP) {
//		            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Saltado " + result.getThrowable());
//		        } else {
//		            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Pasado");
//		        }
//		        
//			  ExtentReportUtiles.extentReport.endTest(ExtentTestManager.getTest());        
//			  ExtentReportUtiles.extentReport.flush();
//		    }

//		@AfterMethod
//		public void getResult(ITestResult result) throws Exception {
////			FactoryExtentReport.configuracionFinalER(result );
//		}
//
//		@AfterTest
//		public void endReport(Session session) {
//			session.getConfigDriver().extent.flush();
//			session.getConfigDriver().extent.close();
//			session.getConfigDriver().getWebDriver().quit();
//
//		}

}

class ExtentTestManager { // new
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void endTest() {
		ExtentReportUtiles.extentReport
				.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));

	}

	public static synchronized ExtentTest startTest(String testName) {
		return startTest(testName, "");
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = ExtentReportUtiles.extentReport.startTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

		return test;
	}
}
