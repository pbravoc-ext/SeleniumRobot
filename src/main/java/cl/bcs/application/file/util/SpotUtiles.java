package cl.bcs.application.file.util;
/**
 * 
 * @author Narveider
 *
 */
public class SpotUtiles {
	/**
	 * 
	 * @param label
	 * @return
	 */
	public static String folio(String label) {
		return label.replaceAll("[^0-9]", "");
	}
}
