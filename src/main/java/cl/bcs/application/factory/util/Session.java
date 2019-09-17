package cl.bcs.application.factory.util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import cl.bcs.application.driver.factory.FactoryWebDriver;
import cl.bcs.application.file.util.Resource;
import cl.bcs.application.spot.util.VariablesUtil;

/**
 * 
 * @author Narveider
 *
 */
public class Session {

	private String navegador;
	private ExtentReports extent;
	private ExtentTest logger;
	private static String folio;
	private static String abono;
	private static String cargo;
	private static String comprobante;	
	private static String movimientoEgreso;
	private static String movimientoIngreso;

	private static FactoryWebDriver configDriver;
	private static SpotExcel spotinfo;
	private static VariablesUtil variables;

	/**
	 * 
	 * @param builder
	 */
	public Session(SessionBuilder builder) {
		navegador = builder.navegador;
		extent = builder.extent;
		logger = builder.logger;

		configDriver = new FactoryWebDriver(navegador,
				Resource.getProperty("url.optimus"));
	}

	/**
	 * 
	 * @return
	 */
	public static FactoryWebDriver getConfigDriver() {
		return configDriver;
	}

	public FactoryWebDriver getConfig() {
		return configDriver;
	}

	/**
	 * 
	 * @return
	 */

	public static SpotExcel getSpot() {
		return spotinfo;
	}

	public static void setSpot(SpotExcel spot) {
		spotinfo = spot;
	}

	public ExtentReports getExtent() {
		return extent;
	}

	public void setExtent(ExtentReports extent) {
		this.extent = extent;
	}

	public static String getFolio() {
		return folio;
	}

	public static void setFolio(String folio) {
		Session.folio = folio;
	}

	public static VariablesUtil getVariables() {
		return variables;
	}

	public static void setVariables(VariablesUtil variablesUtil) {
		variables = variablesUtil;
	}

	public static String getAbono() {
		return abono;
	}

	public static void setAbono(String abono) {
		Session.abono = abono;
	}

	public static String getCargo() {
		return cargo;
	}

	public static void setCargo(String cargo) {
		Session.cargo = cargo;
	}

	public static String getComprobante() {
		return comprobante;
	}

	public static void setComprobante(String comprobante) {
		Session.comprobante = comprobante;
	}

	public static String getMovimientoEgreso() {
		return movimientoEgreso;
	}

	public static void setMovimientoEgreso(String movimientoEgreso) {
		Session.movimientoEgreso = movimientoEgreso;
	}

	public static String getMovimientoIngreso() {
		return movimientoIngreso;
	}

	public static void setMovimientoIngreso(String movimientoIngreso) {
		Session.movimientoIngreso = movimientoIngreso;
	}

	public ExtentTest getLogger() {
		return logger;
	}

	public void setLogger(ExtentTest logger) {
		this.logger = logger;
	}

	public void destroy() {
		if (configDriver != null) {
			configDriver.getWebDriver().quit();
		}
	}
}
