package com.example.servitek;

import java.util.ArrayList;



import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

	String[] tecnicos = { "Tecnico", "Pedro Pablo", "Juan Cardona",
			"Felipe Gonzalez", "Mario Puera", "Cesar Herrera" };
	String[] servicios = { "Servicios", "Servi 1", "Servi 2", "Servi 3",
			"Servi 4", "Servi 5", "Servi 6" };
	EditText placa, cedula, nombre, cantidad;
	TextView precio;
	Spinner servicio, tecnico;
	Button guardar, menu;
	ImageButton agregar, buscar;
	TableLayout tabla;
	TableRow.LayoutParams layoutFila;
	TableRow.LayoutParams layoutId;
	int[] pre = { 0, 1500, 2000, 2500, 2200, 1600, 3000 };
	String[] cod = { "0", "5485", "9856", "4257", "3254", "6581", "1248" };
	ArrayList<Campo> campos;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orden);
		init();
	}

	private void init() {
		campos = new ArrayList<Campo>();
		placa = (EditText) findViewById(R.id.placa);
		menu = (Button) findViewById(R.id.menu);
		guardar = (Button) findViewById(R.id.guardar);
		servicio = (Spinner) findViewById(R.id.servi);
		cantidad = (EditText) findViewById(R.id.Cantidad);
		agregar = (ImageButton) findViewById(R.id.agregar);
		buscar = (ImageButton) findViewById(R.id.buscar);
		precio = (TextView) findViewById(R.id.Precio);
		tecnico = (Spinner) findViewById(R.id.tecnicos);

		tabla = (TableLayout) findViewById(R.id.tabladetalles);
		layoutFila = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);

		tecnico.setAdapter(new ArrayAdapter<String>(this, R.layout.spinnertext,
				tecnicos));

		servicio.setAdapter(new ArrayAdapter<String>(this,
				R.layout.spinnertext, servicios));

		menu.setOnClickListener(this);
		guardar.setOnClickListener(this);
		agregar.setOnClickListener(this);
		buscar.setOnClickListener(this);

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
			public void onTextChanged(CharSequence s, int start, int before, int count) {
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
					placa.setText(aux+"-");
					placa.setInputType(InputType.TYPE_CLASS_NUMBER );
					placa.setSelection(4);
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.agregar:
			OcultaTeclado(v);
			String[] datos = new String[7];
			if (!cantidad.getText().toString().equals("")
					&& servicio.getSelectedItemPosition() != 0) {
				datos[0] = cod[servicio.getSelectedItemPosition()];
				datos[1] = servicio.getSelectedItem().toString();
				datos[2] = cantidad.getText().toString();
				datos[3] = pre[servicio.getSelectedItemPosition()] + "";
				datos[4] = Integer.parseInt(cantidad.getText().toString())
						* pre[servicio.getSelectedItemPosition()] + "";
				datos[5] = Integer.parseInt(datos[4]) * 0.16 + "";
				datos[6] = (Integer.parseInt(datos[4]) + Integer
						.parseInt(datos[4]) * 0.16) + "";
				crearfila(datos);
				cantidad.setText("");
				servicio.setSelection(0);
			} else {
				Mensaje("Llene todos los campos");
			}

			break;
		case R.id.menu:
			Intent acc = new Intent("com.example.servitek.ACCION");
			startActivity(acc);
			break;
		case R.id.buscar:
			OcultaTeclado(v);
			break;
		case R.id.guardar:
			OcultaTeclado(v);
			break;
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
		Mensaje("Orden agragada");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	private void OcultaTeclado(View v){
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context .INPUT_METHOD_SERVICE );
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);
	
	}
	
	private void Mensaje(String mensaje) {
		Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
		TextView v = (TextView) toast.getView().findViewById(
				android.R.id.message);
		if (v != null)
			v.setGravity(Gravity.CENTER);
		v.setTextColor(Color.rgb(225, 216, 79));
		toast.show();
	}

}
