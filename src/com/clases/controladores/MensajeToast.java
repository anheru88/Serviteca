package com.clases.controladores;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class MensajeToast {
	public static void MensajeCorto(Context context, String mensaje) {
		Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
		TextView v = (TextView) toast.getView().findViewById(
				android.R.id.message);
		if (v != null)
			v.setGravity(Gravity.CENTER);
		v.setTextColor(Color.rgb(225, 216, 79));
		toast.show();
	}
	
	public static void MensajeLargo(Context context, String mensaje) {
		Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
		TextView v = (TextView) toast.getView().findViewById(
				android.R.id.message);
		if (v != null)
			v.setGravity(Gravity.CENTER);
		v.setTextColor(Color.rgb(225, 216, 79));
		toast.show();
	}
}
