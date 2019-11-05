package cl.bcs.cuenta.inversion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesCuentaInversion;
import cl.bcs.application.constantes.util.ConstantesRV;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class CuentaInversion {
	private WebDriver webDriver = null;

	public CuentaInversion(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(CuentaInversion.class);

	public static boolean cuentaInversionCliente(Object dato, Session session) {
		try {
			RVExcel datos = (RVExcel) dato;

			// Datos Generales
			String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

			// Ir a pestañana Certificar / Anular Movimientos
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_TAB_CERT_ANULAR)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Buscar Cliente
			WebElement weCliente = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_CLIENTE_INVERSION));
			weCliente.clear();
			weCliente.sendKeys(cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_CLIENTE_INVERSION))
					.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_BOTON_BUSCAR)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_TIPO_COMPROBANTE)).sendKeys(Keys.TAB);

			LOGGER.info(session.getComprobante());
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
//			String big = ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE;
//			String xpath = ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE_INPUT;
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_INPUT_FOLIO_GRILLA))
					.sendKeys(Constantes.SUB_ZEROS + session.getComprobante(), Keys.ENTER);
//			UtilesSelenium.findElement(By.xpath(XPATH_INPUT_FOLIO_GRILLA)).sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			LOGGER.info("Folio Comprobante: " + session.getComprobante());

			String montoCargo = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.FULL_XPATH_CARGO))
					.getText();
			String montoAbono = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.FULL_XPATH_ABONO))
					.getText();
//			if (datos.getTipoInstrumento().equalsIgnoreCase(ConstantesRV.INSTRUMENTO_ACX)) {
//				valorComparar = SessionRV.getMontoTotal();
//			} else {
//				valorComparar = SessionRV.getMontoTotalLocal();
//			}

			if (datos.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
				if (validacionAbonoCargo(montoCargo)) {
					LOGGER.info("Validacion exitosa, se creo un cargo de " + montoCargo);
					session.logger.log(LogStatus.PASS, "Validacion Cargo",
							"Se creó un cargo de " + montoCargo);
					validacionMontoAbonoCargo(montoCargo, SessionRV.getMontoTotal(), ConstantesRV.CARGO, session);
				} else {
					LOGGER.info("Validacion erronea, se debe crear un cargo");
					session.logger.log(LogStatus.WARNING, "Validacion Cargo",
							"Se debe crear un cargo");
				}
			} else {
				if (validacionAbonoCargo(montoAbono)) {
					LOGGER.info("Validacion exitosa, se creo un abono de " + montoAbono);
					session.logger.log(LogStatus.PASS, "Validacion Abono",
							"Se creó un abono de " + montoAbono);

					validacionMontoAbonoCargo(montoAbono, SessionRV.getMontoTotal(), ConstantesRV.ABONO, session);
				} else {
					LOGGER.info("Validacion erronea, se debe crear un abono");
					session.logger.log(LogStatus.WARNING, "Validacion Abono",
							"Se debe crear un abono");
				}
			}

			session.logger.log(LogStatus.INFO, "Ingreso Comprobante", session.getComprobante());

//			WebElement pre = UtilesSelenium.findInputNumber(big, xpath);
//			pre.sendKeys(Session.getComprobante());
//			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE))
//					.sendKeys(ConstantesSpot.SUB_ZEROS + Session.getComprobante());
//			Session.getConfigDriver().waitForLoad();
//			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE))
//					.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Folio comprobante " + session.getComprobante(), session);

			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_ESTADO)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Concepto - " + datos.getOperacion(), session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Cuenta Inversión ==");
			CerrarVentana.init(session);
			return true;

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error-Gestion cuenta inversion", session);
			session.logger.log(LogStatus.ERROR, "Error: Gestion cuenta inversion",
					"Datos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Formatea el monto ingresado para usarlo como BigDecimal
	 * 
	 * @param monto
	 * @return monto en formato BigDecimal
	 */
	protected static String formatoBigDecimal(String monto) {
		monto = monto.replace(" ", "");
		monto = monto.replaceAll("[a-zA-Z]", "");
		monto = monto.replace(".", "");
		monto = monto.replace(",", ".");
		return monto;
	}

	/**
	 * Valida que el cargo o abono sea distinto de cero
	 * 
	 * @param valor
	 * @return Devuelve un 'true' o un 'false'
	 */
	protected static boolean validacionAbonoCargo(String valor) {
		BigDecimal newValor = new BigDecimal(formatoBigDecimal(valor));
		BigDecimal zero = new BigDecimal("0");
		if (newValor.compareTo(zero) == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Valida igualdad de 2 fechas
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return Devuelve un 'true' o un 'false' dependiendo si se cumple la igualdad
	 * @throws ParseException
	 */
	protected static boolean validacionFechas(String fecha1, String fecha2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = sdf.parse(fecha1);
		Date date2 = sdf.parse(fecha2);
		if (date1.compareTo(date2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida que el mopnto del cargo o abono en cuenta inversion es igual al monto
	 * total al ingresar una orden
	 * 
	 * @param montoCargoAbono
	 * @param montoTotalLocal
	 * @param datoValidar
	 */
	protected static void validacionMontoAbonoCargo(String montoCargoAbono, String montoTotalLocal,
			String datoValidar, Session session ) {
		BigDecimal newValor = new BigDecimal(formatoBigDecimal(montoCargoAbono));
		BigDecimal newMonto = new BigDecimal(formatoBigDecimal(montoTotalLocal));
		if (newValor.compareTo(newMonto) == 0) {
			LOGGER.info("Validacion exitosa, " + datoValidar + ": " + montoCargoAbono + " es igual a monto facturado: "
					+ montoTotalLocal);
			session.logger.log(LogStatus.PASS, "Validacion " + datoValidar + " - Monto Facturado",
					"El " + datoValidar + " de " + montoCargoAbono + " es igual al monto facturado de "
							+ montoTotalLocal);
		} else {
			LOGGER.info("Validacion erronea, " + datoValidar + ": " + montoCargoAbono
					+ " debe ser igual a monto facturado: " + montoTotalLocal);
			session.logger.log(LogStatus.WARNING, "Validacion " + datoValidar + " - Monto Facturado",
					"El " + datoValidar + " de " + montoCargoAbono + " debe ser igual al monto facturado de "
							+ montoTotalLocal);
		}
	}

}