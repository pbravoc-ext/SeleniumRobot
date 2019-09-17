package cl.bcs.application.suite;



import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.plataforma.Login;
import cl.bcs.spot.IngresoOperacionSpot;
import cl.bcs.spot.MantenedorPuntas;
import cl.bcs.spot.SeleccionarSpot;

public class Spot {
	public static boolean suiteSpot(SpotExcel usu) {
		boolean result = false;
		result = SeleccionarSpot.seleccionarMantenedorPuntas();
		if(!result) {
			cerrar();
		}
		result = MantenedorPuntas.mantenedorPuntas();
		if(!result) {
			cerrar();
		}
		result = SeleccionarSpot.seleccionarIngresoOperacionSpot();
		if(!result) {
			cerrar();
		}
		result = IngresoOperacionSpot.datosOperacion(usu);
		if(!result) {
			cerrar();
		}
		result = IngresoOperacionSpot.formadePago(usu);
		if(!result) {
			cerrar();
		}
		result = IngresoOperacionSpot.otros();
		if(!result) {
			cerrar();
		}
		return result;
	}

	private static void cerrar() {

		Session.getConfigDriver().getWebDriver().quit();
	}

	public static void inicio() {
		Login.login();
		SeleccionarSpot.init();
	}

}
