package com.servitek.vistas;

import java.util.ArrayList;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Campo;
import com.example.servitek.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Orden extends ActionBarActivity implements OnClickListener {

	EditText placa, cedula, nombre, cantidad;
	TextView precio;
	Spinner servicio, tecnico;
	Button guardar, menu;
	ImageButton agregar, borrar;
	TableLayout tabla;
	TableRow.LayoutParams layoutFila;
	TableRow.LayoutParams layoutId;
	int[] pre = { 0, 1500, 2000, 2500, 2200, 1600, 3000 };
	String[] cod = { "0", "5485", "9856", "4257", "3254", "6581", "1248" };
	ArrayList<Campo> campos;
	Admin_BD bd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orden);
		bd = new Admin_BD(this);
		init();
	}

	private void init() {
		campos = new ArrayList<Campo>();
		placa = (EditText) findViewById(R.id.placa);
		cedula = (EditText) findViewById(R.id.cedula);
		nombre = (EditText) findViewById(R.id.nombre);
		menu = (Button) findViewById(R.id.menu);
		guardar = (Button) findViewById(R.id.guardar);
		servicio = (Spinner) findViewById(R.id.servi);
		cantidad = (EditText) findViewById(R.id.Cantidad);
		agregar = (ImageButton) findViewById(R.id.agregar);
		borrar = (ImageButton) findViewById(R.id.limpiar);
		precio = (TextView) findViewById(R.id.Precio);
		tecnico = (Spinner) findViewById(R.id.tecnicos);

		tabla = (TableLayout) findViewById(R.id.tabladetalles);
		layoutFila = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);

		menu.setOnClickListener(this);
		guardar.setOnClickListener(this);
		agregar.setOnClickListener(this);
		borrar.setOnClickListener(this);
		CargarSpinner();

		servicio.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				precio.setText(String.valueOf(pre[position]));
				System.out.print(position);
				if (position > 0) {
					cantidad.setText(1 + "");
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		placa.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				String aux = s.toString();
				if (aux.length() == 3) {
					placa.setText(aux + " - ");
					placa.setInputType(InputType.TYPE_CLASS_NUMBER);

				} else if (aux.length() > 3 && aux.length() < 6) {
					placa.setInputType(InputType.TYPE_CLASS_TEXT);
					placa.setSelection(1);
					placa.setText("");

				}

				if (!aux.equals(s.toString().toUpperCase())) {
					aux = s.toString().toUpperCase();
					placa.setText(aux);
				}
				placa.setSelection(placa.getText().length());

				if (aux.length() == 9) {
					Cursor c = bd.BuscarPlaca(aux);
					if (c.moveToFirst()) {
						LlenarCampos(c);
						placa.setFocusable(false);
						OcultaTeclado(placa);
						ComponentesActivar(true);
					} else {
						Mensaje("Esta PLaca no ha sido registrada");
					}
				}
			}
		});

	}

	protected void ComponentesActivar(boolean b) {
		agregar.setEnabled(b);
		tecnico.setEnabled(b);
		servicio.setEnabled(b);
		cantidad.setEnabled(b);

	}

	protected void LlenarCampos(Cursor c) {
		cedula.setText(c.getString(2));
		Cursor b = bd.BuscarCliente(c.getString(2));
		nombre.setText(b.getString(2));
	}

	private void CargarSpinner() {
		bd.Leer();
		Cursor tipos = bd.Cursor("_id", "nomtec", "Tecnicos");
		SimpleCursorAdapter adactador1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, tipos,
				new String[] { "nomtec" }, new int[] { android.R.id.text1 }, 0);
		tecnico.setAdapter(adactador1);
		tecnico.setEnabled(false);

		Cursor marcas = bd.Cursor("rowid", "nomser", "Servicios");
		SimpleCursorAdapter adactador2 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, marcas,
				new String[] { "nomser" }, new int[] { android.R.id.text1 }, 0);
		servicio.setAdapter(adactador2);
		servicio.setEnabled(false);
		agregar.setEnabled(false);
		cantidad.setEnabled(false);
		bd.Cerrar();
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.agregar:
			OcultaTeclado(v);
			AgregarOrden();
			break;
		case R.id.menu:
			Intent acc = new Intent("com.example.servitek.ACCION");
			startActivity(acc);
			break;
		case R.id.limpiar:
			OcultaTeclado(v);
			break;
		case R.id.guardar:
			OcultaTeclado(v);
			break;
		}

	}

	private void AgregarOrden() {
		String[] datos = new String[7];
		if (!cantidad.getText().toString().equals("")
				&& servicio.getSelectedItemPosition() != 0) {

			Cursor s = bd.BuscarServicio(servicio.getSelectedItemId());
			datos[0] = s.getString(1);
			datos[1] = s.getString(2);
			datos[2] = cantidad.getText().toString();
			datos[3] = s.getInt(4) + "";
			datos[4] = s.getInt(4) * Integer.parseInt(cantidad.getText().toString()) + "";
			datos[5] = s.getInt(5) * Integer.parseInt(cantidad.getText().toString()) + "";
			datos[6] = (s.getInt(4) + s.getInt(5)) + "";
			crearfila(datos);
			cantidad.setText("");
			servicio.setSelection(0);
			bd.Cerrar();
		} else {
			Mensaje("Llene todos los campos");
		}
	}

	private void crearfila(String[] datos) {
		TableRow fila = new TableRow(Orden.this);
		fila.setLayoutParams(layoutFila);
		for (int i1 = 0; i1 < 7; i1++) {
			Campo aux = new Campo(this, datos[i1]);
			campos.add(aux);
			fila.addView(aux);
		}
		tabla.addView(fila);
		Mensaje("Requerimineto Agregado");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void OcultaTeclado(View v) {
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);

	}

	private void Mensaje(String mensaje) {
		Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
		TextView v = (TextView) toast.getView().findViewById(
				android.R.id.message);
		if (v != null)
			v.setTextSize(20);
		v.setGravity(Gravity.CENTER);
		v.setTextColor(Color.rgb(225, 216, 79));
		toast.show();
	}

}
