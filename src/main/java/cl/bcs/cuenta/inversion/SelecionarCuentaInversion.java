package cl.bcs.cuenta.inversion;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import cl.bcs.application.constantes.util.ConstantesCuentaInversion;
import cl.bcs.application.factory.util.ExtentReport;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.utiles.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.spot.SeleccionarSpot;



public class SelecionarCuentaInversion {
	private static WebDriver webDriver = null;	
	
	public SelecionarCuentaInversion(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SeleccionarSpot.class);
	
	public static boolean cuentaInversionCliente(SpotExcel datos){

//		String cd = ConstantesSelecionarCuentaInversion.XPATH_PARIDAD_CIERRE;
		//Datos Generales
		String cliente = datos.getRut()+" "+datos.getNombre()+" ("+datos.getPortafolio()+")";
		
		//Cerrar Facturación
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.id(ConstantesCuentaInversion.ID_MODULOFACTURACION)).click();
		
		
		//Abrir Cuenta Inversión
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.id(ConstantesCuentaInversion.ID_MODULOCUENTAINVERSION)).click();
		Session.getConfigDriver().waitForLoad();
		
		//Abrir Cuenta Inversión Cliente
		UtilesSelenium.findElement(By.id(ConstantesCuentaInversion.ID_CUENTAINVERSION)).click();
		Session.getConfigDriver().waitForLoad();
		
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
		UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FOLIO_COMPROBANTE)).sendKeys(Session.getComprobante()+Keys.ENTER);
		
		UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_FECHA)).click();
		ExtentReport.Captura("Concepto: Compra Mesa Spot");
		UtilesSelenium.findElement(By.xpath(ConstantesCuentaInversion.XPATH_TIPO_COMPROBANTE)).sendKeys(Keys.TAB);
		ExtentReport.Captura("Folio comprobante");
		LOGGER.info("CUENTA INVERSION COMPLETADA");
		CerrarVentana.init();
		
		
		
//		UtilesSelenium.findElement(By.xpath(xpathfolio)).clear();
//		LOGGER.info("Limpiar Folio");
		
//		UtilesSelenium.findElement(By.xpath("//*[@id='frmCtaCliente_gridMovimientosCuentaInversion']/span/div[2]/div[3]/div/table/thead/tr[2]/th[8]/span/span/span[1]/input")).sendKeys(Keys.TAB);
//		LOGGER.info("Ingresar Folio");
//		
//		UtilesSelenium.findElement(By.xpath(xpathfolio1)).sendKeys(folioComprobante1+Keys.ENTER);
//		LOGGER.info("Ingresar Folio");
		
		Session.getConfigDriver().waitForLoad();
//		UtilesSelenium.findElement(By.xpath(xpathfolio1)).sendKeys(Keys.ENTER);
//		LOGGER.info("Enter Folio");


//		WebElement barra = UtilesSelenium.findElement(By.className("TabCertificarMovimientos"));
//		WebElement horizontalbar = UtilesSelenium.findElement(By.xpath("//*[@id='frmCtaCliente_gridMovimientosCuentaInversion']/span/div[2]/div[4]/table"));
//		Actions action = new Actions(webDriver);
//
//		Actions moveToElement = action.moveToElement(horizontalbar);
//		for (int i = 0; i < 5; i++) {
//		    moveToElement.sendKeys(Keys.RIGHT).build().perform();
//		}
//		
//		WebElement webElement = webDriver.findElement(By.xpath("//*[@id='frmCtaCliente_gridMovimientosCuentaInversion']/span/div[2]/div[4]/table"));
//		((JavascriptExecutor)webDriver).executeScript("arguments[0].scrollIntoView();", webElement);
		
		
		
		return true;
	}

}
