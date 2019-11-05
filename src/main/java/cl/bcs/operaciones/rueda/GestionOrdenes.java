package cl.bcs.operaciones.rueda;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesGesionOrdenes;
import cl.bcs.application.factory.util.RVExcel;
//import cl.bcs.application.constantes.util.ConstantesIngresoOperacionSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SessionRV;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class GestionOrdenes {
	private static final Logger LOGGER = Log4jFactory.getLogger(GestionOrdenes.class);

	public static boolean asignaciones(RVExcel usu, Session session) {
		try {
			String folio = SessionRV.getFolio();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// ASIGNACIONES
			WebElement weAsignaciones = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesGesionOrdenes.XPATH_TAB_ASIGNACIONES));
			weAsignaciones.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// ASIGNACIONES
			WebElement weBuscar = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesGesionOrdenes.XPATH_BTN_BUSCAR_ASIGNACIONES));
			weBuscar.click();

			// Secuencia
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			WebElement weFolio = UtilesSelenium.findInputNumber(ConstantesGesionOrdenes.XPATH_FOLIO_INPUT_1,
					ConstantesGesionOrdenes.XPATH_FOLIO_INPUT_2, session.getConfigDriver());
			LOGGER.info(folio);
			weFolio.sendKeys(folio, Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// click datos
			UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesGesionOrdenes.XPATHERE_FOLIO + folio + Constantes.XPATHERE_OUT))
					.click();

			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// btn enviar a fac
			WebElement weEnviar = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesGesionOrdenes.XPATH_BTN_ENVIAR_FAC));

			weEnviar.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// btn enviar a fac / aceptar
			WebElement weAceptar = UtilesSelenium
					.findElement(session.getConfigDriver(),By.xpath(ConstantesGesionOrdenes.XPATH_BTN_ACEPTAR_ENVIAR_FAC));

			weAceptar.click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// rescatar datos (folios)
			String informacion = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesGesionOrdenes.XPATH_INFORMACION_LABEL))
					.getText();
			LOGGER.info(informacion);
			ArrayList<String> valores = numeros(informacion);

			SessionRV.setAsignacion(valores.get(0).toString());
			SessionRV.setMovimientoGenerado(valores.get(1).toString());

			LOGGER.info("Asignación: " + SessionRV.getAsignacion());
			LOGGER.info("Movimiento: " + SessionRV.getMovimientoGenerado());
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// btn aceptar info
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesGesionOrdenes.XPATH_BTN_ACEPTAR_INFO)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Gestión Ordenes ==");
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Separa agrupacion de numeros dentro de una cadena
	 * 
	 * @param cadena
	 * @return arreglo con numeros separados
	 */
	public static ArrayList<String> numeros(String cadena) {
		ArrayList<String> list = new ArrayList<>();
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(cadena);
		while (m.find()) {
			list.add(m.group());
		}
		return list;
	}
}
