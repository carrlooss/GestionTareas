package com.example.gestiontareas

import android.app.Application
import android.content.Context
import com.example.gestiontareas.providers.db.MyDatabase

class Aplicacion: Application() {
    companion object{
        const val VERSION=2
        const val DB="Base_1"
        const val TABLA="tareas"
        lateinit var appContext: Context
        lateinit var llave: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appContext=applicationContext
        llave=MyDatabase()
    }
}