package cl.bcs.application.driver.factory;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;


import cl.bcs.application.factory.util.NavegadorConstantes;
import cl.bcs.application.file.util.Log4jFactory;

/**
 * 
 * @author Narveider
 *
 */

public class FactoryWebDriver {

	private static final Logger LOGGER = Log4jFactory.getLogger(FactoryWebDriver.class);

	/**
	 * 
	 * @return
	 */
	public static WebDriver createDriverReturn() {
		WebDriver newDriver = null;
		System.setProperty(NavegadorConstantes.DRIVER_CHROME, NavegadorConstantes.UBICACION_DRIVER_CHROME);
		ChromeOptions chromeOptions = new ChromeOptions();

//		chromeOptions.addArguments("--headless");
//		chromeOptions.addArguments("window-size=1920,1080");
		chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		newDriver = new ChromeDriver(chromeOptions);
		LOGGER.info("Driver Creado");
		try {
			newDriver.manage().window().maximize();
		} catch (Exception errorMax) {
			LOGGER.error(errorMax);
		}
		return newDriver;
	}
}
