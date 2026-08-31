package com.example.checorentasautos.main.data

data class Cliente(
    var identificacion: String,
    var nombre: String,
    var telefono: String
) {
    fun registrar() {
        // Inicialización de registro
    }

    fun actualizarDatos(nombre: String, telefono: String) {
        this.nombre = nombre
        this.telefono = telefono
    }
}