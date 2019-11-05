package cl.bcs.application.factory.util;

import java.util.HashMap;


public class CacheSession {
    	public static Log log = new Log("CUS.CacheSession"); 
		static HashMap<Long, HashMap<String, String>> threadSuite = new HashMap<>();

		public static void agregar(String k, String y) {
		Long idThread =  Thread.currentThread().getId();
		HashMap<String, String> threadData = new HashMap<>();
		System.out.println(idThread);
		threadData.put(k, y); 
		threadSuite.put(idThread, threadData);
		threadData.forEach((key,value) -> System.out.println(key + " = " + value));
		}

		public static void main(String[] args) {
			agregar("folio","123456");
		}
}

