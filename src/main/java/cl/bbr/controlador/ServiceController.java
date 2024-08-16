package cl.bbr.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
//import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bbr.model.Response;
import cl.bbr.model.Trx;
import cl.bbr.model.Venta;
import cl.bbr.service.VentaService;
import cl.bbr.validations.Validations;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Venta(s)")
@RestController
@RequestMapping("/v1")
public class ServiceController {

	// log configuration
	private static Logger logger = Logger.getLogger(ServiceController.class);

	@Autowired
	private VentaService ventaDaoService;

	@ApiOperation(value = "Obtener todas las ventas creadas, desde la más actual", notes = "Endpoint para obtener todos los objetos de las ventas.")
	@GetMapping("/sales")
	public ResponseEntity<List<Venta>> obtenerVentas() {
		List<Venta> listaVentas = ventaDaoService.obtenerVentas();
		logger.info("Obtención de ventas sin problemas");
		return new ResponseEntity(listaVentas, HttpStatus.OK);
	}

	@ApiOperation(value = "Obtener venta por su Id", notes = "Endpoint para obtener el objeto de la venta.")
	@GetMapping("/sale/{id}")
	public ResponseEntity<List<Venta>> ventasPorId(String id) {
		Venta venta = ventaDaoService.ventasPorId(id);
		logger.info("Obtención de venta por ID sin problemas");
		return new ResponseEntity(venta, HttpStatus.OK);
	}

	@ApiOperation(value = "Ingresar una venta mediante un Objeto", notes = "Endpoint para ingresar el objeto de la venta.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "00 = OK\n\n"),
			@ApiResponse(code = 202, message = "01	= Error de datos\n\n"
					+ "02	= Error del procedimiento almacenado\n\n" + "03	= Error del Web Services\n\n"
					+ "04	= Largo de trx no coincide con tamaño declarado en trx\n\n"
					+ "05	= Id Cabecera no corresponde a '00'\n\n" + "06	= El Id es distinto del Id de trx\"\n\n"
					+ "07	= Trx no contiene Producto\n\n" + "08	= Trx no contiene Forma de Pago\n\n"
					+ "09	= Canal no cumple con cantidad de campos.\n\n"
					+ "10	= Tipo de transacción recibida no corresponde: \n\n"
					+ "11	= Tipo de documento inválido: \n\n" + "12	= Rut de cliente, inválido: \n\n"
					+ "13	= Nombre del cliente inválido: \n\n" + "14	= Dirección del cliente inválido: \n\n"
					+ "15	= Comuna del cliente inválida: \n\n" + "16	= Ciudad del cliente inválida: \n\n"
					+ "17	= Teléfono del cliente inválido: \n\n" + "18	= Giro del cliente inválido: \n\n"
					+ "19	= Monto total de transacción inválido: \n\n" + "20	= Número de documento inválido \n\n"
					+ "21	= Número de local inválida\n\n" + "22	= Fecha TRX inválida \n\n"
					+ "23	= Hora TRX inválida \n\n" + "24	= Fecha contable inválida \n\n"
					+ "25	= Número de cajero inválido \n\n" + "26	= Razón referencia inválida \n\n"
					+ "27	= Documento origen de NC inválido \n\n" + "28	= Local origen inválido \n\n"
					+ "29	= Pos origen inválido \n\n" + "30	= Número TRX origen inválido \n\n"
					+ "31	= Fecha documento origen inválida \n\n" + "32	= Hora documento origen inválida \n\n"
					+ "33	= Número TRX de la NC inválido \n\n" + "34	= Email de cliente inválido: \n\n"
					+ "35	= Marca de productos inválida: \n\n" + "36	= Número de vendedor inválido \n\n"
					+ "37	= Número de Guía de Depacho inválido \n\n" + "38	= Número de Boleta o Factura inválido "
					+ "39	= Descuento supera máximo permitido 99% \n\n"
					+ "40	= Total de Cabecera desigual a Suma de Productos menos Descuentos \n\n"
					+ "41	= Total de Cabecera desigual a Total de Pagos \n\n"
					+ "42	= Tipo de datos no cumple con cantidad de campos \n\n"
					+ "43	= Largo de campo correlativo invalido \n\n"
					+ "44	= Largo de Código de producto invalido \n\n"
					+ "45	= Largo de campo correlativo de descuento  invalido \n\n"
					+ "46	= Valor de grupo de descuento:  invalido. \n\n"
					+ "47	= Código de descuento:  invalido. \n\n"
					+ "48	= Largo de campo descripcion de descuento invalido \n\n"
					+ "49	= Tipo de descuento aplicado a:  invalido. \n\n"
					+ "50	= Largo de Porcentaje de descuento:  invalido. \n\n"
					+ "51	= Largo de Monto de descuento: invalido.") })

	@PostMapping("/newsale")
	public ResponseEntity<?> guardarVenta(@Valid @RequestBody Trx venta, BindingResult result) {

		Response response = new Response();

		try {
			logger.info("Inicio de validaciones de " + venta);
			response = Validations.isValid(venta);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("Error en inicio de validaciones: " + e1);
			e1.printStackTrace();
		}

		if ((Integer.parseInt(response.code)) > 01) {
			logger.error("Validación no exitosa: code: " + response.code + ", message: " + response.message);
			List<String> messages = new ArrayList<String>(Arrays.asList(response.message));
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}

		ventaDaoService.guardarVenta(venta);

//		if (result.hasErrors()) {
//			logger.error("Result con error " + response);
//			List<String> messages = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
//					.collect(Collectors.toList());
//			return new ResponseEntity<>(messages, null, HttpStatus.BAD_REQUEST);
//		}
		
		logger.info("Creación de venta sin problemas");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Borrar una venta ingresando su Id", notes = "Endpoint para borrar el objeto de una venta.")
	@DeleteMapping("/byebyesale/{id}")
	public ResponseEntity<?> borrarVenta(@PathVariable String id) {
		ventaDaoService.borrarVenta(id);
		logger.info("Venta borrada sin problemas");
		return new ResponseEntity("Venta borrada", HttpStatus.OK);
	}

}
