package cl.bcs.spot;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesConfirmacionOperacionSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

/**
 * 
 * @author rnunez_EXT
 *
 */

public class ConfirmacionOperacionesSpot {

	private static final Logger LOGGER = Log4jFactory.getLogger(ConfirmacionOperacionesSpot.class);

	public static boolean confirmarSpot(SpotExcel datos) {

		String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";

		try {
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE)).sendKeys(cliente);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE))
					.sendKeys(Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			// Buscar
			SeleccionarSpot.seleccionarConfirmacionOperaciones();

			UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_FOLIO_INPUT))
					.sendKeys(Session.getFolio() + Keys.ENTER);
			UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_THERE)).click();

			UtilesExtentReport.captura("Confirmacion operacion spot - Buscar folio - Spot");

			// Confirmar
			UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_CONFIRMAR)).click();
			Session.getConfigDriver().waitForLoad();

			// Aceptar
			UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_ACEPTAR)).click();
			Session.getConfigDriver().waitForLoad();

			// Aceptar
			UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_INFO_ACEPTAR)).click();
			Session.getConfigDriver().waitForLoad();
			LOGGER.info("Confirmacion operacion spot - Datos confirmados, enviados a facturacion");
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Confirmacion operacion spot","Datos confirmados, enviados a facturacion");
			UtilesExtentReport.captura("Confirmacion operacion spot - Datos confirmados, enviados a facturacion");

			LOGGER.info("Finalización confirmación operación spot");

			CerrarVentana.init();
			LOGGER.info("Confirmacion operacion spot - Datos confirmados, enviados a facturacion");
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Confirmacion operacion spot","Datos confirmados, enviados a facturacion");
			UtilesExtentReport.captura("Confirmacion operacion spot - Datos confirmados, enviados a facturacion");
			return true;

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Confirmacion operacion spot - Datos operacion - Spot");
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Error: Confirmacion operacion spot -Datos operacion",
					"Datos: " + e.getMessage());
			return false;
		}
	}
}
