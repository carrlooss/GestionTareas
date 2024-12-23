package com.example.gestiontareas.framentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gestiontareas.R
import com.example.gestiontareas.providers.db.CrudTareas

class EstadisticasFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadisticas, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var registros = CrudTareas().registrosTotales()

        val tvRegistros = view.findViewById<TextView>(R.id.tv_registros)
        tvRegistros.text = "La tabla tareas tiene $registros registros"
    }
}