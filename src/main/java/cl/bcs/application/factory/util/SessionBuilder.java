package cl.bcs.application.factory.util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * 
 * @author Narveider
 *
 */
public class SessionBuilder {
	protected String navegador;
	protected ExtentReports extent;
	protected ExtentTest logger;
	protected String folio;
	protected String abono;
	protected String cargo;
	protected String comprobante;	
	protected String movimientoEgreso;
	protected String movimientoIngreso;
	/**
	 * 
	 * @param browser
	 * @param env
	 * @param usuario
	 */
	public SessionBuilder(String browser) {
		navegador = browser;
	}

	/**
	 * 
	 * @return
	 */
	public Session build() {
		return new Session(this);
	}

}
