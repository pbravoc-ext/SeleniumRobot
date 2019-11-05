package cl.bcs.operaciones.rueda;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesIngresarTransaccion;
import cl.bcs.application.constantes.util.ConstantesRV;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesExtentReport;
//import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class IngresarTransaccion {
	private static final Logger LOGGER = Log4jFactory.getLogger(IngresarTransaccion.class);

	public static boolean ingresarTransaccion(Object dato, Session session) {
		try {
			RVExcel datos = (RVExcel) dato;
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());
			UtilesExtentReport.captura("Ingresar Transaccion - Operaciones de Rueda", session);

			String mercado = datos.getMercado();
			String tipoOperacion = datos.getTipoOperacion();
			String tipoInstrumento = datos.getTipoInstrumento();
			String instrumento = datos.getInstrumento();
			String cl = datos.getCondicionLiquidacion();
			String cantidad = datos.getCantidad();
			String precio = datos.getPrecioLimite();
			String tipoPrecio = datos.getPrecio();

			// mercado
			WebElement weMercado = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_MERCADO_INPUT));
			weMercado.clear();
			weMercado.sendKeys(mercado);
			weMercado.sendKeys(Keys.ENTER);
			LOGGER.info("Mercado: " + mercado);
			session.logger.log(LogStatus.INFO, "Mercado Ingresado", mercado);

			// tipo instrumento
			WebElement weTipoInstrumento = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_TIPO_INSTRUMENTO_INPUT));
			weTipoInstrumento.clear();
			weTipoInstrumento.sendKeys(tipoInstrumento);
			weTipoInstrumento.sendKeys(Keys.ENTER);
			LOGGER.info("Tipo Instrumento: " + tipoInstrumento);
			session.logger.log(LogStatus.INFO, "Tipo Instrumento Ingresado", tipoInstrumento);

			// instrumento
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			WebElement weInstrumento = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_INSTRUMENTO_INPUT));
			weInstrumento.sendKeys(instrumento);
			weInstrumento.sendKeys(Keys.TAB);
			LOGGER.info("Instrumento: " + instrumento);
			session.logger.log(LogStatus.INFO, "Instrumento Ingresado", instrumento);

			// Tipo Operacion
			WebElement weTipoOperacion = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_OPERACION_INPUT));
			weTipoOperacion.clear();
			weTipoOperacion.sendKeys(tipoOperacion);
			weTipoOperacion.sendKeys(Keys.ENTER);
			LOGGER.info("Tipo Operacion: " + tipoOperacion);
			session.logger.log(LogStatus.INFO, "Tipo Operacion Ingresada", tipoOperacion);

			// folio
			int iFolio = (int) (Math.random() * 2000000000) + 1;
			String folio = Integer.toString(iFolio);
			session.setFolioAleatorio(folio);
			WebElement weFolio = UtilesSelenium.findInputNumber(ConstantesIngresarTransaccion.XPATH_FOLIO_ALEATORIO_INPUT_1,
					ConstantesIngresarTransaccion.XPATH_FOLIO_ALEATORIO_INPUT_2, session.getConfigDriver());
			weFolio.clear();
			weFolio.sendKeys(folio);
			weFolio.sendKeys(Keys.ENTER);
			LOGGER.info("Folio Aleatorio Generado: " + folio);
			session.logger.log(LogStatus.INFO, "Folio Aleatorio Generado", folio);

			// si no esta seleccionada bolsa de comercio de santiago en bolsa
