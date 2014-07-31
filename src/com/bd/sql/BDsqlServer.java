package com.bd.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class BDsqlServer {

	private static final String url = "jdbc:jtds:aqlserver://190.255.217.180:1433/Serviteka";
	private static final String user = "sa";
	private static final String password = "master";
	Connection com = null;
	Context context;

	public BDsqlServer(Context context) {
		this.context = context;
		Conectar();
	}

	private void Conectar() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			com = DriverManager.getConnection(url, user, password);
			Toast.makeText(context, "Conectando...", Toast.LENGTH_SHORT).show();
		} catch (SQLException e) {
			Log.e("SQLException", e+"");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e+"");
		} catch (InstantiationException e) {
			Log.e("InstantiationException", e+"");
		} catch (IllegalAccessException e) {
			Log.e("IllegalAccessException", e+"");
		}
	}

	public ResultSet Enviar(String stsql) {
		Statement st;
		ResultSet rs = null;
		if (com != null) {
			 
			try {
				st = com.createStatement();
				rs = st.executeQuery(stsql);
				com.close();
			} catch (SQLException e) {
				Toast.makeText(context, "Fallo la Coneccion", Toast.LENGTH_SHORT)
				.show();
			}
		}
		return rs;

	}
}
