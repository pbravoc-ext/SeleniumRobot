package cl.bcs.cuenta.inversion;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesCuentaInversion;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.application.spot.util.SelecionarCuentaInversionUtil;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.spot.SeleccionarSpot;

public class SelecionarCuentaInversion extends SelecionarCuentaInversionUtil {
	private WebDriver webDriver = null;

	public SelecionarCuentaInversion(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SelecionarCuentaInversion.class);

	public static boolean cuentaInversionCliente(SpotExcel datos) {
		try {

			UtilesExtentReport.captura("Ingreso a cuenta inversion");
			// Datos Generales
			String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";

			String montoEqui = Session.getMontoEq();
			String cargoCI = formatoBigDeciaml(datos.getMontoPrincipal());

			// Ir a pestañana Certificar / Anular Movimientos
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_TAB_CERT_ANULAR)).click();
			Session.getConfigDriver().waitForLoad();

			// Buscar Cliente
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_CLIENTE_INVERSION)).clear();
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_CLIENTE_INVERSION))
					.sendKeys(cliente + Keys.ENTER);
			Session.getConfigDriver().waitForLoad();

			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_BOTON_BUSCAR)).click();
			Session.getConfigDriver().waitForLoad();
			if (datos.getInstrumento().equals("ARBITRAJE INTERBANCARIO")) {

				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_TIPO_COMPROBANTE))
						.sendKeys(Keys.TAB);
				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE))
						.sendKeys(ConstantesSpot.SUB_ZEROS + Session.getComprobante() + Keys.ENTER);
				Session.getConfigDriver().waitForLoad();
				UtilesExtentReport.captura("Folio comprobante compra: " + Session.getComprobante());

				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_ESTADO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesExtentReport.captura("Concepto: " + datos.getOperacion());
				Session.getConfigDriver().waitForLoad();
				
				UtilesSelenium.findElement(By.xpath("//*[@id='frmCtaCliente_gridMovimientosCuentaInversion']/span/div[2]/div[3]/div/table/thead/tr[2]/th[2]/span/span/span[1]/input")).sendKeys(Keys.LEFT_SHIFT,Keys.TAB);

				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_TIPO_COMPROBANTE))
						.sendKeys(Keys.TAB);
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_BTN_LIMPIAR_FOLIO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE))
						.sendKeys(ConstantesSpot.SUB_ZEROS + Session.getComprobanteVenta() + Keys.ENTER);
				Session.getConfigDriver().waitForLoad();
				UtilesExtentReport.captura("Folio comprobante venta: " + Session.getComprobanteVenta());

				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_ESTADO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesExtentReport.captura("Concepto: " + datos.getOperacion());
				Session.getConfigDriver().waitForLoad();

			} else {
				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_TIPO_COMPROBANTE))
						.sendKeys(Keys.TAB);
				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE))
						.sendKeys(ConstantesSpot.SUB_ZEROS + Session.getComprobante() + Keys.ENTER);
				Session.getConfigDriver().waitForLoad();
				UtilesExtentReport.captura("Folio comprobante: " + Session.getComprobante());

				UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_ESTADO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesExtentReport.captura("Concepto: " + datos.getOperacion());
				Session.getConfigDriver().waitForLoad();
			}

			LOGGER.info("CUENTA INVERSION COMPLETADA");
			CerrarVentana.init();
			return true;

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Gestion cuenta inversion - Spot");
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Error: Gestion cuenta inversion",
					"Datos: " + e.getMessage());
			return false;
		}
	}

}
