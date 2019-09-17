package cl.bcs.spot;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import cl.bcs.application.constantes.util.ConstantesConfirmacionOperacionSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

/**
 * 
 * @author rnunez_EXT
 *
 */

public class ConfirmacionOperacionesSpot {

	private static final Logger LOGGER = Log4jFactory.getLogger(ConfirmacionOperacionesSpot.class);
	
	public static boolean confirmarSpot(SpotExcel datos){

		String cliente = datos.getRut()+" "+datos.getNombre()+" ("+datos.getPortafolio()+")";
		
		boolean isLogin = false;
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE)).sendKeys(cliente);
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE)).sendKeys(Keys.ENTER);
		Session.getConfigDriver().waitForLoad();
		//Buscar
		SeleccionarSpot.seleccionarConfirmacionOperaciones();
		
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_FOLIO_INPUT)).sendKeys(Session.getFolio()+Keys.ENTER);
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_THERE)).click();
		
		LOGGER.info("Folio: "+Session.getFolio());
		
		//Confirmar
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_CONFIRMAR)).click();
		Session.getConfigDriver().waitForLoad();	
		
		//Aceptar
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_ACEPTAR)).click();
		Session.getConfigDriver().waitForLoad();
		
		//Aceptar
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_INFO_ACEPTAR)).click();
		Session.getConfigDriver().waitForLoad();
		
		LOGGER.info("Finalización confirmación operación spot");
		
		CerrarVentana.init();
		
		return isLogin;
	}

}
