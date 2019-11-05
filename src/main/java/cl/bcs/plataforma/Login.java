package cl.bcs.plataforma;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
/**
 * 
 * @author dnarvaez_EXT
 *
 */
public class Login {
	private static final Logger LOGGER = Log4jFactory.getLogger(Login.class);
	
	public static boolean login(Session session) {
		boolean isLogin = false;
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			System.out.println(session.getConfigDriver().getTitle());
			UtilesSelenium.findElement(session.getConfigDriver(), By.id(Constantes.ID_USUARIO)).sendKeys("fmettroz");
			UtilesSelenium.findElement(session.getConfigDriver(), By.id(Constantes.ID_PASSWORD)).sendKeys("1");
			UtilesExtentReport.captura("Ingreso de Usuario", session);
			UtilesSelenium.findElement(session.getConfigDriver(), By.id(Constantes.ID_BTN_LOGIN)).click();
			LOGGER.info("Login Exitoso");
			isLogin = true;
		return isLogin;
	}
}
