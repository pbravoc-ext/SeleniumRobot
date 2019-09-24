package cl.bcs.application.spot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.LogStatus;

import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.SpotUtiles;
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

	protected static String formatoBigDeciaml(String valor) {
		valor = valor.replaceAll(" ", "");
		valor = valor.replaceAll("[a-zA-Z]", "");
		valor = valor.replace(".", "");
		valor = valor.replace(",", ".");
		return valor;
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

	protected static float formatoMontoMargen(String margen) {
		margen = margen.replaceAll(" ", "");
		margen = margen.replaceAll("[a-zA-Z]", "");
		return formato(margen);
	}
	
	protected static boolean validacionInputCosto(String Dato) {		
		if(Dato.equals("true")) {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion de campo T/C Costo",
					"T/C Costo no esta disponible");
			LOGGER.info("T/C Costo no esta disponible");
			return true;
		}else {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion de campo T/C Costo",
					"T/C Costo esta disponible");
			LOGGER.info("T/C Costo esta disponible");
			return false;
		}
	}

	protected static void validacionMargen_NA(String montoFinal, String margen, String valotTcCosto,
			String valorTcCierre) {
		java.math.BigDecimal MontoBD = new java.math.BigDecimal(formatoBigDeciaml(montoFinal));
		java.math.BigDecimal Costo = new java.math.BigDecimal(formatoBigDeciaml(valotTcCosto));
		java.math.BigDecimal Cierre = new java.math.BigDecimal(formatoBigDeciaml(valorTcCierre));
		java.math.BigDecimal Resta = Costo.subtract(Cierre);
		java.math.BigDecimal MargenCalc = MontoBD.multiply(Resta);
		java.math.BigDecimal MARGEN = new java.math.BigDecimal(formatoBigDeciaml(margen));
		if (MargenCalc.compareTo(MARGEN) == 0) {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion exitosa de Margen para no arbitraje",
					"Datos: " + MARGEN);
			LOGGER.info("Validacion exitosa de Margen para no arbitraje: " + MARGEN);
		} else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Margen no Valido para no arbitraje",
					"CLP " + MargenCalc + " debe ser igual a " + MARGEN);
			LOGGER.info("Margen no valido para no arbitraje a margen calculado");
		}
	}

	protected static void validacionMargen_A(String montoFinal, String margen, String valorParidadCosto,
			String valorParidadCierre) {
		java.math.BigDecimal MontoBD = new java.math.BigDecimal(formatoBigDeciaml(montoFinal));
		java.math.BigDecimal Costo = new java.math.BigDecimal(formatoBigDeciaml(valorParidadCosto));
		java.math.BigDecimal Cierre = new java.math.BigDecimal(formatoBigDeciaml(valorParidadCierre));
		java.math.BigDecimal Resta = Costo.subtract(Cierre);
		java.math.BigDecimal MargenCalc = MontoBD.multiply(Resta);
		java.math.BigDecimal MARGEN = new java.math.BigDecimal(formatoBigDeciaml(margen));
		if (MargenCalc.compareTo(MARGEN) == 0) {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Validacion exitosa de Margen para arbitraje",
					"Datos: " + MARGEN);
			LOGGER.info("Validacion exitosa de Margen para arbitraje: " + MARGEN);
		} else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Margen no Valido para arbitraje",
					"CLP " + MargenCalc + " debe ser igual a " + MARGEN);
			LOGGER.info("Margen no valido para arbitraje a margen calculado");
		}
	}

	protected static void validacionMontoEquivalente_NA(String montoEquivalente, String montoFinal,
			String valorTcCierre) {
		java.math.BigDecimal nuevoMontoEquivalente = new java.math.BigDecimal(formatoBigDeciaml(montoEquivalente));
		java.math.BigDecimal nuevoMontoFinal = new java.math.BigDecimal(formatoBigDeciaml(montoFinal));
		java.math.BigDecimal nuevoValorTcCierre = new java.math.BigDecimal(formatoBigDeciaml(valorTcCierre));
		java.math.BigDecimal montoCalculado = nuevoMontoFinal.multiply(nuevoValorTcCierre);
		if (montoCalculado.compareTo(nuevoMontoEquivalente) == 0) {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Monto Equivalente Valido para no arbitraje",
					"Datos: " + nuevoMontoEquivalente);
			LOGGER.info("Validacion exitosa de Monto Equivalente para arbitraje: " + nuevoMontoEquivalente);
		}else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Monto Equivalente no Valido para no arbitraje",
					"CLP " + montoCalculado + " debe ser igual a " + nuevoMontoEquivalente);
			LOGGER.info("Monto equivalente no valido para no arbitraje a monto calculado");
		}

	}

	protected static void validacionMontoEquivalente_A(String montoEquivalente, String montoFinal,
			String valorParidadCierre) {
		java.math.BigDecimal nuevoMontoEquivalente = new java.math.BigDecimal(formatoBigDeciaml(montoEquivalente));
		java.math.BigDecimal nuevoMontoFinal = new java.math.BigDecimal(formatoBigDeciaml(montoFinal));
		java.math.BigDecimal nuevoValorParidadCierre = new java.math.BigDecimal(formatoBigDeciaml(valorParidadCierre));
		java.math.BigDecimal montoCalculado = nuevoMontoFinal.multiply(nuevoValorParidadCierre);
		if (montoCalculado.compareTo(nuevoMontoEquivalente) == 0) {
			Session.getConfigDriver().logger.log(LogStatus.INFO, "Monto Equivalente Valido para arbitraje",
					"Datos: " + nuevoMontoEquivalente);
			LOGGER.info("Validacion exitosa de Monto Equivalente para arbitraje: " + nuevoMontoEquivalente);
		}else {
			Session.getConfigDriver().logger.log(LogStatus.WARNING, "Monto Equivalente no Valido para arbitraje",
					"CLP " + montoCalculado + " debe ser igual a " + nuevoMontoEquivalente);
			LOGGER.info("Monto equivalente no valido para arbitraje a monto calculado");
		}
	}

}
