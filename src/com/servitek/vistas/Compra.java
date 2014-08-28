package com.servitek.vistas;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.BuscarItem;
import com.clases.controladores.Campo;
import com.clases.controladores.Dialogo;
import com.clases.controladores.Util;
import com.example.servitek.R;

public class Compra extends Activity implements OnClickListener {

	Admin_BD bd;
	BuscarItem buscar;
	TableLayout tabla;
	TableLayout.LayoutParams layoutFila;
	Button menu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compra);
		tabla = (TableLayout) findViewById(R.id.tabladetalles);
		menu = (Button) findViewById(R.id.menu);
		layoutFila = new TableLayout.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		layoutFila.setMargins(0, 2, 0, 0);
		bd = new Admin_BD(this);
		bd.Escribir();
		fondo.start();
		init();
	}

	private void init() {
		menu.setOnClickListener(this);
	}

	private Handler puente = new Handler() {
		public void handleMessage(Message msg) {
			crearfila((String[]) msg.obj);
		}
	};

	private Thread fondo = new Thread(new Runnable() {
		@Override
		public void run() {
			Cursor c = bd.BuscarOrdenDia(Util.facha());
			Detalles(c);
		}
	});

	protected void crearfila(final String[] datos) {
		TableRow fila = new TableRow(Compra.this);
		fila.setBackgroundResource(R.drawable.fila_tabla);
		fila.setLayoutParams(layoutFila);
		for (int i1 = 0; i1 < datos.length; i1++) {
			if (i1 == 0 || i1 == 1 || i1 == 4) {
				Campo aux = new Campo(this, datos[i1], 0);
				fila.addView(aux);
			} else if (i1 == 2) {
				Campo aux = new Campo(Compra.this, R.drawable.ver_boton);
				aux.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Dialogo(Compra.this, datos[0], datos[1], bd).show();
					}
				});
				fila.addView(aux);
			} else if (i1 == 3) {
				Campo aux = new Campo(Compra.this, R.drawable.edit_boton);
				aux.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent("com.example.servitek.EDITAR");
						Bundle b = new Bundle();
						b.putString("orden", datos[0]);
						b.putString("placa", datos[1]);
						i.putExtras(b);
						startActivity(i);
					}
				});
				fila.addView(aux);
			} else if (i1 == 5) {
				if (datos[i1].equals("0")) {
					Campo aux = new Campo(this, "", 0);
					aux.setBackgroundResource(R.drawable.s0);
					fila.addView(aux);
				} else {
					Campo aux = new Campo(this, "", 0);
					aux.setBackgroundResource(R.drawable.s1);
					fila.addView(aux);
				}

			}
		}
		tabla.addView(fila);
	}

	protected void Detalles(Cursor c) {
		while (c.moveToNext()) {
			String[] str = new String[6];
			str[0] = c.getString(c.getColumnIndexOrThrow("norde"));
			str[1] = c.getString(c.getColumnIndexOrThrow("placa"));
			str[2] = "";
			str[3] = "";
			str[4] = c.getString(c.getColumnIndexOrThrow("estado"));
			str[5] = c.getInt(c.getColumnIndexOrThrow("syncro")) + "";
			Message msg = new Message();
			msg.obj = str;
			puente.sendMessage(msg);
		}
		c.close();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bd.Cerrar();
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.menu:
			Intent acc = new Intent("com.example.servitek.ACCION");
			startActivity(acc);
			break;
		}

	}

}