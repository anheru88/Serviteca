package com.bd.modelos;


import com.clases.controladores.Admin_BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Bdhelper extends SQLiteOpenHelper {

	private static final String BD_NAME = "servitek.sqlite";
	private static final int BD_VERSION = 1;
	Context c;
	public Bdhelper(Context context) {
		super(context, BD_NAME, null, BD_VERSION);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(Admin_BD.sql0);
			db.execSQL(Admin_BD.sql1);
			db.execSQL(Admin_BD.sql2);
			db.execSQL(Admin_BD.sql3);
			db.execSQL(Admin_BD.sql4);
			db.execSQL(Admin_BD.sql5);
			db.execSQL(Admin_BD.sql6);
			db.execSQL(Admin_BD.sql7);
			db.execSQL(Admin_BD.sql8);
			
			CargarTipos(db);
			CargarMarcas(db);
			CargarTecicos(db);
			CargarServicios(db);
		} catch (Exception e) {
			Toast.makeText(c,"error "+ e, Toast.LENGTH_SHORT).show();
		}
		
	}

	/*
	 * ALINEACION LAVADO LUBRICACION BALANCEO
	 
 codcue char(8)
 nomcue varchar(50)
 auxcue  bit
 natcue char  QUE PUEDE SER 'D' O 'C' 
	 
*/
	private void CargarServicios(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Servicios (codser,nomser,codcue,valser,ivaser,tasacomis,codins,concesion) VALUES ('0','Servicios','A','0','0','0','B','c') ");
		db.execSQL("INSERT INTO Servicios (codser,nomser,codcue,valser,ivaser,tasacomis,codins,concesion) VALUES ('159','ALineacion','A','2000','320','50','B','c') ");
		db.execSQL("INSERT INTO Servicios (codser,nomser,codcue,valser,ivaser,tasacomis,codins,concesion) VALUES ('486','Lavado','A','3000','480','150','B','c') ");
		db.execSQL("INSERT INTO Servicios (codser,nomser,codcue,valser,ivaser,tasacomis,codins,concesion) VALUES ('795','Lubricacion','A','4000','720','200','B','c') ");
		db.execSQL("INSERT INTO Servicios (codser,nomser,codcue,valser,ivaser,tasacomis,codins,concesion) VALUES ('268','Balanceo','A','5000','880','250','B','c') ");
	}

	private void CargarTecicos(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Tecnicos (codtec,nomtec,dirtec,teltec) VALUES ('0','Tecnico','tecnico','tecnico')");
		db.execSQL("INSERT INTO Tecnicos (codtec,nomtec,dirtec,teltec) VALUES ('132565','juan perez','calamares','654885')");
		db.execSQL("INSERT INTO Tecnicos (codtec,nomtec,dirtec,teltec) VALUES ('535343','pedro pablo','campestre','654885')");
		db.execSQL("INSERT INTO Tecnicos (codtec,nomtec,dirtec,teltec) VALUES ('588565','jose mendez','socorro','654885')");

	}

	private void CargarMarcas(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Mov_Marcas (codmarca,nombre) VALUES ('0', 'Marca')");
		db.execSQL("INSERT INTO Mov_Marcas (codmarca,nombre) VALUES ('54565', 'Masda')");
		db.execSQL("INSERT INTO Mov_Marcas (codmarca,nombre) VALUES ('45654', 'Jeep')");
		db.execSQL("INSERT INTO Mov_Marcas (codmarca,nombre) VALUES ('56555', 'Zusuki')");
		db.execSQL("INSERT INTO Mov_Marcas (codmarca,nombre) VALUES ('13562', 'Gmc')");
		db.execSQL("INSERT INTO Mov_Marcas (codmarca,nombre) VALUES ('86455', 'Hyunday')");
		db.execSQL("INSERT INTO Mov_Marcas (codmarca,nombre) VALUES ('64568', 'Vmw')");

	}

	private void CargarTipos(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Mov_Clases (codclase,Nomclase) VALUES ('0', 'Tipo')");
		db.execSQL("INSERT INTO Mov_Clases (codclase,Nomclase) VALUES ('456', 'Camion')");
		db.execSQL("INSERT INTO Mov_Clases (codclase,Nomclase) VALUES ('789', 'Ahutomovil')");
		db.execSQL("INSERT INTO Mov_Clases (codclase,Nomclase) VALUES ('123', 'Camioneta')");
		db.execSQL("INSERT INTO Mov_Clases (codclase,Nomclase) VALUES ('654', 'Tractor')");
		db.execSQL("INSERT INTO Mov_Clases (codclase,Nomclase) VALUES ('987', 'Buseta')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS Clientes");
		db.execSQL("DROP TABLE IF EXISTS Vehiculos");
		db.execSQL("DROP TABLE IF EXISTS Tecnicos");
		db.execSQL("DROP TABLE IF EXISTS Servicios");
		db.execSQL("DROP TABLE IF EXISTS Productos");
		db.execSQL("DROP TABLE IF EXISTS Mov_Marcas");
		db.execSQL("DROP TABLE IF EXISTS Mov_Colores");
		db.execSQL("DROP TABLE IF EXISTS Mov_Clases");
		db.execSQL("DROP TABLE IF EXISTS Mov_Imagenes");
		
		onCreate(db);

	}

}
