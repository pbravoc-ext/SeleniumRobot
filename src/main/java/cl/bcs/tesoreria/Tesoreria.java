package cl.bcs.tesoreria;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesFacturacion;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.spot.SeleccionarSpot;

public class Tesoreria {
	private static WebDriver webDriver = null;

	public Tesoreria(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SeleccionarSpot.class);
	
	public static boolean gestionTesoreria(SpotExcel datos) {
		
		 try {
			 
				return true;			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Gestion de Tesoreria - Datos tesoreria - Spot");
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Error: Gestion de Tesoreria - Datos tesoreria - Spot",
					"Datos: " + e.getMessage());
			CerrarVentana.init();
			SeleccionarSpot.seleccionarMenuFacturacion();
			return false;
		}
	}
	
	public static boolean menuTesoreria() {
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.id("MENU_MODULO_TESORERIA")).click();
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.id("MENU_BlotterComprobantesTesoreria")).click();
		Session.getConfigDriver().waitForLoad();		
		return true;
	}

}
