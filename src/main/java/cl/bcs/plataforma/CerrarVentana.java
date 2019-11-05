package cl.bcs.plataforma;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesSelenium;

public class CerrarVentana {
	private static final Logger LOGGER = Log4jFactory.getLogger(CerrarVentana.class);
	
	public static boolean init( Session session){
		boolean isLogin = false;
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(Constantes.XPATH_CERRARVENTANA)).click();
		LOGGER.info("Cerrar ventana");
		isLogin = true;
	return isLogin;
		
	}
}
