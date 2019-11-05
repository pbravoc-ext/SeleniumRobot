package cl.bcs.application.factory.util;

import java.io.Serializable;

/**
 * 
 * @author Narveider
 *
 */

public class RVExcel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String variacion;
	private String rut;
	private String nombre;
	private String portafolio;
	private String instrumento;
	private String cantidad;
	private String precio;
	private String precioLimite;
	private String monedaLiquidacion;
	private String mecanismoCaptacion;
	private String condicionLiquidacion;
	private String bolsaDestino;
	private String mercado;
	private String tipoOperacion;
	private String tipoInstrumento;
	private String operacion;
	private String cuentaInversion;
	private String transferencia;
	private String nominal;

	public String getVariacion() {
		return variacion;
	}

	public void setVariacion(String variacion) {
		this.variacion = variacion;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPortafolio() {
		return portafolio;
	}

	public void setPortafolio(String portafolio) {
		this.portafolio = portafolio;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getPrecioLimite() {
		return precioLimite;
	}

	public void setPrecioLimite(String precioLimite) {
		this.precioLimite = precioLimite;
	}

	public String getMonedaLiquidacion() {
		return monedaLiquidacion;
	}

	public void setMonedaLiquidacion(String monedaLiquidacion) {
		this.monedaLiquidacion = monedaLiquidacion;
	}

	public String getMecanismoCaptacion() {
		return mecanismoCaptacion;
	}

	public void setMecanismoCaptacion(String mecanismoCaptacion) {
		this.mecanismoCaptacion = mecanismoCaptacion;
	}

	public String getCondicionLiquidacion() {
		return condicionLiquidacion;
	}

	public void setCondicionLiquidacion(String condicionLiquidacion) {
		this.condicionLiquidacion = condicionLiquidacion;
	}

	public String getBolsaDestino() {
		return bolsaDestino;
	}

	public void setBolsaDestino(String bolsaDestino) {
		this.bolsaDestino = bolsaDestino;
	}

	public String getMercado() {
		return mercado;
	}

	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getTipoInstrumento() {
		return tipoInstrumento;
	}

	public void setTipoInstrumento(String tipoInstrumento) {
		this.tipoInstrumento = tipoInstrumento;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getCuentaInversion() {
		return cuentaInversion;
	}

	public void setCuentaInversion(String cuentaInversion) {
		this.cuentaInversion = cuentaInversion;
	}
	public String getTransferencia() {
		return transferencia;
	}
	public void setTransferencia(String transferencia) {
		this.transferencia = transferencia;
	}

	public String getNominal() {
		return nominal;
	}

	public void setNominal(String nominal) {
		this.nominal = nominal;
	}

}