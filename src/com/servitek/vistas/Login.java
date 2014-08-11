package com.servitek.vistas;


import com.clases.controladores.Admin_BD;
import com.example.servitek.R;

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


public class Login extends ActionBarActivity implements OnClickListener{

	Button boton;
	EditText user,password;
	Admin_BD db;
	CheckBox me;
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
			//if (user.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
			Intent intent = new Intent("com.example.servitek.ACCION");
			startActivity(intent);
			db.Cerrar();
		//} else {
			//Toast toast = Toast.makeText(Login.this, "Usuario o Password invalidos", Toast.LENGTH_SHORT);
	       // toast.show(); 
		//}
		}
		if (v.getId() == R.id.check) {
			if(me.isChecked()){
				String nombre=user.getText().toString();
				String pass=password.getText().toString();
				SharedPreferences settings = getSharedPreferences("Perfil", MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("user",nombre);
				editor.putString("pass",pass);
				editor.commit();
			}else{
				SharedPreferences settings = getSharedPreferences("Perfil", MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("user","");
				editor.putString("pass","");
				editor.commit();
			}
                 
		}
		
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	
	
}
