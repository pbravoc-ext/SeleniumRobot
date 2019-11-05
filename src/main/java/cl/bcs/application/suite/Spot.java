//package cl.bcs.application.suite;
//
//
//
//import cl.bcs.application.factory.util.Session;
//import cl.bcs.application.factory.util.SpotExcel;
//import cl.bcs.cuenta.inversion.CuentaInversion1;
//import cl.bcs.facturacion.Facturacion;
//import cl.bcs.plataforma.Login;
//import cl.bcs.plataforma.SeleccionMenu;
//import cl.bcs.spot.ConfirmacionOperacionesSpot;
//import cl.bcs.spot.IngresoOperacionSpot;
//import cl.bcs.spot.MantenedorPuntas;
//import cl.bcs.tesoreria.Tesoreria;
//
//public class Spot {
//	public static boolean suiteSpot(SpotExcel usu, Session session) {
//		
//		Login.login(session);
//		boolean result = true;	
//		result = SeleccionMenu.seleccionarMenuSpot(session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarMantenedorPuntas(session);
//		if(result == false) {
//			return result;
//		}
//		result = MantenedorPuntas.mantenedorPuntas(session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarIngresoOperacionSpot(session);
//		if(result == false) {
//			return result;
//		}
//		result = IngresoOperacionSpot.datosOperacion(usu, session);
//		if(result == false) {
//			return result;
//		}
//		result = IngresoOperacionSpot.formadePago(usu, session);
//		if(result == false) {
//			return result;
//		}
//		result = IngresoOperacionSpot.otros(session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarModuloConfirmacionOperacionesSpot(session);
//		if(result == false) {
//			return result;
//		}
//		result = ConfirmacionOperacionesSpot.confirmarSpot(usu, session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarMenuSpot(session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarMenuFacturacion(session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarFacturacion(session);
//		if(result == false) {
//			return result;
//		}
//		result = Facturacion.gestionFacturacion(usu, session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarMenuFacturacion(session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarMenuCuentaInversion(session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarCuentaInversionCliente(session);
//		if(result == false) {
//			return result;
//		}
//		result = CuentaInversion1.cuentaInversionCliente(usu, session);
//		if(result == false) {
//			return result;
//		}
//		result = SeleccionMenu.seleccionarMenuCuentaInversion(session);
//		if(result == false) {
//			return result;
//		}
//		if(usu.getCuentaInversion().equals("NO")) {
//			result = SeleccionMenu.seleccionarMenuTesoreria();
//			if(result == false) {
//				return result;
//			}
//			result = SeleccionMenu.seleccionarGestionTesoreria();
//			if(result == false) {
//				return result;
//			}
//			result = Tesoreria.gestionTesoreria(usu, session);
//			if(result == false) {
//				return result;
//			}
//			result = SeleccionMenu.seleccionarMenuTesoreria();
//			if(result == false) {
//				return result;
//			}
//		}
//		return result;
//	}
//
//}
