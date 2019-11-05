package cl.bcs.facturacion;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.relevantcodes.extentreports.LogStatus;
import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesFacturacion;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.plataforma.SeleccionMenu;


public class Facturacion1 extends SpotUtiles{
	private static WebDriver webDriver = null;

	public Facturacion1(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(Facturacion1.class);

	public static boolean gestionFacturacion(Object dato, Session session) {
		SpotExcel datos = (SpotExcel) dato;
		String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";

		// Datos Movimientos a Facturar
		UtilesSelenium.waitForLoad(session.getConfigDriver());
		UtilesExtentReport.captura("Ingreso Gestión de Facturación - Movimientos a Facturar", session);

		try {

			// Instanciar Datos
			String abono = null;
			String cargo = null;
			String comprobante = null;
			String comprobanteVenta = null;
			String movimientoEgreso = null;
			String movimientoIngreso = null;
			String abonoOp = null;
			String cargoOp = null;
			String comprobanteOp = null;
			String comprobanteVentaOp = null;
			String movimientoIngresoOp = null;
			String movimientoEgresoOp = null;
			String grilla = null;

			// Movimientos a Facturar
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV)).sendKeys(cliente);
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV)).sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARMOV)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_FOLIOINPUT))
					.sendKeys(ConstantesSpot.SUB_ZEROS + session.getFolio(), Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Buscar en Grilla Movimientos por Folio " + session.getFolio(), session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			if (datos.getInstrumento().equalsIgnoreCase(Constantes.INSTRUMENTO_ARB_INTER)) {

				if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) {
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_OPERACION
									+ Constantes.COMPRA + Constantes.XPATHERE_OUT))
							.click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					
					UtilesExtentReport.captura("Buscar en Grilla COMPRA MESA SPOT ", session);

					
					//Validación en Grilla Montos
					grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_MOV_FACTURACION)).getText();
					LOGGER.info(grilla);
					
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					LOGGER.info("=======================================");
					LOGGER.info(session.getMontoSecundario());
					LOGGER.info(SpotUtiles.formatoMontos(session.getMontoSecundario()));
					LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoSecundario()));
					LOGGER.info("=======================================");
					LOGGER.info(grilla);
					LOGGER.info(SpotUtiles.formatoMontos(grilla));
					LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
					LOGGER.info("=======================================");
					
					if(SpotUtiles.validacionValorGrilla2(session.getMontoSecundario(), grilla)) {
						//Validacion correcta 
						session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Igual a " + grilla);
					}else {
						//error
						session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Distinto a " + grilla);
									}
					
					// Generar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					UtilesExtentReport.captura("Desea facturar movimiento seleccionado", session);

					// Confirmar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesExtentReport.captura("Confirmar Movimiento - Generación Comprobante", session);

					// Abono
					abono = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_ABONO_ARB)).getText();
					movimientoEgreso = UtilesSelenium
							.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_EGRESO_ARB_SCI)).getText();
					comprobanteVenta = UtilesSelenium
							.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_COMPROBANTE_ARB_SCI)).getText();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					abonoOp = SpotUtiles.onlyNumbers(abono);
					movimientoEgresoOp = SpotUtiles.onlyNumbers(movimientoEgreso);
					comprobanteVentaOp = SpotUtiles.onlyNumbers(comprobanteVenta);
					if (datos.getOperacion().toUpperCase().equals("COMPRA")) {
						session.logger.log(LogStatus.INFO, "Comprobante Cargo" , abonoOp);
						session.logger.log(LogStatus.INFO, "Comprobante Compra" , comprobanteVentaOp);
						session.logger.log(LogStatus.INFO, "Movimiento Ingreso" , movimientoEgresoOp);
					}else {
						session.logger.log(LogStatus.INFO, "Comprobante Abono" , abonoOp);
						session.logger.log(LogStatus.INFO, "Comprobante Venta" , comprobanteVentaOp);
						session.logger.log(LogStatus.INFO, "Movimiento Egreso" , movimientoEgresoOp);
					}

					LOGGER.info("Abono: " + abonoOp);
					LOGGER.info("Egreso: " + movimientoEgresoOp);
					LOGGER.info("Comprobante Venta: " + comprobanteVentaOp);

					// Aceptar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_OPERACION
									+ Constantes.VENTA + Constantes.XPATHERE_OUT))
							.click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesExtentReport.captura("Buscar en Grilla VENTA MESA SPOT ", session);
					
					//Validación en Grilla Montos
					grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_MOV_FACTURACION)).getText();
					LOGGER.info(grilla);
					
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					LOGGER.info("=======================================");
					LOGGER.info(session.getMontoPrincipal());
					LOGGER.info(SpotUtiles.formatoMontos(session.getMontoPrincipal()));
					LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoPrincipal()));
					LOGGER.info("=======================================");
					LOGGER.info(grilla);
					LOGGER.info(SpotUtiles.formatoMontos(grilla));
					LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
					LOGGER.info("=======================================");
					
					if(SpotUtiles.validacionValorGrilla2(session.getMontoPrincipal(), grilla)) {
						//Validacion correcta 
						session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoPrincipal()+ " Es Igual a " + grilla);
					}else {
						//error
						session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoPrincipal()+ " Es Distinto a " + grilla);
									}

					// Generar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesExtentReport.captura("Desea facturar movimiento seleccionado", session);

					// Confirmar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					UtilesExtentReport.captura("Confirmar Movimiento - Generación Comprobante", session);

					// Cargo
					cargo = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_CARGO_ARB)).getText();
					movimientoIngreso = UtilesSelenium
							.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_INGRESO_ARB_SCI)).getText();
					comprobante = UtilesSelenium
							.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_COMPROBANTE_ARB_SCI)).getText();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					cargoOp = SpotUtiles.onlyNumbers(abono);
					movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
					comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
					
					if (datos.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
						session.logger.log(LogStatus.INFO, "Comprobante Abono" , cargoOp);
						session.logger.log(LogStatus.INFO, "Comprobante Venta" , comprobanteOp);
						session.logger.log(LogStatus.INFO, "Movimiento Egreso" , movimientoIngresoOp);
					}else {
						session.logger.log(LogStatus.INFO, "Comprobante Cargo" , cargoOp);
						session.logger.log(LogStatus.INFO, "Comprobante Compra" , comprobanteOp);
						session.logger.log(LogStatus.INFO, "Movimiento Ingreso" , movimientoIngresoOp);
					}
					LOGGER.info("Cargo: " + cargoOp);
					LOGGER.info("Ingreso: " + movimientoIngresoOp);
					LOGGER.info("Comprobante Compra: " + comprobanteOp);

					// Aceptar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

				} else {     
					// con cuenta inversion  y con arbitraje
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_OPERACION
									+ Constantes.COMPRA + Constantes.XPATHERE_OUT))
							.click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					
					UtilesExtentReport.captura("Buscar en Grilla COMPRA MESA SPOT ", session);
					grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_MOV_FACTURACION)).getText();
					LOGGER.info(grilla);
					
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					LOGGER.info("=======================================");
					LOGGER.info(session.getMontoSecundario());
					LOGGER.info(SpotUtiles.formatoMontos(session.getMontoSecundario()));
					LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoSecundario()));
					LOGGER.info("=======================================");
					LOGGER.info(grilla);
					LOGGER.info(SpotUtiles.formatoMontos(grilla));
					LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
					LOGGER.info("=======================================");
					
					if(SpotUtiles.validacionValorGrilla2(session.getMontoSecundario(), grilla)) {
						//Validacion correcta 
						session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Igual a " + grilla);
					}else {
						//error
						session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Distinto a " + grilla);
									}

					// Generar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesExtentReport.captura("Desea facturar movimiento seleccionado", session);

					// Confirmar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					UtilesExtentReport.captura("Confirmar Movimiento - Generación Comprobante", session);

					// Abono
					abono = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_ABONO_ARB)).getText();
					comprobanteVenta = UtilesSelenium
							.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_COMPROBANTE_ARB)).getText();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					abonoOp = SpotUtiles.onlyNumbers(abono);
					comprobanteVentaOp = SpotUtiles.onlyNumbers(comprobanteVenta);
					
					if (datos.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
						session.logger.log(LogStatus.INFO, "Comprobante Abono" , abonoOp);
						session.logger.log(LogStatus.INFO, "Comprobante Compra" , comprobanteVentaOp);
						//session.logger.log(LogStatus.INFO, "Movimiento Ingreso" , movimientoEgresoOp);
					}else {
						session.logger.log(LogStatus.INFO, "Comprobante Cargo" , abonoOp);
						
						session.logger.log(LogStatus.INFO, "Comprobante Venta" , comprobanteVentaOp);
						//session.logger.log(LogStatus.INFO, "Movimiento Egreso" , movimientoEgresoOp);
					}
					LOGGER.info("Abono: " + abonoOp);
					LOGGER.info("Comprobante Venta: " + comprobanteVentaOp);

					// Aceptar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_OPERACION
									+ Constantes.VENTA + Constantes.XPATHERE_OUT))
							.click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesExtentReport.captura("Buscar en Grilla VENTA MESA SPOT ", session);
					
					grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_MOV_FACTURACION)).getText();
				
					
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					LOGGER.info("=======================================");
					LOGGER.info(session.getMontoPrincipal());
					LOGGER.info(SpotUtiles.formatoMontos(session.getMontoPrincipal()));
					LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoPrincipal()));
					LOGGER.info("=======================================");
					LOGGER.info(grilla);
					LOGGER.info(SpotUtiles.formatoMontos(grilla));
					LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
					LOGGER.info("=======================================");
					
					if(SpotUtiles.validacionValorGrilla2(session.getMontoPrincipal(), grilla)) {
						//Validacion correcta 
						session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoPrincipal()+ " Es Igual a " + grilla);
						LOGGER.info("Validación de Monto Ingresado Exitosa" );
					}else {
						//error
						session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoPrincipal()+ " Es Distinto a " + grilla);
						
						LOGGER.info("Validación de Monto Ingresado Fallida" );
						
									}
					
					// Generar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesExtentReport.captura("Desea facturar movimiento seleccionado", session);

					// Confirmar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());
					UtilesExtentReport.captura("Confirmar Movimiento - Generación Comprobante", session);

					// Cargo
					cargo = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_CARGO_ARB)).getText();
					comprobante = UtilesSelenium
							.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_COMPROBANTE_ARB)).getText();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

					cargoOp = SpotUtiles.onlyNumbers(cargo);
					comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
					
					
					if (datos.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
						session.logger.log(LogStatus.INFO, "Comprobante Cargo" , cargoOp);
						session.logger.log(LogStatus.INFO, "Comprobante Venta" , comprobanteOp);
						//session.logger.log(LogStatus.INFO, "Movimiento Egreso" , movimientoIngresoOp);
					}else {
						session.logger.log(LogStatus.INFO, "Comprobante Abono" , cargoOp);
						session.logger.log(LogStatus.INFO, "Comprobante Compra" , comprobanteOp);
						//session.logger.log(LogStatus.INFO, "Movimiento Ingreso" , movimientoIngresoOp);
					}
					LOGGER.info("Cargo: " + cargoOp);
					LOGGER.info("Comprobante Compra: " + comprobanteOp);

					// Aceptar
					UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO)).click();
					UtilesSelenium.waitForLoad(session.getConfigDriver());

				}

				session.setAbono(abonoOp);
				session.setCargo(cargoOp);
				session.setComprobante(comprobanteOp);
				session.setComprobanteVenta(comprobanteVentaOp);
				session.setMovimientoEgreso(movimientoEgresoOp);
				session.setMovimientoIngreso(movimientoIngresoOp);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				
				
				LOGGER.info("=======================================");
				LOGGER.info(session.getComprobanteVenta());
				LOGGER.info("=======================================");
				
				LOGGER.info("=======================================");
				LOGGER.info(session.getComprobante());
			
				LOGGER.info("=======================================");
				

			} else {
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				//Sin Arbitraje
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_FOLIO
								+ session.getFolio() + Constantes.XPATHERE_OUT))
						.click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Buscar en Grilla folio - " + session.getFolio(), session);
				grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_MOV_FACTURACION)).getText();
				LOGGER.info(grilla);
				
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				LOGGER.info("=======================================");
				LOGGER.info(session.getMontoSecundario());
				LOGGER.info(SpotUtiles.formatoMontos(session.getMontoSecundario()));
				LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoSecundario()));
				LOGGER.info("=======================================");
				LOGGER.info(grilla);
				LOGGER.info(SpotUtiles.formatoMontos(grilla));
				LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
				LOGGER.info("=======================================");
				
				if(SpotUtiles.validacionValorGrilla2(session.getMontoSecundario(), grilla)) {
					//Validacion correcta 
					session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Igual a " + formatoBigDecimal2(grilla));
				}else {
					//error
					session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Distinto a " +formatoBigDecimal2(grilla));
								}

				// Generar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Desea facturar movimiento seleccionado", session);

				// Confirmar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Confirmar Movimiento - Generación Comprobante", session);
				
				// Rescatar Datos
				abono = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_ABONO)).getText();
				cargo = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_CARGO)).getText();
				comprobante = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_COMPROBANTE_CCI))
						.getText();
				abonoOp = SpotUtiles.onlyNumbers(abono);
				cargoOp = SpotUtiles.onlyNumbers(cargo);
				comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
				session.logger.log(LogStatus.INFO, "Folio Abono" , abonoOp);
				session.logger.log(LogStatus.INFO, "Folio Cargo" , cargoOp);
				LOGGER.info("Abono: " + abonoOp);
				LOGGER.info("Cargo: " + cargoOp);
				if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) {
					movimientoIngreso = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_INGRESO))
							.getText();
					movimientoEgreso = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_EGRESO))
							.getText();
					comprobante = UtilesSelenium
							.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_LABEL_COMPROBANTE_SCI)).getText();
					comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
					movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
					movimientoEgresoOp = SpotUtiles.onlyNumbers(movimientoEgreso);
					
					session.logger.log(LogStatus.INFO, "Movimiento Ingreso" , movimientoIngresoOp);
					session.logger.log(LogStatus.INFO, "Movimiento Egreso" , movimientoEgresoOp);
					LOGGER.info("Movimiento Ingreso: " + movimientoIngresoOp);
					LOGGER.info("Movimiento Egreso: " + movimientoEgresoOp);
				}
				
				session.logger.log(LogStatus.INFO, "Comprobante" , comprobanteOp);
				
				LOGGER.info("Comprobante: " + comprobanteOp);
				session.setAbono(abonoOp);
				session.setCargo(cargoOp);
				session.setComprobante(comprobanteOp);
				session.setMovimientoEgreso(movimientoEgresoOp);
				session.setMovimientoIngreso(movimientoIngresoOp);
				UtilesSelenium.waitForLoad(session.getConfigDriver());

				// Aceptar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());

			}

			// Comprobantes de Facturación
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_TABCOMPROBANTEFACTURACION)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Ingreso Comprobante Facturación", session);
			
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_CLIENTEFAC)).sendKeys(cliente, Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());


			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARFAC)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			if (datos.getInstrumento().equals("ARBITRAJE INTERBANCARIO")) {

				// buscar comprobante Venta
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_SECUENCIA))
						.sendKeys(ConstantesSpot.SUB_ZEROS + comprobanteOp + Keys.ENTER);
				UtilesSelenium.waitForLoad(session.getConfigDriver());

				
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_FOLIO_FAC + comprobanteOp
						+ Constantes.XPATHERE_OUT)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Busqueda por comprobante" + comprobanteOp, session);
				
				grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_COMP_FACTURACION)).getText();
				LOGGER.info(grilla);
				
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				LOGGER.info("=======================================");
				LOGGER.info(session.getMontoPrincipal());
				LOGGER.info(SpotUtiles.formatoMontos(session.getMontoPrincipal()));
				LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoPrincipal()));
				LOGGER.info("=======================================");
				LOGGER.info(grilla);
				LOGGER.info(SpotUtiles.formatoMontos(grilla));
				LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
				LOGGER.info("=======================================");
				
				if(SpotUtiles.validacionValorGrilla2(session.getMontoPrincipal(), grilla)) {
					//Validacion correcta 
					session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoPrincipal()+ " Es Igual a " + grilla);
				}else {
					//error
					session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoPrincipal()+ " Es Distinto a " + grilla);
								}

				// Enviar a DTE.
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ENVIARFAC)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Desea enviar comprobante seleccionado al DTE", session);

				// Confirmar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARFAC)).click();
				UtilesSelenium.waitForLoadLong(session.getConfigDriver());
				UtilesExtentReport.captura("El comprobante " + comprobanteOp+ " fue enviado con éxito", session);
				// Aceptar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARFAC)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());

				// buscar comprobante Compra
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_LIMPIAR_SEC)).click();
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_SECUENCIA))
						.sendKeys(ConstantesSpot.SUB_ZEROS + comprobanteVentaOp + Keys.ENTER);
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				

				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_FOLIO_FAC + comprobanteVentaOp
						+ Constantes.XPATHERE_OUT)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Busqueda por comprobante" + comprobanteVentaOp, session);
				
				grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_COMP_FACTURACION)).getText();
				LOGGER.info(grilla);
				
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				LOGGER.info("=======================================");
				LOGGER.info(session.getMontoSecundario());
				LOGGER.info(SpotUtiles.formatoMontos(session.getMontoSecundario()));
				LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoSecundario()));
				LOGGER.info("=======================================");
				LOGGER.info(grilla);
				LOGGER.info(SpotUtiles.formatoMontos(grilla));
				LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
				LOGGER.info("=======================================");
				
				if(SpotUtiles.validacionValorGrilla2(session.getMontoSecundario(), grilla)) {
					//Validacion correcta 
					session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Igual a " + grilla);
				}else {
					//error
					session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Distinto a " + grilla);
								}
				

				// Enviar a DTE.
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ENVIARFAC)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Desea enviar comprobante seleccionado al DTE", session);

				// Confirmar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARFAC)).click();
				UtilesSelenium.waitForLoadLong(session.getConfigDriver());
				UtilesExtentReport.captura("El comprobante " + comprobanteOp+ " fue enviado con éxito", session);

				// Aceptar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARFAC)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());

			} else {

				// buscar comprobante Venta
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_SECUENCIA))
						.sendKeys(ConstantesSpot.SUB_ZEROS + comprobanteOp + Keys.ENTER);
				UtilesSelenium.waitForLoad(session.getConfigDriver());

				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATHERE_COMPARAR_FOLIO_FAC + comprobanteOp
						+ Constantes.XPATHERE_OUT)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Busqueda por comprobante" + comprobanteOp, session);
				
				grilla = UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_MONTO_GRILLA_COMP_FACTURACION)).getText();
				LOGGER.info(grilla);
				
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				LOGGER.info("=======================================");
				LOGGER.info(session.getMontoSecundario());
				LOGGER.info(SpotUtiles.formatoMontos(session.getMontoSecundario()));
				LOGGER.info(SpotUtiles.formatoBigDecimal(session.getMontoSecundario()));
				LOGGER.info("=======================================");
				LOGGER.info(grilla);
				LOGGER.info(SpotUtiles.formatoMontos(grilla));
				LOGGER.info(SpotUtiles.formatoBigDecimal(grilla));
				LOGGER.info("=======================================");
				
				if(SpotUtiles.validacionValorGrilla2(session.getMontoSecundario(), grilla)) {
					//Validacion correcta 
					session.logger.log(LogStatus.PASS, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Igual a " + grilla);
				}else {
					//error
					session.logger.log(LogStatus.WARNING, "Validación de Monto Ingresado " , "Monto "+session.getMontoSecundario()+ " Es Distinto a " + grilla);
								}

				// Enviar a DTE.
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ENVIARFAC)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("Desea enviar comprobante seleccionado al DTE", session);
				
				// Confirmar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARFAC)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());
				UtilesExtentReport.captura("El comprobante " + comprobanteOp+ " fue enviado con éxito", session);

				// Aceptar
				UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARFAC)).click();
				UtilesSelenium.waitForLoad(session.getConfigDriver());

			}

			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("Gestion de facturacion - Datos enviados a DTE");
			session.logger.log(LogStatus.INFO, "Gestion de facturacion", "Datos enviados a DTE");
			UtilesExtentReport.captura("Gestion de facturacion - Datos enviados a DTE", session);
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error Gestion de Facturacion - Datos facturacion - Spot", session);
			session.logger.log(LogStatus.ERROR,
					"Error: Gestion de Facturacion - Datos facturacion - Spot", "Datos: " + e.getMessage());
			UtilesSelenium.findElement(session.getConfigDriver(),By.xpath(ConstantesFacturacion.XPATH_BTN_ERROR)).click();
			CerrarVentana.init(session);
			SeleccionMenu.seleccionarMenuFacturacion(session);
			return false;
		}
	}

}