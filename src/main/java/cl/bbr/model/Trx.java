package cl.bbr.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModelProperty;

public class Trx {

	// Variables privadas
	@ApiModelProperty(value="Igual a Id de trx")
	private String id;
	@ApiModelProperty(value="Número del local de la venta")
	private int nrolocal;

	@ApiModelProperty(value="Día de la venta")
	@JsonFormat(pattern="yyyy:MM:dd", shape = Shape.STRING)
	private String fechacreacion;
	
	@ApiModelProperty(value="Hora de la venta")
	@JsonFormat(pattern="hh:mm:ss", shape = Shape.STRING)
	private String horacreacion;

	@ApiModelProperty(value="Transacción")
	private String trx;

	
	public String getid() {
		return this.id;
	}

	public int getnrolocal() {
		return this.nrolocal;
	}

	public String gettrx() {
		return this.trx;
	}

	public String getfechacreacion() {
		return this.fechacreacion;
	}

	public String gethoracreacion() {
		return this.horacreacion;
	}

	public String toString() {
		
		// Valida si es NULL
		if ( trx == null ) {
			
			return "[id:" + id + "]";
		}
		else {
			return "[id:" + id 
					+ ", nrolocal:" + nrolocal
					+ ", fechacreacion:" + fechacreacion 
					+ ", horacreacion:"	+ horacreacion
					+ ", trx:"	+ trx 
					+ "]";
		}
	}
	// -------------------------------------------------------------------

	}


