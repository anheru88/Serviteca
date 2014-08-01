package com.example.servitek;



import java.sql.ResultSet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.bd.sql.HiloAsyn;
import com.bd.sql.HiloListener;



public class Compra extends Activity implements HiloListener {

	Thread eje;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compra);
		SincronizarDB();
	}


	private void SincronizarDB(){
		new HiloAsyn(Compra.this, true, this).execute("INSERT vehiculo (Placa,Codter,Codmarca,Codcolor,Modelo,Codclase) VALUES ('PTR - 024','1047389512',4587,45851823,1478,7892)");
	}


	@Override
	public void Resultado(ResultSet r) {
		Toast.makeText(this, "termino", Toast.LENGTH_SHORT)
		.show();
	}


	@Override
	public void Confir(boolean s) {
		if (s) {
			Toast.makeText(this, "save", Toast.LENGTH_SHORT)
			.show();
		}
	}
}