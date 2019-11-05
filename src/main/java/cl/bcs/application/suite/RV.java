package cl.bcs.application.suite;

import org.apache.log4j.Logger;

import cl.bcs.application.constantes.util.Constantes;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.Session;
import cl.bcs.application.file.util.Log4jFactory;
import cl.bcs.application.file.util.UtilesSelenium;
import cl.bcs.cuenta.inversion.CuentaInversion;
import cl.bcs.custodia.ConsultarCarteras;
import cl.bcs.custodia.ConsultarCustodia;
import cl.bcs.facturacion.Facturacion;
import cl.bcs.operaciones.rueda.GestionOrdenes;
import cl.bcs.operaciones.rueda.IngresarOrden;
import cl.bcs.operaciones.rueda.IngresarTransaccion;
import cl.bcs.operaciones.rueda.InterfazAsignacion;
import cl.bcs.operaciones.rueda.NegociacionOrdenes;
import cl.bcs.plataforma.Login;
import cl.bcs.plataforma.SeleccionMenu;
import cl.bcs.tesoreria.Tesoreria;

public class RV {
	private static final Logger LOGGER = Log4jFactory.getLogger(RV.class);

	public static boolean suiteRV(RVExcel usu,Session session) {
		boolean result = true;
		LOGGER.info("===================================================");
		LOGGER.info("Variacion: " + usu.getVariacion());
		LOGGER.info("===================================================");
		UtilesSelenium.openPage(session.getConfigDriver());
		
		Login.login(session);
		result = SeleccionMenu.seleccionarMenuCustodia(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarModuloConsultarCustodia(session);
		if (result == false) {
			return result;
		}
		result = ConsultarCustodia.custodia(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuCustodia(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuOperacionesRueda(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarOR_IngresarOrden(session);
		if (result == false) {
			return result;
		}
		result = IngresarOrden.ingresarOrden(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarOR_IngresarTransaccion(session);
		if (result == false) {
			return result;
		}
		result = IngresarTransaccion.ingresarTransaccion(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarOR_NegociacionOrdenes(session);
		if (result == false) {
			return result;
		}
		result = NegociacionOrdenes.ordenesPorNegociar(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarOR_InterfazAsignacion(session);
		if (result == false) {
			return result;
		}
		result = InterfazAsignacion.transacciones(usu,session);
		if (result == false) {
			return result;
		}
		result = InterfazAsignacion.ordenes(usu,session);
		if (result == false) {
			return result;
		}
		result = InterfazAsignacion.interfazAsignacion(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarOR_GestionOrdenes(session);
		if (result == false) {
			return result;
		}
		result = GestionOrdenes.asignaciones(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuOperacionesRueda(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuFacturacion(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarFacturacion(session);
		if (result == false) {
			return result;
		}
		result = Facturacion.gestionFacturacion(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuFacturacion(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuCustodia(session);
		if (result == false) {
			return result;
		}

		if (usu.getNominal().equalsIgnoreCase(Constantes.NO)) {
			result = SeleccionMenu.seleccionarModuloConsultarCarteras(usu,session);
			if (result == false) {
				return result;
			}

			if (usu.getOperacion().equalsIgnoreCase(Constantes.COMPRA)) {
				result = ConsultarCarteras.carteras(usu,session);
				if (result == false) {
					return result;
				}
			} else {
				result = ConsultarCarteras.movimientos(usu,session);
				if (result == false) {
					return result;
				}
			}
		}

		result = SeleccionMenu.seleccionarModuloConsultarCustodia(session);
		if (result == false) {
			return result;
		}
		result = ConsultarCustodia.custodia(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuCustodia(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuCuentaInversion(session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarCuentaInversionCliente(session);
		if (result == false) {
			return result;
		}
		result = CuentaInversion.cuentaInversionCliente(usu,session);
		if (result == false) {
			return result;
		}
		result = SeleccionMenu.seleccionarMenuCuentaInversion(session);
		if (result == false) {
			return result;
		}
		if (usu.getCuentaInversion().equalsIgnoreCase(Constantes.NO)) {

			result = SeleccionMenu.seleccionarMenuTesoreria(usu,session);
			if (result == false) {
				return result;
			}
			result = SeleccionMenu.seleccionarGestionTesoreria(usu,session);
			if (result == false) {
				return result;
			}
			result = Tesoreria.gestionTesoreria(usu,session);
			if (result == false) {
				return result;
			}
			result = SeleccionMenu.seleccionarMenuTesoreria(usu,session);
			if (result == false) {
				return result;
			}
		}
		if (result != false) {
			session.getConfigDriver().quit();
		}
		return result;
	}

}
