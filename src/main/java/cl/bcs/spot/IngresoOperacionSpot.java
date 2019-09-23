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

	public static boolean datosOperacion(SpotExcel datos) {
		String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";
		String operacion = datos.getOperacion();
		String instrumento = datos.getInstrumento();
		String monedaPrincipal = datos.getMontoPrincipal();
		String tcCierre = datos.getMontoSecundario();
		String paridadCierre = datos.getParidadCierre();
		try {
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO))
					.sendKeys(cliente);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO))
					.sendKeys(Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO_ARROW)).click();
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO_SELECT))
					.click();
			Session.getConfigDriver().waitForLoad();
			Session.getConfigDriver().waitForLoad(6000);
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso cliente", "Datos: " + cliente);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BTN_LIMPIAR)).click();

			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase = Session.getConfigDriver().getWebDriver().findElements(By.id(ConstantesIngresoOperacionSpot.ID_BCSCOMBO_MONEDAPRINCIPAL));
			for (WebElement webElement : bcsComboboxBase) {
				WebElement inputEntregamos = webElement.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDAPRINCIPAL));
				inputEntregamos.clear();
				inputEntregamos.sendKeys(datos.getMonedaPrincipal());			
			}
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Seleccion moneda principal",
					"Datos: " + datos.getMonedaPrincipal());
			LOGGER.info("Moneda Principal Seleccionada");

			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase2 = Session.getConfigDriver().getWebDriver()
					.findElements(By.id(ConstantesIngresoOperacionSpot.ID_BCSCOMBO_MONEDASECUNDARIA));
			for (WebElement webElement2 : bcsComboboxBase2) {
				WebElement inputEntregamos2 = webElement2
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDASECUNDARIA));
				inputEntregamos2.clear();
				inputEntregamos2.sendKeys(datos.getMonedaSecundaria());
			}
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Seleccion moneda Secundaria",
					"Datos: " + datos.getMonedaSecundaria());
			LOGGER.info("Moneda Secundaria Seleccionada");

			String iosPuntaCompra = UtilesSelenium
					.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PUNTA_COMPRA))
					.getAttribute(ConstantesSpotTags.TAG_TITLE);
			LOGGER.info("IOSPuntaCompra: " + iosPuntaCompra);
			Session.getConfigDriver().waitForLoad();
			String iosPuntaVenta = UtilesSelenium
					.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PUNTA_VENTA))
					.getAttribute(ConstantesSpotTags.TAG_TITLE);
			LOGGER.info("IOSPuntaVenta: " + iosPuntaVenta);
			Session.getConfigDriver().waitForLoad();
			LOGGER.info("PuntaCompra: " + Session.getVariables().getPuntaCompra());
			LOGGER.info("PuntaVenta: " + Session.getVariables().getPuntaVenta());
			validacionValoresPunta(Session.getVariables().getPuntaCompra(), Session.getVariables().getPuntaVenta(),
					iosPuntaCompra, iosPuntaVenta);

			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_OPERACION)).clear();
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_OPERACION)).sendKeys(operacion);
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso operacion", "Datos: " + operacion);
			LOGGER.info("Operacion Seleccionada");
			Session.getConfigDriver().waitForLoad();

			if (datos.getMonedaPrincipal().equals(ConstantesSpot.MONEDA_EUR)) {
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).clear();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO))
						.sendKeys(instrumento);
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso instrumento", "Datos: " + instrumento);
				LOGGER.info("Instrumento Seleccionado" + instrumento);
				Session.getConfigDriver().waitForLoad();

				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.sendKeys(ConstantesSpot.SUB_ZEROS + monedaPrincipal);
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto principal",
						"Datos: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);
				LOGGER.info("Ingreso monto principal: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);

				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PARIDAD_CIERRE))
						.sendKeys(ConstantesSpot.SUB_ZEROS + paridadCierre);
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto Paridad cierre",
						"Datos: " + ConstantesSpot.SUB_ZEROS + paridadCierre);
				LOGGER.info("Ingreso Paridad Cierre: " + ConstantesSpot.SUB_ZEROS + paridadCierre);
				// String paridadCierre =
				// UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PARIDAD_CIERRE)).getAttribute("title");

			} else {
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).clear();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO))
						.sendKeys(instrumento);
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso instrumento", "Datos: " + instrumento);
				LOGGER.info("Instrumento Seleccionado: " + instrumento);
				Session.getConfigDriver().waitForLoad();

				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.sendKeys(ConstantesSpot.SUB_ZEROS + monedaPrincipal + Keys.ENTER);

				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto principal",
						"Datos: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);

				LOGGER.info("Ingreso monto principal: " + ConstantesSpot.SUB_ZEROS + monedaPrincipal);

				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_CIERRE))
						.sendKeys(ConstantesSpot.SUB_ZEROS + tcCierre + Keys.ENTER);

				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto T/C cierre",
						"Datos: " + ConstantesSpot.SUB_ZEROS + tcCierre);

				LOGGER.info("Ingreso T/C Cierre: " + ConstantesSpot.SUB_ZEROS + tcCierre);

				String montoFinal = UtilesSelenium
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("Monto: " + montoFinal);
				String valorTcCierre = UtilesSelenium
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_CIERRE))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("T/C Cierre: " + valorTcCierre);
				String valotTcCosto = UtilesSelenium
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_COSTO))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("T/C Costo: " + valotTcCosto);
				String montoEquivalente = UtilesSelenium
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONTO_EQUIVALENTE))
						.getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("Monto Equivalente: " + montoEquivalente);
				float valor = formato(montoFinal) * formato(valorTcCierre);

				if (Float.compare(formato(SpotUtiles.folio(montoEquivalente)), valor) == 0) {
					Session.getConfigDriver().logger.log(LogStatus.INFO, "Monto Equivalente Valido",
							"Datos: " + montoEquivalente);
				} else {
					Session.getConfigDriver().logger.log(LogStatus.WARNING, "Monto Equivalente no Valido",
							"CLP " + valor + " debe ser igual a " + montoEquivalente);
				}

				String margen = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MARGEN))
						.getAttribute("title");
				float valor2 = (formato(montoFinal) * (formato(valotTcCosto) - formato(valorTcCierre)));
				LOGGER.info("MARGEN RESCATADO: " + formato(SpotUtiles.folio(margen)));
				LOGGER.info("MARGEN CALCULADO: " + valor);
				if (Float.compare(formato(SpotUtiles.folio(margen)), valor2) == 0) {
					Session.getConfigDriver().logger.log(LogStatus.INFO, "Margen Valido", "Datos: " + margen);
				} else {
					Session.getConfigDriver().logger.log(LogStatus.WARNING, "Margen no Valido",
							"Margen debe ser igual a " + formato(montoFinal) + " por (" + formato(valorTcCierre) + " - "
									+ formato(valotTcCosto) + ")");
				}
			}

			/**
			 * 
			 * 6 ok 7 no arbitraje - ok 8 no arbitraje - margen=montoPpal*(tcCierre -
			 * tcCosto) 7 arbitraje - montoEquivalente=montoPpal*paridadCierre 8 arbitraje -
			 * verificar montoEquivalente sea calculado monto ppal * (paridad
			 * cierre-paridadCosto)
			 * 
			 * 
			 * 
			 */

			UtilesExtentReport.captura("Ingresar operacion spot - Datos operacion - Spot");
			Session.getConfigDriver().waitForLoad();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Ingresar operacion spot - Datos operacion - Spot");
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Error: Ingresar operacion spot -Datos operacion",
					"Datos: " + e.getMessage());
			return false;
		}
	}

	public static boolean formadePago(SpotExcel datos) {
		try {
			/**
			 * Pagamos
			 */
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_SELECCIONAR_FORMA_DE_PAGO))
					.click();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_PAGAMOS))
					.sendKeys(Keys.CLEAR, ConstantesSpot.SUB_ZEROS, Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			/**
			 * Recibimos
			 */
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_RECIBIMOS))
					.sendKeys(Keys.CLEAR, ConstantesSpot.SUB_ZEROS, Keys.ENTER);
			Session.getConfigDriver().waitForLoad();

			UtilesExtentReport.captura("Ingresar operacion spot - Limpieza forma de pago - Spot");

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

			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase1 = Session.getConfigDriver().getWebDriver()
					.findElements(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BCSCOMBOBOX_INGRESORECIBIMOS));
			for (WebElement webElement : bcsComboboxBase1) {
				WebElement inputRecibimos = webElement
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_SELECCIONRECIBIMOS));
				inputRecibimos.sendKeys(pago + Keys.ENTER);
			}

			// Ingreso Entregamos

			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase2 = Session.getConfigDriver().getWebDriver()
					.findElements(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BCSCOMBOBOX_INGRESOPAGAMOS));
			for (WebElement webElement : bcsComboboxBase2) {
				WebElement inputEntregamos = webElement
						.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INPUT_SELECCIONPAGAMOS));
				inputEntregamos.sendKeys(pago + Keys.ENTER);
			}
			Session.getConfigDriver().waitForLoad();
			UtilesExtentReport.captura("Ingresar operacion spot - Forma de pago - Spot");
			return true;
		} catch (Exception e) {
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Forma de pago", "Datos: " + e.getMessage());
			UtilesExtentReport.capturaError("Error : Ingresar operacion spot - Forma de pago - Spot ");
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public static boolean otros() {
		try {
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_SELECCIONAR_OTROS)).click();
			Session.getConfigDriver().waitForLoad();

			UtilesExtentReport.captura("Ingresar operacion spot - Otros - Spot");

			String agenteSpot = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_AGENTE))
					.getAttribute(ConstantesSpotTags.TAG_VALUE);
			LOGGER.info("Agente Spot: " + agenteSpot);
			String tipoComprobanteSpot = UtilesSelenium
					.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TIPOCOMPROBANTE))
					.getAttribute(ConstantesSpotTags.TAG_VALUE);
			LOGGER.info("Comprobante Spot: " + tipoComprobanteSpot);
			if (ConstantesSpot.AGENTE.equals(agenteSpot)) {
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa",
						"Agente concuerda con Agenten ingresado");
			} else {
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_AGENTE))
						.sendKeys(ConstantesSpot.AGENTE + Keys.ENTER);
				Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida",
						"Agente no concuerda, por lo que se ingresó el agente correcto");
			}
			if (ConstantesSpot.TIPOCOMPROBANTE.equals(tipoComprobanteSpot)) {
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa",
						"Tipo de comprobante es Factura Electronica Exenta");
			} else {
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TIPOCOMPROBANTE)).clear();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TIPOCOMPROBANTE))
						.sendKeys(ConstantesSpot.TIPOCOMPROBANTE + Keys.ENTER);
				Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida",
						"Tipo de Comprobante no es Factura Electronica Exenta, por lo que se ingresó el Tipo de Comprobante correcto");
			}

			UtilesExtentReport.captura("Ingresar operacion spot - Informacion lista para enviar - Otros - Spot");

			// Aceptar
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BTN_ACEPTAR)).click();
			Session.getConfigDriver().waitForLoad(6000);

			UtilesExtentReport.captura("Ingresar operacion spot - Folio Otros - Spot");
			// Generar Folio
			String informacion = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_LABEL_FOLIO))
					.getText();
			LOGGER.info("Dato Label: " + informacion);
			LOGGER.info("Folio: " + SpotUtiles.folio(informacion));
			String rescatarFolioOp = SpotUtiles.folio(informacion);
			Session.getConfigDriver().waitForLoad();
			Session.setFolio(rescatarFolioOp);
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Folio ", "Datos: " + Session.getFolio());
			LOGGER.info("FOLIO SESION: " + Session.getFolio());
			// Botón Acpetar información..
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BTN_ACEPTARINFO)).click();
			Session.getConfigDriver().waitForLoad();
			UtilesExtentReport.captura("Ingresar operacion spot - Otros - Spot");
			CerrarVentana.init();
			return true;
		} catch (Exception e) {
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Forma de pago", "Datos: " + e.getMessage());
			LOGGER.error(e.getStackTrace());
			UtilesExtentReport.capturaError("Error : Ingresar operacion spot - Otros - Spot ");
			return false;
		}
	}
}
