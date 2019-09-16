package cl.bcs.spot;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import cl.bcs.application.constantes.util.ConstantesMantenedorPuntas;
import cl.bcs.application.constantes.util.ConstantesSpot;
import cl.bcs.application.constantes.util.ConstantesSpotTags;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.spot.util.MantenedorUtil;
import cl.bcs.application.spot.util.VariablesUtil;
import cl.bcs.application.utiles.UtilesSelenium;
import cl.bcs.plataforma.CerrarVentana;

public class MantenedorPuntas extends MantenedorUtil {
	private static WebDriver webDriver = null;

	public MantenedorPuntas(WebDriver driver) {
		webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	private static final Logger LOGGER = Log4jFactory.getLogger(MantenedorPuntas.class);

	public static boolean mantenedorPuntas() {
		LOGGER.info("Mantenedor de puntas -  Spot");
		Session.getConfigDriver().waitForLoad();
		VariablesUtil m = new VariablesUtil();
		try {
			switch (validarmoneda()) {
			case "USD":
				m.setCompra(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_COMPRA_USD)).getText());
				m.setMonto(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_MONTO_USD)).getText());
				m.setPuntaCompra(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_PUNTA_COMPRA))
						.getAttribute(ConstantesSpotTags.TAG_TITLE));
				m.setPuntaVenta(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_PUNTA_VENTA))
						.getAttribute(ConstantesSpotTags.TAG_TITLE));
				m.setVenta(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_VENTA_USD)).getText());
				LOGGER.info("Datos USD");
				LOGGER.info("Valor Compra: " + m.getPuntaCompra());
				LOGGER.info("Valor Venta: " + m.getPuntaVenta());
				LOGGER.info("Monto USD: " + m.getMonto());
				LOGGER.info("Compra USD: " + m.getCompra());
				LOGGER.info("Venta USD: " + m.getVenta());
				Session.setVariables(m);
				CerrarVentana.init();
				return true;
			case "EUR":
				if (findEUR()) {
					m.setCompra(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_COMPRA_EUR)).getText());
					m.setMonto(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_MONTO_EUR)).getText());
					m.setPuntaCompra(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_PUNTA_COMPRA))
							.getAttribute(ConstantesSpotTags.TAG_TITLE));
					m.setPuntaVenta(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_PUNTA_VENTA))
							.getAttribute(ConstantesSpotTags.TAG_TITLE));
					m.setVenta(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_VENTA_EUR)).getText());
					m.setMontoMil(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_MONTO_EUR_MIL)).getText());
					m.setCompraMil(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_COMPRA_EUR_MIL)).getText());
					m.setVentaMil(UtilesSelenium.findElement(By.xpath(ConstantesMantenedorPuntas.XPATH_VENTA_EUR_MIL)).getText());
					LOGGER.info("Datos EUR");
					LOGGER.info("Valor Compra: " + m.getPuntaCompra());
					LOGGER.info("Valor Venta: " + m.getPuntaVenta());
					LOGGER.info("Monto EUR: " + m.getMonto());
					LOGGER.info("Compra EUR: " + m.getCompra());
					LOGGER.info("Venta EUR: " + m.getVenta());
					LOGGER.info("Monto EUR 1000: " + m.getMonto());
					LOGGER.info("Compra EUR 1000: " + m.getCompra());
					LOGGER.info("Venta EUR 1000: " + m.getVenta());
					Session.setVariables(m);
					CerrarVentana.init();
					return true;
				}
			default:
				LOGGER.error("Fuera de rango -  moneda");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + " - Spot");
			return false;
		}
	}

}
