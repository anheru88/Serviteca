package com.clases.controladores;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

public class DBimagen {

	public static byte[] GetBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}

	public static Bitmap GetImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}
}
