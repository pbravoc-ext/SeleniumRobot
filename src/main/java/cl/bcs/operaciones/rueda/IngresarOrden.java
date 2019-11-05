package cl.bcs.operaciones.rueda;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesIngresarOrden;
import cl.bcs.application.constantes.util.ConstantesRV;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class IngresarOrden {
	private static final Logger LOGGER = Log4jFactory.getLogger(IngresarOrden.class);

	public static boolean ingresarOrden(Object dato, Session session) {
		try {
			RVExcel datos = (RVExcel) dato;
			String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";
			String instrumento = datos.getInstrumento();
			String operacion = datos.getOperacion();
			String cantidad = datos.getCantidad();
			String mecanismo = datos.getMecanismoCaptacion();
			String tipoPrecio = datos.getPrecio();
			String precio = datos.getPrecioLimite();
			String cl = datos.getCondicionLiquidacion();
			String bolsaDestino = datos.getBolsaDestino();

			WebElement weOperacion = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_OPERACION_INPUT));
			WebElement weInstrumento = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_INSTRUMENTO_INPUT));
			WebElement weTipoPrecio = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_TIPO_PRECIO_INPUT));
			WebElement weCondicion = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_CONDICION_INPUT));
			WebElement weBolsa = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_BOLSA_DESTINO));

			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			LOGGER.info("Ingresar Orden - Operaciones de Rueda");
			UtilesExtentReport.captura("Ingresar Orden - Operaciones de Rueda", session);

			// Ingreso CLiente (OK)
			WebElement weCliente1 = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_CLIENTE_INPUT_1));
			weCliente1.clear();
			weCliente1.sendKeys(cliente);
			WebElement weCliente2 = UtilesSelenium.findInputText(ConstantesIngresarOrden.XPATH_CLIENTE_INPUT_2, session.getConfigDriver());
			weCliente2.click();
			LOGGER.info("Cliente: " + cliente);
			session.logger.log(LogStatus.INFO, "Cliente Ingresado", cliente);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Mecanismo de Captación // ORDEN ESCRITA
			WebElement weMecanismo1 = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_MECANISMO_INPUT_1));
			weMecanismo1.clear();
			weMecanismo1.sendKeys(mecanismo);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
		//	WebElement weMecanismo2 = UtilesSelenium.findInputText(ConstantesIngresarOrden.XPATH_MECANISMO_INPUT_2);
		//	weMecanismo2.click();
			LOGGER.info("Mecanismo: " + mecanismo);
			session.logger.log(LogStatus.INFO, "Mecanismo Igresado", mecanismo);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Ingreso Operación //COMPRA o VENTA
			weOperacion.clear();
			weOperacion.sendKeys(operacion);
			LOGGER.info("Operación: " + operacion);
			session.logger.log(LogStatus.INFO, "Operación Igresada", operacion);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Ingreso Cantidad //100
			SessionRV.setCantidad(cantidad);
			WebElement weCantidadFinal = UtilesSelenium.findInputNumber(ConstantesIngresarOrden.XPATH_CANTIDAD_INPUT_1,
					ConstantesIngresarOrden.XPATH_CANTIDAD_INPUT_2, session.getConfigDriver());
			weCantidadFinal.sendKeys(cantidad);
			LOGGER.info("Cantidad: " + cantidad);
			session.logger.log(LogStatus.INFO, "Cantidad Igresada", cantidad);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Ingreso Instrumento
			weInstrumento.clear();
			weInstrumento.sendKeys(instrumento);
			weInstrumento.sendKeys(Keys.ENTER);
			LOGGER.info("Instrumento: " + instrumento);
			session.logger.log(LogStatus.INFO, "Instrumento Igresado", instrumento);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Tipo de Precio //Limite
			weTipoPrecio.clear();
			weTipoPrecio.sendKeys(tipoPrecio);
			weTipoPrecio.sendKeys(Keys.ENTER);
			LOGGER.info("Tipo de Precio: " + tipoPrecio);
			session.logger.log(LogStatus.INFO, "Tipo de Precio Igresado", tipoPrecio);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Precio // CLP 1000 USD 230
			WebElement wePrecioFinal = UtilesSelenium.findInputNumber(ConstantesIngresarOrden.XPATH_PRECIO_INPUT_1,
					ConstantesIngresarOrden.XPATH_PRECIO_INPUT_2, session.getConfigDriver());
			wePrecioFinal.sendKeys(precio);
			LOGGER.info("Precio: " + precio);
			session.logger.log(LogStatus.INFO, "Precio Igresado", precio);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Condicion liquidacion
			weCondicion.clear();
			weCondicion.sendKeys(cl + Keys.TAB);
			LOGGER.info("Condicion liquidacion: " + cl);
			session.logger.log(LogStatus.INFO, "Condicion liquidacion Igresada", cl);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			

			// Bolsa Destino
			weBolsa.clear();
			weBolsa.sendKeys(bolsaDestino + Keys.TAB);
			LOGGER.info("Bolsa Destino: " + bolsaDestino);
			session.logger.log(LogStatus.INFO, "Condicion liquidacion Igresada", bolsaDestino);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			String montoTotal = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_MONTO_TOTAL_INPUT))
					.getText();
			LOGGER.info("Monto Total: " + montoTotal);
			SessionRV.setMontoTotal(montoTotal);

			String montoOperacion = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_MONTO_OPERACION_INPUT)).getText();
			String cantidadFinal = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_CANTIDAD_INPUT))
					.getAttribute(Constantes.TAG_TITLE);
			String precioFinal = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_PRECIO_INPUT))
					.getAttribute(Constantes.TAG_TITLE);
			SessionRV.setPrecioOrden(precioFinal);
			if (validacionMontoOperacion(montoOperacion, precioFinal, cantidadFinal)) {
				LOGGER.info("Validacion Monto Operacion correcta: " + montoOperacion);
				session.logger.log(LogStatus.PASS, "Validacion Monto Operacion",
						"Datos: " + montoOperacion);
			} else {
				LOGGER.error("Validacion Monto Operacion erronea: " + montoOperacion);
				session.logger.log(LogStatus.WARNING, "Validacion Monto Operacion",
						"Datos: " + montoOperacion);
			}
			UtilesExtentReport.captura("Datos ingresados - Ingresar Orden - Operaciones de Rueda", session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Mas datos
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_TAB_MAS_DATOS)).click();
			WebElement weFormaDePago = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_FORMA_DE_PAGO));
			String condicion = weFormaDePago.getAttribute(Constantes.TAG_DISABLE);
			if (datos.getTransferencia().equalsIgnoreCase(Constantes.SI)) {
				if (condicion.equalsIgnoreCase(Constantes.TRUE)) {
					UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesIngresarOrden.XPATH_CHECKBOX_MAS_DATOS)).click();
					LOGGER.info("Presionar checkbox");
				}
				weFormaDePago.clear();
				weFormaDePago.sendKeys(ConstantesRV.FORMA_PAGO);
				LOGGER.info("Forma de Pago Ingresada: " + ConstantesRV.FORMA_PAGO);
				UtilesExtentReport.captura("Forma de Pago Ingresada - Ingresar Orden - Operaciones de Rueda", session);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				weFormaDePago.sendKeys(Keys.TAB);
			} else {
				if (condicion.equalsIgnoreCase(Constantes.FALSE)) {
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_CHECKBOX_MAS_DATOS)).click();
					LOGGER.info("Presionar checkbox");
				}
			}

			// botón aceptar
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_BTN_ACEPTAR)).click();
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

			// Rescatar Folio
			String ordeningresada = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.FULL_XPATH_LABEL_INFO))
					.getText();
			SessionRV.setFolio(SpotUtiles.onlyNumbers(ordeningresada));
			session.logger.log(LogStatus.INFO, "Folio rescatado", "Folio: " + SessionRV.getFolio());
			UtilesExtentReport.captura("Folio rescatado - Ingresar Orden - Operaciones de Rueda", session);
			LOGGER.info("Folio Ingreso Orden: " + SpotUtiles.onlyNumbers(ordeningresada));

			// Boton aceptar info
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarOrden.XPATH_BTN_ACEPTAR_INFO)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Ingresar Orden ==");
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el monto Operación sea la multiplicación del Precio por cantidad.
	 * 
	 * @param montoOperacion rescatado de resumen ingreso orden
	 * @param precio         precio ingresado
	 * @param cantidad       cantidad ingresada
	 * @return devuelve un 'true' o un 'false'
	 */
	public static boolean validacionMontoOperacion(String montoOperacion, String precio, String cantidad) {
		BigDecimal montoOperacionFinal = new BigDecimal(formatoBigDecimal(montoOperacion));
		BigDecimal precioFinal = new BigDecimal(formatoBigDecimal(precio));
		BigDecimal cantidadFinal = new BigDecimal(formatoBigDecimal(cantidad));
		BigDecimal valorCalculado = precioFinal.multiply(cantidadFinal);
		if (valorCalculado.compareTo(montoOperacionFinal) == 0) {
			return true;
		} else {
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

}
