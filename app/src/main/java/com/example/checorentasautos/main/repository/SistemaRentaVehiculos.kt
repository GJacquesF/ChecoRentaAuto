package com.example.checorentasautos.main.repository

import com.example.checorentasautos.main.data.Cliente
import com.example.checorentasautos.main.data.Renta
import com.example.checorentasautos.main.data.Vehiculo

object SistemaRentaVehiculos {
    val vehiculos: MutableList<Vehiculo> = mutableListOf()
    val clientes: MutableList<Cliente> = mutableListOf()
    val rentas: MutableList<Renta> = mutableListOf()

    fun registrarVehiculo(vehiculo: Vehiculo) {
        vehiculo.registrar()
        vehiculos.add(vehiculo)
    }

    fun registrarCliente(cliente: Cliente) {
        cliente.registrar()
        clientes.add(cliente)
    }

    fun consultarVehiculos(): List<Vehiculo> = vehiculos.toList()

    fun consultarVehiculosDisponibles(): List<Vehiculo> {
        return vehiculos.filter { it.estaDisponible() }
    }

    fun realizarRenta(identificacionCliente: String, placaVehiculo: String, dias: Int): Renta? {
        val cliente = clientes.find { it.identificacion == identificacionCliente }
        val vehiculo = vehiculos.find { it.placa == placaVehiculo && it.estaDisponible() }

        return if (cliente != null && vehiculo != null) {
            val nuevaRenta = Renta.crear(cliente, vehiculo, dias)
            rentas.add(nuevaRenta)
            nuevaRenta
        } else {
            null
        }
    }

    fun registrarDevolucion(placaVehiculo: String): Boolean {
        val rentaActiva = rentas.find { it.vehiculo.placa == placaVehiculo && it.activa }
        return if (rentaActiva != null) {
            rentaActiva.registrarDevolucion()
            true
        } else {
            false
        }
    }

    fun mostrarRentas(): List<Renta> = rentas.toList()

    fun consultarRenta(renta: Renta): String {
        return renta.obtenerResumen()
    }
}