package cl.bcs.application.file.util;


import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.driver.factory.FactoryExtentReport;
import cl.bcs.application.factory.util.Session;
/**
 * 
 * @author Narveider
 *
 */
public class UtilesExtentReport {

	public static void captura(String descripcion, Session session) {
		String screenshotPath;
		try {
			screenshotPath = FactoryExtentReport.getScreenshot(session.getConfigDriver(),
					descripcion + "-" + DateUtil.fecha());
			
			session.logger.log(LogStatus.PASS, descripcion,
					session.logger.addScreenCapture(screenshotPath));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void capturaError(String descripcion,  Session session) {
		String screenshotPath;
		try {
			screenshotPath = FactoryExtentReport.getScreenshot(session.getConfigDriver(),
					descripcion + "-" + DateUtil.fecha());
			session.logger.log(LogStatus.ERROR, descripcion,
					session.logger.addScreenCapture(screenshotPath));
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
