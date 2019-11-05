package cl.bcs.facturacion;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.relevantcodes.extentreports.LogStatus;
import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.constantes.util.ConstantesFacturacion;
import cl.bcs.application.constantes.util.ConstantesRV;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;
import cl.bcs.plataforma.SeleccionMenu;

public class Facturacion {
	private static WebDriver webDriver = null;

	public Facturacion(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(SeleccionMenu.class);

	public static boolean gestionFacturacion(Object dato, Session session) {
		RVExcel datos = (RVExcel) dato;
		String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";

		// Datos Movimientos a Facturar

		try {
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesExtentReport.captura("Gestion de facturacion - Movimientos a facturar", session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Movimientos a Facturar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV))
					.sendKeys(cliente);
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV))
					.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARMOV))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			String usarFolio = session.getFolioAleatorio();

			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_FOLIOINPUT))
					.sendKeys(Constantes.SUB_ZEROS + usarFolio, Keys.ENTER);
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.XPATHERE + usarFolio + Constantes.XPATHERE_OUT)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Fecha Transaccion Factura
			String fechaTransaccion = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.FULL_XPATH_FECHA_TRANSACCION)).getText();
			LOGGER.info("Fecha Transaccion: " + fechaTransaccion);
			session.setFechaTransaccion(fechaTransaccion);
			LOGGER.info("Fecha Transaccion: " + session.getFechaTransaccion());

			// Fecha Liquidacion Factura
			String fechaLiquidacion = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.FULL_XPATH_FECHA_LIQUIDACION)).getText();
			LOGGER.info("Fecha Liquidacion: " + fechaLiquidacion);
			session.setFechaLiquidacion(fechaLiquidacion);
			LOGGER.info("Fecha Liquidacion: " + session.getFechaLiquidacion());

			// Monto total local (para compara en cuenta de inversion con el cargo)
			String montoTotalLocal = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.FULL_XPATH_MONTO_TOTAL_LOCAL)).getText();
			LOGGER.info("Monto Total Local: " + montoTotalLocal);
			session.setMontoTotalLocal(montoTotalLocal);
			LOGGER.info("Monto Total Local: " + session.getMontoTotalLocal());

			// Monto factura
			String montoFactura = UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_MONTO_FACTURA_RV))
					.getText();
			LOGGER.info("Monto Facturado: " + montoFactura);
			session.setMontoFactura(montoFactura);
			LOGGER.info("Monto Facturado: " + session.getMontoFactura());

			if (validacionMontos(montoFactura, session.getMontoAsignacion())) {
				LOGGER.info("Validacion exitosa - Monto facturado: " + montoFactura + " es igual al monto asignado: "
						+ session.getMontoAsignacion());
				session.logger.log(LogStatus.PASS, "Validacion Monto Facturado", "Monto facturado " + montoFactura
						+ " es igual al monto asignado " + session.getMontoAsignacion());
			} else {
				LOGGER.info("Validacion errónea - Monto facturado: " + montoFactura
						+ " debe ser igual al monto asignado: " + session.getMontoAsignacion());
				session.logger.log(LogStatus.WARNING, "Validacion Monto Facturado", "Monto facturado " + montoFactura
						+ " debe ser igual al monto asignado " + session.getMontoAsignacion());
			}

			// Generar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV))
					.click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			UtilesExtentReport.captura("Desea Facturar Movimiento Seleccionado", session);

			// Confirmar
			UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV))
					.click();
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

			UtilesExtentReport.captura("Gestion de facturacion - Datos capturados", session);
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());
			// Rescatar Datos
			String folioCartera = null;
			String cargo = null;
			String abono = null;
			String movimientoIngreso = null;
			String movimientoEgreso = null;
			String comprobanteIngreso = null;
