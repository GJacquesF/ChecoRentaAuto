package com.example.checorentasautos.main.data

data class Renta(
    val folio: String,
    val cliente: Cliente,
    val vehiculo: Vehiculo,
    var dias: Int,
    var costoTotal: Double = 0.0,
    var activa: Boolean = true
) {
    init {
        costoTotal = calcularCostoTotal()
    }

    fun calcularCostoTotal(): Double {
        return vehiculo.calcularCosto(dias)
    }

    fun registrarDevolucion() {
        activa = false
        vehiculo.marcarDevuelto()
    }

    fun obtenerResumen(): String {
        val estado = if (activa) "ACTIVA" else "FINALIZADA"
        return "Folio: #$folio | Cliente: ${cliente.nombre} | Vehículo: ${vehiculo.marca} ${vehiculo.modelo} | Días: $dias | Total: $$costoTotal - $estado"
    }

    companion object {
        private var contadorFolio = 12

        fun crear(cliente: Cliente, vehiculo: Vehiculo, dias: Int): Renta {
            val folioGenerado = String.format("%04d", contadorFolio++)
            val renta = Renta(folioGenerado, cliente, vehiculo, dias)
            vehiculo.marcarRentado()
            return renta
        }
    }
}