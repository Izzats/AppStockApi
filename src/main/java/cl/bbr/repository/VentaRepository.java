package cl.bbr.repository;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.bbr.model.Venta;


@Repository
public interface VentaRepository extends CrudRepository<Venta, String> {
	
	@Query("SELECT ven_nrounico AS id,ven_local,ven_fechacrea,ven_horacrea,ven_boleta,ven_estado,ven_trx "
		     + " FROM EXT_VENTAS_TRX "
			 + " ORDER BY ven_fechacrea DESC,ven_horacrea DESC")
	public List<Venta> obtenerVentas();
	
	
	@Query("SELECT ven_nrounico id,ven_local,ven_fechacrea,ven_horacrea,ven_boleta,ven_estado,ven_trx "
		     + " FROM EXT_VENTAS_TRX "
		     + "WHERE VEN_NROUNICO =:nrounico")
	public Venta ventasPorId(@Param("nrounico") String id);
	
	@Transactional
	@Modifying
	@Query(value = "{call SP_APPS_INSERTA( :nrounico, :local, :fechacrea, :horacrea, :trx, :boleta, :estado)}")
	public void guardarVenta(@Param("nrounico") String ven_nrounico, @Param("local") int ven_local, @Param("fechacrea") String ven_fechacrea, @Param("horacrea") String ven_horacrea, @Param("trx") String ven_trx, @Param("boleta") String ven_boleta, @Param("estado") String ven_estado);

	
	@Transactional
	@Modifying
	@Query(value = "{call SP_APPS_BORRA( :nrounico, :param1, :param2)}")
	public void borrarVenta(@Param("nrounico") String ven_nrounico, @Param("param1") String ven_boleta, @Param("param2") String ven_estado);

	
}
