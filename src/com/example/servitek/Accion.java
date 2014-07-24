package com.example.servitek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;



public class Accion extends Activity implements OnClickListener {

	ImageButton vehiculo, detalles, compras, salir;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accion);

		vehiculo = (ImageButton) findViewById(R.id.vehiculo);
		vehiculo.setOnClickListener(this);
		compras = (ImageButton) findViewById(R.id.compras);
		compras.setOnClickListener(this);
		detalles = (ImageButton) findViewById(R.id.det);
		detalles.setOnClickListener(this);
		salir = (ImageButton) findViewById(R.id.salir);
		salir.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.vehiculo:
			Intent vehi = new Intent("com.example.servitek.VEHICULO");
			startActivity(vehi);
			break;
		case R.id.compras:
			Intent comp = new Intent("com.example.servitek.ORDEN");
			startActivity(comp);
			break;
		case R.id.det:
			Intent detl = new Intent("com.example.servitek.COMPRA");
			startActivity(detl);
			break;
		case R.id.salir:
			Intent login = new Intent("com.example.servitek.LOGIN");
			startActivity(login);
			break;
		}

	}
}