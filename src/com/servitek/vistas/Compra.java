package com.servitek.vistas;

import java.sql.ResultSet;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.clases.controladores.Admin_BD;
import com.clases.controladores.BuscarItem;
import com.clases.controladores.HiloAsyn;
import com.clases.controladores.HiloListener;
import com.example.servitek.R;

public class Compra extends Activity implements HiloListener {

	AutoCompleteTextView placa;
	Admin_BD bd;
	String[] datos;
	Cursor cursor;
	BuscarItem buscar;

    final static int[] to = new int[] { android.R.id.text1 };
    final static String[] from = new String[] { "placa" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compra);
		bd = new Admin_BD(this);
		bd.Leer();
		placa = (AutoCompleteTextView) findViewById(R.id.Autocom);
		placa.setThreshold(1);
		cursor = bd.AutoComplete("");      
        buscar = new BuscarItem(getApplicationContext(), cursor);
        placa.setAdapter(buscar);
		
	}

	

	private void SincronizarDB() {
		new HiloAsyn(Compra.this, true, this)
				.execute("INSERT vehiculo (Placa,Codter,Codmarca,Codcolor,Modelo,Codclase) VALUES ('PTR - 024','1047389512',4587,45851823,1478,7892)");
	}

	@Override
	public void Resultado(ResultSet r) {
		Toast.makeText(this, "termino", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void Confir(boolean s) {
		if (s) {
			Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
		}
	}
}