package basededatos.bd;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class BaseDeDatos extends Activity {

    TextView tv;
    String texto="";
    SQLiteDatabase db=null;
    Cursor cursor=null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basededatos);

        tv=(TextView) findViewById(R.id.textView);

        //abre o crea base de datos
        db=this.openOrCreateDatabase("musica.db",MODE_PRIVATE,null);

        //---crea una tabla si no existe
    //db.execSQL("create table if not exists"+"seoperas (id integer primary key,titulo text,"+"compositor text, year integer);" );
        db.execSQL("create table if not exists operas (id integer primary key,titulo text, compositor text, year integer);" );

        ejecutaSQL();
        muestraTabla();
        db.close();
        tv.append(texto);
    }

    void ejecutaSQL()
    {
        //--inserta datos en la tabla
        db.execSQL("insert into operas(titulo, compositor, year) " + "values('Don Giovani','W.A. Mozart',1787);" );

        for(int i=2;i<8;i++)
             db.execSQL("delete from operas where id="+i+"; ");

        db.execSQL("insert into operas(titulo,compositor,year)" +"values('Giulo Cesare','G.F. Haendel',1274);") ;
        db.execSQL("insert into operas(titulo,compositor,year)" +"values('Orlando Fabregas','A. Vivaldi',1274);") ;

        //---selecciona todos los datos en un cursor
        cursor=db.rawQuery("select * from operas",null);



    }

    void muestraTabla()
    {
        tv.append("\n Tamaño: "+db.getPageSize());
        int numeroDeColumnas=cursor.getColumnCount();
        tv.append("\n Columnas: "+numeroDeColumnas);
        int numeroDeFilas=cursor.getCount();
        tv.append("\n Filas: "+numeroDeFilas);

        cursor.moveToFirst();
        for(int i=1;i<=numeroDeFilas;i++)
        {
            //---loop sobre las filas
            int id=cursor.getInt(0);
            String titulo=cursor.getString(1);
            String compositor=cursor.getString(2);
            int year =cursor.getInt(3);
            texto=texto+"\n "+id+", "+titulo+", "+compositor+", "+year;
            cursor.moveToNext();
        }
    }

}
