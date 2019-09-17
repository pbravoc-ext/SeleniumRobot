package cl.bcs.facturacion;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import cl.bcs.application.constantes.util.ConstantesFacturacion;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.spot.SeleccionarSpot;

public class SeleccionarFacturacion {
	private static final Logger LOGGER = Log4jFactory.getLogger(SeleccionarSpot.class);
	
	public static boolean gestionFacturacion(SpotExcel datos){
		String cliente = datos.getRut()+" "+datos.getNombre()+" ("+datos.getPortafolio()+")";
		
		//Datos Movimientos a Facturar
		
		boolean result = false;
		
		//Movimientos a Facturar
//		UtilesSelenium.findElement(By.xpath("//*[@id='blotterFacturacion']/div/div/div/ul/li[1]")).click();
//		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV)).sendKeys(cliente);
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV)).sendKeys(Keys.ENTER);
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARMOV)).click();
		Session.getConfigDriver().waitForLoad();

		LOGGER.info("====================");

	;	
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_FOLIOINPUT)).sendKeys(ConstantesSpot.SUB_ZEROS+Session.getFolio(), Keys.ENTER);
		LOGGER.info("====================");
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATHERE)).click();
//		LOGGER.info("Folio: ");

		LOGGER.info("====================");
		
		//Generar
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV)).click();
		Session.getConfigDriver().waitForLoad();
		
		//Confirmar
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV)).click();		
		Session.getConfigDriver().waitForLoad();

		//Rescatar Datos	
		String movimientoEgreso = null;
		String movimientoIngreso = null;
		String abono = UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_LABEL1)).getText();
		String cargo = UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_LABEL2)).getText();
		String comprobante = UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_LABEL3)).getText();
		String abonoOp = SpotUtiles.folio(abono);
		String cargoOp = SpotUtiles.folio(cargo);	
		String comprobanteOp = SpotUtiles.folio(comprobante);
		LOGGER.info("Abono: " + abonoOp);
		LOGGER.info("Cargo: " + cargoOp);
		LOGGER.info("Comprobante: " + comprobanteOp);	
		Session.setAbono(abonoOp);
		Session.setCargo(cargoOp);
		Session.setComprobante(comprobanteOp);
		Session.setMovimientoEgreso(movimientoEgreso);
		Session.setMovimientoIngreso(movimientoIngreso);
		Session.getConfigDriver().waitForLoad();
		
		//Aceptar
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO)).click();		
		Session.getConfigDriver().waitForLoad();
		
		//Comprobantes de Facturación
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_TABCOMPROBANTEFACTURACION)).click();
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_CLIENTEFAC)).sendKeys(cliente,Keys.ENTER);
		Session.getConfigDriver().waitForLoad();
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARFAC)).click();
		Session.getConfigDriver().waitForLoad();

		
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_SECUENCIA)).sendKeys(ConstantesSpot.SUB_ZEROS+comprobanteOp+Keys.ENTER);
		Session.getConfigDriver().waitForLoad();
		
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_COMPARARFOLIOFAC+comprobanteOp+ConstantesFacturacion.XPATH_COMPARARFOLIOFA2C)).click();
		Session.getConfigDriver().waitForLoad();
		
//		//Enviar a DTE.
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_ENVIARFAC)).click();
		Session.getConfigDriver().waitForLoad();
		
		//Confirmar
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARFAC)).click();
		Session.getConfigDriver().waitForLoad(6000);
		
		//Aceptar
		UtilesSelenium.findElement(By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARFAC)).click();
		Session.getConfigDriver().waitForLoad();
		CerrarVentana.init();
		
//		SelecionarCuentaInversion.cuentaInversionCliente(abonoOp, cargoOp, comprobanteOp, movimientoEgreso, movimientoIngreso);
		
		/*
		
		
		List<WebElement> tabla = Session.getConfigDriver().getWebDriver().findElements(By.className("k-numeric-wrap k-state-default k-expand-padding"));
		for (WebElement webElement : tabla) {
			List<WebElement> webelement2 = webElement.findElements(By.tagName("input"));
			for (WebElement webElement3 : webelement2) {
				webElement3.sendKeys("000000");
				System.out.println("Hola");
			Session.getConfigDriver().waitForLoad(20000);
			}
		}
		
		
		
		*/
//		List<WebElement> tabla = Session.getConfigDriver().getWebDriver().findElements(By.className("TabCertificarMovimientos"));
//		for (WebElement webElement : tabla) {
//			List<WebElement> webelement2 = webElement.findElements(By.xpath("//*[@id=\"frmCtaCliente_gridMovimientosCuentaInversion\"]/span/div[2]/div[4]/table/tbody/tr[8]/td[10]"));
//			for (WebElement webElement3 : webelement2) {
//				System.out.println(webElement3.getText());
//			}
//		}
//		
//		Session.getConfigDriver().waitForLoad();
//		UtilesSelenium.findElement(By.xpath(xpathfolio)).sendKeys(Keys.ENTER);
//		LOGGER.info("Enter Folio");
		
		Session.getConfigDriver().waitForLoad();
		return result;
	}

	
}
