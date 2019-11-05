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

import cl.bcs.application.constantes.util.ConstantesCuentaInversion;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;


public class CuentaInversion1 {
	private WebDriver webDriver = null;

	public CuentaInversion1(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(CuentaInversion1.class);

	public static boolean cuentaInversionCliente(Object dato,Session session) {
		SpotExcel datos = (SpotExcel) dato;

		try {
			// Datos Generales
			String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";

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

			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesCuentaInversion.XPATH_BOTON_BUSCAR)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_TIPO_COMPROBANTE)).sendKeys(Keys.TAB);

			LOGGER.info(session.getComprobante());
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
//			String big = ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE;
//			String xpath = ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE_INPUT;
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.XPATH_INPUT_FOLIO_GRILLA))
					.sendKeys(ConstantesSpot.SUB_ZEROS + session.getComprobante(), Keys.ENTER);
//			UtilesSelenium.findElement(By.xpath(XPATH_INPUT_FOLIO_GRILLA)).sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			LOGGER.info("Folio Comprobante: " + session.getComprobante());

			String montoCargo = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.FULL_XPATH_CARGO))
					.getText();
			String montoAbono = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesCuentaInversion.FULL_XPATH_ABONO))
					.getText();
			if (datos.getOperacion().equalsIgnoreCase("COMPRA")) {
				if (validacionAbonoCargo(montoCargo)) {
					LOGGER.info("Validacion exitosa, se creo un cargo de " + montoCargo);
				} else {
					LOGGER.info("Validacion erronea, se debe crear un cargo");
				}
				if (validacionMontoAbonoCargo(montoCargo, SessionRV.getMontoTotalLocal())) {
					LOGGER.info("Validacion exitosa, cargo: " + montoCargo + " es igual a monto facturado: "
							+ SessionRV.getMontoTotalLocal());
				} else {
					LOGGER.info("Validacion erronea, cargo: " + montoCargo + " debe ser igual a monto facturado: "
							+ SessionRV.getMontoTotalLocal());
				}
			} else {
				if (validacionAbonoCargo(montoAbono)) {
					LOGGER.info("Validacion exitosa, se creo un abono de " + montoAbono);
				} else {
					LOGGER.info("Validacion erronea, se debe crear un abono");
				}
				if (validacionMontoAbonoCargo(montoAbono, SessionRV.getMontoTotalLocal())) {
					LOGGER.info("Validacion exitosa, abono: " + montoAbono + " es igual a monto facturado: "
							+ SessionRV.getMontoTotalLocal());
				} else {
					LOGGER.info("Validacion erronea, abono: " + montoAbono + " debe ser igual a monto facturado: "
							+ SessionRV.getMontoTotalLocal());
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
			UtilesExtentReport.capturaError("Error: Gestion cuenta inversion", session);
			session.logger.log(LogStatus.ERROR, "Error: Gestion cuenta inversion",
					"Datos: " + e.getMessage());
			return false;
		}
	}

	public static boolean validacionAbonoCargo(String valor) {
		BigDecimal newValor = new BigDecimal(formatoValidacionGrilla1(valor));
		BigDecimal zero = new BigDecimal("0");
		if (newValor.compareTo(zero) == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean validacionMontoAbonoCargo(String cargoAbono, String montoTotalLocal) {
		BigDecimal newValor = new BigDecimal(formatoValidacionGrilla1(cargoAbono));
		BigDecimal newMonto = new BigDecimal(formatoValidacionGrilla1(montoTotalLocal));
		if (newValor.compareTo(newMonto) == 0) {
			return true;
		} else {
			return false;
		}
	}

	protected static String formatoValidacionGrilla1(String monto) {
		monto = monto.replaceAll(" ", "");
		monto = monto.replaceAll("[a-zA-Z]", "");
		monto = monto.replace(".", "");
		monto = monto.replace(",", ".");
		return monto;
	}

	public static boolean validacionFechas(String fecha1, String fecha2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = sdf.parse(fecha1);
		Date date2 = sdf.parse(fecha2);
		if (date1.compareTo(date2) == 0) {
			return true;
		} else {
			return false;
		}
	}

}
