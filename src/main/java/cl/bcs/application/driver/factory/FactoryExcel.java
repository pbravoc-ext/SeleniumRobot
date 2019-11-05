package cl.bcs.application.driver.factory;

import cl.bcs.application.dao.ReadExcelDAO;
import cl.bcs.application.dao.Impl.ReadExcelDAOImpl;

public class FactoryExcel {
	ReadExcelDAO re = new ReadExcelDAOImpl();
	public  Object getReadExcel(String clase){
		if(clase == null) {
			return null;
		}
		if(clase.equalsIgnoreCase("spot")) {
			return re.leerExcelSpot();
		}
		if(clase.equalsIgnoreCase("rv")) {
			return re.leerExcelRV();
		}
		return null;
		
	}
}
