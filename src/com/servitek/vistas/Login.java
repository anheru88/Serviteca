package com.servitek.vistas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;

public class Login extends ActionBarActivity implements OnClickListener {

	private Button boton;
	private EditText user, password;
	private Admin_BD db;
	private CheckBox me;
	private String usuario, pass;
	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	private Boolean saveLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		me = (CheckBox) findViewById(R.id.check);
		me.setOnClickListener(this);
		db = new Admin_BD(this);
		db.Escribir();
		user = (EditText) findViewById(R.id.user);
		password = (EditText) findViewById(R.id.pass);
		boton = (Button) findViewById(R.id.bvehi);
		boton.setOnClickListener(this);

		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();

		saveLogin = loginPreferences.getBoolean("saveLogin", false);
		if (saveLogin == true) {
			user.setText(loginPreferences.getString("username", ""));
			password.setText(loginPreferences.getString("password", ""));
			me.setChecked(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.Cerrar();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bvehi) {
			if (user.getText().toString().equals("admin")
					&& password.getText().toString().equals("admin")) {
				Intent intent = new Intent("com.example.servitek.ACCION");
				startActivity(intent);
				db.Cerrar();
			} else {
				Util.MensajeCorto(Login.this, "Usuario o Password invalidos");
			}

			usuario = user.getText().toString();
			pass = password.getText().toString();

			if (me.isChecked()) {
				loginPrefsEditor.putBoolean("saveLogin", true);
				loginPrefsEditor.putString("username", usuario);
				loginPrefsEditor.putString("password", pass);
				loginPrefsEditor.commit();
			} else {
				loginPrefsEditor.clear();
				loginPrefsEditor.commit();
			}
		}
	}
}
