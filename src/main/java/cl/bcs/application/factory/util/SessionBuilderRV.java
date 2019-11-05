package cl.bcs.application.factory.util;

/**
 * 
 * @author Narveider
 *
 */
public class SessionBuilderRV {
	protected String folio;
	protected String folioAleatorio;
	protected String nTransaccion;
	protected String folioAsignacion;
/**
 * 
 * @param browser
 */
	public SessionBuilderRV() {
	}

	/**
	 * 
	 * @return
	 */
	public SessionRV build() {
		return new SessionRV(this);
	}

}
