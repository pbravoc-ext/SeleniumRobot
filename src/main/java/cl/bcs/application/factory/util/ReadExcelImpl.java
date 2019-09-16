package cl.bcs.application.factory.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.monitorjbl.xlsx.StreamingReader;

/**
 * 
 * @author Narveider
 *
 */
public class ReadExcelImpl {

	public static List<SpotExcel> leerExcel() {

		List<SpotExcel> usuario = new ArrayList<SpotExcel>();
		try {

			File excelFile = new File(System.getProperty("user.dir") + "/Datos/Datos.xlsx");
			FileInputStream fis = new FileInputStream(excelFile);

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("Spot");
			Iterator<Row> rowIt = sheet.iterator();

			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				SpotExcel u = new SpotExcel();
				u.setVariacion(validacion(row.getCell(0).toString()));
				u.setRut(validacion(row.getCell(1).toString()));
				u.setNombre(validacion(row.getCell(2).toString()));
				u.setPortafolio(validacion(row.getCell(3).toString()));
				u.setInstrumento(validacion(row.getCell(4).toString()));
				u.setMonedaPrincipal(validacion(row.getCell(5).toString()));
				u.setMonedaSecundaria(validacion(row.getCell(6).toString()));
				u.setMontoPrincipal(validacion(row.getCell(7).toString()));
				u.setMontoSecundario(validacion(row.getCell(8).toString()));
				u.setTipoComprobante(validacion(row.getCell(9).toString()));
				u.setAgente(validacion(row.getCell(10).toString()));
				u.setParidadCierre(validacion(row.getCell(11).toString()));
				usuario.add(u);
			}

			workbook.close();
			fis.close();
			return usuario;
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	private static String validacion(String row) {
	if(row.isEmpty()|| row.isBlank()||row == null) {
		return "null";
		}
	return row;
	}
}
