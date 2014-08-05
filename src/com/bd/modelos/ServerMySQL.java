package com.bd.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.util.Log;

public class ServerMySQL {

	private static final String usuario = "sa";
	private static final String contrasena = "master";
	private static final String ip = "190.255.217.180"; 
	private static final String puerto = "1433";
	private static final String bd = "Serviteka";
	Connection conexionMySQL;

	public ServerMySQL() {
		ConectarBDMySQL();
	}

	public void ConectarBDMySQL() {
		if (conexionMySQL == null) {
			String urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/"
					+ bd;
			try {

				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conexionMySQL = DriverManager.getConnection(urlConexionMySQL,
						usuario, contrasena);
				Log.i("login", "exitoso");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				Log.e("ERRORC", e + " ");
			} catch (SQLException e) {
				Log.e("ERRORS", e + " ");
			}
		}
	}

	public ResultSet Capturar(String SQLEjecutar) {

		Statement st;
		ResultSet rs = null;
		try {
			st = conexionMySQL.createStatement();
			rs = st.executeQuery(SQLEjecutar);
		} catch (SQLException e) {
			Log.e("ERROR", e + "");
		}
		return rs;
	}
}
