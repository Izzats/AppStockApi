package cl.bbr.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;


public class Venta {

	// Variables privadas
	@Id
	private String id;
	private int ven_local;
//	private java.sql.Date ven_fechacrea;
//	private java.sql.Time ven_horacrea;
	private Date ven_fechacrea;
//	@DateTimeFormat(pattern = "hh:mm:ss")
	private Time ven_horacrea;
	private String ven_trx;
	private String ven_boleta;
	private String ven_estado;
	
	// -------------------------------------------------------------------
	public String getven_nrounico() {
		return this.id;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public void setven_nrounico( String ven_nrounico ) {
		this.id = ven_nrounico;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public int getven_local() {
		return this.ven_local;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public void setven_local( int ven_local ) {
		this.ven_local = ven_local;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public String getven_boleta() {
		return this.ven_boleta;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public void setven_boleta( String ven_boleta ) {
		this.ven_boleta = ven_boleta;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public String getven_estado() {
		return this.ven_estado;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public void setven_estado( String ven_estado ) {
		this.ven_estado = ven_estado;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public String getven_trx() {
		return this.ven_trx;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public void setven_trx( String ven_trx ) {
		this.ven_trx = ven_trx;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public java.sql.Date getven_fechacrea() {
		return this.ven_fechacrea;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public void setven_fechacrea( java.sql.Date ven_fechacrea ) {
		this.ven_fechacrea = ven_fechacrea;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public java.sql.Time getven_horacrea() {
		return this.ven_horacrea;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	public void setven_horacrea( java.sql.Time ven_horacrea ) {
		this.ven_horacrea = ven_horacrea;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	@Override
	public String toString() {
		return "[ven_nrounico:" + id 
				+ ", ven_local:" + ven_local
				+ ", ven_boleta:" + ven_boleta 
				+ ", ven_estado:" + ven_estado 
				+ ", ven_trx:" + ven_trx 
				+ "]";
	}
	// -------------------------------------------------------------------

}
