package com.clases.controladores;


import com.example.servitek.R;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Dialogo extends Dialog{

	

	TableLayout tabla;
	TableLayout.LayoutParams params;
	TextView total;
	String placa;
	String norden;
	TableLayout.LayoutParams layoutFila;
	Admin_BD bd;
	int tal = 0;
	
	public Dialogo(Context context, String cod, String placao, Admin_BD db) {
		super(context);
		setTitle("Detalles "+placao);
		placa = placao;
		norden = cod;
		bd = db;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		tabla = (TableLayout) findViewById(R.id.tabladetalles);
		total = (TextView) findViewById(R.id.total);
		layoutFila = new TableLayout.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		
		fondo.start();
		
	}
	
	private  Handler puente = new Handler() {
		public void handleMessage(Message msg) {
			crearfila((String[]) msg.obj);
			total.setText("$"+tal);
		}
	};
	
	private Thread fondo = new Thread(new Runnable() {
		@Override
		public void run() {
			Cursor c = bd.GetDetalles((long)Long.valueOf(norden));
			Detalles(c);
		}
	});
	
	private void crearfila(String[] datos) {
		TableRow fila = new TableRow(this.getContext());
		fila.setLayoutParams(layoutFila);
		for (int i1 = 0; i1 < datos.length; i1++) {
			Campo aux = new Campo(this.getContext(), datos[i1]);
			fila.addView(aux);
		}
		tabla.addView(fila);
	}
	
	protected void Detalles(Cursor c) {
		while (c.moveToNext()){
			String[] str = new String[7];
            str[0] = c.getString(c.getColumnIndexOrThrow("codser"));
            Cursor o = bd.BuscarServicio("codser",str[0]);
            str[1] = o.getString(o.getColumnIndexOrThrow("nomser"));
            str[2] = c.getInt(c.getColumnIndexOrThrow("cantd"))+"";
            str[3] = o.getInt(o.getColumnIndexOrThrow("valser"))+"";
            str[4] = c.getInt(c.getColumnIndexOrThrow("subtal"))+"";
            str[5] = c.getInt(c.getColumnIndexOrThrow("iva"))+"";
            str[6] = c.getInt(c.getColumnIndexOrThrow("total"))+"";
            tal += c.getInt(c.getColumnIndexOrThrow("total"));
            Message msg = new Message();
			msg.obj = str;
			puente.sendMessage(msg);           
         }
	}

	

}
