package cl.bcs.spot;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesConfirmacionOperacionSpot;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.plataforma.SeleccionMenu;

/**
 * 
 * @author rnunez_EXT
 *
 */

public class ConfirmacionOperacionesSpot {

	private static final Logger LOGGER = Log4jFactory.getLogger(ConfirmacionOperacionesSpot.class);

	public static boolean confirmarSpot(Object dato,Session session) {
		SpotExcel datos = (SpotExcel) dato;
		String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";

		try {
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE)).sendKeys(cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE))
					.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			// Buscar
			SeleccionMenu.seleccionarModuloConfirmacionOperacionesSpot(session);

			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_FOLIO_INPUT))
					.sendKeys(ConstantesSpot.SUB_ZEROS+session.getFolio() + Keys.ENTER);
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConfirmacionOperacionSpot.XPATHERE_THERE+session.getFolio()+Constantes.XPATHERE_OUT)).click();

			UtilesExtentReport.captura("Confirmacion operacion spot - Buscar folio - Spot", session);

			// Confirmar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_CONFIRMAR)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Aceptar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_ACEPTAR)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Aceptar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_INFO_ACEPTAR)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("Confirmacion operacion spot - Datos confirmados, enviados a facturacion");
			session.logger.log(LogStatus.INFO, "Confirmacion operacion spot","Datos confirmados, enviados a facturacion");
			UtilesExtentReport.captura("Confirmacion operacion spot - Datos confirmados, enviados a facturacion", session);

			LOGGER.info("Finalización confirmación operación spot");

			CerrarVentana.init(session);
			LOGGER.info("Confirmacion operacion spot - Datos confirmados, enviados a facturacion");
			session.logger.log(LogStatus.INFO, "Confirmacion operacion spot","Datos confirmados, enviados a facturacion");
			UtilesExtentReport.captura("Confirmacion operacion spot - Datos confirmados, enviados a facturacion", session);
			return true;

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Confirmacion operacion spot - Datos operacion - Spot", session);
			session.logger.log(LogStatus.ERROR, "Error: Confirmacion operacion spot -Datos operacion",
					"Datos: " + e.getMessage());
			return false;
		}
	}
}
