package cl.bcs.spot;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import cl.bcs.application.constantes.util.ConstantesConfirmacionOperacionSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.utiles.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

/**
 * 
 * @author rnunez_EXT
 *
 */

public class ConfirmacionOperacionesSpot {

	private static WebDriver webDriver = null;	
	
	public ConfirmacionOperacionesSpot(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}
	
	private static final Logger LOGGER = Log4jFactory.getLogger(ConfirmacionOperacionesSpot.class);
	
	public static boolean confirmarSpot(SpotExcel datos){

		String cliente = datos.getRut()+" "+datos.getNombre()+" ("+datos.getPortafolio()+")";
		
		boolean isLogin = false;
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.id("MENU_LiquidarSpot")).click();
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE)).sendKeys(cliente);
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_CLIENTE)).sendKeys(Keys.ENTER);
		Session.getConfigDriver().waitForLoad();
		//Buscar
		UtilesSelenium.findElement(By.id("FORM_LiquidarSpot")).click();
		Session.getConfigDriver().waitForLoad();
		
		
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_FOLIO_INPUT)).sendKeys(Session.getFolio()+Keys.ENTER);
		
		String xpathere = "//*[@id='grid-mov']/span/div[2]/div[4]/table//span[@ng-bind='dataItem.FolioOperacion' and contains(text(),"+Session.getFolio()+")]";
		UtilesSelenium.findElement(By.xpath(xpathere)).click();
//		LOGGER.info("Folio: ");
		
		//Confirmar
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_CONFIRMAR)).click();
		Session.getConfigDriver().waitForLoad();	
		
		//Aceptar
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_ACEPTAR)).click();
		Session.getConfigDriver().waitForLoad();
		
		//Aceptar
		UtilesSelenium.findElement(By.xpath(ConstantesConfirmacionOperacionSpot.XPATH_BOTON_INFO_ACEPTAR)).click();
		Session.getConfigDriver().waitForLoad();
		
		CerrarVentana.init();
		
		return isLogin;
	}

}
