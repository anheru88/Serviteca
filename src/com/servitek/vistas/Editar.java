package com.servitek.vistas;


import com.example.servitek.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Editar extends Activity{
	TextView orden;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar);
		orden = (TextView) findViewById(R.id.orden);
		
		Bundle recogerDatos = getIntent().getExtras();
		String nun = recogerDatos.getString("orden");
		String placa = recogerDatos.getString("placa");
		orden.setText(nun+" "+placa);
		
		
		
		
	}

	

}
