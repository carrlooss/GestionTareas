package com.example.gestiontareas.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiontareas.databinding.TareaLayoutBinding
import com.example.gestiontareas.models.TareaModel

class TareaViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val tipos = listOf("Normal", "Importante")

    val binding  = TareaLayoutBinding.bind(v)
    fun render(
        t: TareaModel,
        borrarTarea: (Int) -> Unit,
        updateTarea: (TareaModel) -> Unit
    ){
        binding.tvTipo.text = tipos[t.tipo]
        binding.tvNombre.text=t.nombre
        binding.tvDescripcion.text=t.descripcion
        binding.cbRealizado.isChecked = t.realizado

        binding.btnBorrar.setOnClickListener {
            borrarTarea(adapterPosition)
        }
        binding.btnUpdate.setOnClickListener {
            updateTarea(t)
        }
    }

}
