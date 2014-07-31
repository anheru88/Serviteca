package com.bd.sql;

import java.sql.ResultSet;

import android.content.Context;
import android.os.AsyncTask;

public class HiloAsyn extends AsyncTask<String, Void, ResultSet>{
	public HiloResponde  res = null;
	BDsqlServer db;
	Context context;
	public HiloAsyn(Context context) {
		this.context = context;
	}
	

	@Override
	protected ResultSet doInBackground(String... query) {
		db = new BDsqlServer(context);
		return db.Enviar(query[0]);
	}
}
