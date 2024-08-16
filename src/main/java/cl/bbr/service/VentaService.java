package cl.bbr.service;

import java.util.List;

import cl.bbr.model.Trx;
import cl.bbr.model.Venta;




public interface VentaService {

	public List<Venta> obtenerVentas();
	public Venta ventasPorId(String id);
	public void guardarVenta(Trx venta);
	public void borrarVenta(String id);
	
	public String obtenerTrx(String trx);
}
