package cl.bbr.validations;

import java.util.Arrays;

import org.apache.log4j.Logger;

import cl.bbr.model.Response;
import cl.bbr.model.Trx;

public class GeneralsValids {

	private static Logger logger = Logger.getLogger(GeneralsValids.class);

	public static Response isValid(Trx venta) {
		logger.info("Inicio Validaciones Generales: " + venta);
		Response message = new Response();

		String[] ARR_CANAL00 = null;
		String[] ARR_CANAL01 = null;
		String[] ARR_CANAL02 = null;
		String[] ARR_CANAL04 = null;
		String CABECERA;
		String ID_BLOCK;
		String PRECIO_VTA;
		int LENGTH;
		int COUNT_PROD = 0;
		int COUNT_FPAG = 0;
		int SUMA_PRODS = 0;
		int SUMA_DESP = 0;
		int DESC_TOT_GRAL = 0;
		int TOTAL_PAGOS = 0;
		int PERCENT = 0;
		float DCTO_TOT_PROD = 0;
		float CALCULO_DCTO = 0;

		
		// Cantidad de caracteres
		logger.info("Tamaño total trx " + venta.gettrx().length());

		// Cantidad de caracteres escritas en trx
		LENGTH = Integer.parseInt(venta.gettrx().substring(0, 4));
		if (venta.gettrx().length() != LENGTH) {
			logger.error("Code 04: Largo de trx no coincide con tamaño declarado en trx");
			message.setResponseError("04", "Largo de trx no coincide con tamaño declarado en trx");
		}
		String[] parts = venta.gettrx().split("\\$");
		logger.info("Array de bloques de trx separado por $ : " + Arrays.asList(parts));
		ARR_CANAL00 = parts[0].split("\\|");
		for (int i = 0; i < ARR_CANAL00.length; i++) {
			ARR_CANAL00[i] = ARR_CANAL00[i].replace("\"", "");
		}

		logger.info("Array del 1er bloque separado por | : " + Arrays.asList(ARR_CANAL00));
		logger.info("idBlock de Cabecera : "
				+ ARR_CANAL00[0].substring(ARR_CANAL00[0].length() - 2, ARR_CANAL00[0].length()));
		CABECERA = ARR_CANAL00[0].substring(ARR_CANAL00[0].length() - 2, ARR_CANAL00[0].length());
		if (!CABECERA.equals("00")) {
			logger.error("Code 05: Id Cabecera no corresponde a '00'");
			message.setResponseError("05", "Id Cabecera no corresponde a '00'");
		}

		// Validacion de igualdad de Ids
		logger.info("Id de json trx " + ARR_CANAL00[1]);
		logger.info("Id de trx" + venta.getid());
		if (!ARR_CANAL00[1].equals(venta.getid())) {
			logger.error("Code 06: El Id " + ARR_CANAL00[1] + " es distinto del Id de trx");
			message.setResponseError("06", "El Id " + ARR_CANAL00[1] + " es distinto del Id de trx");
		}

		// Producto y Forma de Pago
		for (int i = 1; i < parts.length; i++) {
			ID_BLOCK = parts[i].substring(1, 3);

			if (ID_BLOCK.equals("01")) {
				ARR_CANAL01 = parts[i].split("\\|");
				SUMA_PRODS = SUMA_PRODS + Integer.parseInt(ARR_CANAL01[5].replace("\"", ""));
				logger.info("Suma de productos " + SUMA_PRODS);
				COUNT_PROD++;

				for (int j = 0; j < parts.length; j++) {
					ARR_CANAL02 = parts[j].split("\\|");

					if (parts[j].substring(1, 3).equals("02") && (ARR_CANAL01[2].equals(ARR_CANAL02[2]))) {
						
						message = Valid02Desc.isValid02Desc(ARR_CANAL02);
						
						
						System.out.println(Arrays.asList(ARR_CANAL02));
						logger.info("Igualdad de codigo de producto: " + ARR_CANAL01[2].equals(ARR_CANAL02[2]));

						if (ARR_CANAL02[5].equals("777701")) {
							System.out.println(SUMA_DESP);
							SUMA_DESP = SUMA_DESP + Integer.parseInt(ARR_CANAL02[9].replace("\"", ""));

						}
						if (ARR_CANAL02[5].equals("777702")) {
							System.out.println(DESC_TOT_GRAL);
							DESC_TOT_GRAL = Integer.parseInt(ARR_CANAL02[9].replace("\"", ""));

						}
						PERCENT = Integer.parseInt((ARR_CANAL02[8].trim())) / 10;
						logger.info("Porcentaje " + PERCENT + "%");

						DCTO_TOT_PROD = Float.parseFloat(ARR_CANAL02[9].replace("\"", ""));
						logger.info("Monto descuento total: " + DCTO_TOT_PROD);

						PRECIO_VTA = ARR_CANAL01[5].replace("\"", "");
						logger.info("Precio de venta canal 01: " + Float.parseFloat(PRECIO_VTA));

						CALCULO_DCTO = (Float.parseFloat(ARR_CANAL02[9].replace("\"", "")) * 100)
								/ Float.parseFloat(PRECIO_VTA);

						logger.info("% de descuento calculado: " + CALCULO_DCTO);
						logger.info("Monto a descontar del precio de venta: " + DCTO_TOT_PROD);
						logger.info("Monto a descontar del precio de venta calculado: "
								+ (Float.parseFloat(PRECIO_VTA) * PERCENT) / 100);

						if (CALCULO_DCTO > 99) {
							logger.error("Code 39: Descuento supera máximo permitido 99%: " + CALCULO_DCTO + "%");
							message.setResponseError("39",
									"Descuento supera máximo permitido 99%: " + CALCULO_DCTO + "%");
						}

					}
				}

			}

			if (ID_BLOCK.equals("04")) {
				ARR_CANAL04 = parts[i].split("\\|");
				TOTAL_PAGOS = Integer.parseInt(ARR_CANAL04[4]);
				logger.info("Total desde Pagos " + ARR_CANAL04[4]);
				COUNT_FPAG++;
			}

		}

		logger.info("Total suma de productos - Suma descuentos - Descuento total general: "
				+ (SUMA_PRODS - SUMA_DESP - DESC_TOT_GRAL));

		if (!(COUNT_PROD > 0)) {
			logger.error("Code 07: Trx no contiene Producto");
			message.setResponseError("07", "Trx no contiene Producto");
		}
		if (!(COUNT_FPAG > 0)) {
			logger.error("Code 08: Trx no contiene Forma de Pago");
			message.setResponseError("08", "Trx no contiene Forma de Pago");
		}

		if (Integer.parseInt(ARR_CANAL00[11]) != (SUMA_PRODS - SUMA_DESP - DESC_TOT_GRAL)) {
			logger.error("Code 40: Total de Cabecera desigual a Suma de Productos menos Descuentos");
			message.setResponseError("40", "Total de Cabecera desigual a Suma de Productos menos Descuentos");
		}

		if (Integer.parseInt(ARR_CANAL00[11]) != (TOTAL_PAGOS)) {
			logger.error("Code 41: Total de Cabecera desigual a Total de Pagos");
			message.setResponseError("41", "Total de Cabecera desigual a Total de Pagos");
		}
		
		logger.info("Término Validaciones Generales");
		return message;
	}

}
