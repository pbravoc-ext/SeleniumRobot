package cl.bcs.spot;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesIngresoOperacionSpot;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.constantes.util.ConstantesSpotTags;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.application.spot.util.IngresoOperacionSpotUtil;
import cl.bcs.plataforma.CerrarVentana;

public class IngresoOperacionSpot extends IngresoOperacionSpotUtil {
	private static WebDriver webDriver = null;

	public IngresoOperacionSpot(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(IngresoOperacionSpot.class);

	public static boolean datosOperacion(Object dato, Session session) {
		
		SpotExcel datos = (SpotExcel) dato;
		
		String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";
		String operacion = datos.getOperacion();
		String instrumento = datos.getInstrumento();
		String monedaPrincipal = datos.getMontoPrincipal();
		String tcCierre = datos.getMontoSecundario();
		String paridadCierre = datos.getParidadCierre();
		try {

			String validacionTcCosto = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_COSTO)).getAttribute(ConstantesSpotTags.TAG_DISABLE);
			
			// Ingreso cliente
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO))
					.sendKeys(cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO))
					.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO_ARROW)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO_SELECT))
					.click();

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			session.logger.log(LogStatus.INFO, "Ingreso cliente", "Datos: " + cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Boton limpiar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_BTN_LIMPIAR)).click();

			// Seleccion moneda principal
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			List<WebElement> bcsComboboxBase = session.getConfigDriver()
					.findElements(By.id(ConstantesIngresoOperacionSpot.ID_BCSCOMBO_MONEDAPRINCIPAL));
			for (WebElement webElement : bcsComboboxBase) {
				WebElement inputEntregamos = webElement
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDAPRINCIPAL));
				inputEntregamos.clear();
				inputEntregamos.sendKeys(datos.getMonedaPrincipal());
			}
			session.logger.log(LogStatus.INFO, "Seleccion moneda principal",
					"Datos: " + datos.getMonedaPrincipal());
			LOGGER.info("Moneda Principal Seleccionada");

			// selecion moneda sceundaria
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			List<WebElement> bcsComboboxBase2 = session.getConfigDriver()
					.findElements(By.id(ConstantesIngresoOperacionSpot.ID_BCSCOMBO_MONEDASECUNDARIA));
			for (WebElement webElement2 : bcsComboboxBase2) {
				WebElement inputEntregamos2 = webElement2
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDASECUNDARIA));
				inputEntregamos2.clear();
				inputEntregamos2.sendKeys(datos.getMonedaSecundaria());
			}
			session.logger.log(LogStatus.INFO, "Seleccion moneda Secundaria",
					"Datos: " + datos.getMonedaSecundaria());
			LOGGER.info("Moneda Secundaria Seleccionada");

			// Valores punta compra/venta
			String iosPuntaCompra = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_PUNTA_COMPRA))
					.getAttribute(ConstantesSpotTags.TAG_TITLE);
			LOGGER.info("IOSPuntaCompra: " + iosPuntaCompra);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			String iosPuntaVenta = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_PUNTA_VENTA))
					.getAttribute(ConstantesSpotTags.TAG_TITLE);
			LOGGER.info("IOSPuntaVenta: " + iosPuntaVenta);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("PuntaCompra: " + session.getVariables().getPuntaCompra());
			LOGGER.info("PuntaVenta: " + session.getVariables().getPuntaVenta());

			// Ingreso operacion
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_OPERACION)).clear();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_OPERACION)).sendKeys(operacion);
			session.logger.log(LogStatus.INFO, "Ingreso operacion", "Datos: " + operacion);
			LOGGER.info("Operacion Seleccionada");
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Validacion valores punta compra/venta
			validacionValoresPunta(session.getVariables().getPuntaCompra(), session.getVariables().getPuntaVenta(),
					iosPuntaCompra, iosPuntaVenta, session);

			if (datos.getMonedaPrincipal().equals(ConstantesSpot.MONEDA_EUR)) {

				// Ingreso instrumento
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).clear();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO))
						.sendKeys(instrumento);
				session.logger.log(LogStatus.INFO, "Ingreso instrumento", "Datos: " + instrumento);
				LOGGER.info("Instrumento Seleccionado" + instrumento);
				UtilesSelenium.waitForLoad(session.getConfigDriver());

				// Ingreso monto moneda principal
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.sendKeys(ConstantesSpot.SUB_ZEROS + monedaPrincipal);
				session.logger.log(LogStatus.INFO, "Ingreso monto principal",
						"Datos: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);
				LOGGER.info("Ingreso monto principal: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				
				// Validacion campo T/C Costo
				validacionInputCosto(validacionTcCosto, session);

				// Ingreso monto paridad de cierre
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_PARIDAD_CIERRE))
						.sendKeys(ConstantesSpot.SUB_ZEROS + paridadCierre);
				session.logger.log(LogStatus.INFO, "Ingreso monto Paridad cierre",
						"Datos: " + ConstantesSpot.SUB_ZEROS + paridadCierre);
				LOGGER.info("Ingreso Paridad Cierre: " + ConstantesSpot.SUB_ZEROS + paridadCierre);

				// Rescatando datos
				String margen = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MARGEN))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String montoEquivalente = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONTO_EQUIVALENTE))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String montoFinal = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String valorParidadCierre = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_PARIDAD_CIERRE))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String valorParidadCosto = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_PARIDAD_COSTO))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);

				// Mostrando datos

				validacionMargen_A(montoFinal, margen, valorParidadCosto, valorParidadCierre, session);

				validacionMontoEquivalente_A(montoEquivalente, montoFinal, valorParidadCierre, session);

			} else {

				// Ingreso instrumento
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).clear();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO))
						.sendKeys(instrumento);
				session.logger.log(LogStatus.INFO, "Ingreso instrumento", "Datos: " + instrumento);
				LOGGER.info("Instrumento Seleccionado: " + instrumento);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				validacionInputCosto(validacionTcCosto, session);

				// Ingreso monto moneda principal
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.sendKeys(ConstantesSpot.SUB_ZEROS + monedaPrincipal + Keys.ENTER);
				session.logger.log(LogStatus.INFO, "Ingreso monto principal",
						"Datos: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);
				LOGGER.info("Ingreso monto principal: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);

				// Ingreso monto TC cierre
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_CIERRE))
						.sendKeys(ConstantesSpot.SUB_ZEROS + tcCierre + Keys.ENTER);
				session.logger.log(LogStatus.INFO, "Ingreso monto T/C cierre",
						"Datos: " + ConstantesSpot.SUB_ZEROS + tcCierre);
				LOGGER.info("Ingreso T/C Cierre: " + ConstantesSpot.SUB_ZEROS + tcCierre);

				// Rescatando datos
				String montoFinal = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String valorTcCierre = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_CIERRE))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String valotTcCosto = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_COSTO))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String montoEquivalente = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONTO_EQUIVALENTE))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				String margen = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_MARGEN))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);

				// Mostrando datos
				LOGGER.info("Monto: " + montoFinal);
				LOGGER.info("T/C Cierre: " + valorTcCierre);
				LOGGER.info("T/C Costo: " + valotTcCosto);
				LOGGER.info("Monto Equivalente: " + montoEquivalente);

				validacionMargen_NA(montoFinal, margen, valotTcCosto, valorTcCierre, session);

				validacionMontoEquivalente_NA(montoEquivalente, montoFinal, valorTcCierre, session);
			}

			/**
			 * 
			 * 6 OK 7 no arbitraje - OK 8 no arbitraje - margen=montoPpal*(tcCierre
			 * -tcCosto) OK 7 arbitraje - montoEquivalente=montoPpal*paridadCierre
			 * 
			 * 8 arbitraje - verificar Margen sea calculado monto ppal * (paridad
			 * cierre-paridadCosto)
			 * 
			 * 
			 * 
			 */

			UtilesExtentReport.captura("Ingresar operacion spot - Datos operacion - Spot", session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Ingresar operacion spot - Datos operacion - Spot", session);
			session.logger.log(LogStatus.ERROR, "Error: Ingresar operacion spot -Datos operacion",
					"Datos: " + e.getMessage());
			return false;
		}
	}

	public static boolean formadePago(Object dato, Session session) {
		SpotExcel datos = (SpotExcel) dato;
		
		try {
			/**
			 * Pagamos
			 */
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_SELECCIONAR_FORMA_DE_PAGO))
					.click();
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_PAGAMOS))
					.sendKeys(Keys.CLEAR, ConstantesSpot.SUB_ZEROS, Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			/**
			 * Recibimos
			 */
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_RECIBIMOS))
					.sendKeys(Keys.CLEAR, ConstantesSpot.SUB_ZEROS, Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesExtentReport.captura("Ingresar operacion spot - Limpieza forma de pago - Spot", session);

			String pago = "";
			switch (datos.getPortafolio()) {
			case "0":
				pago = ConstantesSpot.CUENTA_INVERSION;
				break;
			case "1":
				pago = ConstantesSpot.TRANSFERENCIA;
				break;

			default:
				break;
			}

			// Ingreso Recibimos

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			List<WebElement> bcsComboboxBase1 = session.getConfigDriver()
					.findElements(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BCSCOMBOBOX_INGRESORECIBIMOS));
			for (WebElement webElement : bcsComboboxBase1) {
				WebElement inputRecibimos = webElement
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_SELECCIONRECIBIMOS));
				inputRecibimos.sendKeys(pago + Keys.ENTER);
			}

			// Ingreso Entregamos

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			List<WebElement> bcsComboboxBase2 = session.getConfigDriver()
					.findElements(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BCSCOMBOBOX_INGRESOPAGAMOS));
			for (WebElement webElement : bcsComboboxBase2) {
				WebElement inputEntregamos = webElement
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_SELECCIONPAGAMOS));
				inputEntregamos.sendKeys(pago + Keys.ENTER);
			}
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Ingresar operacion spot - Forma de pago - Spot", session);

			
			// Rescatar Fechas
			String fechaEntregamos = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(
					"//*[@id='FORM_MesaSpot']/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[3]/table/tbody/tr/td[3]"))
					.getText();
			String fechaRecibimos = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(
					"//*[@id='FORM_MesaSpot']/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[4]/table/tbody/tr/td[3]"))
					.getText();
			session.setFechaDesde(fechaEntregamos);
			session.setFechaHasta(fechaRecibimos);
			comparar(fechaEntregamos, fechaRecibimos);
			return true;
			
		} catch (Exception e) {
			session.logger.log(LogStatus.ERROR, "Forma de pago", "Datos: " + e.getMessage());
			UtilesExtentReport.capturaError("Error : Ingresar operacion spot - Forma de pago - Spot ", session);
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public static boolean otros(Session session) {
		try {
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_SELECCIONAR_OTROS)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesExtentReport.captura("Ingresar operacion spot - Otros - Spot", session);

			String agenteSpot = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_AGENTE))
					.getAttribute(ConstantesSpotTags.TAG_VALUE);
			LOGGER.info("Agente Spot: " + agenteSpot);
			String tipoComprobanteSpot = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_TIPOCOMPROBANTE))
					.getAttribute(ConstantesSpotTags.TAG_VALUE);
			LOGGER.info("Comprobante Spot: " + tipoComprobanteSpot);
			if (ConstantesSpot.AGENTE.equals(agenteSpot)) {
				session.logger.log(LogStatus.INFO, "Validacion Existosa",
						"Agente concuerda con Agenten ingresado");
			} else {
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_AGENTE))
						.sendKeys(ConstantesSpot.AGENTE + Keys.ENTER);
				session.logger.log(LogStatus.WARNING, "Validacion Fallida",
						"Agente no concuerda, por lo que se ingresó el agente correcto");
			}
			if (ConstantesSpot.TIPOCOMPROBANTE.equals(tipoComprobanteSpot)) {
				session.logger.log(LogStatus.INFO, "Validacion Existosa",
						"Tipo de comprobante es Factura Electronica Exenta");
			} else {
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_TIPOCOMPROBANTE)).clear();
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_TIPOCOMPROBANTE))
						.sendKeys(ConstantesSpot.TIPOCOMPROBANTE + Keys.ENTER);
				session.logger.log(LogStatus.WARNING, "Validacion Fallida",
						"Tipo de Comprobante no es Factura Electronica Exenta, por lo que se ingresó el Tipo de Comprobante correcto");
			}

			UtilesExtentReport.captura("Ingresar operacion spot - Informacion lista para enviar - Otros - Spot", session);

			// Aceptar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_BTN_ACEPTAR)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			UtilesExtentReport.captura("Ingresar operacion spot - Folio Otros - Spot", session);
			// Generar Folio
			String informacion = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_LABEL_FOLIO))
					.getText();
			LOGGER.info("Dato Label: " + informacion);
			LOGGER.info("Folio: " + SpotUtiles.onlyNumbers(informacion));
			String rescatarFolioOp = SpotUtiles.onlyNumbers(informacion);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			session.setFolio(rescatarFolioOp);
			session.logger.log(LogStatus.INFO, "Folio ", "Datos: " + session.getFolio());
			LOGGER.info("FOLIO SESION: " + session.getFolio());
			// Botón Acpetar información..
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresoOperacionSpot.XPATH_BTN_ACEPTARINFO)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Ingresar operacion spot - Otros - Spot", session);
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			session.logger.log(LogStatus.ERROR, "Forma de pago", "Datos: " + e.getMessage());
			LOGGER.error(e.getStackTrace());
			UtilesExtentReport.capturaError("Error : Ingresar operacion spot - Otros - Spot ", session);
			return false;
		}

	}

}
