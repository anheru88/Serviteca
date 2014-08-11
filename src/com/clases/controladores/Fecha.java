package com.clases.controladores;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Fecha {
	public static String facha() {
		Calendar hoy = Calendar.getInstance();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		String c = formato.format(hoy.getTime()); 
		return c;
	}
}
