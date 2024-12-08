package com.example.gestiontareas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiontareas.R
import com.example.gestiontareas.models.TareaModel

class TareaAdapter(
    var lista: MutableList<TareaModel>,
    private val borrarTarea: (Int)->Unit,
    private val updateTarea: (TareaModel)->Unit
): RecyclerView.Adapter<TareaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.tarea_layout, parent, false)
        return TareaViewHolder(v)
    }

    override fun getItemCount()=lista.size

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        holder.render(lista[position], borrarTarea, updateTarea)
    }
}