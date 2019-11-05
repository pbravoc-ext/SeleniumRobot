package cl.bcs.operaciones.rueda;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesNegociacionOrdenes;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class NegociacionOrdenes {
	private static final Logger LOGGER = Log4jFactory.getLogger(NegociacionOrdenes.class);

	public static boolean ordenesPorNegociar(Object dato, Session session) {
		try {
			RVExcel datos = (RVExcel) dato;
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			UtilesExtentReport.captura("Negociación Ordenes - Operaciones de Rueda", session);

			String instrumento = datos.getInstrumento();
//			String tipoInstrumento = datos.getTipoInstrumento();
//			String mercado = datos.getMercado();
//			String operacion = datos.getOperacion();
			
			
//			// mercado
//			WebElement weMercado = UtilesSelenium
//					.findElement(By.xpath(ConstantesNegociacionOrdenes.XPATH_MERCADO_INPUT));
//			weMercado.clear();
//			weMercado.sendKeys(mercado);
//			weMercado.sendKeys(Keys.ENTER);
//			LOGGER.info("Mercado: " + mercado);
//			session.logger.log(LogStatus.INFO, "Mercado Ingresado", mercado);
//			
//
//			// tipo instrumento
//			WebElement weTipoInstrumento = UtilesSelenium
//					.findElement(By.xpath(ConstantesNegociacionOrdenes.XPATH_TIPO_INSTRUMENTO_INPUT));
//			weTipoInstrumento.clear();
//			weTipoInstrumento.sendKeys(tipoInstrumento);
//			weTipoInstrumento.sendKeys(Keys.ENTER);
//			LOGGER.info("Tipo Instrumento: " + tipoInstrumento);
//			session.logger.log(LogStatus.INFO, "Tipo Instrumento Ingresado", tipoInstrumento);
//			
//
//			// operacion
//			WebElement weOperacion = UtilesSelenium
//					.findElement(By.xpath(ConstantesNegociacionOrdenes.XPATH_OPERACION_INPUT));
//			weOperacion.clear();
//			weOperacion.sendKeys(operacion);
//			weOperacion.sendKeys(Keys.ENTER);
//			LOGGER.info("Operacion: " + operacion);
//			session.logger.log(LogStatus.INFO, "Operacion Ingresada", operacion);
			

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			// instrumento
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesNegociacionOrdenes.XPATH_INSTRUMENTO_INPUT))
					.sendKeys(instrumento);
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesNegociacionOrdenes.XPATH_INSTRUMENTO_INPUT))
					.sendKeys(Keys.ENTER);
			LOGGER.info("Instrumento: " + instrumento);
			session.logger.log(LogStatus.INFO, "Instrumento Ingresado", instrumento);


			UtilesSelenium.waitForLoad(session.getConfigDriver());




//			// bolsa de comercio de santiago en bolsa
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			String bolsa = "BOLSA DE COMERCIO DE SANTIAGO";
//			WebElement bol = UtilesSelenium
//					.findElement(By.xpath("//*[@id='OrdenesPorNegociar.Bolsa']/span/span/span/input"));
//			bol.clear();
//			bol.sendKeys(bolsa);

			// Folio
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			String folio = SessionRV.getFolio();
//			LOGGER.info(folio);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			WebElement weFolio = UtilesSelenium.findInputNumber(ConstantesNegociacionOrdenes.XPATH_FOLIO_INPUT_1,
					ConstantesNegociacionOrdenes.XPATH_FOLIO_INPUT_2, session.getConfigDriver());
			weFolio.sendKeys(folio);
			weFolio.sendKeys(Keys.ENTER);
			LOGGER.info("Folio: " + folio);
			session.logger.log(LogStatus.INFO, "Folio Ingresado", folio);

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			
			// click en grilla
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesNegociacionOrdenes.XPATH_TABLA_GRILLA)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			
			// BTN oferta manual
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesNegociacionOrdenes.XPATH_BTN_OFERTA_MANUAL)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			
			// BTN aceptar msj informacion
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesNegociacionOrdenes.XPATH_BTN_ACEPTAR_INFO)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Negociación Ordenes ==");
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

}
