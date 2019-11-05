package cl.bcs.custodia;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesConsultarCustodia;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class ConsultarCustodia {

	private static final Logger LOGGER = Log4jFactory.getLogger(ConsultarCustodia.class);

	public static boolean custodia(Object dato, Session session) {
//		try {
			RVExcel datos = (RVExcel) dato;
			String cliente = datos.getRut() + " " + datos.getNombre();
			String mercado = datos.getMercado();
			String portafolio = "(" + datos.getPortafolio() + ") - PROPGENERALES";
			String instrumento = datos.getInstrumento();
			String operacion = datos.getOperacion();
//			String numeroPortafolio = datos.getPortafolio();
//			String nominal = datos.getNominal();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesExtentReport.captura("Consultar Custodia", session);

			// Ingresar Cliente
			WebElement weCliente = UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCustodia.XPATH_CLIENTE_INPUT));
			weCliente.clear();
			weCliente.sendKeys(cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weCliente.sendKeys(Keys.TAB);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			System.out.println("kshbdkjns");
//			session.logger.log(LogStatus.INFO, "Ingresando cliente: ", cliente);

//			// Ingresar Portafolio
//			WebElement wePortafolio = UtilesSelenium
//					.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCustodia.XPATH_PORTAFOLIO_INPUT));
//			wePortafolio.clear();
//			wePortafolio.sendKeys(portafolio);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			wePortafolio.sendKeys(Keys.ENTER);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			session.logger.log(LogStatus.INFO, "Ingresando portafolio: ", portafolio);

			// Ingresar Mercado
			WebElement weMercado = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCustodia.XPATH_MERCADO_INPUT));
			weMercado.clear();
			weMercado.sendKeys(mercado);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weMercado.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			session.logger.log(LogStatus.INFO, "Ingresando mercado: ", mercado);

			// Ingresar Instrumento
			WebElement weInstrumento = UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCustodia.XPATH_INSTRUMENTO_INPUT));
			weInstrumento.sendKeys(instrumento, Keys.TAB);

//			UtilesSelenium.findElement(By.xpath(ConstantesConsultarCustodia.XPATH_INSTRUMENTO_INPUT))
//					.sendKeys(instrumento, Keys.TAB);

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			session.logger.log(LogStatus.INFO, "Ingresando instrumento: ", instrumento);

			// BTN Buscar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesConsultarCustodia.XPATH_BTN_BUSCAR)).click();
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

//			String totalCustodia = UtilesSelenium
//					.findElement(By.xpath(ConstantesConsultarCustodia.XPATH_CUSTODIA_DISPONIBLE_GRILLA)).getText();
//			LOGGER.info("Total Custodia Disponible: " + totalCustodia);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//
//			String custodiaTransito = UtilesSelenium
//					.findElement(By.xpath(ConstantesConsultarCustodia.XPATH_CUSTODIA_NOMINAL_GRILLA)).getText();
//			LOGGER.info("Total Custodia Nominal: " + custodiaTransito);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());

			revisarCustodia(operacion, session);

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Consultar Custodia ==");
			CerrarVentana.init(session);
			SessionRV.setEstadoFlujo(1);
			return true;
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage());
//			LOGGER.error(e.getCause());
//			LOGGER.error(e.getLocalizedMessage());
//			return false;
//		}
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
	 * Verifica si se usa la custodia disponible o en transito dependiendo de la
	 * operacion y si es nominal
	 * 
	 * @param nominal
	 * @param operacion
	 */
	public static void revisarCustodia(String operacion, Session session) {

		String totalCustodia = UtilesSelenium
				.findElement(session.getConfigDriver(), By.xpath(ConstantesConsultarCustodia.XPATH_CUSTODIA_DISPONIBLE_GRILLA)).getText();
		LOGGER.info("Total Custodia Disponible: " + totalCustodia);
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		if (SessionRV.getEstadoFlujo() == 0) {
			LOGGER.info("Inicio del flujo");
			SessionRV.setCustodiaInicial(totalCustodia);
			LOGGER.info("Custodia Disponible: " + SessionRV.getCustodiaInicial());
			session.logger.log(LogStatus.INFO, "Rescatando custodia inicial disponible: ",
					SessionRV.getCustodiaInicial());
			UtilesExtentReport.captura("Rescatando custodia inicial disponible", session);
		} else {
			LOGGER.info("Final del flujo");
			LOGGER.info("Custodia Disponible Inicial: " + SessionRV.getCustodiaInicial());
			LOGGER.info("Custodia Disponible Final: " + totalCustodia);
			session.logger.log(LogStatus.INFO, "Rescatando custodia final disponible: ",
					totalCustodia);
			UtilesExtentReport.captura("Rescatando custodia final disponible", session);
			validacionCustodiaCantidad(operacion, SessionRV.getCustodiaInicial(), totalCustodia,
					SessionRV.getCantidad(), session);
		}

	}

	/**
	 * Valida que la custodia haya aumentado o disminuido acorde a la cantidad
	 * ingresada
	 * 
	 * @param operacion       Operacion ingresada en la orden
	 * @param custodiaInicial Custodia Inicial
	 * @param custodiaFinal   Custodia Final
	 * @param cantidad        Cantidad ingresada en la orden
	 */
	public static void validacionCustodiaCantidad(String operacion, String custodiaInicial, String custodiaFinal,
			String cantidad, Session session) {
		BigDecimal custodiaInicialOp = new BigDecimal(formatoBigDecimal(custodiaInicial));
		BigDecimal custodiaFinalOp = new BigDecimal(formatoBigDecimal(custodiaFinal));
		BigDecimal cantidadOp = new BigDecimal(formatoBigDecimal(cantidad));
		BigDecimal calculoCompra = custodiaFinalOp.subtract(custodiaInicialOp);
		BigDecimal calculoVenta = custodiaInicialOp.subtract(custodiaFinalOp);
		if (operacion.toUpperCase().equalsIgnoreCase(Constantes.COMPRA)) {
			if (custodiaFinalOp.compareTo(custodiaInicialOp) > 0 && calculoCompra.compareTo(cantidadOp) == 0) {
				LOGGER.info("Validacion exitosa - Valor de custodia ha aumentado en " + cantidad);
				session.logger.log(LogStatus.PASS, "Valor de custodia ha aumentado en " + cantidad,
						"Custodia Inicial: " + custodiaInicial + " y Custodia Final: " + custodiaFinal);
			} else {
				LOGGER.error("Validacion erronea - Valor de custodia debe aumentar en " + cantidad);
				session.logger.log(LogStatus.WARNING,
						"Valor de custodia debe aumentar en " + cantidad,
						"Custodia Inicial: " + custodiaInicial + " y Custodia Final: " + custodiaFinal);
			}
		} else {
			if (custodiaFinalOp.compareTo(custodiaInicialOp) < 0 && calculoVenta.compareTo(cantidadOp) == 0) {
				LOGGER.info("Validacion exitosa - Valor de custodia ha disminuido en " + cantidad);
				session.logger.log(LogStatus.PASS, "Valor de custodia ha disminuido en " + cantidad,
						"Custodia Inicial: " + custodiaInicial + " y Custodia Final: " + custodiaFinal);
			} else {
				LOGGER.error("Validacion erronea - Valor de custodia debe disminuir en " + cantidad);
				session.logger.log(LogStatus.WARNING,
						"Valor de custodia debe disminuir en " + cantidad,
						"Custodia Inicial: " + custodiaInicial + " y Custodia Final: " + custodiaFinal);
			}
		}
	}
}
