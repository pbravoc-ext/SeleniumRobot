package cl.bcs.spot;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.utiles.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

/**
 * 
 * @author dnarvaez_EXT
 *
 */
public class SeleccionarSpot {

	private static WebDriver webDriver = null;

	public SeleccionarSpot(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SeleccionarSpot.class);

	/**
	 * 
	 * @return
	 */
	public static boolean init() {
		try {
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.id(ConstantesSpot.ID_MENU_SPOT)).click();
			LOGGER.info("Seleccion menu Spot - Spot");
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + " - Spot");
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean seleccionarMantenedorPuntas() {
		try {
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.id(ConstantesSpot.ID_SPOT_MANTENEDOR_PUNTAS)).click();
			LOGGER.info("Seleccion mantenedor de puntas - Spot");
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + " - Spot");
		}
		return false;
	}
	public static boolean seleccionarIngresoOperacionSpot() {
		try {
			UtilesSelenium.findElement(By.id(ConstantesSpot.ID_SPOT_INGRESO_OPERACION_SPOT)).click();
			Session.getConfigDriver().waitForLoad();
			LOGGER.info("Seleccion Ingreso Operación Spot - Spot");
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + " - Spot");
		}
		return false;
	}

}