//			String comprobanteEgreso = null;
			String comprobante = null;
			String folioCarteraOp = null;
			String cargoOp = null;
			String abonoOp = null;
			String movimientoIngresoOp = null;
			String movimientoEgresoOp = null;
			String comprobanteIngresoOp = null;
			String comprobanteEgresoOp = null;
			String comprobanteOp = null;

			if (datos.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
				if (datos.getTipoInstrumento().equalsIgnoreCase(ConstantesRV.INSTRUMENTO_ACN)) { // ACCIONES NACIONALES
					if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) {
						if (datos.getNominal().equalsIgnoreCase(Constantes.SI)) { // COMPRA + NOMINAL + TESORERIA

							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							movimientoIngreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							cargoOp = SpotUtiles.onlyNumbers(cargo);
							movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Cargo             : " + cargoOp);
							LOGGER.info("Movimiento Ingreso: " + movimientoIngresoOp);
							LOGGER.info("Comprobante       : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Cargo :", cargoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Ingreso :", movimientoIngresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						} else { // COMPRA + CUSTODIA + TESORERÍA

							folioCartera = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							movimientoIngreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							comprobanteIngreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_4)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_5)).getText();
							folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
							cargoOp = SpotUtiles.onlyNumbers(cargo);
							movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
							comprobanteIngresoOp = SpotUtiles.onlyNumbers(comprobanteIngreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Folio Cartera      : " + folioCarteraOp);
							LOGGER.info("Cargo              : " + cargoOp);
							LOGGER.info("Movimiento Ingreso : " + movimientoIngresoOp);
							LOGGER.info("Comprobante Ingreso: " + comprobanteIngresoOp);
							LOGGER.info("Comprobante        : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Folio Cartera :", folioCarteraOp);
							session.logger.log(LogStatus.INFO, "Cargo :", cargoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Ingreso :", movimientoIngresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante Ingreso :", comprobanteIngresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						}
					} else {
						// cuenta inversion
						if (datos.getNominal().equalsIgnoreCase(Constantes.SI)) {// COMPRA + NOMINAL + CUENTA INVERSION

							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							cargoOp = SpotUtiles.onlyNumbers(cargo);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Cargo        : " + cargoOp);
							LOGGER.info("Comprobante  : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Cargo :", cargoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						} else { // COMPRA + CUSTODIA + CUENTA INVERSION

							folioCartera = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
							cargoOp = SpotUtiles.onlyNumbers(cargo);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Folio Cartera: " + folioCarteraOp);
							LOGGER.info("Cargo        : " + cargoOp);
							LOGGER.info("Comprobante  : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Folio Cartera :", folioCarteraOp);
							session.logger.log(LogStatus.INFO, "Cargo :", cargoOp);
							session.logger.log(LogStatus.INFO, "Folio Cartera :", comprobanteOp);

						}
					}

				} else {
					// ACCIONES EXTRANJERAS

					if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.SI)) {
						if (datos.getNominal().equalsIgnoreCase(Constantes.SI)) { // COMPRA + NOMINAL + CUENTA INVERSION

							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							movimientoIngreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							cargoOp = SpotUtiles.onlyNumbers(cargo);
							movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Cargo             : " + cargoOp);
							LOGGER.info("Movimiento Ingreso: " + movimientoIngresoOp);
							LOGGER.info("Comprobante       : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Cargo :", cargoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Ingreso :", movimientoIngresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						} else {// COMPRA + CUSTODIA + CUENTA INVERSION

							folioCartera = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							movimientoIngreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_4)).getText();

							folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
							cargoOp = SpotUtiles.onlyNumbers(cargo);
							movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);

							LOGGER.info("Folio Cartera      : " + folioCarteraOp);
							LOGGER.info("Cargo              : " + cargoOp);
							LOGGER.info("Movimiento Ingreso : " + movimientoIngresoOp);
							LOGGER.info("Comprobante        : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Folio Cartera :", folioCarteraOp);
							session.logger.log(LogStatus.INFO, "Cargo :", cargoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Ingreso :", movimientoIngresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						}

					} else {
						if (datos.getNominal().equalsIgnoreCase(Constantes.NO)) {// COMPRA + CUSTODIA + TESORERÍA
							folioCartera = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							movimientoIngreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_4)).getText();
							folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
							cargoOp = SpotUtiles.onlyNumbers(cargo);
							movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Folio Cartera      : " + folioCarteraOp);
							LOGGER.info("Cargo              : " + cargoOp);
							LOGGER.info("Movimiento Ingreso : " + movimientoIngresoOp);
							LOGGER.info("Comprobante        : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Folio Cartera :", folioCarteraOp);
							session.logger.log(LogStatus.INFO, "Cargo :", cargoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Ingreso :", movimientoIngresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						} else {// COMPRA + NOMINAL + TESORERÍA

							cargo = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							cargoOp = SpotUtiles.onlyNumbers(abono);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Cargo        : " + cargoOp);
							LOGGER.info("Comprobante  : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Abono :", cargoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);
						}

					}

				}

			} else {
				// VENTA
				if (datos.getTipoInstrumento().equalsIgnoreCase(ConstantesRV.INSTRUMENTO_ACN)) {// ACCIONES
					if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) { // SIN CUENTA INVERSION
						if (datos.getNominal().equalsIgnoreCase(Constantes.SI)) { // ES NOMINAL
							abono = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							movimientoEgreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							abonoOp = SpotUtiles.onlyNumbers(abono);
							movimientoEgresoOp = SpotUtiles.onlyNumbers(movimientoEgreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);

							LOGGER.info("Abono             : " + abonoOp);
							LOGGER.info("Movimiento Egreso : " + movimientoEgresoOp);
							LOGGER.info("Comprobante       : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Abono :", abonoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Egreso :", movimientoEgresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante  :", comprobanteOp);

						} else {
							// VARIACION 6
							folioCartera = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							abono = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							movimientoEgreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_4)).getText();
							folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
							abonoOp = SpotUtiles.onlyNumbers(abono);
							movimientoEgresoOp = SpotUtiles.onlyNumbers(movimientoEgreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Folio Cartera     : " + folioCarteraOp);
							LOGGER.info("Abono             : " + abonoOp);
							LOGGER.info("Movimiento Egreso : " + movimientoEgresoOp);
							LOGGER.info("Comprobante       : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Folio Cartera :", folioCarteraOp);
							session.logger.log(LogStatus.INFO, "Abono :", abonoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Egreso :", movimientoEgresoOp);
							session.logger.log(LogStatus.INFO, "Folio Cartera :", comprobanteOp);
						}
					}

				} else { // ACCIONES EXTRANJERAS
					if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) { // SIN CUENTA INVERSION
						if (datos.getNominal().equalsIgnoreCase(Constantes.NO)) {// VENTA + CUSTODIA + TESORERÍA
							folioCartera = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							abono = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							movimientoEgreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_4)).getText();

							folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
							abonoOp = SpotUtiles.onlyNumbers(abono);
							movimientoEgresoOp = SpotUtiles.onlyNumbers(movimientoEgreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);

							LOGGER.info("Folio Cartera      : " + folioCarteraOp);
							LOGGER.info("Abono              : " + abonoOp);
							LOGGER.info("Movimiento Egreso : " + movimientoEgresoOp);
							LOGGER.info("Comprobante        : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Folio Cartera :", folioCarteraOp);
							session.logger.log(LogStatus.INFO, "Abono :", abonoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Egreso :", movimientoEgresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						} else {// VENTA + NOMINAL + TESORERÍA

							abono = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							movimientoEgreso = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							abonoOp = SpotUtiles.onlyNumbers(abono);
							movimientoEgresoOp = SpotUtiles.onlyNumbers(movimientoEgreso);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Abono        : " + abonoOp);
							LOGGER.info("Movimiento Egreso : " + movimientoEgresoOp);
							LOGGER.info("Comprobante  : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Abono :", abonoOp);
							session.logger.log(LogStatus.INFO, "Movimiento Egreso :", movimientoEgresoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);
						}

					} else {
						if (datos.getNominal().equalsIgnoreCase(Constantes.SI)) {// VENTA + NOMINAL + CUENTA INVERSION

							abono = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							abonoOp = SpotUtiles.onlyNumbers(abono);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Abono        : " + abonoOp);
							LOGGER.info("Comprobante  : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Abono :", abonoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						} else {// VENTA + CUSTODIA + CUENTA INVERSION

							folioCartera = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1)).getText();
							abono = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2)).getText();
							comprobante = UtilesSelenium.findElement(session.getConfigDriver(),
									By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3)).getText();
							folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
							abonoOp = SpotUtiles.onlyNumbers(abono);
							comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
							LOGGER.info("Folio Cartera: " + folioCarteraOp);
							LOGGER.info("Abono        : " + abonoOp);
							LOGGER.info("Comprobante  : " + comprobanteOp);

							session.logger.log(LogStatus.INFO, "Folio Cartera :", folioCarteraOp);
							session.logger.log(LogStatus.INFO, "Abono :", abonoOp);
							session.logger.log(LogStatus.INFO, "Comprobante :", comprobanteOp);

						}

					}
				}
			}

			session.setFolioCartera(folioCarteraOp);
			session.setAbono(abonoOp);
			session.setCargo(cargoOp);
			session.setComprobante(comprobanteOp);
			session.setMovimientoEgreso(movimientoEgresoOp);
			session.setMovimientoIngreso(movimientoIngresoOp);
			session.setComprobanteIngreso(comprobanteIngresoOp);
			session.setComprobanteEgreso(comprobanteEgresoOp);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Aceptar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO))
					.click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Comprobantes de Facturación
			UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.XPATH_TABCOMPROBANTEFACTURACION)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_CLIENTEFAC))
					.sendKeys(cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_CLIENTEFAC))
					.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARFAC))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_SECUENCIA))
					.sendKeys(Constantes.SUB_ZEROS + comprobanteOp + Keys.ENTER);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(
							ConstantesFacturacion.XPATH_COMPARARFOLIOFAC + comprobanteOp + Constantes.XPATHERE_OUT))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

