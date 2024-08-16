package cl.bbr.validations;


import org.apache.log4j.Logger;

import cl.bbr.model.Response;


public class Valid02Desc {


	private static Logger logger = Logger.getLogger(Valid99.class);

	private static String TIPO_DATOS = "";
	private static int CANTIDAD_CAMPOS = 10;
	private static int LARGO_CORRELATIVO = 1;
	private static int LARGO_CODIGOPDCTO = 20;
	private static int LARGO_CORRELATIVO_DCTO = 1;
	private static String VALOR_GRUPODCTO = "211";
	private static int LARGO_VALOR_GRUPODCTO = 12;
	private static String CODIGO_DCTO_PDCTO = "777701";
	private static String CODIGO_DCTO_TOTAL = "777702";
	private static int LARGO_DESCRIP_DCTO = 20;
	private static String DCTO_PARA_P = "0";
	private static String DCTO_PARA_T = "0";
	private static int LARGO_PORCENTDESC = 4;
	private static int LARGO_MONTODESC = 8;

	
	public static Response isValid02Desc(String[] aRR_CANAL02String) {

		for (int i = 0; i < aRR_CANAL02String.length; i++) {
			aRR_CANAL02String[i] = aRR_CANAL02String[i].replace("\"", "");
		}

		logger.info("Inicio validaciones específicas para Tipo de datos "+TIPO_DATOS+", Descuentos");

		//[02, 3, 192824199562, 3, 211, 777701, Cupon Primera Compra, 0, 0150, 4499]
		Response message = new Response();

		TIPO_DATOS = aRR_CANAL02String[0];
		
		if (!(aRR_CANAL02String.length==CANTIDAD_CAMPOS)) {
			logger.error("Tipo de datos "+TIPO_DATOS+" no cumple con cantidad de campos.");
			message.setResponseError("42", "Tipo de datos "+TIPO_DATOS+" no cumple con cantidad de campos.");
			return message;
		}

		if (aRR_CANAL02String[1].trim().length() > LARGO_CORRELATIVO) {
			System.out.println(aRR_CANAL02String[1].trim().length());
			logger.error("Largo de campo correlativo " + aRR_CANAL02String[1]+" invalido");
			message.setResponseError("43", "Largo de campo correlativo " + aRR_CANAL02String[1]+" invalido");
			return message;
		}
		
		if (aRR_CANAL02String[2].length() > LARGO_CODIGOPDCTO) {
			logger.error("Largo de Código de producto " + aRR_CANAL02String[2]+" invalido");
			message.setResponseError("44", "Largo de Código de producto " + aRR_CANAL02String[2]+" invalido");
			return message;
		}

		if (aRR_CANAL02String[3].length() > LARGO_CORRELATIVO_DCTO) {
			logger.error("Largo de campo correlativo de descuento " + aRR_CANAL02String[3]+" invalido");
			message.setResponseError("45", "Largo de campo correlativo de descuento " + aRR_CANAL02String[3]+" invalido");
			return message;
		}
		
		if (!aRR_CANAL02String[4].equals(VALOR_GRUPODCTO) || aRR_CANAL02String[4].length() > LARGO_VALOR_GRUPODCTO) {
			logger.error("Valor de grupo de descuento: " + aRR_CANAL02String[4]+" invalido.");
			message.setResponseError("46", "Valor de grupo de descuento: " + aRR_CANAL02String[3]+" invalido.");
			return message;
		}
		
		if (!(aRR_CANAL02String[5].equals(CODIGO_DCTO_PDCTO) || aRR_CANAL02String[5].equals(CODIGO_DCTO_TOTAL))) {
			logger.error("Código de descuento: " + aRR_CANAL02String[5]+" invalido.");
			message.setResponseError("47", "Código de descuento: " + aRR_CANAL02String[5]+" invalido.");
			return message;
		}
		
		if (aRR_CANAL02String[6].length() > LARGO_DESCRIP_DCTO) {
			logger.error("Largo de campo descripcion de descuento " + aRR_CANAL02String[6]+" invalido");
			message.setResponseError("48", "Largo de campo descripcion de descuento " + aRR_CANAL02String[6]+" invalido");
			return message;
		}

		if (!(aRR_CANAL02String[7].equals(DCTO_PARA_P) || aRR_CANAL02String[7].equals(DCTO_PARA_T))) {
			logger.error("Tipo de descuento aplicado a: " + aRR_CANAL02String[7]+" invalido.");
			message.setResponseError("49", "Tipo de descuento aplicado a: " + aRR_CANAL02String[7]+" invalido.");
			return message;
		}
		
		if (aRR_CANAL02String[8].length() > LARGO_PORCENTDESC) {
			logger.error("Largo de Porcentaje de descuento: " + aRR_CANAL02String[8]+" invalido");
			message.setResponseError("50", "Largo de Porcentaje de descuento: " + aRR_CANAL02String[8]+" invalido");
			return message;
		}

		if (aRR_CANAL02String[9].length() > LARGO_MONTODESC) {
			logger.error("Largo de Monto de descuento: " + aRR_CANAL02String[9]+" invalido");
			message.setResponseError("51", "Largo de Monto de descuento: " + aRR_CANAL02String[9]+" invalido");
			return message;
		}
		
		logger.info("Validaciones satisfactorias para Tipo de datos "+TIPO_DATOS+", Descuentos");
		message.getClass();
		logger.info("Fin validaciones específicas para Tipo de datos "+TIPO_DATOS+", Descuentos");
		return message;
	}
	
}
