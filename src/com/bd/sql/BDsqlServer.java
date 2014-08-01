package com.bd.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.util.Log;

public class BDsqlServer {

	private static final String url = "jdbc:jtds:sqlserver://Serviteka.mssql.somee.com:1433/Serviteka";
	private static final String user = "1047389512_SQLLogin_1";
	private static final String password = "qikxi5vux3";
	public Connection com = null;

	public BDsqlServer() {
		Conectar();
	}

	private void Conectar() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			com = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			Log.e("SQLException", e + "");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e + "");
			e.printStackTrace();
		} catch (InstantiationException e) {
			Log.e("InstantiationException", e + "");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e("IllegalAccessException", e + "");
			e.printStackTrace();
		}
	}

	public void Insertar(String stsql) {
		Statement st;
		if (com != null) {
			try {
				st = com.createStatement();
				st.execute(stsql);
			} catch (SQLException e) {
				Log.i("Coneccion", e + "");
				return;
			}
		}
	}

	public ResultSet Consultar(String stsql) {
		Statement st;
		ResultSet rs = null;
		if (com != null) {
			try {
				st = com.createStatement();
				rs = st.executeQuery(stsql);
				com.close();
			} catch (SQLException e) {
				Log.i("Coneccion", e + "");
				return rs;
			}
		}
		return rs;
	}

	public void CerrarConeccion() {
		try {
			com.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