//			//Enviar a DTE.
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ENVIARFAC))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Confirmar
			UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARFAC))
					.click();
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

			// Aceptar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARFAC))
					.click();

			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			LOGGER.info("Gestion de facturacion - Datos enviados a DTE");
			session.logger.log(LogStatus.INFO, "Gestion de facturacion", "Datos enviados a DTE");
			UtilesExtentReport.captura("Gestion de facturacion - Datos enviados a DTE", session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Gestión de Facturación ==");
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error-Gestion de Facturacion - Datos facturacion", session);
			session.logger.log(LogStatus.ERROR, "Error: Gestion de Facturacion - Datos facturacion",
					"Datos: " + e.getMessage());

			return false;
		}
	}

	public static boolean movimientosFacturar(Object dato, Session session) {
		try {

			RVExcel datos = (RVExcel) dato;
			String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";
			String usarFolio = session.getFolioAleatorio();

			// Movimientos a Facturar
			WebElement weCliente = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.XPATH_CLIENTEMOV));
			weCliente.sendKeys(cliente);
			weCliente.sendKeys(Keys.ENTER);

			// BTN buscar movimientos
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARMOV))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Ingresar folio
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_FOLIOINPUT))
					.sendKeys(Constantes.SUB_ZEROS + usarFolio, Keys.ENTER);
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

			// Click en la grilla
			UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.XPATHERE + usarFolio + Constantes.XPATHERE_OUT)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Fecha Transaccion Factura
			String fechaTransaccion = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.FULL_XPATH_FECHA_TRANSACCION)).getText();
			LOGGER.info("Fecha Transaccion: " + fechaTransaccion);
			session.setFechaTransaccion(fechaTransaccion);
			LOGGER.info("Fecha Transaccion: " + session.getFechaTransaccion());

			// Fecha Liquidacion Factura
			String fechaLiquidacion = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.FULL_XPATH_FECHA_LIQUIDACION)).getText();
			LOGGER.info("Fecha Liquidacion: " + fechaLiquidacion);
			session.setFechaLiquidacion(fechaLiquidacion);
			LOGGER.info("Fecha Liquidacion: " + session.getFechaLiquidacion());

			// Monto total local (para compara en cuenta de inversion con el cargo)
			String montoTotalLocal = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.FULL_XPATH_MONTO_TOTAL_LOCAL)).getText();
			LOGGER.info("Monto Total Local: " + montoTotalLocal);
			session.setMontoTotalLocal(montoTotalLocal);
			LOGGER.info("Monto Total Local: " + session.getMontoTotalLocal());

			// Monto factura
			String montoFactura = UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_MONTO_FACTURA_RV))
					.getText();
			LOGGER.info("Monto Facturado: " + montoFactura);
			session.setMontoFactura(montoFactura);
			LOGGER.info("Monto Facturado: " + session.getMontoFactura());

			if (validacionMontos(montoFactura, session.getMontoAsignacion())) {
				LOGGER.info("Validacion exitosa - Monto facturado: " + montoFactura + " es igual al monto asignado: "
						+ session.getMontoAsignacion());
				session.logger.log(LogStatus.PASS, "Validacion Monto Facturado", "Monto facturado " + montoFactura
						+ " es igual al monto asignado " + session.getMontoAsignacion());
			} else {
				LOGGER.info("Validacion errónea - Monto facturado: " + montoFactura
						+ " debe ser igual al monto asignado: " + session.getMontoAsignacion());
				session.logger.log(LogStatus.WARNING, "Validacion Monto Facturado", "Monto facturado " + montoFactura
						+ " debe ser igual al monto asignado " + session.getMontoAsignacion());
			}

			// Generar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_GENERARMOV))
					.click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Confirmar
			UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARMOV))
					.click();
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

			// Rescatar Datos
			String folioCartera = null;
			String cargo = null;
			String abono = null;
			String movimientoIngreso = null;
			String movimientoEgreso = null;
			String comprobanteIngreso = null;
			String comprobanteEgreso = null;
			String comprobante = null;
			String folioCarteraOp = null;
			String cargoOp = null;
			String abonoOp = null;
			String movimientoIngresoOp = null;
			String movimientoEgresoOp = null;
			String comprobanteIngresoOp = null;
			String comprobanteEgresoOp = null;
			String comprobanteOp = null;

			if (datos.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
				if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) {
					folioCartera = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1))
							.getText();
					cargo = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2))
							.getText();
					movimientoIngreso = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3))
							.getText();
					comprobanteIngreso = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_4))
							.getText();
					comprobante = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_5))
							.getText();
					folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
					cargoOp = SpotUtiles.onlyNumbers(cargo);
					movimientoIngresoOp = SpotUtiles.onlyNumbers(movimientoIngreso);
					comprobanteIngresoOp = SpotUtiles.onlyNumbers(comprobanteIngreso);
					comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
					LOGGER.info("Folio Cartera      : " + folioCarteraOp);
					LOGGER.info("Cargo              : " + cargoOp);
					LOGGER.info("Movimiento Ingreso : " + movimientoIngresoOp);
					LOGGER.info("Comprobante Ingreso: " + comprobanteIngresoOp);
					LOGGER.info("Comprobante        : " + comprobanteOp);
				} else {
					folioCartera = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1))
							.getText();
					cargo = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2))
							.getText();
					comprobante = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3))
							.getText();
					folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
					cargoOp = SpotUtiles.onlyNumbers(cargo);
					comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
					LOGGER.info("Folio Cartera: " + folioCarteraOp);
					LOGGER.info("Cargo        : " + cargoOp);
					LOGGER.info("Comprobante  : " + comprobanteOp);
				}
			} else {
				if (datos.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) {
					folioCartera = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1))
							.getText();
					abono = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2))
							.getText();
					movimientoEgreso = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3))
							.getText();
					comprobanteEgreso = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_4))
							.getText();
					comprobante = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_5))
							.getText();
					folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
					abonoOp = SpotUtiles.onlyNumbers(abono);
					movimientoEgresoOp = SpotUtiles.onlyNumbers(movimientoEgreso);
					comprobanteEgresoOp = SpotUtiles.onlyNumbers(comprobanteEgreso);
					comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
					LOGGER.info("Folio Cartera     : " + folioCarteraOp);
					LOGGER.info("Abono             : " + abonoOp);
					LOGGER.info("Movimiento Egreso : " + movimientoEgresoOp);
					LOGGER.info("Comprobante Egreso: " + comprobanteEgresoOp);
					LOGGER.info("Comprobante       : " + comprobanteOp);
				} else {

					folioCartera = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_1))
							.getText();
					abono = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_2))
							.getText();
					comprobante = UtilesSelenium
							.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.FULL_XPATH_LABEL_3))
							.getText();
					folioCarteraOp = SpotUtiles.onlyNumbers(folioCartera);
					abonoOp = SpotUtiles.onlyNumbers(abono);
					comprobanteOp = SpotUtiles.onlyNumbers(comprobante);
					LOGGER.info("Folio Cartera: " + folioCarteraOp);
					LOGGER.info("Abono        : " + abonoOp);
					LOGGER.info("Comprobante  : " + comprobanteOp);
				}

			}

			session.setFolioCartera(folioCarteraOp);
			session.setAbono(abonoOp);
			session.setCargo(cargoOp);
			session.setComprobante(comprobanteOp);
			session.setMovimientoEgreso(movimientoEgresoOp);
			session.setMovimientoIngreso(movimientoIngresoOp);
			session.setComprobanteIngreso(comprobanteIngresoOp);
			session.setComprobanteEgreso(comprobanteEgresoOp);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Aceptar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARINFO))
					.click();
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error-Gestión de Facturación - Movimientos a Facturar", session);
			session.logger.log(LogStatus.ERROR, "Error - Gestión de Facturación - Movimientos a Facturar",
					e.getMessage());
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ERROR))
					.click();
			CerrarVentana.init(session);
			SeleccionMenu.seleccionarMenuFacturacion(session);
			return false;
		}
	}

	public static boolean comprobantesFacturacion(Object dato, Session session) {
		try {

			RVExcel datos = (RVExcel) dato;
			String cliente = datos.getRut() + " " + datos.getNombre() + " (" + datos.getPortafolio() + ")";
			String comprobante = session.getComprobante();

			// Comprobantes de Facturación
			UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.XPATH_TABCOMPROBANTEFACTURACION)).click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Ingresar cliente
			WebElement weCliente = UtilesSelenium.findElement(session.getConfigDriver(),
					By.xpath(ConstantesFacturacion.XPATH_CLIENTEFAC));
			weCliente.sendKeys(cliente);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			weCliente.sendKeys(Keys.ENTER);
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// BTN buscar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_BUSCARFAC))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Ingresar folio
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_SECUENCIA))
					.sendKeys(Constantes.SUB_ZEROS + comprobante + Keys.ENTER);
			UtilesSelenium.waitForLoadMid(session.getConfigDriver());

			// Click en la grilla
			UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(
							ConstantesFacturacion.XPATH_COMPARARFOLIOFAC + comprobante + Constantes.XPATHERE_OUT))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

