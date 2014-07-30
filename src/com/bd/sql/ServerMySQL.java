package com.bd.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.util.Log;

public class ServerMySQL {

	private  String usuario ;
	private  String contrasena ;
	private  String ip ;
	private  String puerto;
	private  String catalogo;
	
	Connection  conexionMySQL;

	

	public ServerMySQL(String usuario, String contrasena, String ip,
			String puerto, String catalogo) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.ip = ip;
		this.puerto = puerto;
		this.catalogo = catalogo;
		ConectarBDMySQL();
	}

	public void ConectarBDMySQL() {
		if (conexionMySQL == null) {
			String urlConexionMySQL = "";
			if (catalogo != "")
				urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/"
						+ catalogo;
			else
				urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto;
			if (usuario != "" & contrasena != "" & ip != "" & puerto != "") {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conexionMySQL = DriverManager.getConnection(urlConexionMySQL, usuario, contrasena);
				} catch (ClassNotFoundException e) {
					Log.e("ERROR", e +"");
				} catch (SQLException e) {
					Log.e("ERROR", e +"");
				}
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
			Log.e("ERROR", e +"");
		}
		return rs; 
	}
}
