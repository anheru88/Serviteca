package com.example.servitek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Admin_BD {

	private static final String Tabla_Cliente = "Clientes";
	private static final String Tabla_Movil = "Vehiculos";
	private static final String Tabla_Tecnicos = "Tecnicos";
	private static final String Tabla_Servicios = "Servicios";
	private static final String Tabla_Productos = "Productos";
	private static final String Tabla_Marcas = "Mov_Marcas";
	private static final String Tabla_Colores = "Mov_Colores";
	private static final String Tabla_Clases = "Mov_Clases";
	private static final String Tabla_Imagen = "Mov_Imagenes";

	public static final String sql0 = "CREATE TABLE " + Tabla_Cliente + " ( "
			+ "Codter varchar primary key not null , "
			+ "Nomter	Varchar not null, " + "Dirter	Varchar null, "
			+ "Telter	Varchar null, " + "coddane varchar null, "
			+ "email	varchar null )";

	public static final String sql1 = "CREATE TABLE " + Tabla_Movil + " ( "
			+ "placa  Varchar primary key not null , "
			+ "Codter	Varchar not null, " + "Codmarca Char not null, "
			+ "Codcolor Char not null, " + "Modelo	Integer  null, "
			+ "Codclase Char not null,"
			+ "fecha_ingreso TIMESTAMP NOT NULL DEFAULT current_timestamp )";

	public static final String sql2 = "CREATE TABLE " + Tabla_Tecnicos + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codtec varchar  not null , " + "nomtec varchar not null, "
			+ "dirtec varchar not null, " + "teltec	varchar not null )";

	public static final String sql3 = "CREATE TABLE " + Tabla_Servicios + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codser char  not null , " + "nomser varchar  not null, "
			+ "codcue char not null, " + "valser Integer not null, "
			+ "ivaser	Integer not null, " + "tasacomis	Integer not null, "
			+ "codins	char not null, " + "concesion char null )";

	public static final String sql4 = "CREATE TABLE " + Tabla_Productos + " ( "
			+ "codins	char  primary key not null , "
			+ "Nomins  Varchar  not null, " + "codinterfase char not null,"
			+ "precio_publico Integer not null, " + "ivains	Integer not null )";

	public static final String sql5 = "CREATE TABLE " + Tabla_Marcas + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codmarca  Varchar not null, " + "nombre	Varchar not null)";

	public static final String sql6 = "CREATE TABLE " + Tabla_Colores + " ( "
			+ "codcolor  char primary key not null , "
			+ "Nomcolor	Varchar not null)";

	public static final String sql7 = "CREATE TABLE " + Tabla_Clases + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codclase  char  not null , " + "Nomclase	Varchar not null)";

	public static final String sql8 = "CREATE TABLE " + Tabla_Imagen + " ( "
			+ "placa  Varchar primary key not null , "
			+ "bitmap BLOB not null)";

	private Bdhelper helper;
	private SQLiteDatabase bd;
	public static final String tag = "mytag";

	public Admin_BD(Context c) {
		helper = new Bdhelper(c);
	}

	private ContentValues ContenedorCliente(String cc, String nombre,
			String dir, String tel, String coddane, String mail) {
		ContentValues valores = new ContentValues();
		valores.put("Codter", cc);
		valores.put("Nomter", nombre);
		valores.put("Dirter", dir);
		valores.put("Telter", tel);
		valores.put("coddane", coddane);
		valores.put("email", mail);
		return valores;

	}

	private ContentValues ContenedorMovil(String placa, String cc, int marca,
			String color, String modelo, int tipo) {
		ContentValues valores = new ContentValues();
		valores.put("placa", placa);
		valores.put("Codter", cc);
		valores.put("Codmarca", CodigoMarcaId(marca));
		valores.put("Codcolor", color);
		valores.put("Modelo", modelo);
		valores.put("Codclase", CodigoTipoId(tipo));
		return valores;
	}

	private long InsertarCliente(String cc, String nombre, String dir,
			String tel, String coddane, String mail) {
		ContentValues datos = ContenedorCliente(cc, nombre, dir, tel, coddane,
				mail);
		long v = bd.insert(Tabla_Cliente, null, datos);
		return v;
	}

	private long InsertarMovil(String placa, String cc, int marca,
			String color, String modelo, int tipo) {
		ContentValues datos = ContenedorMovil(placa, cc, marca, color, modelo,
				tipo);
		long v = bd.insert(Tabla_Movil, null, datos);
		return v;
	}

	private long FotoCarro(String placa, byte[] image) {
		return bd.insert(Tabla_Imagen, null, createContentValues(placa, image));
	}

	private ContentValues createContentValues(String placa, byte[] image) {
		ContentValues cv = new ContentValues();
		cv.put("placa", placa);
		cv.put("bitmap", image);
		return cv;
	}

	public boolean RegistrarVehiculo(String cc, String nombre, String dir,
			String tel, String coddane, String mail, String placa, int marca,
			String color, String modelo, int tipo, byte[] image, boolean b) {

		Escribir();

		if (b) {
			long v1 = InsertarMovil(placa, cc, marca, color, modelo, tipo);
			long v3 = FotoCarro(placa, image);
			long v2 = InsertarCliente(cc, nombre, dir, tel, coddane, mail);
			if (v1 == -1 && v2 == -1 && v3 == -1) {
				Cerrar();
				return false;
			} else {
				Cerrar();
				return true;
			}

		} else {

			return false;
		}
	}

	public byte[] BuscarImagen(String placa) throws SQLException {
		Cursor c = bd.query(Tabla_Imagen, new String[] { "rowid", "placa",
				"bitmap" }, "placa =?", new String[] { placa }, null, null,
				null);
		c.moveToFirst();
		return c.getBlob(2);
	}

	private String CodigoTipoId(int id) throws SQLException {
		String[] columna = { "_id", "codclase", "Nomclase" };
		Cursor c = bd.query(Tabla_Clases, columna, "_id = " + id, null, null,
				null, null);
		c.moveToFirst();
		return c.getString(1);
	}

	public long CodigoTipo(String codigo) throws SQLException {
		Cursor c = bd.rawQuery("SELECT * FROM Mov_Clases WHERE codclase ="
				+ codigo, null);
		if (c.moveToFirst() && c != null) {
			return c.getLong(0);
		}
		return 0;
	}

	private String CodigoMarcaId(int id) {
		String[] columna = { "_id", "codmarca", "nombre" };
		Cursor c = bd.query(Tabla_Marcas, columna, "_id = " + id, null, null,
				null, null);
		c.moveToFirst();
		return c.getString(1);
	}

	public long CodigoMarca(String codigo) throws SQLException {
		Cursor c = bd.rawQuery("SELECT * FROM Mov_Marcas WHERE codmarca = "
				+ codigo, null);
		if (c.moveToFirst() && c != null) {
			return c.getLong(0);
		}
		return 0;
	}

	public Cursor BuscarPlaca(String placa) {
		Leer();
		String[] columnas = { "rowid", "placa", "Codter", "Codmarca",
				"Codcolor", "Modelo", "Codclase" };
		Cursor c = bd.query(Tabla_Movil, columnas, "placa =?",
				new String[] { placa }, null, null, null);
		c.moveToFirst();
		return c;
	}

	public Cursor BuscarCliente(String cc) {
		String[] columnas = { "rowid", "Codter", "Nomter", "Dirter", "Telter",
				"coddane", "email" };
		Cursor c = bd.query(Tabla_Cliente, columnas, "Codter =?",
				new String[] { cc }, null, null, null);
		c.moveToFirst();
		return c;
	}

	public Cursor SpinnerTipo() {
		Leer();
		Cursor c = bd.rawQuery(
				"SELECT  rowid AS _id, Nomclase  FROM Mov_Clases", null);
		c.moveToFirst();
		return c;
	}

	public Cursor SpinnerMarca() {
		Cursor c = bd.rawQuery("SELECT  rowid AS _id, nombre  FROM Mov_Marcas",
				null);
		c.moveToFirst();
		Cerrar();
		return c;
	}

	public Cursor SpinnerTecnico() {
		return null;

	}

	public Cursor SpinnerServicios() {
		return null;

	}

	private void Escribir() {
		bd = helper.getWritableDatabase();
	}

	public void Cerrar() {
		bd.close();
	}

	private void Leer() {
		bd = helper.getReadableDatabase();
	}

	public long EditarCliente(String cc, String nombre, String dir, String tel,
			String dane, String email, String placa) {
		Escribir();
		Cursor c = BuscarCliente(cc);
		if (c.moveToFirst()) {
			return bd.update(Tabla_Cliente,
					ContenedorCliente(cc, nombre, dir, tel, dane, email),
					"Codter =? ", new String[] { cc });
		} else{
			Log.i(tag, placa);
		ContentValues valor = new ContentValues();
		valor.put("Codter", cc);
		bd.update(Tabla_Movil, valor, "placa =?", new String[] { placa });
		return InsertarCliente(cc, nombre, dir, tel, dane, email);
		}
	}

}
