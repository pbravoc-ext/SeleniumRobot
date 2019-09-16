package cl.bcs.application.spot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.factory.util.ExtentReport;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.spot.MantenedorPuntas;

public class IngresoOperacionSpotUtil {
	
	private static final Logger LOGGER = Log4jFactory.getLogger(MantenedorPuntas.class);
	
	protected static Float formato(String cantidad) {
		cantidad = cantidad.replace(".","");
		cantidad = cantidad.replace(",", ".");
		float example = Float.parseFloat(cantidad);
		return example;
	}
	
	protected static String folioOperacion(String label) {
		return label.replaceAll("[^0-9]", "");
	}
	
	protected static String comparar(String fecha1,String fecha2) {
		try {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		Date date1 = sdf.parse(fecha1); 
		Date date2 = sdf.parse(fecha2);
		int valor = date1.compareTo(date2);
			if (valor >= 0) {
				ExtentReport.Captura("Fecha Correcta");
				LOGGER.info("Fecha Correcta ");
			}
			else {
				ExtentReport.CapturaError("Fecha forma de pago 'Recibimos' es mayor a 'Entregamos'");
				LOGGER.error("Fecha Incorrecta");
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
		}		
		return ""; 
	}
	
	protected static void validacionValoresPunta(String puntaCompra, String puntaVenta, String IOSPuntaCompra, String IOSPuntaVenta) {
		if (Float.compare(formato(IOSPuntaCompra), formato(puntaCompra)) == 0) { 
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa", "Comparacion valores Punta Compra");
			LOGGER.info("IOSPuntaCompra OK");
		}else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida", "Comparacion valores Punta Compra");
			LOGGER.info("IOSPuntaCompra NO");
		}
		Session.getConfigDriver().waitForLoad();
		
		if (Float.compare(formato(IOSPuntaVenta), formato(puntaVenta)) == 0) { 
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion Existosa", "Comparacion valores Punta Venta");
			LOGGER.info("IOSPuntaVenta OK");
		}else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Validacion Fallida", "Comparacion valores Punta Venta");
			LOGGER.info("IOSPuntaVenta NO");
		}
		Session.getConfigDriver().waitForLoad();
	}
}
