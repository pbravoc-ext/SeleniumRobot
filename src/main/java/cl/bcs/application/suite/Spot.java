package cl.bcs.application.suite;



import cl.bcs.application.factory.util.SpotExcel;
import cl.bcs.cuenta.inversion.SelecionarCuentaInversion;
import cl.bcs.facturacion.Facturacion;
import cl.bcs.plataforma.Login;
import cl.bcs.spot.ConfirmacionOperacionesSpot;
import cl.bcs.spot.IngresoOperacionSpot;
import cl.bcs.spot.MantenedorPuntas;
import cl.bcs.spot.SeleccionarSpot;

public class Spot {
	public static boolean suiteSpot(SpotExcel usu) {
		boolean result = true;	
		result = SeleccionarSpot.init();
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarMantenedorPuntas();
		if(result == false) {
			return result;
		}
		result = MantenedorPuntas.mantenedorPuntas();
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarIngresoOperacionSpot();
		if(result == false) {
			return result;
		}
		result = IngresoOperacionSpot.datosOperacion(usu);
		if(result == false) {
			return result;
		}
		result = IngresoOperacionSpot.formadePago(usu);
		if(result == false) {
			return result;
		}
		result = IngresoOperacionSpot.otros();
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarMenuConfirmacionOperaciones();
		if(result == false) {
			return result;
		}
		result = ConfirmacionOperacionesSpot.confirmarSpot(usu);
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.init();
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarMenuFacturacion();
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarFacturacion();
		if(result == false) {
			return result;
		}
		result = Facturacion.gestionFacturacion(usu);
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarMenuFacturacion();
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarMenuCuentaInversion();
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarCuentaInversionCliente();
		if(result == false) {
			return result;
		}
		result = SelecionarCuentaInversion.cuentaInversionCliente(usu);
		if(result == false) {
			return result;
		}
		result = SeleccionarSpot.seleccionarMenuCuentaInversion();
		if(result == false) {
			return result;
		}
		return result;
	}

}
