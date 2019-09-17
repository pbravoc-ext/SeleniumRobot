package cl.bcs.application.suite;



import cl.bcs.application.factory.util.Session;
import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.plataforma.Login;
import cl.bcs.spot.IngresoOperacionSpot;
import cl.bcs.spot.MantenedorPuntas;
import cl.bcs.spot.SeleccionarSpot;

public class Spot {
	public static boolean Suite_Spot(SpotExcel usu) {
		boolean result = false;
		result = SeleccionarSpot.seleccionarMantenedorPuntas();
		result = MantenedorPuntas.mantenedorPuntas();
		result = SeleccionarSpot.seleccionarIngresoOperacionSpot();
		result = IngresoOperacionSpot.datosOperacion(usu);
		result = IngresoOperacionSpot.formadePago(usu);
		result = IngresoOperacionSpot.otros();
//			result = ConfirmacionOperacionesSpot.init();
		// result = SeleccionarFacturacion.init();
		// Cerrar();
		return result;
	}

	private static void Cerrar() {

		Session.getConfigDriver().getWebDriver().quit();
	}

	public static void inicio() {
		Login.login();
		SeleccionarSpot.init();
	}

}
