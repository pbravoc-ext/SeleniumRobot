package cl.bcs.application.dao.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cl.bcs.application.dao.ReadExcelDAO;
import cl.bcs.application.factory.util.RVExcel;
import cl.bcs.application.factory.util.SpotExcel;

public class ReadExcelDAOImpl implements ReadExcelDAO{
	private File excelFile = new File(System.getProperty("user.dir") + "/Datos/Datos.xlsx");
	private FileInputStream fis = null;
	private XSSFWorkbook workbook = null;	
	private XSSFSheet sheet = null;
	
	@Override
	public List<SpotExcel> leerExcelSpot() {
		try {
			fis = new FileInputStream(excelFile);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet("Spot");
			Iterator<Row> rowIt = sheet.iterator();
			List<SpotExcel> lista = new ArrayList<SpotExcel>();
			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				SpotExcel sp = new SpotExcel();
				sp.setVariacion(validacion(row.getCell(0).toString()));
				sp.setRut(validacion(row.getCell(1).toString()));
				sp.setNombre(validacion(row.getCell(2).toString()));
				sp.setPortafolio(validacion(row.getCell(3).toString()));
				sp.setInstrumento(validacion(row.getCell(4).toString()));
				sp.setMonedaPrincipal(validacion(row.getCell(5).toString()));
				sp.setMonedaSecundaria(validacion(row.getCell(6).toString()));
				sp.setMontoPrincipal(validacion(row.getCell(7).toString()));
				sp.setMontoSecundario(validacion(row.getCell(8).toString()));
				sp.setTipoComprobante(validacion(row.getCell(9).toString()));
				sp.setAgente(validacion(row.getCell(10).toString()));
				sp.setParidadCierre(validacion(row.getCell(11).toString()));
				sp.setOperacion(validacion(row.getCell(12).toString()));
				sp.setCuentaInversion(validacion(row.getCell(13).toString()));
				lista.add(sp);
			}
			workbook.close();
			fis.close();
			return lista;
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	@Override
	public List<RVExcel> leerExcelRV() {
		try {
			fis = new FileInputStream(excelFile);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet("RV");
			Iterator<Row> rowIt = sheet.iterator();
			List<RVExcel> listaRV = new ArrayList<RVExcel>();
			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				RVExcel rv = new RVExcel();
				rv.setVariacion(validacion(row.getCell(0).toString()));
				rv.setRut(validacion(row.getCell(1).toString()));
				rv.setNombre(validacion(row.getCell(2).toString()));
				rv.setPortafolio(validacion(row.getCell(3).toString()));
				rv.setInstrumento(validacion(row.getCell(4).toString()));
				rv.setCantidad(validacion(row.getCell(5).toString()));
				rv.setPrecio(validacion(row.getCell(6).toString()));
				rv.setPrecioLimite(validacion(row.getCell(7).toString()));
				rv.setMonedaLiquidacion(validacion(row.getCell(8).toString()));
				rv.setMecanismoCaptacion(validacion(row.getCell(9).toString()));
				rv.setCondicionLiquidacion(validacion(row.getCell(10).toString()));
				rv.setBolsaDestino(validacion(row.getCell(11).toString()));
				rv.setMercado(validacion(row.getCell(12).toString()));
				rv.setTipoOperacion(validacion(row.getCell(13).toString()));
				rv.setTipoInstrumento(validacion(row.getCell(14).toString()));
				rv.setOperacion(validacion(row.getCell(15).toString()));
				rv.setCuentaInversion(validacion(row.getCell(16).toString()));
				rv.setTransferencia(validacion(row.getCell(17).toString()));
				rv.setNominal(validacion(row.getCell(18).toString()));
				listaRV.add(rv);
			}
			workbook.close();
			fis.close();
			return listaRV;
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return null;
		}
	}

	private static String validacion(String row) {
		if(row == null) {
			return "null";
		}
		if (row.isEmpty() || row.isBlank()) {
			return "CAMPO VACIO";
		}
		return row;
	}

}
