package cl.bbr.validations;

import java.util.Arrays;

import org.apache.log4j.Logger;

import cl.bbr.model.Response;
import cl.bbr.model.Trx;
import cl.bbr.utils.UtilesSP;

public class Valid01a08 {

	private static Logger logger = Logger.getLogger(Valid99.class);
	
	
	private static String SEPARADOR_SUBSTRING = "\\$";
	private static String SEPARADOR_CAMPOS = "\\|";
	private static String CANAL = "";
	private static String TIPO_TRANSACCION = "V";
	private static String RUT = "";
	private static String TIPO_DOCB = "B";
	private static String TIPO_DOCTB = "TB";
	private static String TIPO_DOCTF = "TF";
	private static String TIPO_DOCTG = "TG";
	private static String TIPO_DOCNE = "NE";
	private static String TIPO_DOCPV = "PV";
	private static int CANTIDAD_CAMPOS = 13;
	private static int LARGO_TIPO_TRANS = 1;
	private static int LARGO_TIPO_DOCUMENTO = 2;	
	private static int LARGO_RUT = 12;
	private static int LARGO_DATOS_CLIENTE = 30;
	private static int LARGO_TELEFONO_CLIENTE = 15;
	private static int LARGO_MONTO_TOTAL = 8;
	private static int LARGO_MARC_PDCTO = 50;
	
	public static Response isValid01a08(Trx venta) {

		Response message = new Response();

		String[] parts = venta.gettrx().split(SEPARADOR_SUBSTRING);
		System.out.println("Array de bloques de trx separado por $ : " + Arrays.asList(parts));
		String[] trxId = parts[0].split(SEPARADOR_CAMPOS);
		for (int i = 0; i < trxId.length; i++) {
			trxId[i] = trxId[i].replace("\"", "");
		}
		RUT = trxId[4].replace("-", "");
		CANAL = (String) trxId[1].subSequence(0, 2);
		logger.info("Inicio validaciones específicas para Canal "+CANAL+" a validar");
		
//		System.out.println("Array del 1er bloque separado por | : " + Arrays.asList(trxId));
//		System.out.println("idBlock de Cabecera : " + trxId[1].subSequence(0, 2));
//		System.out.println(trxId.length);
//		System.out.println("canal "+trxId[1].subSequence(0, 2));
		if (!(trxId.length==CANTIDAD_CAMPOS)) {
			logger.error("Canal "+CANAL+" no cumple con cantidad de campos en Tipo de Datos 00");
			message.setResponseError("09", "Canal "+CANAL+" no cumple con cantidad de campos en Tipo de Datos 00");
			return message;
		}

		if (!trxId[2].equals(TIPO_TRANSACCION) || trxId[2].length() > LARGO_TIPO_TRANS) {
			logger.error("Tipo de documento inválido " + trxId[2]);
			message.setResponseError("10", "Tipo de transacción recibida no corresponde: " + trxId[2]);
			return message;
		}    System.out.println(trxId[3]);
		if (!(trxId[3].equals(TIPO_DOCB) || !(trxId[3].equals(TIPO_DOCTB)) || !(trxId[3].equals(TIPO_DOCTF)) || !(trxId[3].equals(TIPO_DOCTG)) || !(trxId[3].equals(TIPO_DOCNE)) || !(trxId[3].equals(TIPO_DOCPV)))) {
			logger.error("Tipo de documento inválido " + trxId[3]);
			message.setResponseError("11", "Tipo de documento inválido: " + trxId[3]);
			return message;
		}

		if (trxId[4].length() > LARGO_RUT || !UtilesSP.isValidRut(RUT)) {
			logger.error("Rut de cliente, inválido: " + trxId[4]);
			message.setResponseError("12", "Rut de cliente, inválido: " + trxId[4]);
			return message;
		}
		
		if (trxId[3].equals(TIPO_DOCTF) || trxId[3].equals(TIPO_DOCTG)) {
			if (trxId[5].length() > LARGO_DATOS_CLIENTE) {
				logger.error("Nombre del cliente inválido " + trxId[5]);
				message.setResponseError("13", "Nombre del cliente inválido: " + trxId[5]);
				return message;
			}
			
			if (trxId[6].length() > LARGO_DATOS_CLIENTE) {
				logger.error("Dirección del cliente inválido " + trxId[6]);
				message.setResponseError("14", "Dirección del cliente inválido: " + trxId[6]);
				return message;
			}
			
			if (trxId[7].length() > LARGO_DATOS_CLIENTE) {
				logger.error("Comuna del cliente inválida " + trxId[7]);
				message.setResponseError("15", "Comuna del cliente inválida: " + trxId[7]);
				return message;
			}
			
			if (trxId[8].length() > LARGO_DATOS_CLIENTE) {
				logger.error("Ciudad del cliente inválida " + trxId[8]);
				message.setResponseError("16", "Ciudad del cliente inválida: " + trxId[8]);
				return message;
			}
			
			if (trxId[9].length() > LARGO_TELEFONO_CLIENTE) {
				logger.error("Teléfono del cliente inválido: " + trxId[9]);
				message.setResponseError("17", "Teléfono del cliente inválido: " + trxId[9]);
				return message;
			}
			
			if (trxId[10].length() > LARGO_DATOS_CLIENTE) {
				logger.error("Giro del cliente inválido " + trxId[10]);
				message.setResponseError("18", "Giro del cliente inválido: " + trxId[10]);
				return message;
			}
			
			if (trxId[11].length() > LARGO_MONTO_TOTAL) {
				logger.error("Monto total de transacción inválido: " + trxId[11]);
				message.setResponseError("19", "Monto total de transacción inválido: " + trxId[11]);
				return message;
			}
		}

		if (trxId[12].length() > LARGO_MARC_PDCTO) {
			logger.error("Marca de productos inválida: " + trxId[12]);
			message.setResponseError("35", "Marca de productos inválida: " + trxId[12]);
			return message;
		}
		
		logger.info("Validaciones satisfactorias canal "+CANAL);
		message.getClass();
		logger.info("Fin validaciones específicas para Canal "+CANAL);
		return message;
	}
	
}
