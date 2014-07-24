package com.example.servitek;



import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
		agregar = (Button) findViewById(R.id.agregar);
		rs = this.getResources();
		tabla = (TableLayout) findViewById(R.id.tabla);
		cabecera = (TableLayout) findViewById(R.id.cabecera);
		layoutFila = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		layoutId = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		layoutTexto = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		layoutOtro = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

		agregarCabecera();

		agregar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				agregarFilasTabla(uno + 1);
				uno++;
			}
		});

	}

	public void agregarCabecera() {
		TableRow fila;
		TextView txtId;
		TextView txtNombre;
		TextView txtOtro;

		fila = new TableRow(this);
		fila.setLayoutParams(layoutFila);

		txtId = new TextView(this);
		txtNombre = new TextView(this);
		txtOtro = new TextView(this);

		txtId.setText("id");
		txtId.setGravity(Gravity.CENTER_HORIZONTAL);
		txtId.setTextAppearance(this, R.style.etiqueta);
		txtId.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		txtId.setLayoutParams(layoutId);

		txtNombre.setText("valor");
		txtNombre.setGravity(Gravity.CENTER_HORIZONTAL);
		txtNombre.setTextAppearance(this, R.style.etiqueta);
		txtNombre.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		txtNombre.setLayoutParams(layoutTexto);

		txtOtro.setText("otro");
		txtOtro.setGravity(Gravity.CENTER_HORIZONTAL);
		txtOtro.setTextAppearance(this, R.style.etiqueta);
		txtOtro.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		txtOtro.setLayoutParams(layoutOtro);

		fila.addView(txtId);
		fila.addView(txtNombre);
		fila.addView(txtOtro);
		cabecera.addView(fila);
	}

	public void agregarFilasTabla(int i) {

		TableRow fila;
		fila = new TableRow(this);
		fila.setLayoutParams(layoutFila);
		fila.setGravity(Gravity.CENTER_HORIZONTAL);
		for (int i1 = 0; i1 < 7; i1++) {
			fila.addView(new Campo(this, "hola"));
		}

		tabla.addView(fila);

	}
}