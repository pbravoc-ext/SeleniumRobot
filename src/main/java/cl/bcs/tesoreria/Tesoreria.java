package cl.bcs.tesoreria;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesTesoreria;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
//import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.plataforma.SeleccionMenu;

public class Tesoreria {
	private static WebDriver webDriver = null;

	public Tesoreria(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SeleccionMenu.class);

	public static boolean gestionTesoreria(Object dato, Session session) {
		RVExcel datos = (RVExcel) dato;

		try {
			String socio = datos.getRut() + " " + datos.getNombre();
			String portafolio = "(" + datos.getPortafolio() + ") - PROPGENERALES";

			String Egreso = session.getMovimientoEgreso();
			String Ingreso = session.getMovimientoIngreso();
			String buscarEgreso = ConstantesTesoreria.XPATHERE_EGRESO + Egreso + Constantes.XPATHERE_OUT;
			String buscarIngreso = ConstantesTesoreria.XPATHERE_INGRESO + Ingreso + Constantes.XPATHERE_OUT;
			
			// RV
			String fechaDesde = SessionRV.getFechaTransaccion();
			String fechaHasta = SessionRV.getFechaLiquidacion();
			
			// Ingreso socio
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			WebElement weSocio = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_SOCIO_NEGOCIO_INPUT));
			weSocio.clear();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weSocio.sendKeys(socio);
			weSocio.sendKeys(Keys.ENTER);

			// Fecha desde
			WebElement weFechaDesde = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_FECHA_DESDE_INPUT));
			weFechaDesde.clear();
			weFechaDesde.sendKeys(fechaDesde); 
			weFechaDesde.sendKeys(Keys.TAB);

			// Fecha hasta
			WebElement weFechaHasta = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_FECHA_HASTA_INPUT));
			weFechaHasta.clear();
			weFechaHasta.sendKeys(fechaHasta); 
			weFechaHasta.sendKeys(Keys.TAB);

			// Ingreso portafolio
			WebElement wePortafolio = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_PORTAFOLIO_INTPUT));
			wePortafolio.clear();
			wePortafolio.sendKeys(portafolio);
			wePortafolio.sendKeys(Keys.TAB);

			// Limpiar estado
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_ESTADO_INTPUT)).clear();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Boton buscar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_BTN_BUSCAR)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			if (datos.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
				// Busqueda grilla por secuencia - Ingreso
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_GRILLA_SECUENCIA_INPUT))
						.sendKeys(Constantes.SUB_ZEROS + Ingreso, Keys.ENTER);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(buscarIngreso)).click();
				LOGGER.info("Seleccionado" + " " + Ingreso);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Ingreso " + Ingreso + " seleccionado", session);
			} else {
				// Busqueda grilla por secuencia - Engreso
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesTesoreria.XPATH_GRILLA_SECUENCIA_INPUT))
						.sendKeys(Constantes.SUB_ZEROS + Egreso, Keys.ENTER);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(buscarEgreso)).click();
				LOGGER.info("Seleccionado" + " " + Egreso);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Egreso " + Egreso + " seleccionado", session);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
			}

			// Busqueda grilla por secuencia Folio1 //Engreso
//			UtilesSelenium.findElement(By.xpath(ConstantesTesoreria.XPATH_GRILLA_SECUENCIA_INPUT))
//					.sendKeys(ConstantesSpot.SUB_ZEROS + Egreso, Keys.ENTER);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			UtilesSelenium.findElement(By.xpath(buscarEgreso)).click();
//			LOGGER.info("Seleccionado" + " " + Egreso);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			UtilesExtentReport.captura("Egreso " + Egreso + " seleccionado");
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//
//			// Boton limpiar secuencia
//			UtilesSelenium.findElement(By.xpath(ConstantesTesoreria.XPATH_BTN_SCN)).click();
//
//			// Busqueda grilla por secuencia Folio2//Ingreso
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			UtilesSelenium.findElement(By.xpath("//*[@id='grid-ingreso-egreso']//input"))
//					.sendKeys(ConstantesSpot.SUB_ZEROS + Ingreso, Keys.ENTER);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			UtilesSelenium.findElement(By.xpath(buscarIngreso)).click();
//			LOGGER.info("Seleccionado" + " " + Ingreso);
//			UtilesSelenium.waitForLoad(session.getConfigDriver());
//			UtilesExtentReport.captura("Ingreso " + Ingreso + " seleccionado");
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Gestión de Tesorería ==");
			CerrarVentana.init(session);

			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Gestion de Tesoreria - Datos tesoreria - Spot", session);
			session.logger.log(LogStatus.ERROR,
					"Error: Gestion de Tesoreria - Datos tesoreria - Spot", "Datos: " + e.getMessage());
			CerrarVentana.init(session);
			return false;
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

	/**
	 * Valida la igualdad de 2 valores
	 * @param montoFactura
	 * @param montoAsignacion
	 * @return Devuelve un 'true' o un 'false' dependiendo si se cumple la igualdad
	 */
	protected static boolean validacionMontos(String montoFactura, String montoAsignacion) {
		BigDecimal montoFinal = new BigDecimal(formatoBigDecimal(montoFactura));
		BigDecimal asignacionFinal = new BigDecimal(formatoBigDecimal(montoAsignacion));
		if (montoFinal.compareTo(asignacionFinal) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida igualdad de 2 fechas
	 * @param fecha1
	 * @param fecha2
	 * @return Devuelve un 'true' o un 'false' dependiendo si se cumple la igualdad
	 * @throws ParseException
	 */
	protected static boolean validacionFechas(String fecha1, String fecha2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = sdf.parse(fecha1);
		Date date2 = sdf.parse(fecha2);
		if (date1.compareTo(date2) == 0) {
			return true;
		} else {
			return false;
		}
	}

}
