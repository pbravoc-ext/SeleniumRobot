package cl.bcs.spot;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.constantes.util.ConstantesIngresoOperacionSpot;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.constantes.util.ConstantesSpotTags;
import cl.bcs.application.factory.util.ExtentReport;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.spot.util.IngresoOperacionSpotUtil;
import cl.bcs.application.utiles.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class IngresoOperacionSpot extends IngresoOperacionSpotUtil {
	private static WebDriver webDriver = Session.getConfigDriver().getWebDriver();

	private static final Logger LOGGER = Log4jFactory.getLogger(IngresoOperacionSpot.class);
	private static String operacion = "COMPRA";
	private static String instrumento = "DISTRIBUCION";
	private static String instrumento2 = "ARBITRAJE DISTRIBUCION";
	private static String monedaPrincipal = "1000";
	private static String monedaPrincipal2 = "5000";
	private static String tcCierre = "640,56";
	private static String paridadCierre = "1,1040";
	

	public static boolean datosOperacion(SpotExcel datos) {
		String cliente = datos.getRut()+" "+datos.getNombre()+" ("+datos.getPortafolio()+")";
		try {
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO)).sendKeys(cliente);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO)).sendKeys(Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO_ARROW)).click();
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_CLIENTE_PORTAFOLIO_SELECT)).click();		
			Session.getConfigDriver().waitForLoad();
			Session.getConfigDriver().waitForLoad(6000);
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso cliente", "Datos: " + cliente);
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_BTN_LIMPIAR)).click();
			
			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase = webDriver.findElements(By.id(ConstantesIngresoOperacionSpot.ID_BCSCOMBO_MONEDAPRINCIPAL));
			for (WebElement webElement : bcsComboboxBase) {
				WebElement inputEntregamos = webElement.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDAPRINCIPAL));
				inputEntregamos.clear();
				inputEntregamos.sendKeys(Session.getSpot().getMonedaPrincipal());			
			}
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Seleccion moneda principal", "Datos: " + Session.getSpot().getMonedaPrincipal());
			LOGGER.info("Moneda Principal Seleccionada");
			
			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase2 = webDriver.findElements(By.id(ConstantesIngresoOperacionSpot.ID_BCSCOMBO_MONEDASECUNDARIA));
			for (WebElement webElement2 : bcsComboboxBase2) {
				WebElement inputEntregamos2 = webElement2.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDASECUNDARIA));
				inputEntregamos2.clear();
				inputEntregamos2.sendKeys(Session.getSpot().getMonedaSecundaria());			
			}
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Seleccion moneda Secundaria", "Datos: " + Session.getSpot().getMonedaSecundaria());
			LOGGER.info("Moneda Secundaria Seleccionada");
			
			String iosPuntaCompra = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PUNTA_COMPRA)).getAttribute(ConstantesSpotTags.TAG_TITLE);
			LOGGER.info("IOSPuntaCompra: " + iosPuntaCompra);
			Session.getConfigDriver().waitForLoad();
			String iosPuntaVenta = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PUNTA_VENTA)).getAttribute(ConstantesSpotTags.TAG_TITLE);
			LOGGER.info("IOSPuntaVenta: " + iosPuntaVenta);
			Session.getConfigDriver().waitForLoad();
			LOGGER.info("PuntaCompra: " + Session.getVariables().getPuntaCompra());
			LOGGER.info("PuntaVenta: " + Session.getVariables().getPuntaVenta());
			validacionValoresPunta(Session.getVariables().getPuntaCompra(), Session.getVariables().getPuntaVenta(), iosPuntaCompra, iosPuntaVenta);
			
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_OPERACION)).clear();
			Session.getConfigDriver().waitForLoad();
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_OPERACION)).sendKeys(operacion);
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso operacion", "Datos: " + operacion);
			LOGGER.info("Operacion Seleccionada");
			Session.getConfigDriver().waitForLoad();
			
			if(Session.getSpot().getMonedaPrincipal().equals(ConstantesSpot.MONEDA_EUR)) {
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).clear();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).sendKeys(instrumento2);
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso instrumento", "Datos: " + instrumento2);
				LOGGER.info("Instrumento Seleccionado" + instrumento2);
				Session.getConfigDriver().waitForLoad();

				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.sendKeys(ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + monedaPrincipal2);				
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto principal",
						"Datos: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + monedaPrincipal2);
				LOGGER.info("Ingreso monto principal: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + monedaPrincipal2);
				
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PARIDAD_CIERRE)).sendKeys(ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + paridadCierre);
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto Paridad cierre", "Datos: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + paridadCierre);
				LOGGER.info("Ingreso Paridad Cierre: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + paridadCierre);
				//String paridadCierre = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_PARIDAD_CIERRE)).getAttribute("title");
				
			}else {
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).click();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).clear();
				Session.getConfigDriver().waitForLoad();
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_INSTRUMENTO)).sendKeys(instrumento);
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso instrumento", "Datos: " + instrumento);
				LOGGER.info("Instrumento Seleccionado: " + instrumento);
				Session.getConfigDriver().waitForLoad();

				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO))
						.sendKeys(ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + monedaPrincipal + Keys.ENTER);

				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto principal",
						"Datos: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + monedaPrincipal);

				LOGGER.info(
						"Ingreso monto principal: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + monedaPrincipal);

				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_CIERRE))
						.sendKeys(ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + tcCierre + Keys.ENTER);

				Session.getConfigDriver().logger.log(LogStatus.INFO, "Ingreso monto T/C cierre",
						"Datos: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + tcCierre);

				LOGGER.info("Ingreso T/C Cierre: " + ConstantesIngresoOperacionSpot.SUB_DATO_ZEROS + tcCierre);

				String montoFinal = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONEDA_PRINCIPAL_MONTO)).getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("Monto: " + montoFinal);
				String valorTcCierre = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_CIERRE)).getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("T/C Cierre: " + valorTcCierre);
				String valotTcCosto = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_TC_COSTO)).getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("T/C Costo: " + valotTcCosto);
				String montoEquivalente = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MONTO_EQUIVALENTE)).getAttribute(ConstantesSpotTags.TAG_TITLE);
				LOGGER.info("Monto Equivalente: " + montoEquivalente);
				float valor = formato(montoFinal)*formato(valorTcCierre);
				
				if(Float.compare(formato(folioOperacion(montoEquivalente)),valor) == 0) {					
					Session.getConfigDriver().logger.log(LogStatus.INFO, "Monto Equivalente Valido",
							"Datos: " + montoEquivalente);					
				}else {
					Session.getConfigDriver().logger.log(LogStatus.WARNING, "Monto Equivalente no Valido",
							"CLP "+valor+" debe ser igual a " + montoEquivalente);						
				}	
				
				String margen = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_MARGEN)).getAttribute("title");
				float valor2 = (formato(montoFinal)*(formato(valotTcCosto)-formato(valorTcCierre)));
				LOGGER.info("MARGEN RESCATADO: " + formato(folioOperacion(margen)));
				LOGGER.info("MARGEN CALCULADO: "+ valor);
				if(Float.compare(formato(folioOperacion(margen)),valor2) == 0) {					
					Session.getConfigDriver().logger.log(LogStatus.INFO, "Margen Valido",
							"Datos: " + margen);					
				}else {
					Session.getConfigDriver().logger.log(LogStatus.WARNING, "Margen no Valido",
							"Margen debe ser igual a "+formato(montoFinal)+" por (" + formato(valorTcCierre) + " - " + formato(valotTcCosto) + ")");	
				}		
			}
			
			/**
			 * 
			 * 6 ok
			 * 7 no arbitraje - ok
			 * 8 no arbitraje - margen=montoPpal*(tcCierre - tcCosto) 
			 * 7 arbitraje - montoEquivalente=montoPpal*paridadCierre
			 * 8 arbitraje - verificar montoEquivalente sea calculado monto ppal * (paridad cierre-paridadCosto)
			 * 
			 * 
			 * 
			 */
			
			ExtentReport.Captura("Ingresar operacion spot - Datos operacion - Spot");
			Session.getConfigDriver().waitForLoad();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			ExtentReport.CapturaError("Error: Ingresar operacion spot - Datos operacion - Spot");
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Error: Ingresar operacion spot -Datos operacion", "Datos: " + e.getMessage());
			return false;
		}
	}

	public static boolean formadePago(SpotExcel datos) {
		//pagamos
		try {
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_SELECCIONAR_FORMA_DE_PAGO)).click();
			UtilesSelenium.findElement(By.xpath("//*[@id='txtEnt1']/span/span/input[1]")).sendKeys(Keys.CLEAR,"0000",Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
//			//Recibimos
			UtilesSelenium.findElement(By.xpath("//*[@id='txtRec1']/span/span/input[1]")).sendKeys(Keys.CLEAR,"0000",Keys.ENTER);
			Session.getConfigDriver().waitForLoad();
			String pago = "";
			switch (datos.getPortafolio()) {
			case "0":
				pago = "CUENTA INVERSION CLIENTE";
				break;
			case "1":
				pago = "TRANSFERENCIA 24";
				break;

			default:
				break;
			}
			
			// Ingreso Recibimos
			
			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase1 = Session.getConfigDriver().getWebDriver().findElements(By.xpath("//*[@id=\"FORM_MesaSpot\"]/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[2]/bcs-combobox-base[1]"));
			for (WebElement webElement : bcsComboboxBase1) {
				WebElement inputRecibimos = webElement.findElement(By.xpath("//*[@id=\"FORM_MesaSpot\"]/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[2]/bcs-combobox-base[1]/span/span/span/input"));
				inputRecibimos.sendKeys(pago+Keys.ENTER);
				
			}
			
			// Ingreso Entregamos
			
			Session.getConfigDriver().waitForLoad();
			List<WebElement> bcsComboboxBase2 = Session.getConfigDriver().getWebDriver().findElements(By.xpath("//*[@id=\"FORM_MesaSpot\"]/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[2]/bcs-combobox-base[2]"));
			for (WebElement webElement : bcsComboboxBase2) {
				WebElement inputEntregamos = webElement.findElement(By.xpath("//*[@id=\"FORM_MesaSpot\"]/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[2]/bcs-combobox-base[2]/span/span/span/input"));
				inputEntregamos.sendKeys(pago+Keys.ENTER);
				
			}
//			Session.getConfigDriver().waitForLoad(6000);
//			try {
//				String fechaRecibimos = null;
//			List<WebElement> tablaRecibimos  = webDriver.findElements(By.xpath("//*[@id=\"FORM_MesaSpot\"]/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa"));
//			for (WebElement webElement : tablaRecibimos) {
//				List<WebElement> example = webElement.findElements(By.xpath("//*[@id=\"FORM_MesaSpot\"]/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[3]/table/tbody/tr/td[3]"));
//				for (WebElement webElement2 : example) {
//					System.out.println("Dentro de la tabla");
//					if (webElement2.getText().contains("-")) {
//						System.out.println("Encontrado");
//						fechaRecibimos = webElement2.getText();
//					}
//				}
//				
//			}
//			
//		
//			
//			WebElement fechaEntregamos = webDriver.findElement(By.xpath("//*[@id=\"FORM_MesaSpot\"]/div[2]/div/form/div[1]/div[3]/div/div[2]/div/div/div/bcs-forma-pago-mesa/div/div[4]/table/tbody/tr/td[3]"));
//			System.out.println(fechaRecibimos);
//			System.out.println(fechaEntregamos.getText());
//			comparar(fechaRecibimos,fechaEntregamos.getText());
//			}catch (Exception e) {
//				System.out.println("Error Fechas");
//			}
			Session.getConfigDriver().waitForLoad();
			ExtentReport.Captura("Ingresar operacion spot - Forma de pago - Spot");
			return true;
		} catch (Exception e) {
			Session.getConfigDriver().logger.log(LogStatus.ERROR, "Forma de pago", "Datos: " + e.getMessage());
			ExtentReport.CapturaError("Error : Ingresar operacion spot - Forma de pago - Spot ");
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public static boolean otros() {
		try {
			UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_SELECCIONAR_OTROS)).click();
			Session.getConfigDriver().waitForLoad();
			String xpathTipoComprobante = "//*[@id='UI_TIPO_COMPROBANTE']/span/span/span/input";
			String xpathLabelFolio = "//*[@id='FORM_VentanaMensajeResultado']/div[2]/div/div[1]/div[1]/div[1]/label";
			String xpathBotonAceptar = "//*[@id='FORM_MesaSpot']/div[2]/div/form/div[2]/bcs-button[1]/button";
			String xpathBotonAceptarInfo = "//*[@id='FORM_VentanaMensajeResultado']/div[2]/div/div[2]/bcs-button/button";
			
			String agente = "FRANCISCO METTROZ";
			String tipoComprobante = "FACTURA ELECTRONICA EXENTA";

			String agenteSpot = UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_AGENTE)).getAttribute("value");
			LOGGER.info("Agente Spot: " + agenteSpot);
			String tipoComprobanteSpot = UtilesSelenium.findElement(By.xpath(xpathTipoComprobante)).getAttribute("value");
			LOGGER.info("Comprobante Spot: " + tipoComprobanteSpot);
			if(agente.equals(agenteSpot)){
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa", "Agente concuerda con Agenten ingresado");
			}else {
				UtilesSelenium.findElement(By.xpath(ConstantesIngresoOperacionSpot.XPATH_AGENTE)).sendKeys(agente+Keys.ENTER);
				Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida", "Agente no concuerda, por lo que se ingresó el agente correcto");
			}
			if(tipoComprobante.equals(tipoComprobanteSpot)) {
				Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa", "Tipo de comprobante es Factura Electronica Exenta");			
			}else {
				UtilesSelenium.findElement(By.xpath(xpathTipoComprobante)).clear();
				UtilesSelenium.findElement(By.xpath(xpathTipoComprobante)).sendKeys(tipoComprobante+Keys.ENTER);	
				Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida", "Tipo de Comprobante no es Factura Electronica Exenta, por lo que se ingresó el Tipo de Comprobante correcto");
			}

			//Aceptar
			UtilesSelenium.findElement(By.xpath(xpathBotonAceptar)).click();	
			Session.getConfigDriver().waitForLoad();
		
			//Generar Folio
			String informacion = UtilesSelenium.findElement(By.xpath(xpathLabelFolio)).getText();
			LOGGER.info("Dato Label: " + informacion);
			LOGGER.info("Folio: " + folioOperacion(informacion));
			String rescatarFolioOp = folioOperacion(informacion);
			Session.getConfigDriver().waitForLoad();
			Session.setFolio(rescatarFolioOp);
			LOGGER.info("FOLIO SESION: " + Session.getFolio());
			ExtentReport.Captura("Ingresar operacion spot - Otros Seleccion folio - Spot");
			// Botón Acpetar información..		
			UtilesSelenium.findElement(By.xpath(xpathBotonAceptarInfo)).click();
			Session.getConfigDriver().waitForLoad();
			ExtentReport.Captura("Ingresar operacion spot - Otros - Spot");
			CerrarVentana.init();
			ExtentReport.Captura("Ingresar operacion spot - Otros - Spot");
			return true;
		} catch (Exception e) {
			ExtentReport.CapturaError("Error : Ingresar operacion spot - Otros - Spot ");
			return false;
		}
	}
}
