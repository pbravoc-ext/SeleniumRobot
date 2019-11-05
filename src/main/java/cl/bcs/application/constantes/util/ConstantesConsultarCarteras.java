package cl.bcs.application.constantes.util;

public class ConstantesConsultarCarteras {	

	public static final String XPATH_CLIENTE_INPUT = "//*[@id='frmCarteras_Cliente']/span/span/span/input";
	public static final String XPATH_CLIENTE_INPUT_MOVIMIENTOS = "//*[@id='frmCartola_Cliente']/span/span/span/input";
	
	public static final String XPATH_PORTAFOLIO_INPUT = "//*[@id='ConsultarCarteras_Portafolio']/span/span/span/input";
	public static final String XPATH_PORTAFOLIO_INPUT_MOVIMIENTOS = "//*[@id='frmCartola_Portafolio']/span/span/span/input";	

	public static final String XPATH_MERCADO_INPUT = "//*[@id='ConsultarCarteras']/form/div/div[2]/div[1]/bcs-combobox-base/span/span/span/input";
	public static final String XPATH_MERCADO_INPUT_MOVIMIENTOS = "//*[@id='gridCartolaMovimientos']/div/div/form/div/div[2]/div[1]/bcs-combobox-base/span/span/span/input";

	public static final String XPATH_INSTRUMENTO_INPUT = "//*[@id='UI_INSTRUMENTO']/span/input";
	public static final String XPATH_INSTRUMENTO_INPUT_MOVIMIENTOS = "//*[@id='UI_INSTRUMENTO']/span/input";

	public static final String XPATH_BTN_BUSCAR = "//*[@id='ConsultarCarteras']/div[1]/bcs-button[1]/button";
	public static final String XPATH_BTN_BUSCAR_MOVIMIENTOS = "//*[@id='gridCartolaMovimientos']/div/div/div[1]/bcs-button[1]/button";
	
	public static final String XPATH_BUSCAR_FOLIO_INPUT = "//*[@id='grid-carterasRV']/span/div[2]/div[3]/div/table/thead/tr[2]/th[1]/span/span/span[1]/input";
	public static final String XPATH_BUSCAR_FOLIO_INPUT_MOVIMIENTOS_1 = "//*[@id='grid-cartola-movimientos']/span/div[2]/div[3]/div/table/thead/tr[2]/th[1]/span/span/span[1]/span/input[1]";
	public static final String XPATH_BUSCAR_FOLIO_INPUT_MOVIMIENTOS_2 = "//*[@id='grid-cartola-movimientos']/span/div[2]/div[3]/div/table/thead/tr[2]/th[1]/span/span/span[1]/span/input[2]";
	
	public static final String FULL_XPATH_FECHA_INGRESO = "/html/body/div[6]/div[2]/div[2]/div/div/div/div/div/div[1]/div/div/div/div[2]/div[1]/bcs-grid[2]/span/div[2]/div[4]/table/tbody/tr/td[2]";
	
	public static final String FULL_XPATH_FECHA_LIQUIDACION = "/html/body/div[6]/div[2]/div[2]/div/div/div/div/div/div[1]/div/div/div/div[2]/div[1]/bcs-grid[2]/span/div[2]/div[4]/table/tbody/tr/td[3]";

	public static final String XPATH_MONTO_COMPRA = "//*[@id='grid-carterasRV']/span/div[2]/div[4]/table/tbody/tr/td[17]/label";
	public static final String XPATH_VALOR_COMPRA = "//*[@id='grid-carterasRV']/span/div[2]/div[4]/table/tbody/tr/td[16]/label";

	public static final String XPATH_TAB_MOVIMIENTOS = "//*[@id='blotterCarteras']/div/div/div/ul/li[3]/a";
	public static final String XPATH_BTN_ERROR_MOVIMIENTOS = "//*[@id='FORM_VentanaMensajeResultado']/div[2]/div/div[2]/bcs-button/button";
	
	public static final String FULL_XPATH_CANTIDAD = "	/html/body/div[6]/div[2]/div[2]/div/div/div/div/div/div[3]/div/div/div/div[2]/div[1]/bcs-grid/span/div[2]/div[4]/table/tbody/tr/td[11]/label";

	public static final String FULL_XPATH_VENTA = "//*[@id=\"grid-cartola-movimientos\"]/span/div[2]/div[4]/table/tbody/tr[1]/td[4]/span[@ng-bind='dataItem.NombreConcepto' and contains(text(),'VENTA')]";
	
																											 
}
