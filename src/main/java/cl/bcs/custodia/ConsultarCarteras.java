package cl.bcs.custodia;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;
import cl.bcs.application.constantes.util.ConstantesConsultarCarteras;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
//import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class ConsultarCarteras {

	private static final Logger LOGGER = Log4jFactory.getLogger(ConsultarCarteras.class);

	public static boolean carteras(Object dato, Session session) {
		try {
			RVExcel datos = (RVExcel) dato;

			String cliente = datos.getRut() + " " + datos.getNombre();
			String mercado = datos.getMercado();
			String portafolio = "(" + datos.getPortafolio() + ") - PROPGENERALES";
			String instrumento = datos.getInstrumento();
			String folioCartera = session.getFolioCartera();
//			String valorComparar = null;

			UtilesSelenium.waitForLoadLong(session.getConfigDriver());
			LOGGER.info("Consultar Carteras - Operaciones de Rueda");
			UtilesExtentReport.captura("Consultar Carteras - Operaciones de Rueda", session);

			// Ingresar Cliente
			WebElement weCliente = UtilesSelenium.findElement(
					session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.XPATH_CLIENTE_INPUT));
			weCliente.clear();
			weCliente.sendKeys(cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weCliente.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("Cliente: " + cliente);
			session.logger.log(LogStatus.INFO, "Cliente Ingresado", cliente);

			// Ingresar Portafolio
			WebElement wePortafolio = UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.XPATH_PORTAFOLIO_INPUT));
			wePortafolio.clear();
			wePortafolio.sendKeys(portafolio);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			wePortafolio.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("Portafolio: " + portafolio);
			session.logger.log(LogStatus.INFO, "Portafolio Ingresado", portafolio);

			// Ingresar Mercado
			WebElement weMercado = UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.XPATH_MERCADO_INPUT));
			weMercado.clear();
			weMercado.sendKeys(mercado);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weMercado.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("Mercado: " + mercado);
			session.logger.log(LogStatus.INFO, "Mercado Ingresado", mercado);

			// Ingresar Instrumento
			WebElement weInstrumento = UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.XPATH_INSTRUMENTO_INPUT));
			weInstrumento.sendKeys(instrumento, Keys.TAB);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("Instrumento: " + instrumento);
			session.logger.log(LogStatus.INFO, "Instrumento Ingresado", instrumento);

			// BTN Buscar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.XPATH_BTN_BUSCAR)).click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Folio cartera
			WebElement weFolio = UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.XPATH_BUSCAR_FOLIO_INPUT));
			weFolio.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weFolio.sendKeys(folioCartera);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weFolio.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("Folio Cartera: " + folioCartera);
			session.logger.log(LogStatus.INFO, "Folio Cartera Ingresado", folioCartera);

			// Monto Asignacion (Usar luego el rescatado en facturacion)
//				String montoFactura = session.getMontoAsignacion();

			// Fecha Ingreso
			String fechaIngreso = UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.FULL_XPATH_FECHA_INGRESO)).getText();
			LOGGER.info("Fecha Transaccion Cartera: " + fechaIngreso);
			LOGGER.info("Fecha Transaccion Factura: " + session.getFechaTransaccion());
			session.logger.log(LogStatus.INFO, "Fecha Transaccion Cartera Rescatada", fechaIngreso);

			// Fecha Liquidacion Factura
			String fechaLiquidacion = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.FULL_XPATH_FECHA_LIQUIDACION)).getText();
			LOGGER.info("Fecha Liquidacion Cartera: " + fechaLiquidacion);
			LOGGER.info("Fecha Transaccion Factura: " + session.getFechaLiquidacion());
			session.logger.log(LogStatus.INFO, "Fecha Liquidacion Cartera Rescatada", fechaLiquidacion);

			if (validacionFechas(session.getFechaLiquidacion(), fechaLiquidacion)) {
				session.logger.log(LogStatus.PASS,
						"Fecha 'Liquidacion' factura es igual a Fecha 'Liquidacion' Cartera",
						"Fecha 'Liquidacion' factura: " + session.getFechaLiquidacion()
								+ " y Fecha 'Liquidacion' Cartera: " + fechaLiquidacion);
				LOGGER.info("Fecha 'Liquidacion' factura: " + session.getFechaLiquidacion()
						+ " es igual a Fecha 'Liquidacion' Cartera: " + fechaLiquidacion);
			} else {
				session.logger.log(LogStatus.WARNING,
						"Fecha 'Liquidacion' factura es distinta a Fecha 'Liquidacion' Cartera",
						"Fecha 'Liquidacion' factura: " + session.getFechaLiquidacion()
								+ " y Fecha 'Liquidacion' Cartera: " + fechaLiquidacion);
				LOGGER.error("Fecha 'Liquidacion' factura: " + session.getFechaLiquidacion()
						+ " debe ser igual a Fecha 'Liquidacion' de cartera: " + fechaLiquidacion);
			}
