package com.example.gestiontareas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestiontareas.adapters.TareaAdapter
import com.example.gestiontareas.databinding.ActivityMainBinding
import com.example.gestiontareas.models.TareaModel
import com.example.gestiontareas.providers.db.CrudTareas
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.gestiontareas.framentos.EstadisticasFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lista= mutableListOf<TareaModel>()
    private lateinit var adapter: TareaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //applicationContext.deleteDatabase("Base_1")

        setSearchView()
        setListeners()
        setRecycler()
        val estadisticas = EstadisticasFragment()
        cargarFraments(estadisticas)
        title="Mis Tareas"
    }

    private fun setRecycler() {
        val layoutManger=LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManger
        traerRegistros()
        adapter=TareaAdapter(lista, { position->borrarContacto(position)}, { c->update(c)})
        binding.recyclerView.adapter=adapter
    }
    //----------------------------------------------------------------------------------------------
    private fun update(t: TareaModel){
        val i=Intent(this, AddActivity::class.java).apply {
            putExtra("TAREA", t)
        }
        startActivity(i)
    }
    //----------------------------------------------------------------------------------------------
    private fun borrarContacto(p: Int){
        val id=lista[p].id
        //Lo elimino de la lisa
        lista.removeAt(p)
        //lo elimino de la base de datos
        if(CrudTareas().borrar(id)){
            adapter.notifyItemRemoved(p)
        }else{
            Toast.makeText(this, "No se eliminó ningún registro", Toast.LENGTH_SHORT).show()
        }
    }
    //----------------------------------------------------------------------------------------------

    private fun traerRegistros() {
        lista=CrudTareas().read()
        if(lista.size>0){
            binding.ivTareas.visibility=View.INVISIBLE
        }else{
            binding.ivTareas.visibility=View.VISIBLE
        }

    }

    private fun setListeners() {
        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        setRecycler()
    }
    /////////////////////Menu principal
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_salir->{
                finish()
            }
            R.id.item_borrar_todo->{
                confirmarBorrado()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmarBorrado(){
        val builder=AlertDialog.Builder(this)
            .setTitle("¿Borrar Tareas?")
            .setMessage("¿Borrar todas las tareas?")
            .setNegativeButton("CANCELAR"){
                    dialog,_->dialog.dismiss()
            }
            .setPositiveButton("ACEPTAR"){
                    _,_->
                CrudTareas().borrarTodo()
                setRecycler()
            }
            .create()
            .show()
   }

    private fun setSearchView(){
        // Configuración del SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Se ejecuta cuando el usuario presiona "Buscar"
                query?.let {
                    if (query == ""){
                        lista = CrudTareas().read()
                    }else{
                        lista = CrudTareas().searchItems(query)
                    }
                    adapter=TareaAdapter(lista, { position->borrarContacto(position)}, { c->update(c)})
                    binding.recyclerView.adapter=adapter
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Cuando el texto cambia
                newText?.let {
                    if (newText == ""){
                        lista = CrudTareas().read()
                    }else{
                        lista = CrudTareas().searchItems(newText)
                    }
                    adapter=TareaAdapter(lista, { position->borrarContacto(position)}, { c->update(c)})
                    binding.recyclerView.adapter=adapter
                }
                return true
            }
        })
    }

    private fun cargarFraments(estadisticas: EstadisticasFragment){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_estadisticas, estadisticas)
        }
    }
}