package cl.bcs.application.constantes.util;

public class ConstantesIngresarOrden {

	public static final String XPATH_CLIENTE_INPUT = "//*[@id='IngresarOrdenRVForm_comboPortafolio']/span/span/span/input";
	public static final String XPATH_CLIENTE_INPUT_1 = "//bcs-combobox-base[@id='IngresarOrdenRVForm_comboPortafolio']//input";
	public static final String XPATH_CLIENTE_INPUT_2 = "/html/body/div[10]/div/div[2]/ul/li";

	public static final String XPATH_MECANISMO_INPUT = "//*[@id='IngresarOrdenRVForm_comboMecanismoCaptacion']/span/span/span/input";
	public static final String XPATH_MECANISMO_INPUT_1 = "//*[@id='IngresarOrdenRVForm_comboMecanismoCaptacion']//span//input";
	public static final String XPATH_MECANISMO_INPUT_2 = "/html/body/div[13]/div/div[2]/ul/li[1]";

	public static final String XPATH_OPERACION_INPUT = "//*[@id='IngresarOrdenRVForm_comboOperacion']//span//input";

	public static final String XPATH_CANTIDAD_INPUT = "//*[@id='IngresarOrdenRVForm_inputCantidad']/span/span/input[1]";
	public static final String XPATH_CANTIDAD_INPUT_1 = "//*[@id='IngresarOrdenRVForm_inputCantidad']/span/span/input[1]";
	public static final String XPATH_CANTIDAD_INPUT_2 = "//*[@id='IngresarOrdenRVForm_inputCantidad']/span/span/input[2]";

	public static final String XPATH_INSTRUMENTO_INPUT = "//*[@id='IngresarOrdenRVForm_inputInstrumentos']//span//input";

	public static final String XPATH_TIPO_PRECIO_INPUT = "//*[@id='IngresarOrdenRVForm_comboTipoPrecio']//span//input";
	
	public static final String XPATH_BOLSA_DESTINO = "//*[@id=\"IngresarOrdenRVForm_comboBolsa\"]/span/span/span/input";

	public static final String XPATH_PRECIO_INPUT = "//*[@id='IngresarOrdenRVForm_inputPrecio']/span/span/input[1]";
	public static final String XPATH_PRECIO_INPUT_1 = "/html/body/div[6]/div[2]/div[2]/div/form[1]/div/div[1]/div[1]/div[5]/div[1]/bcs-input-monetario/span/span/input[1]";
	public static final String XPATH_PRECIO_INPUT_2 = "/html/body/div[6]/div[2]/div[2]/div/form[1]/div/div[1]/div[1]/div[5]/div[1]/bcs-input-monetario/span/span/input[2]";

	public static final String XPATH_CONDICION_INPUT = "//*[@id='FORM_IngresarOrdenMain']/div[2]/div/form[1]/div/div[1]/div[1]/div[6]/div[1]/bcs-input-condicion-liquidacion/span/bcs-combobox-base/span/span/span/input";

	public static final String XPATH_MONTO_OPERACION_INPUT = "//*[@id='FORM_IngresarOrdenMain']/div[2]/div/form[1]/div/div[1]/div[3]/div/div[1]/div/bcs-resumen-orden/div/table/tbody/tr[2]/td[2]";
	public static final String XPATH_MONTO_TOTAL_INPUT = "//*[@id='FORM_IngresarOrdenMain']/div[2]/div/form[1]/div/div[1]/div[3]/div/div[1]/div/bcs-resumen-orden/div/table/tbody/tr[8]/td[2]/strong";
	
	public static final String XPATH_BTN_ACEPTAR = "//*[@id='UI_ACEPTAR']//button";
	
	public static final String FULL_XPATH_LABEL_INFO = "/html/body/div[31]/div[2]/div[2]/div/div[1]/div[1]/div[1]/label";

	public static final String XPATH_BTN_ACEPTAR_INFO = "//*[@id='FORM_VentanaMensajeResultado']/div[2]/div/div[2]/bcs-button/button";
	
	public static final String XPATH_TAB_MAS_DATOS = "//*[@id='FORM_IngresarOrdenMain']/div[2]/div/form[1]/div/div[1]/div[3]/ul/li[3]/a";
	
	public static final String XPATH_CHECKBOX_MAS_DATOS = "//*[@id='FORM_IngresarOrdenMain']/div[2]/div/form[1]/div/div[1]/div[3]/div/div[3]/div[1]/bcs-input-checkbox/div";
	
	public static final String XPATH_FORMA_DE_PAGO = "//*[@id='IngresarOrdenRVForm_comboFormaDePago']/span/span/span/input";

}