//			String montoCompraCartera = UtilesSelenium
//					.findElement(By.xpath(ConstantesConsultarCarteras.XPATH_MONTO_COMPRA)).getText();
			String valorCompraCartera = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.XPATH_VALOR_COMPRA)).getText();
			LOGGER.info("Valor Compra Cartera: " + valorCompraCartera);
//			LOGGER.info("Monto Compra Cartera: " + montoCompraCartera);
			LOGGER.info("Monto Compra Factura: " + session.getMontoAsignacion());

			String valorCompraFinal = valorCompraCartera.replaceAll("[a-zA-Z]", "");
			LOGGER.info("Valor Compra Cartera: " + valorCompraFinal);

//			if (datos.getTipoInstrumento().equalsIgnoreCase(ConstantesRV.INSTRUMENTO_ACX)) {
//				valorComparar = valorCompraCartera;
//			} else {
//				valorComparar = montoCompraCartera;
//			}
//			
			session.logger.log(LogStatus.INFO, "Valor Compra Cartera Rescatado", valorCompraCartera);

			if (validacionMontos(valorCompraCartera, session.getMontoFactura())) {
				session.logger.log(LogStatus.PASS, "Valor Cartera es igual a Monto Facturado",
						"Valor Cartera: " + valorCompraCartera + " y Monto Facturado: " + session.getMontoFactura());
				LOGGER.info("Validacion exitosa - Valor cartera: " + valorCompraCartera
						+ " es igual al monto facturado: " + session.getMontoFactura());
			} else {
				session.logger.log(LogStatus.WARNING, "Valor Cartera es distinto a Monto Facturado",
						"Valor Cartera: " + valorCompraCartera + " y Monto Facturado: " + session.getMontoFactura());
				LOGGER.error("Validacion errónea - Valor cartera: " + valorCompraCartera
						+ " debe ser igual al monto facturado: " + session.getMontoFactura());
			}

			UtilesExtentReport.captura("Datos Rescatados", session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Consultar Carteras ==");
			CerrarVentana.init(session);
			session.setEstadoFlujo(1);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public static boolean movimientos(Object dato, Session session) {
		RVExcel datos = (RVExcel) dato;

		String cliente = datos.getRut() + " " + datos.getNombre();
		String mercado = datos.getMercado();
		String portafolio = "(" + datos.getPortafolio() + ") - PROPGENERALES";
		String folioCartera = session.getFolioCartera();

		UtilesSelenium.waitForLoadLong(session.getConfigDriver());
		LOGGER.info("Consultar Movimientos - Operaciones de Rueda");
		UtilesExtentReport.captura("Consultar Movimientos - Operaciones de Rueda", session);

		// Tab Movimientos
		UtilesSelenium.waitForLoadLong(session.getConfigDriver());
		UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.XPATH_TAB_MOVIMIENTOS)).click();

		// Boton Mensaje Error
		UtilesSelenium.waitForLoadMid(session.getConfigDriver());
		try {

			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.XPATH_BTN_ERROR_MOVIMIENTOS))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Mensaje Error en Movimiento Cartera", session);

		} catch (Exception e) {
			LOGGER.info("Sin Mensaje de Error");
		}
		UtilesSelenium.waitForLoad(session.getConfigDriver());

		// Ingresar Cliente
		WebElement weClienteMov = UtilesSelenium
				.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.XPATH_CLIENTE_INPUT_MOVIMIENTOS));
		weClienteMov.clear();
		weClienteMov.sendKeys(cliente);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		weClienteMov.sendKeys(Keys.ENTER);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		LOGGER.info("Cliente: " + cliente);
		session.logger.log(LogStatus.INFO, "Cliente Ingresado", cliente);

		// Ingresar Portafolio
		WebElement wePortafolioMov = UtilesSelenium
				.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.XPATH_PORTAFOLIO_INPUT_MOVIMIENTOS));
		wePortafolioMov.clear();
		wePortafolioMov.sendKeys(portafolio);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		wePortafolioMov.sendKeys(Keys.ENTER);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		LOGGER.info("Portafolio: " + portafolio);
		session.logger.log(LogStatus.INFO, "Portafolio Ingresado", portafolio);

		// Ingresar Mercado
		WebElement weMercadoMov = UtilesSelenium
				.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.XPATH_MERCADO_INPUT_MOVIMIENTOS));
		weMercadoMov.clear();
		weMercadoMov.sendKeys(mercado);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		weMercadoMov.sendKeys(Keys.ENTER);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		LOGGER.info("Mercado: " + mercado);
		session.logger.log(LogStatus.INFO, "Mercado Ingresado", mercado);

		// BTN Buscar
		UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCarteras.XPATH_BTN_BUSCAR_MOVIMIENTOS)).click();
		UtilesSelenium.waitForLoadMid(session.getConfigDriver());

		// Folio cartera
		WebElement weFolioMov2 = UtilesSelenium.findInputNumber(
				ConstantesConsultarCarteras.XPATH_BUSCAR_FOLIO_INPUT_MOVIMIENTOS_1,
				ConstantesConsultarCarteras.XPATH_BUSCAR_FOLIO_INPUT_MOVIMIENTOS_2, session.getConfigDriver());
		weFolioMov2.sendKeys(folioCartera);
		LOGGER.info("Folio Cartera: " + folioCartera);
		session.logger.log(LogStatus.INFO, "Folio Cartera Ingresado", folioCartera);
		UtilesSelenium.waitForLoad(session.getConfigDriver());

		// Rescatar Cantidad
		String cantidad = UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.FULL_XPATH_CANTIDAD))
				.getText();

		LOGGER.info(cantidad);

		// BigDecimal cant = new BigDecimal(cantidad);

		LOGGER.info("Cantidad: " + cantidad);
		session.logger.log(LogStatus.INFO, "Cantidad", "" + cantidad);
		UtilesSelenium.waitForLoadLong(session.getConfigDriver());

		// Click Venta
		UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCarteras.FULL_XPATH_CANTIDAD))
				.click();
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		UtilesExtentReport.captura("Datos Rescatados", session);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		LOGGER.info("== Cerrar Consultar Carteras ==");
		CerrarVentana.init(session);
		session.setEstadoFlujo(1);
		try {
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Valida igualdad de 2 fechas
	 * 
	 * @param fechaLiquidación
	 * @param fechaFactura
	 * @return Devuelve un 'true' o un 'false' dependiendo si se cumple la igualdad
	 * @throws ParseException
	 */
	public static boolean validacionFechas(String fechaLiquidación, String fechaFactura) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = sdf.parse(fechaLiquidación);
		Date date2 = sdf.parse(fechaFactura);
		if (date1.compareTo(date2) == 0) {
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

	/**
	 * Valida la igualdad de 2 valores
	 * 
	 * @param montoFactura
	 * @param montoAsignacion
	 * @return Devuelve un 'true' o un 'false' dependiendo si se cumple la igualdad
	 */
	public static boolean validacionMontos(String montoFactura, String montoAsignacion) {
		BigDecimal montoFinal = new BigDecimal(formatoBigDecimal(montoFactura));
		BigDecimal asignacionFinal = new BigDecimal(formatoBigDecimal(montoAsignacion));
		if (montoFinal.compareTo(asignacionFinal) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
