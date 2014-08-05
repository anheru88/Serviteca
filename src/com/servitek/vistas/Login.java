package com.servitek.vistas;


import com.example.servitek.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Login extends ActionBarActivity {

	Button boton;
	EditText user,password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		user = (EditText) findViewById(R.id.user);
		password = (EditText) findViewById(R.id.pass);
		boton = (Button) findViewById(R.id.bvehi);
		boton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//if (user.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
					Intent intent = new Intent("com.example.servitek.ACCION");
					startActivity(intent);
				//} else {
					//Toast toast = Toast.makeText(Login.this, "Usuario o Password invalidos", Toast.LENGTH_SHORT);
			       // toast.show(); 
				//}
			}
		});
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
}
