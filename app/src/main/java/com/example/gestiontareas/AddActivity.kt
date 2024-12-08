package com.example.gestiontareas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gestiontareas.databinding.ActivityAddBinding
import com.example.gestiontareas.models.TareaModel
import com.example.gestiontareas.providers.db.CrudTareas

class AddActivity : AppCompatActivity() {
    val tipos = listOf("Normal", "Importante")

    private lateinit var binding: ActivityAddBinding
    private var realizado : Boolean = false
    private var tipo: Int = 0
    private var nombre : String = ""
    private var descripcion = ""
    private var id=-1
    private var isUpdate=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setOpcionesTipo()
        recogerTarea()
        setListeners()
        if(isUpdate){
            binding.etTitle2.text="Editar Tarea"
            binding.btn2Enviar.text="EDITAR"
        }
    }

    private fun setOpcionesTipo(){
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipoTarea.adapter = adapter
    }

    private fun recogerTarea() {
        val datos=intent.extras
        if(datos!=null){
            val t = datos.getSerializable("TAREA") as TareaModel
            isUpdate=true
            realizado = t.realizado
            nombre = t.nombre
            descripcion = t.descripcion
            id = t.id

            pintarDatos()
        }
    }

    private fun pintarDatos() {
        binding.cbRealizado.isChecked = realizado
        binding.spTipoTarea.setSelection(tipo)
        binding.etNombre.setText(nombre)
        binding.etDescripcion.setText(descripcion)
    }

    private fun setListeners() {
        binding.btnCancelar.setOnClickListener{
            finish()
        }
        binding.btn2Reset.setOnClickListener {
            limpiar()
        }
        binding.btn2Enviar.setOnClickListener {
            guardarRegistro()
        }
    }

    private fun guardarRegistro() {
        if(datosCorrectos()){
            val t=TareaModel(id, tipo, nombre, descripcion, realizado)
            if(!isUpdate) {
                if (CrudTareas().create(t) != -1L) {
                    Toast.makeText(
                        this,
                        "Se ha añadido un registro a las tareas",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }else{
                if(CrudTareas().update(t)){
                    Toast.makeText(this, "Tarea Editada", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun datosCorrectos(): Boolean {
        tipo = binding.spTipoTarea.selectedItemPosition
        nombre=binding.etNombre.text.toString().trim()
        descripcion=binding.etDescripcion.text.toString().trim()
        realizado=binding.cbRealizado.isChecked
        if(descripcion.length<3){
            binding.etDescripcion.error="El campo nombre debe tener al menos 3 caracteres"
            return false;
        }
        return true
    }

    private fun limpiar(){
        binding.spTipoTarea.setSelection(0)
        binding.etNombre.setText("")
        binding.etDescripcion.setText("")
        binding.cbRealizado.isChecked = false
    }
}