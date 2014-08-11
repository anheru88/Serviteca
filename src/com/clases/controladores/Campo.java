package com.clases.controladores;




import com.example.servitek.R;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

public class Campo extends TextView {
	TableRow.LayoutParams part;
	public Campo(Context c, String texto) {
		super(c);
		part = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		setText(texto);
		setGravity(Gravity.CENTER_HORIZONTAL);
		setTextAppearance(c,R.style.etiqueta);
		setBackgroundResource(R.drawable.tabla_celda);
		setLayoutParams(part);
	}
	
	public Campo(Context c, int in) {
		super(c);
		part = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		setGravity(Gravity.CENTER);
		setTextAppearance(c,R.style.detalles);
		setBackgroundResource(in);
		setLayoutParams(part);
	}
	
	public Campo(Context c, String texto, int i) {
		super(c);
		setText(texto);
		part = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		setGravity(Gravity.CENTER);
		setTextAppearance(c,R.style.detalles);
		setLayoutParams(part);
	}
}
