package cl.bcs.application.factory.util;

import com.relevantcodes.extentreports.ExtentTest;

public class SessionRV {

	private static String folio;
	private static String folioAleatorio;
	private static String folioAsignacion;
	private static String nTransaccion;
	private static String precioOrden;
	private static String asignacion;
	private static String movimientoGenerado;
	private static String montoAsignacion;
	private static String fechaTransaccion;
	private static String fechaLiquidacion;
	private static String custodiaInicial;
	private static int estadoFlujo;
	private static String folioCartera;
	private static String montoFactura;
	private static String cantidad;
	private static String montoTotalLocal;
	private static String comprobanteEgreso;
	private static String comprobanteIngreso;
	private static String montoTotal;


	private static RVExcel rvinfo;

	/**
	 * 
	 * @param builder
	 */
	public SessionRV(SessionBuilderRV builder) {
		folio = builder.folio;
		folioAleatorio = builder.folioAleatorio;
		folioAsignacion = builder.folioAsignacion;
		nTransaccion = builder.nTransaccion;

	}

	/**
	 * 
	 * @return
	 */

	public static RVExcel getRV() {
		return rvinfo;
	}

	public static void setRV(RVExcel rv) {
		rvinfo = rv;
	}

	public static String getFolio() {
		return folio;
	}

	public static void setFolio(String folio) {
		SessionRV.folio = folio;
	}

	public static String getFolioAleatorio() {
		return folioAleatorio;
	}

	public static void setFolioAleatorio(String folioAleatorio) {
		SessionRV.folioAleatorio = folioAleatorio;
	}

	public static String getnTransaccion() {
		return nTransaccion;
	}

	public static void setnTransaccion(String nTransaccion) {
		SessionRV.nTransaccion = nTransaccion;
	}

	public static String getPrecioOrden() {
		return precioOrden;
	}

	public static void setPrecioOrden(String precioOrden) {
		SessionRV.precioOrden = precioOrden;
	}

	public static String getAsignacion() {
		return asignacion;
	}

	public static void setAsignacion(String asignacion) {
		SessionRV.asignacion = asignacion;
	}

	public static String getMovimientoGenerado() {
		return movimientoGenerado;
	}

	public static void setMovimientoGenerado(String movimientoGenerado) {
		SessionRV.movimientoGenerado = movimientoGenerado;
	}

	public static String getMontoAsignacion() {
		return montoAsignacion;
	}

	public static void setMontoAsignacion(String montoAsignacion) {
		SessionRV.montoAsignacion = montoAsignacion;
	}

	public static String getFechaTransaccion() {
		return fechaTransaccion;
	}

	public static void setFechaTransaccion(String fechaTransaccion) {
		SessionRV.fechaTransaccion = fechaTransaccion;
	}

	public static String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public static void setFechaLiquidacion(String fechaLiquidacion) {
		SessionRV.fechaLiquidacion = fechaLiquidacion;
	}

	public static String getCustodiaInicial() {
		return custodiaInicial;
	}

	public static void setCustodiaInicial(String custodiaInicial) {
		SessionRV.custodiaInicial = custodiaInicial;
	}

	public static int getEstadoFlujo() {
		return estadoFlujo;
	}

	public static void setEstadoFlujo(int estadoFlujo) {
		SessionRV.estadoFlujo = estadoFlujo;
	}

	public static String getFolioCartera() {
		return folioCartera;
	}

	public static void setFolioCartera(String folioCartera) {
		SessionRV.folioCartera = folioCartera;
	}

	public static String getMontoFactura() {
		return montoFactura;
	}

	public static void setMontoFactura(String montoFactura) {
		SessionRV.montoFactura = montoFactura;
	}

	public static String getCantidad() {
		return cantidad;
	}

	public static void setCantidad(String cantidad) {
		SessionRV.cantidad = cantidad;
	}

	public static String getMontoTotalLocal() {
		return montoTotalLocal;
	}

	public static void setMontoTotalLocal(String montoTotalLocal) {
		SessionRV.montoTotalLocal = montoTotalLocal;
	}

	public static String getComprobanteEgreso() {
		return comprobanteEgreso;
	}

	public static void setComprobanteEgreso(String comprobanteEgreso) {
		SessionRV.comprobanteEgreso = comprobanteEgreso;
	}

	public static String getComprobanteIngreso() {
		return comprobanteIngreso;
	}

	public static void setComprobanteIngreso(String comprobanteIngreso) {
		SessionRV.comprobanteIngreso = comprobanteIngreso;
	}

	public static String getMontoTotal() {
		return montoTotal;
	}

	public static void setMontoTotal(String montoTotal) {
		SessionRV.montoTotal = montoTotal;
	}

	public static String getFolioAsignacion() {
		return folioAsignacion;
	}

	public static void setFolioAsignacion(String folioAsignacion) {
		SessionRV.folioAsignacion = folioAsignacion;
	}

}
