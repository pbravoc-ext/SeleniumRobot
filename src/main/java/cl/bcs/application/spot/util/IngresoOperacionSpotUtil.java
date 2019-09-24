package cl.bcs.application.spot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesExtentReport;
import cl.bcs.spot.MantenedorPuntas;
/**
 * 
 * @author Narveider
 *
 */
public class IngresoOperacionSpotUtil {

	private static final Logger LOGGER = Log4jFactory.getLogger(MantenedorPuntas.class);

	/**
	 * 
	 * @param cantidad
	 * @return
	 */
	protected static Float formato(String cantidad) {
		cantidad = cantidad.replace(".", "");
		cantidad = cantidad.replace(",", ".");
		return Float.parseFloat(cantidad);
	}

	/**
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return
	 */
	// modificar
	protected static String comparar(String fecha1, String fecha2) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = sdf.parse(fecha1);
			Date date2 = sdf.parse(fecha2);
			int valor = date1.compareTo(date2);
			if (valor >= 0) {
				UtilesExtentReport.captura("Validacion de fecha es correcta");
				LOGGER.info("Fecha Correcta ");
			} else {
				UtilesExtentReport.capturaError("Fecha forma de pago 'Recibimos' es mayor a 'Entregamos'");
				LOGGER.error("Fecha Incorrecta");
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
		}
		return "";
	}

	/**
	 * 
	 * @param puntaCompra
	 * @param puntaVenta
	 * @param iosPuntaCompra
	 * @param iosPuntaVenta
	 */
	protected static void validacionValoresPunta(String puntaCompra, String puntaVenta, String iosPuntaCompra,
			String iosPuntaVenta) {
		if (Float.compare(formato(iosPuntaCompra), formato(puntaCompra)) == 0) {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa",
					"Comparacion valores Punta Compra");
			LOGGER.info("IOSPuntaCompra OK");
		} else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida",
					"Comparacion valores Punta Compra");
			LOGGER.info("IOSPuntaCompra NO");
		}
		Session.getConfigDriver().waitForLoad();

		if (Float.compare(formato(iosPuntaVenta), formato(puntaVenta)) == 0) {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa",
					"Comparacion valores Punta Venta");
			LOGGER.info("IOSPuntaVenta OK");
		} else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida",
					"Comparacion valores Punta Venta");
			LOGGER.info("IOSPuntaVenta NO");
		}
		Session.getConfigDriver().waitForLoad();
	}
}
