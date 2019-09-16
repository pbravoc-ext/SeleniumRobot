package cl.bcs.plataforma;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.utiles.UtilesSelenium;

public class CerrarVentana {
	private static WebDriver webDriver = null;	
	
	public CerrarVentana(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}
	private static final Logger LOGGER = Log4jFactory.getLogger(CerrarVentana.class);
	
	public static boolean init(){
		boolean isLogin = false;
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath("/html/body/div[6]/div[1]/div/a[2]")).click();
		LOGGER.info("Cerrar ventana ");
		isLogin = true;
	return isLogin;
		
	}
}
