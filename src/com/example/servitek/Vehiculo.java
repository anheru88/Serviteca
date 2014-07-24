package com.example.servitek;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Vehiculo extends ActionBarActivity implements OnClickListener {
	EditText placa, cedula, nombre, direccion, celular, modelo, mail;
	Spinner tipo, marca;
	Button color, guardar, menu, editar;
	ImageButton borrar, imagen;
	Intent camara;
	TextView carcolor;
	final static int cons = 0;
	int cc = 0;
	Admin_BD bd;
	boolean sw = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehiculo);
		bd = new Admin_BD(this);
		init();
	}

	private void init() {
		placa = (EditText) findViewById(R.id.placa);
		cedula = (EditText) findViewById(R.id.cedula);
		nombre = (EditText) findViewById(R.id.nombre);
		direccion = (EditText) findViewById(R.id.dir);
		celular = (EditText) findViewById(R.id.tel);
		modelo = (EditText) findViewById(R.id.model);
		mail = (EditText) findViewById(R.id.mail);
		tipo = (Spinner) findViewById(R.id.tipos);
		marca = (Spinner) findViewById(R.id.marcas);
		imagen = (ImageButton) findViewById(R.id.foto);
		borrar = (ImageButton) findViewById(R.id.borrar);
		menu = (Button) findViewById(R.id.menu);
		editar = (Button) findViewById(R.id.editar);
		color = (Button) findViewById(R.id.btcolor);
		carcolor = (TextView) findViewById(R.id.carcolor);
		guardar = (Button) findViewById(R.id.guardar);

		imagen.setOnClickListener(this);
		menu.setOnClickListener(this);
		editar.setOnClickListener(this);
		color.setOnClickListener(this);
		borrar.setOnClickListener(this);
		guardar.setOnClickListener(this);
		editar.setEnabled(false);
		Spinnerbd();

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
					Cursor c = bd.BuscarPlaca(aux);
					if (c.moveToFirst()) {
						LlenarCampos(c);
						editar.setEnabled(true);
						DesAct(false, false);
						OculTeclado(placa);
					} else {
						Mensaje("No hay registro");
					}

				}
			}
		});

	}

	protected void OculTeclado(View v) {
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	private void Spinnerbd() {
		Cursor tipos = bd.SpinnerTipo();
		SimpleCursorAdapter adactador1 = new SimpleCursorAdapter(Vehiculo.this,
				android.R.layout.simple_spinner_dropdown_item, tipos,
				new String[] { "Nomclase" }, new int[] { android.R.id.text1 },
				0);
		tipo.setAdapter(adactador1);

		Cursor marcas = bd.SpinnerMarca();
		SimpleCursorAdapter adactador2 = new SimpleCursorAdapter(Vehiculo.this,
				android.R.layout.simple_spinner_dropdown_item, marcas,
				new String[] { "nombre" }, new int[] { android.R.id.text1 }, 0);
		marca.setAdapter(adactador2);
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.foto:
			camara = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(camara, cons);
			break;
		case R.id.menu:
			Intent acc = new Intent("com.example.servitek.ACCION");
			startActivity(acc);
			break;
		case R.id.btcolor:
			color_carro();
			OculTeclado(v);
			break;
		case R.id.borrar:
			VaciarCampos();
			placa.setInputType(InputType.TYPE_CLASS_TEXT);
			DesAct(true, true);
			break;
		case R.id.editar:
			DesAct(false, true);
			break;
		case R.id.guardar:
			if (sw) {
				GuardarRegistro();
			} else {
				Editar();
			}

			break;
		}

	}

	private void Editar() {
		if (!cedula.getText().toString().equals("")
				&& !nombre.getText().toString().equals("")) {
			if(bd.EditarCliente(cedula.getText().toString(), nombre.getText()
					.toString(), direccion.getText().toString(), celular
					.getText().toString(),"hola", mail.getText().toString(), placa.getText().toString()) != -1){
				Mensaje("Edicion Exitosa");
				bd.Cerrar();
				VaciarCampos();
			}
		}

	}

	private void GuardarRegistro() {
		if (!cedula.getText().toString().equals("")
				&& !nombre.getText().toString().equals("")
				&& !placa.getText().toString().equals("")
				&& placa.getText().toString().trim().length() == 9
				&& !modelo.getText().toString().equals("")
				&& marca.getSelectedItemPosition() != 0
				&& tipo.getSelectedItemPosition() != 0 && cc != 0) {

			if (bd.RegistrarVehiculo(cedula.getText().toString(), nombre
					.getText().toString(), direccion.getText().toString(),
					celular.getText().toString(), "hola", mail.getText()
							.toString(), placa.getText().toString(), marca
							.getSelectedItemPosition(), cc + "", modelo
							.getText().toString(), tipo
							.getSelectedItemPosition(),
					getBytes(((BitmapDrawable) imagen.getDrawable())
							.getBitmap()), sw)) {
				Mensaje("Registro Exitoso");
				VaciarCampos();
				placa.setInputType(InputType.TYPE_CLASS_TEXT);
			} else {
				Mensaje("Error al registrar");
			}

		} else {
			Mensaje("Campos requeridos vacios");
		}
	}

	private void Mensaje(String mensaje) {
		Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
		TextView v = (TextView) toast.getView().findViewById(
				android.R.id.message);
		if (v != null)
			v.setGravity(Gravity.CENTER);
		v.setTextColor(Color.rgb(225, 216, 79));
		toast.show();
	}

	private void VaciarCampos() {
		placa.setText("");
		cedula.setText("");
		nombre.setText("");
		direccion.setText("");
		celular.setText("");
		mail.setText("");
		modelo.setText("");
		tipo.setSelection(0);
		marca.setSelection(0);
		carcolor.setBackgroundColor(Color.rgb(255, 255, 255));
		imagen.setImageDrawable(null);
	}

	protected void LlenarCampos(Cursor c) {
		byte[] i = bd.BuscarImagen(c.getString(1));
		Cursor b = bd.BuscarCliente(c.getString(2));
		cedula.setText(c.getString(2));
		String m = c.getString(3);
		carcolor.setBackgroundColor(c.getInt(4));
		modelo.setText(c.getString(5));
		String t = c.getString(6);
		nombre.setText(b.getString(2));
		direccion.setText(b.getString(3));
		celular.setText(b.getString(4));
		mail.setText(b.getString(6));
		tipo.setSelection((int) bd.CodigoTipo(t));
		marca.setSelection((int) bd.CodigoMarca(m));
		bd.Cerrar();
		imagen.setImageBitmap(GetImage(i));
	}

	private void DesAct(boolean b1, boolean b2) {
		placa.setEnabled(b1);
		cedula.setEnabled(b2);
		nombre.setEnabled(b2);
		direccion.setEnabled(b2);
		celular.setEnabled(b2);
		mail.setEnabled(b2);
		modelo.setEnabled(b1);
		tipo.setEnabled(b1);
		marca.setEnabled(b1);
		color.setEnabled(b1);
		imagen.setEnabled(b2);
		guardar.setEnabled(b2);
		sw = b1;
	}

	private void color_carro() {
		Bitmap color = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
		cc = getDominantColor1(color);
		carcolor.setBackgroundColor(cc);
	}

	@Override
	protected void onActivityResult(int RequesCode, int ResultCode, Intent data) {
		super.onActivityResult(RequesCode, ResultCode, data);
		if (ResultCode == Activity.RESULT_OK) {
			Bundle ext = data.getExtras();
			Bitmap bmt = (Bitmap) ext.get("data");
			imagen.setImageBitmap(bmt);
		}
	}

	public int getDominantColor1(Bitmap bitmap) {

		if (bitmap == null)
			throw new NullPointerException();

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int size = width * height;
		int pixels[] = new int[size];

		Bitmap bitmap2 = bitmap.copy(Bitmap.Config.ARGB_4444, false);

		bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);

		final List<HashMap<Integer, Integer>> colorMap = new ArrayList<HashMap<Integer, Integer>>();
		colorMap.add(new HashMap<Integer, Integer>());
		colorMap.add(new HashMap<Integer, Integer>());
		colorMap.add(new HashMap<Integer, Integer>());

		int color = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		Integer rC, gC, bC;
		for (int i = 0; i < pixels.length; i++) {
			color = pixels[i];

			r = Color.red(color);
			g = Color.green(color);
			b = Color.blue(color);

			rC = colorMap.get(0).get(r);
			if (rC == null)
				rC = 0;
			colorMap.get(0).put(r, ++rC);

			gC = colorMap.get(1).get(g);
			if (gC == null)
				gC = 0;
			colorMap.get(1).put(g, ++gC);

			bC = colorMap.get(2).get(b);
			if (bC == null)
				bC = 0;
			colorMap.get(2).put(b, ++bC);
		}

		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			int max = 0;
			int val = 0;
			for (Map.Entry<Integer, Integer> entry : colorMap.get(i).entrySet()) {
				if (entry.getValue() > max) {
					max = entry.getValue();
					val = entry.getKey();
				}
			}
			rgb[i] = val;
		}

		return Color.rgb(rgb[0], rgb[1], rgb[2]);
	}

	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}

	public static Bitmap GetImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

}
