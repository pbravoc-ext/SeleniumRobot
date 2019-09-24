package cl.bcs.cuenta.inversion;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesCuentaInversion;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.spot.SeleccionarSpot;



public class SelecionarCuentaInversion {
	private WebDriver webDriver = null;	
	
	public SelecionarCuentaInversion(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SelecionarCuentaInversion.class);
	
	public static boolean cuentaInversionCliente(SpotExcel datos){
		try {
			
			//Datos Generales
			String cliente = datos.getRut()+" "+datos.getNombre()+" ("+datos.getPortafolio()+")";			
			
//			//Abrir Cuenta Inversión
//			Session.getConfigDriver().waitForLoad();
//			UtilesSelenium.findElement(By.id(ConstantesCuentaInversion.ID_MODULOCUENTAINVERSION)).click();
//			Session.getConfigDriver().waitForLoad();
//			
//			//Abrir Cuenta Inversión Cliente
//			UtilesSelenium.findElement(By.id(ConstantesCuentaInversion.ID_CUENTAINVERSION)).click();
//			Session.getConfigDriver().waitForLoad();
			
			//Ir a pestañana Certificar / Anular Movimientos
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_TAB_CERT_ANULAR)).click();
			Session.getConfigDriver().waitForLoad();

			//Buscar Cliente
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_CLIENTE_INVERSION)).clear();
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_CLIENTE_INVERSION)).sendKeys(cliente+Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_BOTON_BUSCAR)).click();
			Session.getConfigDriver().waitForLoad();

			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_TIPO_COMPROBANTE)).sendKeys(Keys.TAB);
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE)).sendKeys(ConstantesSpot.SUB_ZEROS+Session.getComprobante()+Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			UtilesExtentReport.captura("Folio comprobante"+ Session.getComprobante());
			
			UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_ESTADO)).click();
			Session.getConfigDriver().waitForLoad();
			UtilesExtentReport.captura("Concepto: " + datos.getOperacion());
			Session.getConfigDriver().waitForLoad();
			
			LOGGER.info("CUENTA INVERSION COMPLETADA");
			CerrarVentana.init();
			return true;
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error: Gestion cuenta inversion - Spot");
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Error: Gestion cuenta inversion",
					"Datos: " + e.getMessage());
			return false;
		}
	}

}
