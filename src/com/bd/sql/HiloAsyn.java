package com.bd.sql;

import java.sql.ResultSet;

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

	public HiloAsyn(Context context, boolean sw, HiloListener listener) {
		this.sw = sw;
		this.context = context;
		this.Listener = listener;
		this.in = false;
	}

	@Override
	protected void onPreExecute() {
		if (!sw) {
			this.pd = ProgressDialog.show(context, "Enviando",
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
		if (this.pd != null) {
			int pro = values[0].intValue();
			this.pd.setProgress(pro);
		}
	}

	@Override
	protected void onPostExecute(ResultSet result) {
		if (this.pd != null) {
			this.pd.dismiss();
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
