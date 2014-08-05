package com.clases.controladores;

import java.sql.ResultSet;

import com.bd.modelos.BDsqlServer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class HiloAsyn extends AsyncTask<String, Integer, ResultSet> {
	public HiloListener Listener;
	private BDsqlServer sql;
	Context context;
	boolean sw,in;
	private ProgressDialog pd = null;

	public HiloAsyn(Context contex, boolean s, HiloListener listener) {
		sw = s;
		context = contex;
		Listener = listener;
		in = false;
	}

	@Override
	protected void onPreExecute() {
		if (!sw) {
			pd = ProgressDialog.show(context, "Enviando",
					"Espere unos segundos...", true, false);
		}

	}

	@Override
	protected ResultSet doInBackground(String... query) {
		sql = new BDsqlServer();
		if (sql.com != null) {
			if (sw) {
				for (int i = 0; i < query.length; i++) {
					sql.Insertar(query[0]);
					in = true;
				}
				return null;
			} else {
				ResultSet r = sql.Consultar(query[0]);
				publishProgress(99);
				return r;
			}
		} 
			return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (pd != null) {
			int pro = values[0].intValue();
			pd.setProgress(pro);
		}
	}

	@Override
	protected void onPostExecute(ResultSet result) {
		if (pd != null) {
			pd.dismiss();
			Listener.Resultado(result);
		}else if (in) {
			Listener.Confir(in);
		}else{
			Listener.Confir(in);
		}
	}

	@Override
	protected void onCancelled() {
		Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
	}
}
