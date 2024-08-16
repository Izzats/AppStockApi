package cl.bbr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import cl.bbr.model.Trx;
import cl.bbr.model.Venta;
import cl.bbr.repository.VentaRepository;



@Service
public class VentaServiceImpl implements VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	public List<Venta> obtenerVentas(){
		return ventaRepository.obtenerVentas();
	}


	public Venta ventasPorId(String id) {
		return ventaRepository.ventasPorId(id);
	}


	@Override
	public void guardarVenta(Trx venta) {
		ventaRepository.guardarVenta(venta.getid(), venta.getnrolocal(),venta.getfechacreacion(), venta.gethoracreacion(), venta.gettrx(), "", "11111111111111");		
	}

	@Override
	public void borrarVenta(String id) {
		ventaRepository.borrarVenta(id,  "", "11111111111111");
	}


	@Override
	public String obtenerTrx(String trx) {
		// TODO Auto-generated method stub
		return null;
	}

}
