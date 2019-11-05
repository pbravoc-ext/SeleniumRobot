	package cl.bcs.application.factory.util;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import cl.bcs.application.driver.factory.FactoryWebDriver;
import cl.bcs.application.spot.util.VariablesUtil;

/**
 * 
 * @author Narveider
 *
 */
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
	private VariablesUtil variables;


	public Session() {
		this.configDriver = FactoryWebDriver.createDriverReturn();
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

	public VariablesUtil getVariables() {
		return variables;
	}

	public void setVariables(VariablesUtil variablesUtil) {
		variables = variablesUtil;
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
}