//			String bolsa = "BOLSA DE COMERCIO DE SANTIAGO";
//			WebElement bol = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(
//					"//*[@id='FORM_CrearTransaccion']/div[2]/div/div[1]/form/div[3]/bcs-combobox-base[2]/span/span/span/input"));
//			bol.clear();
//			bol.sendKeys(bolsa);
//			WebElement cli1 = UtilesSelenium.findInputText("/html/body/div[18]/div/div[2]/ul/li");
//			cli1.click();
//			
			// Condición de Liquidación
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			WebElement weCondicion = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_CONDICION_INPUT));
			weCondicion.clear();
			weCondicion.sendKeys(cl + Keys.ENTER);
			LOGGER.info("Condicion Liquidacion: " + cl);
			session.logger.log(LogStatus.INFO, "Condicion Liquidación Ingresada", cl);

			// Cantidad
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			WebElement weCantidad = UtilesSelenium.findInputNumber(ConstantesIngresarTransaccion.XPATH_FOLIO_INPUT_1,
					ConstantesIngresarTransaccion.XPATH_FOLIO_INPUT_2, session.getConfigDriver());
			weCantidad.sendKeys(cantidad);
			LOGGER.info("Cantidad: " + cantidad);
			session.logger.log(LogStatus.INFO, "Cantidad Ingresada", cantidad);

			// Precio
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			WebElement wePrecio = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath((ConstantesIngresarTransaccion.XPATH_PRECIO_INPUT_1)));
			wePrecio.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			WebElement wePrecio1 = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath((ConstantesIngresarTransaccion.XPATH_PRECIO_INPUT_2)));
			wePrecio1.sendKeys(precio);
			wePrecio1.sendKeys(Keys.ENTER);
			LOGGER.info("Precio: " + precio);
			session.logger.log(LogStatus.INFO, "Precio Ingresado", precio);


			// Validar si el tipo de precio es 'Limite'
			if(tipoPrecio.equalsIgnoreCase(ConstantesRV.PRECIO_LIMITE)) {
				String precioFinal = UtilesSelenium
						.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_PRECIO_INPUT_1))
						.getAttribute(Constantes.TAG_TITLE);
				if (validacion2(tipoOperacion, precioFinal, session.getPrecioOrden())) {
					LOGGER.info("Validacion Flujo Limite exitosa para operacion: " + tipoOperacion);
					session.logger.log(LogStatus.PASS, "Validacion Flujo Limite", tipoOperacion);
//					UtilesExtentReport.captura("Validacion exitosa");
				} else {
					LOGGER.error("Validacion Flujo Limite erronea para operacion: " + tipoOperacion);
					session.logger.log(LogStatus.WARNING, "Validacion Flujo Limite", tipoOperacion);
//					UtilesExtentReport.capturaError("Validacion erronea");				
				}
			}
			
			UtilesExtentReport.captura("Datos Ingresados - Ingresar Transaccion - Operaciones de Rueda", session);

			// btn ingresar
			WebElement weIngresar = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_BTN_INGRESAR));
			weIngresar.click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			String ordeningresada = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_LABEL_INFO))
					.getText();
			session.setnTransaccion(SpotUtiles.onlyNumbers(ordeningresada));
			LOGGER.info("Folio Transaccion: "+SpotUtiles.onlyNumbers(ordeningresada));
			session.logger.log(LogStatus.INFO, "Folio Transaccion Rescatado: ", session.getnTransaccion());
			UtilesExtentReport.captura("Folio Transaccion Rescatado", session);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesIngresarTransaccion.XPATH_BTN_ACEPTAR_INFO)).click();
			LOGGER.info("== Cerrar Ingresar Transacción ==");
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el precio es menor o igual a la orden en 'COMPRA' y/o es mayor o igual a la orden en 'VENTA'
	 * @param operacion Tipo de  operacion
	 * @param precio Precio ingresado
	 * @param orden Precio de ingreso orden
	 * @return Devuelve un 'true' o un 'false' si la validacion es o no correcta
	 */
	public static boolean validacion2(String operacion, String precio, String orden) {
		BigDecimal precioFinal = new BigDecimal(formatoBigDecimal(precio));
		BigDecimal ordenFinal = new BigDecimal(formatoBigDecimal(orden));
		if (operacion.toUpperCase().equalsIgnoreCase(ConstantesRV.COMPRA_ACN)) {
			if (precioFinal.compareTo(ordenFinal) <= 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (precioFinal.compareTo(ordenFinal) >= 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Formatea el monto ingresado para usarlo como BigDecimal
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
