package cl.bbr.validations;

import java.util.Arrays;

import javax.validation.Valid;

import org.apache.log4j.Logger;

import cl.bbr.controlador.ServiceController;
import cl.bbr.model.Response;
import cl.bbr.model.Trx;

public class Validations {

	private static Logger logger = Logger.getLogger(Validations.class);
	
	public static Response isValid(Trx venta) {
		Response response = new Response();
		String[] parts = venta.gettrx().split("\\$"); // Array según $
		String[] trxId = parts[0].split("\\|"); // Array encabezado según |
		for (int i = 0; i < trxId.length; i++) {  // Eliminación de "
			trxId[i] = trxId[i].replace("\"", "");
		}
		
		String canal = (String) (trxId[1].subSequence(0, 2));
		logger.info("Inicio validaciones para Canal "+canal+" a validar");

		if(canal.equals("99")) {
			response = GeneralsValids.isValid(venta);
			logger.info("Validaciones Generales de Canal "+canal+": "+response);
			if(response.code.equals("00")) response = Valid99.isValid99(venta);
			logger.info("Validaciones Específicas de Canal "+canal+": "+response);
		}
			
		if(canal.equals("10")) {
			response = GeneralsValids.isValid(venta);
			logger.info("Validaciones Generales de Canal "+canal+": "+response);
			
			if(response.code.equals("00")) response = Valid10.isValid10(venta);
			logger.info("Validaciones Específicas de Canal "+canal+": "+response);
		}

		if(canal.equals("11")) {
			response = GeneralsValids.isValid(venta);
			logger.info("Validaciones Generales de Canal "+canal+": "+response);
			if(response.code.equals("00")) response = Valid11.isValid11(venta);
			logger.info("Validaciones Específicas de Canal "+canal+": "+response);
		}
		
		if(canal.equals("12")) {
			response = GeneralsValids.isValid(venta);
			logger.info("Validaciones Generales de Canal "+canal+": "+response);
			System.out.println(response);
			if(response.code.equals("00")) response = Valid12.isValid12(venta);
			logger.info("Validaciones Específicas de Canal "+canal+": "+response);
		}
		
		if(canal.equals("01") || canal.equals("02") || canal.equals("03") || canal.equals("04") || canal.equals("05") || canal.equals("06") || canal.equals("07") || canal.equals("08")) {
			response = GeneralsValids.isValid(venta);
			logger.info("Validaciones Generales de Canal "+canal+": "+response);
			System.out.println(response);
			if(response.code.equals("00")) response = Valid01a08.isValid01a08(venta);
			logger.info("Validaciones Específicas de Canal "+canal+": "+response);
		}
		
		logger.info("Fin validaciones para Canal "+canal);
		return response;
	}


}