//			//Enviar a DTE.
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ENVIARFAC))
					.click();
			UtilesSelenium.waitForLoad(session.getConfigDriver());

			// Confirmar
			UtilesSelenium
					.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_CONFIRMARFAC))
					.click();
			UtilesSelenium.waitForLoadLong(session.getConfigDriver());

			// Aceptar
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ACEPTARFAC))
					.click();

			UtilesSelenium.waitForLoadMid(session.getConfigDriver());
			LOGGER.info("Gestion de facturacion - Datos enviados a DTE");
			session.logger.log(LogStatus.INFO, "Gestion de facturacion", "Datos enviados a DTE");
			UtilesExtentReport.captura("Gestion de facturacion - Datos enviados a DTE", session);
			UtilesSelenium.waitForLoad(session.getConfigDriver());
			LOGGER.info("== Cerrar Gestión de Facturación ==");
			CerrarVentana.init(session);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			UtilesExtentReport.capturaError("Error-Gestión de Facturación - Comprobantes de Facturación", session);
			session.logger.log(LogStatus.ERROR, "Error - Gestión de Facturación - Comprobantes de Facturación",
					e.getMessage());
			UtilesSelenium.findElement(session.getConfigDriver(), By.xpath(ConstantesFacturacion.XPATH_BTN_ERROR))
					.click();
			CerrarVentana.init(session);
			SeleccionMenu.seleccionarMenuFacturacion(session);
			return false;
		}
	}

	/**
	 * Formatea el monto ingresado para usarlo como BigDecimal
	 * 
	 * @param monto
	 * @return monto en formato BigDecimal
	 */
	protected static String formatoBigDecimal(String monto) {
		monto = monto.replace(" ", "");
		monto = monto.replaceAll("[a-zA-Z]", "");
		monto = monto.replace(".", "");
		monto = monto.replace(",", ".");
		return monto;
	}

	/**
	 * Valida que el monto de la fatura sea igual al monto de la asignacion
	 * 
	 * @param montoFactura
	 * @param montoAsignacion
	 * @return Devuelve un 'true' o un 'false'
	 */
	protected static boolean validacionMontos(String montoFactura, String montoAsignacion) {
		BigDecimal montoFinal = new BigDecimal(formatoBigDecimal(montoFactura));
		BigDecimal asignacionFinal = new BigDecimal(formatoBigDecimal(montoAsignacion));
		if (montoFinal.compareTo(asignacionFinal) == 0) {
			return true;
		} else {
			return false;
		}
	}

}
