package com.example.servitek;



import java.sql.ResultSet;

import com.bd.sql.HiloAsyn;
import com.bd.sql.HiloResponde;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;



public class Compra extends Activity implements HiloResponde {

	Thread eje;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compra);
		SincronizarDB();
	}


	private void SincronizarDB() {
		new HiloAsyn(this).execute();
	}


	@Override
	public void Resultado(ResultSet r) {
		Toast.makeText(this, "termino", Toast.LENGTH_SHORT)
		.show();
	}
}