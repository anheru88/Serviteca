package com.example.servitek;



import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class Compra extends Activity {

	TableLayout tabla;
	TableLayout cabecera;
	TableRow.LayoutParams layoutFila;
	TableRow.LayoutParams layoutId;
	TableRow.LayoutParams layoutTexto;
	TableRow.LayoutParams layoutOtro;
	Button agregar;
	int uno = 0;

	Resources rs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compra);
		

	}
}