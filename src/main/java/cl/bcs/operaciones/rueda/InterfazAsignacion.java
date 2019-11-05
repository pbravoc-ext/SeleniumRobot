package cl.bcs.operaciones.rueda;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesInterfazAsignacion;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class InterfazAsignacion {
	private static final Logger LOGGER = Log4jFactory.getLogger(InterfazAsignacion.class);

	public static boolean transacciones(Object dato, Session session) {
		try {
			RVExcel datos = (RVExcel) dato;
			String instrumento = datos.getInstrumento();

			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// BTN Agregar Transaccion
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_AGREGAR_TRANSACCION)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Instrumento
			WebElement weInstrumento = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_INSTRUMENTO_INPUT));
			weInstrumento.sendKeys(instrumento);
			weInstrumento.sendKeys(Keys.ENTER);
			LOGGER.info("Instrumento: " + instrumento);
			session.logger.log(LogStatus.INFO, "Instrumento Ingresado", instrumento);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// BTN Buscar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_BUSCAR_TRANSACCION)).click();

			// Ingresar folio aleatorio
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			String folioAleatorio = session.getFolioAleatorio();
			LOGGER.info("Folio Aleatorio: " + folioAleatorio);
			session.logger.log(LogStatus.INFO, "Folio Aleatorio Ingresado", folioAleatorio);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Ingresar folio
			WebElement weFolio = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.FULL_XPATH_FOLIO_INPUT_TRANSACCION));
			weFolio.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weFolio.sendKeys(folioAleatorio);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weFolio.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Click en la grilla
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.FULL_XPATH_FOLIO_GRILLA)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// btn
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_SELECTOR_TRANSACCION)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("OK");
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}

	}

	public static boolean ordenes(Object dato, Session session) {
		try {
			RVExcel datos = (RVExcel) dato;

			String instrumento = datos.getInstrumento();

			// ordenes
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_TAB_ORDENES)).click();

			// btn agregar
			WebElement btn = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_AGREGAR_ORDENES));
			btn.click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// instrumento
			WebElement weInstrumento = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_INSTRUMENTO_INPUT_ORDENES));
			weInstrumento.sendKeys(instrumento);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weInstrumento.sendKeys(Keys.ENTER);
			LOGGER.info("Instrumento: " + instrumento);
			session.logger.log(LogStatus.INFO, "Instrumento Ingresado", instrumento);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// btnBuscar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_BUSCAR_ORDENES)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// folio click
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATHERE_FOLIO_ORDENES
					+ session.getFolio() + Constantes.XPATHERE_OUT)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// btn aceptar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_ACEPTAR_ORDENES)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// btn conologica
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_CRONOLOGICA)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Rescatar Monto Asignacion
			String montoAsignacion = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_MONTO_ASIGNACION_GRILLA)).getText();
			LOGGER.info("Monto Asignacion: " + montoAsignacion);
			session.logger.log(LogStatus.INFO, "Monto Asignacion Rescatado", montoAsignacion);
			session.setMontoAsignacion(montoAsignacion);

			// btn aceptar final
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_ACEPTAR_FINAL)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("OK");
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public static boolean interfazAsignacion(RVExcel usu, Session session) {
		try {
			String ordeningresada = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.FULL_XPATH_LABEL))
					.getText();
			session.setFolioAsignacion(SpotUtiles.onlyNumbers(ordeningresada));
			LOGGER.info("Folio Asignacion: " + SpotUtiles.onlyNumbers(ordeningresada));
			session.logger.log(LogStatus.INFO, "Folio Asignacion Rescatado",
					SpotUtiles.onlyNumbers(ordeningresada));

			// btn aceptar final
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesInterfazAsignacion.XPATH_BTN_ACEPTAR_INFO)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Interfaz Asignación ==");
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

}
