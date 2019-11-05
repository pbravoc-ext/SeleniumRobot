	package cl.bcs.application.factory.util;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import cl.bcs.application.driver.factory.FactoryWebDriver;


public class Session {

	public ExtentTest logger;
	private String folio;
	private String abono;
	private String cargo;
	private String comprobanteVenta;
	private String comprobante;
	private String movimientoEgreso;
	private String movimientoIngreso;
	private String fechaDesde;
	private String fechaHasta;
	private String montoEq;
	private String montoSecundario;
	private String montoPrincipal;
	private int variacion;
	private WebDriver configDriver;
	private SpotExcel spotinfo;


	private   String folioAleatorio;
	private   String folioAsignacion;
	private   String nTransaccion;
	private   String precioOrden;
	private   String asignacion;
	private   String movimientoGenerado;
	private   String montoAsignacion;
	private   String fechaTransaccion;
	private   String fechaLiquidacion;
	private   String custodiaInicial;
	private   int estadoFlujo;
	private   String folioCartera;
	private   String montoFactura;
	private   String cantidad;
	private   String montoTotalLocal;
	private   String comprobanteEgreso;
	private   String comprobanteIngreso;
	private   String montoTotal;


	public Session(int i, ExtentTest test) {
		this.configDriver = FactoryWebDriver.createDriverReturn();
		this.estadoFlujo = i;
		this.logger = test;
	}

	/**
	 * 
	 * @return
	 */
	public WebDriver getConfigDriver() {
		return configDriver;
	}

	/**
	 * 
	 * @return
	 */

	public SpotExcel getSpot() {
		return spotinfo;
	}

	public void setSpot(SpotExcel spot) {
		spotinfo = spot;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}


	public String getAbono() {
		return abono;
	}

	public void setAbono(String abono) {
		this.abono = abono;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public String getMovimientoEgreso() {
		return movimientoEgreso;
	}

	public void setMovimientoEgreso(String movimientoEgreso) {
		this.movimientoEgreso = movimientoEgreso;
	}

	public String getMovimientoIngreso() {
		return movimientoIngreso;
	}

	public void setMovimientoIngreso(String movimientoIngreso) {
		this.movimientoIngreso = movimientoIngreso;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getMontoEq() {
		return montoEq;
	}

	public void setMontoEq(String montoEq) {
		this.montoEq = montoEq;
	}

	public String getMontoSecundario() {
		return montoSecundario;
	}

	public void setMontoSecundario(String montoSecundario) {
		this.montoSecundario = montoSecundario;
	}

	public String getMontoPrincipal() {
		return montoPrincipal;
	}

	public void setMontoPrincipal(String montoPrincipal) {
		this.montoPrincipal = montoPrincipal;
	}

	public int getVariacion() {
		return variacion;
	}

	public void setVariacion(int variacion) {
		this.variacion = variacion;
	}

	public String getComprobanteVenta() {
		return comprobanteVenta;
	}

	public void setComprobanteVenta(String comprobanteVenta) {
		this.comprobanteVenta = comprobanteVenta;
	}

	public String getFolioAleatorio() {
		return folioAleatorio;
	}

	public void setFolioAleatorio(String folioAleatorio) {
		this.folioAleatorio = folioAleatorio;
	}

	public String getFolioAsignacion() {
		return folioAsignacion;
	}

	public void setFolioAsignacion(String folioAsignacion) {
		this.folioAsignacion = folioAsignacion;
	}

	public String getnTransaccion() {
		return nTransaccion;
	}

	public void setnTransaccion(String nTransaccion) {
		this.nTransaccion = nTransaccion;
	}

	public String getPrecioOrden() {
		return precioOrden;
	}

	public void setPrecioOrden(String precioOrden) {
		this.precioOrden = precioOrden;
	}

	public String getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}

	public String getMovimientoGenerado() {
		return movimientoGenerado;
	}

	public void setMovimientoGenerado(String movimientoGenerado) {
		this.movimientoGenerado = movimientoGenerado;
	}

	public String getMontoAsignacion() {
		return montoAsignacion;
	}

	public void setMontoAsignacion(String montoAsignacion) {
		this.montoAsignacion = montoAsignacion;
	}

	public String getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(String fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public String getCustodiaInicial() {
		return custodiaInicial;
	}

	public void setCustodiaInicial(String custodiaInicial) {
		this.custodiaInicial = custodiaInicial;
	}

	public int getEstadoFlujo() {
		return estadoFlujo;
	}

	public void setEstadoFlujo(int estadoFlujo) {
		this.estadoFlujo = estadoFlujo;
	}

	public String getFolioCartera() {
		return folioCartera;
	}

	public void setFolioCartera(String folioCartera) {
		this.folioCartera = folioCartera;
	}

	public String getMontoFactura() {
		return montoFactura;
	}

	public void setMontoFactura(String montoFactura) {
		this.montoFactura = montoFactura;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getMontoTotalLocal() {
		return montoTotalLocal;
	}

	public void setMontoTotalLocal(String montoTotalLocal) {
		this.montoTotalLocal = montoTotalLocal;
	}

	public String getComprobanteEgreso() {
		return comprobanteEgreso;
	}

	public void setComprobanteEgreso(String comprobanteEgreso) {
		this.comprobanteEgreso = comprobanteEgreso;
	}

	public String getComprobanteIngreso() {
		return comprobanteIngreso;
	}

	public void setComprobanteIngreso(String comprobanteIngreso) {
		this.comprobanteIngreso = comprobanteIngreso;
	}

	public String getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}
}
