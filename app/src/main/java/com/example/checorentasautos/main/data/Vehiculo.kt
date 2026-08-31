package com.example.checorentasautos.main.data

data class Vehiculo(
    var placa: String,
    var marca: String,
    var modelo: String,
    var año: Int,
    var costoRentaDiario: Double,
    var disponible: Boolean = true
) {
    fun registrar() {
        // Inicialización de registro
    }

    fun estaDisponible(): Boolean = disponible

    fun marcarRentado() {
        disponible = false
    }

    fun marcarDevuelto() {
        disponible = true
    }

    fun calcularCosto(dias: Int): Double {
        return costoRentaDiario * dias
    }
}