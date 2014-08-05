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
		this.setText(texto);
		this.setGravity(Gravity.CENTER_HORIZONTAL);
		this.setTextAppearance(c,R.style.etiqueta);
		this.setBackgroundResource(R.drawable.tabla_celda);
		this.setLayoutParams(part);
	}
}
