package com.example.gestiontareas.providers.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gestiontareas.Aplicacion

class MyDatabase(): SQLiteOpenHelper(Aplicacion.appContext, Aplicacion.DB, null, Aplicacion.VERSION) {
    private val q="create table ${Aplicacion.TABLA}(" +
            "id integer primary key autoincrement," +
            "tipo integer not null," +
            "nombre text not null," +
            "descripcion text not null," +
            "realizado boolean not null);"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(q)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(newVersion>oldVersion){
            val borrarTabla="drop table ${Aplicacion.TABLA};"
            db?.execSQL(borrarTabla)
            onCreate(db)
        }
    }
}







