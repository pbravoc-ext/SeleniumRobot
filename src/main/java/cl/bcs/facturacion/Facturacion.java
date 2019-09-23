package cl.bcs.facturacion;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesFacturacion;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.spot.SeleccionarSpot;

public class Facturacion {
	private static WebDriver webDriver = null;

	public Facturacion(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SeleccionarSpot.class);

	public static boolean gestionFacturacion(SpotExcel datos) {
		String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";

		// Datos Movimientos a Facturar

		try {
			// Movimientos a Facturar
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV)).sendKeys(cliente);
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV)).sendKeys(Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARMOV)).click();
			Session.getConfigDriver().waitForLoad();

			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_FOLIOINPUT))
					.sendKeys(ConstantesSpot.SUB_ZEROS + Session.getFolio(), Keys.ENTER);
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATHERE)).click();

			// Generar
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV)).click();
			Session.getConfigDriver().waitForLoad();

			// Confirmar
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV)).click();
			Session.getConfigDriver().waitForLoad();

			// Rescatar Datos
			String movimientoEgreso = null;
			String movimientoIngreso = null;
			String abono = UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_LABEL1)).getText();
			String cargo = UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_LABEL2)).getText();
			String comprobante = UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_LABEL3)).getText();
			String abonoOp = SpotUtiles.folio(abono);
			String cargoOp = SpotUtiles.folio(cargo);
			String comprobanteOp = SpotUtiles.folio(comprobante);
			LOGGER.info("Abono: " + abonoOp);
			LOGGER.info("Cargo: " + cargoOp);
			LOGGER.info("Comprobante: " + comprobanteOp);
			Session.setAbono(abonoOp);
			Session.setCargo(cargoOp);
			Session.setComprobante(comprobanteOp);
			Session.setMovimientoEgreso(movimientoEgreso);
			Session.setMovimientoIngreso(movimientoIngreso);
			Session.getConfigDriver().waitForLoad();

			// Aceptar
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO)).click();
			Session.getConfigDriver().waitForLoad();

			// Comprobantes de Facturación
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_TABCOMPROBANTEFACTURACION)).click();
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_CLIENTEFAC)).sendKeys(cliente, Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARFAC)).click();
			Session.getConfigDriver().waitForLoad();

			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_SECUENCIA))
					.sendKeys(ConstantesSpot.SUB_ZEROS + comprobanteOp + Keys.ENTER);
			Session.getConfigDriver().waitForLoad();

			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_COMPARARFOLIOFAC + comprobanteOp
					+ ConstantesFacturacion.XPATH_COMPARARFOLIOFA2C)).click();
			Session.getConfigDriver().waitForLoad();

//			//Enviar a DTE.
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_ENVIARFAC)).click();
			Session.getConfigDriver().waitForLoad();

			// Confirmar
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARFAC)).click();
			Session.getConfigDriver().waitForLoad(6000);

			// Aceptar
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARFAC)).click();
			Session.getConfigDriver().waitForLoad();
			
			Session.getConfigDriver().waitForLoad();
			LOGGER.info("Gestion de facturacion - Datos enviados a DTE");
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Gestion de facturacion","Datos enviados a DTE");
			UtilesExtentReport.captura("Gestion de facturacion - Datos enviados a DTE");
			CerrarVentana.init();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Gestion de Facturacion - Datos facturacion - Spot");
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Error: Gestion de Facturacion - Datos facturacion - Spot",
					"Datos: " + e.getMessage());
			UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_ERROR)).click();
			CerrarVentana.init();
			SeleccionarSpot.seleccionarMenuFacturacion();
			return false;
		}
	}

}
