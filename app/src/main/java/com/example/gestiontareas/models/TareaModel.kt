package com.example.gestiontareas.models

import java.io.Serializable

data class TareaModel(
    val id: Int,
    val tipo: Int,
    val nombre: String,
    val descripcion: String,
    val realizado: Boolean,
): Serializable


