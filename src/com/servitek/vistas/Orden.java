package com.servitek.vistas;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.BuscarItem;
import com.clases.controladores.Campo;
import com.clases.controladores.DBimagen;
import com.clases.controladores.Fecha;
import com.clases.controladores.MensajeToast;
import com.example.servitek.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Orden extends ActionBarActivity implements OnClickListener {

	EditText cedula, nombre, cantidad;
	AutoCompleteTextView placa;
	TextView precio, numorden;
	Spinner servicio, tecnico;
	Button guardar, menu;
	ImageButton agregar, borrar,imagen;
	TableLayout tabla;
	TableRow.LayoutParams layoutFila;
	Admin_BD bd;
	BuscarItem buscar;
	private ProgressDialog pd;
	Cursor s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orden);
		bd = new Admin_BD(this);
		bd.Escribir();
		BusquedaAuto();
		init();
	}

	private void BusquedaAuto() {
		placa = (AutoCompleteTextView) findViewById(R.id.Autocom);
		placa.setThreshold(1);
		Cursor cursor = bd.AutoComplete("");
		buscar = new BuscarItem(getApplicationContext(), cursor);
		placa.setAdapter(buscar);
		placa.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
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
					BuscarRegistro(aux);
				}
			}
		});
	}
	
	private void init() {
		cedula = (EditText) findViewById(R.id.cedula);
		nombre = (EditText) findViewById(R.id.nombre);
		menu = (Button) findViewById(R.id.menu);
		imagen = (ImageButton) findViewById(R.id.foto);
		guardar = (Button) findViewById(R.id.guardar);
		servicio = (Spinner) findViewById(R.id.servi);
		cantidad = (EditText) findViewById(R.id.Cantidad);
		agregar = (ImageButton) findViewById(R.id.agregar);
		borrar = (ImageButton) findViewById(R.id.limpiar);
		precio = (TextView) findViewById(R.id.Precio);
		numorden = (TextView) findViewById(R.id.numorden);
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
				if (position > 0) {
					cantidad.setText(1 + "");
					s = bd.BuscarServicio("_id",(position+1)+"");
					precio.setText(s.getInt(4)+"");
				}else{
					s = null;
				}
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}
	
	protected void BuscarRegistro(String aux) {
		Cursor c = bd.BuscarPlaca(aux);
		if (c.moveToFirst()) {
			LlenarCampos(c);
			placa.setFocusable(false);
			OcultaTeclado(placa);
			ComponentesActivar(true);
			jj.execute(placa.getText().toString());
		} else {
			MensajeToast.MensajeCorto(Orden.this,
					"Esta placa no ha sido registrada");
		}
		
	}

	protected void ComponentesActivar(boolean b) {
		agregar.setEnabled(b);
		tecnico.setEnabled(b);
		servicio.setEnabled(b);
		cantidad.setEnabled(b);

	}

	protected void LlenarCampos(Cursor c) {
		byte[] i = bd.BuscarImagen(c.getString(1));
		cedula.setText(c.getString(2));
		Cursor b = bd.BuscarCliente(c.getString(2));
		nombre.setText(b.getString(2));
		imagen.setImageBitmap(DBimagen.GetImage(i)); 
	}

	private void CargarSpinner() {
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
			datos[0] = s.getString(1);
			datos[1] = s.getString(2);
			datos[2] = cantidad.getText().toString();
			datos[3] = s.getInt(4) + "";
			datos[4] = s.getInt(4)
					* Integer.parseInt(cantidad.getText().toString()) + "";
			datos[5] = s.getInt(5)
					* Integer.parseInt(cantidad.getText().toString()) + "";
			datos[6] = (s.getInt(4) + s.getInt(5)) + "";
			crearfila(datos);
			if (numorden.getText().toString().equals("")) {
				int id = bd.OrdeneCompra(placa.getText().toString(), 0, datos);
				numorden.setText(id+"");
			} else { 
				bd.OrdeneCompra(placa.getText().toString(), Integer.parseInt(numorden.getText().toString()), datos); 
			}
			
			cantidad.setText("");
			servicio.setSelection(0);
			s = null;
		} else {
			MensajeToast.MensajeCorto(this, "Llene todos los campos");
		}
	}

	private void crearfila(String[] datos) {
		TableRow fila = new TableRow(Orden.this);
		fila.setLayoutParams(layoutFila);
		for (int i1 = 0; i1 < datos.length; i1++) {
			Campo aux = new Campo(this, datos[i1]);
			fila.addView(aux);
		}
		tabla.addView(fila);
	}

	private void OcultaTeclado(View v) {
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		bd.Cerrar();
		
	}
	
	
	private  AsyncTask<String, Void, Long> jj = new AsyncTask<String, Void, Long>(){
		
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Orden.this, "Buscando",
					"Espere unos segundos...", true, false);
		}

		@Override
		protected Long doInBackground(String... params) {
			Long i = Long.valueOf(bd.BuscarOrden(placa.getText().toString(), Fecha.facha()));
			return i;	
		}

		@Override
		protected void onPostExecute(Long result) {
			pd.dismiss();
			if (result > 0 ) {
				numorden.setText(""+result);
				Detalles(bd.GetDetalles((long)result));
			}
		}

		@Override
		protected void onCancelled() {
			MensajeToast.MensajeCorto(Orden.this, "Tarea cancelada");
		}
		
	};

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
            crearfila(str);            
         }
	}
}
