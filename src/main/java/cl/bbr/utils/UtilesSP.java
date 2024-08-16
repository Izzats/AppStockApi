package cl.bbr.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilesSP {

	/**
	 * Formato de time stamp gen�rico para registro journal (yyyymmdd).
	 */
	public static Format formatterFecha = new SimpleDateFormat("yyyyMMdd");

	// Objeto para monitoreo
//	private static Logger logger = Logger.getLogger( UtilesSP.class );

	/**
	 * Retorna los dias de un mes
	 * 
	 * @param aYear  A�o
	 * @param aMonth Mes
	 * @param logger Logger para monitoreo
	 * @return - Retorna los dias del mes
	 */
	// private static int daysPerMonth(int aYear, int aMonth, Logger logger){
	private static int daysPerMonth(int aYear, int aMonth) {

		try {

			boolean esBisiesto;
			esBisiesto = new GregorianCalendar().isLeapYear(aYear);
			final int[] DaysInMonth = new int[13];
			DaysInMonth[1] = 31; // Enero
			DaysInMonth[2] = 28; // Febrero, el m�todo devuelve 29 si es bisiesto
			DaysInMonth[3] = 31; // Marzo
			DaysInMonth[4] = 30; // Abril
			DaysInMonth[5] = 31; // Mayo
			DaysInMonth[6] = 30; // Junio
			DaysInMonth[7] = 31; // Julio
			DaysInMonth[8] = 31; // Agosto
			DaysInMonth[9] = 30; // Septiembre
			DaysInMonth[10] = 31; // Octubre
			DaysInMonth[11] = 30; // Noviembre
			DaysInMonth[12] = 31; // Diciembre

			int resultado = DaysInMonth[aMonth];
			if ((aMonth == 2) && (esBisiesto == true)) {
				return resultado + 1;
			} else
				return resultado;
		} catch (RuntimeException ex) {
//           logger.error( "daysPerMonth --->" + ex );
			return 0;
		} catch (Exception e) {
//        	logger.error( "daysPerMonth --->" + e );
			return 0;
		}
	}

	/**
	 * Valida si una fecha es valida
	 * 
	 * @param Y      Year de la fecha a validar
	 * @param M      Mes de la fecha a validar
	 * @param D      Dia de la fecha a validar
	 * @param logger Logger para monitoreo
	 * @return Retorna true si es una fecha y false en caso contrario
	 */
	public static boolean isValidDate(int Y, int M, int D) {
		try {
			if ((Y == 0) && (M == 0) && (M == 0)) {
				return true;
			}
			if ((Y >= 1) && (Y <= 9999) && (M >= 1) && (M <= 12) && (D >= 1) && (D <= daysPerMonth(Y, M))) {
				return true;
			} else
				return false;
		} catch (RuntimeException ex) {
//            logger.error( "isValidDate --->" + ex );
			return false;
		} catch (Exception e) {
//        	logger.error( "isValidDate --->" + e );
			return false;
		}
	}

	/**
	 * Obtiene la fecha modificada, disminuyendo o incrementada en la cantidad de
	 * dias indicado.
	 * 
	 * @param fecha     a modificar en formato AAAAMMDD
	 * @param cantDias  Cantidad de dias aumento o incremento de la fecha
	 * @param flagSigno Indica si aumentar o dismunir los d�as
	 * @param logger    Logger para monitoreo
	 * @return String con la fecha en formato AAAAMMDD
	 */
	public static String obtenerFechaDiaModificada(String fecha, int cantDias, boolean flagSigno) {
		try {
			String fechaModificada;

			// Se obtiene a�o, mes y d�a
			int ano = Integer.parseInt(fecha.substring(0, 4));
			int mes = Integer.parseInt(fecha.substring(4, 6));
			int dia = Integer.parseInt(fecha.substring(6, 8));

			if ((ano == 0) || (mes == 0) || (dia == 0)) {
				return null;
			}
			if (!isValidDate(ano, mes, dia)) {
				return null;
			}

			mes -= 1; // 0 based
			GregorianCalendar calendar = new GregorianCalendar(ano, mes, dia);

			// le agrego la cantidad de meses
			if (flagSigno == true)
				calendar.add(Calendar.DATE, cantDias);
			else
				calendar.add(Calendar.DATE, -cantDias);

			// Obtener year
			int valorFecha = calendar.get(Calendar.YEAR);
			fechaModificada = String.valueOf(valorFecha);

			// Obtener mes
			valorFecha = calendar.get(Calendar.MONTH);
			valorFecha += 1;
			if (valorFecha < 10) {
				fechaModificada = fechaModificada.concat("0");
			}
			fechaModificada = fechaModificada.concat(String.valueOf(valorFecha));

			// Obtener dia
			valorFecha = calendar.get(Calendar.DAY_OF_MONTH);
			if (valorFecha < 10) {
				fechaModificada = fechaModificada.concat("0");
			}
			fechaModificada = fechaModificada.concat(String.valueOf(valorFecha));

			return fechaModificada;
		} catch (RuntimeException ex) {
//			logger.error( "obtenerFechaModificada --->" + ex );
			return null;
		} catch (Exception e) {
//			logger.error( "obtenerFechaModificada --->" + e );
			return null;
		}
	}

	/**
	 * Obtiene el dia de la semana de la fecha actual menos cantidad de dias
	 * indicada por parametro
	 * 
	 * @param cantDias Cantidad de dias a restar del dia actual
	 * @param logger   Logger para monitoreo
	 * @return Retorna el dia de la semana
	 */
	public static String obtenerDiaSemana(int cantDias) {
		try {
			String cbDia[] = { "Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" };
			Calendar calendar = new GregorianCalendar();
			Date actualTime = new Date();
			calendar.setTime(actualTime);

			// Obtener dia
			calendar.add(Calendar.DATE, -cantDias);
			int dia = calendar.get(Calendar.DAY_OF_WEEK);
			String diaSemana = cbDia[dia - 1];
			return diaSemana;
		} catch (Exception e) {
//			logger.error( "obtenerDiaSemana --->" + e );
			return null;
		}
	}

	/**
	 * Valida si el string es numerico
	 * 
	 * @param string String a validar
	 * @param logger Logger para monitoreo
	 * @return Retorna int 1 => OK, 0 => Error
	 */
	public static int validarInteger(String string) {
		try {
			for (int i = 0; i < string.length(); i++) {
				if (!Character.isDigit(string.charAt(i))) {
					return (0);
				}
			}
			return (1);
		} catch (RuntimeException ex) {
//        	logger.error("validarInteger --->" + ex );
			return 0;
		} catch (Exception e) {
//        	logger.error("validarInteger --->" + e );
			return 0;
		}
	}

	/**
	 * Al string lo formatea al largo len con espacios en blanco a la derecha
	 * 
	 * @param string String a formatear, puede ser vacio
	 * @param len    Largo del string final
	 * @param logger Logger para monitoreo
	 * @return Retorna el string formateado o null cuando hay error
	 */
	public static String setStringVacio(String string, int len) {
		try {
			int lenDif = len - string.length();
			if (lenDif < 0) {
//                logger.error("setStringVacio ---> El len variable no corresponde " + string);
				return null;
			}
			StringBuffer auxString = new StringBuffer(string);
			for (int i = 0; i < lenDif; i++) {
				auxString.append(" ");
			}
			return auxString.toString();
		} catch (RuntimeException ex) {
//            logger.error("setStringVacio --->" + ex );
			return null;
		} catch (Exception e) {
//        	logger.error("setStringVacio --->" + e );
			return null;
		}
	}

	/**
	 * Al string lo formatea al largo len con ceros a la izquierda
	 * 
	 * @param string String a formatear, puede ser vacio
	 * @param len    Largo del string final
	 * @param logger Logger para monitoreo
	 * @return Retorna el string formateado o null cuando hay error
	 */
	public static String setIntegerVacio(String string, int len) {
		try {
			int lenDif = len - string.length();
			if (lenDif < 0) {
//                logger.error("setIntegerVacio ---> El len variable no corresponde " + string);
				return null;
			}
			StringBuffer auxString = new StringBuffer();
			for (int i = 0; i < lenDif; i++) {
				if (i == 0) {
					auxString.append("0");
				} else {
					auxString.append("0");
				}
			}
			auxString.append(string);
			return auxString.toString();
		} catch (RuntimeException ex) {
//            logger.error("setIntegerVacio --->" + ex );
			return null;
		} catch (Exception e) {
//        	logger.error( "setIntegerVacio --->" + e );
			return null;
		}
	}

	/**
	 * Al string lo formatea al largo len con ESPACIOS a la izquierda. creado el
	 * 10.11.2011
	 * 
	 * @param string String a formatear, puede ser vac�o
	 * @param len    Largo del string final
	 * @param logger Logger para monitoreo
	 * @return Retorna el string formateado o
	 * @author Marco Subiabre null cuando hay error
	 */
	public static String setIntegerVacio2(String string, int len) {
		try {
			int lenDif = len - string.length();
			if (lenDif < 0) {
//                logger.error("setIntegerVacio ---> El len variable no corresponde " + string);
				return null;
			}
			StringBuffer auxString = new StringBuffer();
			for (int i = 0; i < lenDif; i++) {
				if (i == 0) {
					auxString.append(" ");
				} else {
					auxString.append(" ");
				}
			}
			auxString.append(string);
			return auxString.toString();
		} catch (RuntimeException ex) {
//            logger.error("setIntegerVacio --->" + ex );
			return null;
		} catch (Exception e) {
//        	logger.error( "setIntegerVacio --->" + e );
			return null;
		}
	}

	/**
	 * Al string lo formatea al largo len con ceros a la derecha
	 * 
	 * @param string String a formatear, puede ser vacio
	 * @param len    Largo del string final
	 * @param logger Logger para monitoreo
	 * @return Retorna el string formateado o null cuando hay error
	 */
	public static String setIntegerVacioDer(String string, int len) {
		try {
			int lenDif = len - string.length();
			if (lenDif < 0) {
//                logger.error("setIntegerVacio ---> El len variable no corresponde " + string);
				return null;
			}
			StringBuffer auxString = new StringBuffer();
			for (int i = 0; i < lenDif; i++) {
				if (i == 0) {
					auxString.append("0");
				} else {
					auxString.append("0");
				}
			}
			StringBuffer auxFinal = new StringBuffer();
			auxFinal.append(string);
			auxFinal.append(auxString);
			return auxFinal.toString();
		} catch (RuntimeException ex) {
//            logger.error("setIntegerVacio --->" + ex );
			return null;
		} catch (Exception e) {
//        	logger.error( "setIntegerVacio --->" + e );
			return null;
		}
	}

	/**
	 * Trunca el string al largo especificado. Si el string es m�s corto que el
	 * valor especificado rellena con espacio en blanco a la derecha.
	 * 
	 * @param string String a formatear, puede ser vacio
	 * @param len    Largo del string final
	 * @param logger Logger para monitoreo
	 * @return Retorna el string formateado o null cuando hay error
	 */
	public static String truncString(String str, int len) {
		try {
			String ret = null;
			if (str.length() > len)
				ret = str.substring(0, len);
			else if (str.length() <= len)
				ret = UtilesSP.setStringVacio(str, len);
			return ret;

		} catch (RuntimeException ex) {
//			logger.error("truncString --->" + ex);
			return null;
		} catch (Exception e) {
//			logger.error("truncString --->" + e);
			return null;
		}
	}

	/**
	 * Logging de excepciones.
	 * 
	 * @param logger Objeto para realizar logging del stack trace de Java.
	 * @param e      Excepci�n recibida.
	 */
	public static void logstacktrace(Exception e) {
		// Se loggea excepcion y mensaje
//		logger.info( e.toString() );

		// Se obtiene stack de trace
		StackTraceElement stack[] = e.getStackTrace();

		// Se detalla las condiciones del error
		for (int i = 0; i < stack.length; i++) {
			String filename = stack[i].getFileName();
			if (filename == null) {
				continue;
			}
			String className = stack[i].getClassName();
			String methodName = stack[i].getMethodName();
			int line = stack[i].getLineNumber();
//            logger.error( filename + " " + 
//            		      className + " " + 
//            		      methodName + " " + 
//               		      line );
		}
	}

	/**
	 * setea un string a vacio si este es un null
	 * 
	 * @param string
	 * @return retorna un string
	 */
	public static String analizaString(String string) {
		try {
			if (string == null || string.equals("null"))
				string = "";

			return string;
		} catch (RuntimeException ex) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Verifica que la variable pasada como parametro sea un String valido, ni nulo
	 * ni vacio
	 * 
	 * @param String variable - Variable a validar
	 * @param String nombre - nombre de la variable a validar
	 * @return boolean. True si el String es valido
	 */
	public static boolean isStringValido(String variable, String nombre) {
		try {
			if (variable == null) {
//				logger.info( " isStringValido: String "+ nombre + " es null");
				return false;
			}
			if (variable.length() == 0) {
//				logger.info( "isStringValido: String "+ nombre + " vacio");
				return false;
			}
			if (variable.trim().length() == 0) {
//				logger.info( " isStringValido: String "+ nombre + " vacio");
				return false;
			}
			return true;
		} catch (RuntimeException ex) {
//			logger.error( " isStringValido --->" + ex );
		} catch (Exception e) {
//			logger.error( " isStringValido --->" + e );
		}
		return false;
	}

	/**
	 * Verifica que la variable pasada como parametro sea un String Numerico valido
	 * 
	 * @param String variable - Variable Numerica a validar
	 * @param String nombre - nombre de la variable a validar
	 * @return boolean. True si el String es valido y num�rico
	 */
	public static boolean isStringNumericoValido(String variable, String nombre) {
		try {
			if (!isStringValido(variable, nombre)) {
				return false;
			}

			if (UtilesSP.validarInteger(variable) == 0) {
//				logger.info( " isStringNumericoValido: String "+ nombre + " no es numerico " + variable);
				return false;
			}
			return true;
		} catch (RuntimeException ex) {
//			logger.error( " isStringNumericoValido --->" + ex );
		} catch (Exception e) {
//			logger.error( " isStringNumericoValido --->" + e );
		}
		return false;
	}

	/**
	 * 
	 * @param variable(es un valor on el formato 9999900)
	 * @param cant,       es la cantidad de decimales del objeto variable
	 * @param logger      registra un error en caso de ser necesario
	 * @return 99999.00 || 9999.900 (segun el valor de la cantidad)
	 */
	public static String obtieneDecimal(String variable, int cant) {
		try {
			if (variable.length() < 3)
				return variable;
			String entero = variable.substring(0, (variable.length() - cant));
			String decimal = variable.substring((variable.length() - cant));
			return entero + "." + decimal;
		} catch (RuntimeException ex) {
//			logger.error( " obtieneDecimal --->" + ex );
		} catch (Exception e) {
//			logger.error( " obtieneDecimal --->" + e );
		}
		return null;
	}

	// *******************************************************************
	// *******************************************************************
	/**
	 * Obtiene la fecha actual del sistema.
	 * 
	 * @return String con la fecha en formato AAAAMMDD
	 */
	public static String obtenerSoloFechaActual() {
		try {
			Calendar calendar = new GregorianCalendar();
			Date actualTime = new Date();
			calendar.setTime(actualTime);

			String fecha;

			// Obtener year
			int valorFecha = calendar.get(Calendar.YEAR);
			fecha = String.valueOf(valorFecha);

			// Obtener mes
			valorFecha = calendar.get(Calendar.MONTH);
			valorFecha += 1;
			if (valorFecha < 10) {
				fecha = fecha.concat("0");
				fecha = fecha.concat(String.valueOf(valorFecha));
			} else {
				fecha = fecha.concat(String.valueOf(valorFecha));
			}

			// Obtener dia
			valorFecha = calendar.get(Calendar.DAY_OF_MONTH);
			if (valorFecha < 10) {
				fecha = fecha.concat("0");
				fecha = fecha.concat(String.valueOf(valorFecha));
			} else {
				fecha = fecha.concat(String.valueOf(valorFecha));
			}

			return fecha;
		} catch (RuntimeException re) {
		} catch (Exception e) {
		}
		return null;
	}

	// *******************************************************************
	// *******************************************************************
	/**
	 * Obtiene la Hora actual del sistema.
	 * 
	 * @return String con la Hora en formato HHMMSS
	 */
	public static String obtenerSoloHoraActual() {
		try {
			Calendar calendar = new GregorianCalendar();
			Date actualTime = new Date();
			calendar.setTime(actualTime);

			String hora;

			// Obtener horas
			int valorHora = calendar.get(Calendar.HOUR_OF_DAY);
			if (valorHora < 10) {
				hora = "0";
				hora = hora.concat(String.valueOf(valorHora));
			} else {
				hora = String.valueOf(valorHora);
			}

			// Obtener minutos
			valorHora = calendar.get(Calendar.MINUTE);
			if (valorHora < 10) {
				hora = hora.concat("0");
				hora = hora.concat(String.valueOf(valorHora));
			} else {
				hora = hora.concat(String.valueOf(valorHora));
			}

			// Obtener segundos
			valorHora = calendar.get(Calendar.SECOND);
			if (valorHora < 10) {
				hora = hora.concat("0");
				hora = hora.concat(String.valueOf(valorHora));
			} else {
				hora = hora.concat(String.valueOf(valorHora));
			}

			return hora;
		} catch (RuntimeException re) {
		} catch (Exception e) {
		}
		return null;
	}

	// *******************************************************************
	// *******************************************************************
	/**
	 * transforma el string fecha a un formato string fecha distinto
	 * 
	 * @param fechaInicio string fecha en formato AAAAMMDD
	 * @return String fecha en formato AAAA-MM-DD
	 */
	public static String formatFechaADate(String fechaInicio) {
		try {
			String ano, mes, dia;

			ano = fechaInicio.charAt(0) + "" + fechaInicio.charAt(1) + "" + fechaInicio.charAt(2) + ""
					+ fechaInicio.charAt(3);
			mes = fechaInicio.charAt(4) + "" + fechaInicio.charAt(5);
			dia = fechaInicio.charAt(6) + "" + fechaInicio.charAt(7);

			return ano + "-" + mes + "-" + dia;
		} catch (Exception e) {
			// logger.error("obtieneFecha : Exception "+e);
			return null;
		}
	}

	// *******************************************************************
	// *******************************************************************
	/**
	 * Convierte una fecha en un string con un formato especifico.
	 * 
	 * @param vardFecha   Fecha
	 * @param varcFormato el formato de salida, por ejemplo "dd.MM.yyyy"
	 * @return fecha ya formatada en texto
	 */
	public static String formatDateToString(Date vardFecha, String varcFormato) {
		String varcFecha;
		SimpleDateFormat dateformatDDMMYYYY = new SimpleDateFormat(varcFormato);
		StringBuilder salidaDDMMYYYY = new StringBuilder(dateformatDDMMYYYY.format(vardFecha));
		varcFecha = "" + salidaDDMMYYYY;
		return varcFecha;
	}

	// *******************************************************************
	// *******************************************************************
	public static String formatHora(String str) {
		try {
			String hora = new String();
			if (str != null && str.length() == 6) {
				hora = hora.concat(str.substring(0, 2));
				hora = hora.concat(":");
				hora = hora.concat(str.substring(2, 4));
				hora = hora.concat(":");
				hora = hora.concat(str.substring(4, 6));
				return hora;
			}
		} catch (RuntimeException re) {
		} catch (Exception e) {
		}
		return null;
	}

	// *******************************************************************
	// *******************************************************************
	/**
	 * Obtiene la Hora actual del sistema.
	 * 
	 * @return String con la Hora en formato HHMMSSmmm
	 */
	public static String obtenerSoloHoraActualMilisegundos() {
		try {
			Calendar calendar = new GregorianCalendar();
			Date actualTime = new Date();
			calendar.setTime(actualTime);

			String hora;

			// Obtener horas
			int valorHora = calendar.get(Calendar.HOUR_OF_DAY);
			if (valorHora < 10) {
				hora = "0";
				hora = hora.concat(String.valueOf(valorHora));
			} else {
				hora = String.valueOf(valorHora);
			}

			// Obtener minutos
			valorHora = calendar.get(Calendar.MINUTE);
			if (valorHora < 10) {
				hora = hora.concat("0");
				hora = hora.concat(String.valueOf(valorHora));
			} else {
				hora = hora.concat(String.valueOf(valorHora));
			}

			// Obtener segundos
			valorHora = calendar.get(Calendar.SECOND);
			if (valorHora < 10) {
				hora = hora.concat("0");
				hora = hora.concat(String.valueOf(valorHora));
			} else {
				hora = hora.concat(String.valueOf(valorHora));
			}

			// Obtener milisegundos
			valorHora = calendar.get(Calendar.MILLISECOND);
			if (String.valueOf(valorHora).length() < 2)
				hora = hora.concat("00" + String.valueOf(valorHora));
			else {
				if (String.valueOf(valorHora).length() == 2)
					hora = hora.concat("0" + String.valueOf(valorHora));
				else
					hora = hora.concat(String.valueOf(valorHora));
			}

			return hora;
		} catch (RuntimeException re) {
		} catch (Exception e) {
		}
		return null;
	}

	// *******************************************************************
	// *******************************************************************
	public static String replace(String string, String oldString, String newString) {
		string = string.replace("'", "''");
		return string;
	}

	// *******************************************************************
	// *******************************************************************
	/**
	 * @param valor EN FORMATO 99999.99
	 *
	 * @return 99999
	 */
	public static int obtieneEnteroDeDouble(double valor) {
		try {
			BigDecimal iva = new BigDecimal(valor);
			int resp = (int) iva.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
			return resp;
		} catch (Exception e) {
			return 0;
		}
	}

	// *******************************************************************
	// *******************************************************************
	/**
	 * mueve los archivos de una carpeta a otra. No devuelve nada en caso de error.
	 * 
	 * @param sourceFile      tipo string, archivo de origen.
	 * @param destinationFile tipo string, archivo de destino
	 */
	public static void fileMove(String sourceFile, String destinationFile) {
		System.out.println("Desde: " + sourceFile);
		System.out.println("Hacia: " + destinationFile);

		try {
			File inFile = new File(sourceFile);
			File outFile = new File(destinationFile);

			FileInputStream in = new FileInputStream(inFile);
			FileOutputStream out = new FileOutputStream(outFile);

			int c;
			while ((c = in.read()) != -1)
				out.write(c);

			in.close();
			out.close();

			File file = new File(sourceFile);
			if (file.exists()) {
				file.delete();
			}

		} catch (IOException e) {
			System.err.println("Hubo un error de entrada/salida!!!");
		}
	}
	// *******************************************************************

	// *******************************************************************
	// *******************************************************************
	/**
	 * Valida si el directorio especificado existe. Tiene la opcion de crearlo
	 * 
	 * @param path   string con la ruta de entrada
	 * @param action boolean, si es true y no encuentra directorio, lo crea.
	 * @return
	 */
	public static boolean validateDir(String path, boolean action) {
		File file = new File(path);
		boolean isDirectory = file.isDirectory();
		if (!isDirectory && action) // si no existe el directorio y puedo crearlo, entonces lo crea.
			file.mkdirs();
		return isDirectory;
	}
	// *******************************************************************

	// *******************************************************************
	// *******************************************************************
	/**
	 * Convierte de String a Date
	 * 
	 * @param vcFecha   texto con la fecha a convertir
	 * @param vcFormato el formato dd=dia, MM=mes, yyyy=a�o
	 * @return
	 */
	public static Date StringToDate(String vcFecha, String vcFormato) {
		Date vcFechaSalida = null;
		try {
			if (vcFecha.isEmpty()) // string de entrada es vacio
				return null;

			if (vcFecha.length() < 8 || vcFecha.length() > 10) // largo de fecha inferior a 8
				return null;

			SimpleDateFormat sdf = new SimpleDateFormat(vcFormato);
			vcFechaSalida = (Date) sdf.parse(vcFecha);

		} catch (Exception e) {
//			UtilesSP.logstacktrace(logger, e);
			return null;
		}
		return vcFechaSalida;
	}
	// *******************************************************************

	// -------------------------------------------------------------------
	// Definidas por Andres Caniumilla
	// -------------------------------------------------------------------

	/*
	 * Crea un String, con la repetici�n de n veces el valor de s
	 */
	// -------------------------------------------------------------------
	public static String repetirString(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	/*
	 * Formatear Titulo a Longitud Definida
	 */
	public static String FormatearTitulo(String s) {
		String nuevo = null;

		// Centra el Titulo
		int resta = 80 - s.length() - 2;
		nuevo = repetirString("=", resta / 2) + " " + s + " "
				+ repetirString("=", resta / 2 + ((resta % 2 == 0) ? 0 : 1));
		return (nuevo);
	}
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	/*
	 * Convierte un Array de String a String, separando cada campo por un caracter
	 * delimitador
	 */
	public static String ConvertirToString(String[] stringArray, String delimitador) {
		StringBuilder sb = new StringBuilder();
		for (String elemento : stringArray) {
			if (elemento == null) {
				sb.append(delimitador);
			} else {
				sb.append(elemento);
				sb.append(delimitador);
			}
		}
		return sb.toString();
	}
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	/*
	 * Imprime la Estructura de la Estructura del ResultSet
	 */
	public static void EstructuraResultSet(ResultSet rs) {
		ResultSetMetaData infoResultados;
//		logger.debug( "--- Estructura ResultSet ---");
		try {
			infoResultados = rs.getMetaData();
			for (int i = 1; i <= infoResultados.getColumnCount(); i++) {
//				logger.debug( "[" + i + "] Campo " + infoResultados.getColumnLabel(i) + "\t" +"Tipo: " + infoResultados.getColumnTypeName(i) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
//		logger.debug( "--- Fin Estructura ---");
	}
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	/**
	 * Funci�n que elimina acentos y caracteres especiales de una cadena de texto.
	 * 
	 * @param input
	 * @return cadena de texto limpia de acentos y caracteres especiales.
	 */
	public static String LimpiarString(String input) {

		// Caracteres validos
		String valido = "[0-9a-zA-Z�� ]*";

		// Cadena de caracteres original a sustituir.
		String orig = "��������������u�������������������";

		// Cadena de caracteres ASCII que reemplazar�n los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		String subcadena;
		int pos, i;
		char letra;

		// No es NULL y algun caracter de la cadena no es validos
		if (input != null && !input.isEmpty() && !input.matches(valido)) {

			// Log
//			logger.debug( "LimpiarString -> input  [" + input + "]" );

			// Recorre el String
			for (i = 0; i < output.length(); i++) {

				// Toma la letra
				subcadena = output.substring(i, i + 1);
				letra = output.charAt(i);

				// Es un caracter NO valido
				if (!subcadena.matches(valido)) {

					// Verifica si est� en el Patron (orig)
					pos = orig.indexOf(letra);
					if (pos >= 0) {

						// Reemplazamos los caracteres especiales.
						output = output.replace(letra, ascii.charAt(pos));
					} else {
						// No esta en el patr�n. Se reemplaza con un espacio en blanco
						output = output.replace(letra, ' ');
					}
				}
			}

			// Quita blancos
			output = output.trim();

			// Log
//			logger.debug( "LimpiarString -> output [" + output + "]" );
		}

		return output;
	}
	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	/**
	 * Fecha 24.04.2012, devuelve el digito verificador de un Rut
	 * 
	 * @author Marco Subiabre
	 * @param vnRut
	 * @return valor del DV, si es vacio entonces es error
	 */
	public static String ObtieneDV(int vnRut) {
		String vcDv = "";
		int vnFactor = 0;
		int vnSuma = 0;
		int vnDigito = 0;
		int vnResto = 0;
		int vnResultado = 0;

		try {
			for (vnFactor = 2, vnSuma = 0; vnRut > 0; vnFactor++) {
				vnDigito = vnRut % 10;
				vnRut /= 10;
				vnSuma += vnDigito * vnFactor;
				if (vnFactor >= 7)
					vnFactor = 1;
			}

			// Ahora viene el algoritmo del m�dulo 11
			vnResto = vnSuma % 11;
			vnResultado = 11 - vnResto;

			// Si el resultado es menor que 10, devuelve el n�mero.
			// Si es igual a 10, entonces devuelve "K"
			// Si no, entonces devuelve VACIO = error
			if (vnResultado < 10)
				vcDv = String.valueOf(vnResultado);
			else if (vnResultado == 10)
				vcDv = "K";
			else if (vnResultado == 11)
				vcDv = "0";

			return vcDv;

		} catch (Exception e) {
			return "";
		}
	}

	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	/*
	 * Convierte un Objeto a JSON
	 * 
	 * @param Object Objeto de cualquier tipo
	 * 
	 * @return String JSON
	 */
	public static String ConvertirJSON(Object objeto) {

		// Variable Local
		String jsonInString;

		// Lo Mapea como un JSON
		ObjectMapper mapper = new ObjectMapper();
		jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(objeto);
		} catch (JsonProcessingException e) {
//			UtilesSP.logstacktrace( logger, e );
		}

		return jsonInString;
	}
	// -------------------------------------------------------------------

	public static boolean isValidRut(String rutConDigitoVerificador) {
		try {
			// Validar rut
			if (!UtilesSP.isStringValido(rutConDigitoVerificador, "rutConDigitoVerificador")) {

				return false;
			}
			System.out.println("dv desde UtilesSP "+rutConDigitoVerificador.substring(0, rutConDigitoVerificador.length() - 1));
			String dv = UtilesSP.ObtieneDV(
					Integer.parseInt(rutConDigitoVerificador.substring(0, rutConDigitoVerificador.length() - 1)));

			String digitoVerificador = rutConDigitoVerificador.substring(rutConDigitoVerificador.length() - 1,
					rutConDigitoVerificador.length());
			System.out.println("dv = "+dv);
			System.out.println("digitoVerificador = "+digitoVerificador);
//			if (!dv.equals(digitoVerificador)) {
//				System.out.println("Rut " + rutConDigitoVerificador + " inválido");
//				return false;
//			}

			int largo = rutConDigitoVerificador.length();
			if (largo < 3) {
				System.out.println("Utiles: isValidRut2: rut muy corto");
				return false;
			}
			return true;
		} catch (RuntimeException ex) {
			System.out.println(ex);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;

	}
	
	public static Boolean validaEmail(String email) {
		Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
