package com.example.checorentasautos.main.data

object DataManager {
    val listaVehiculos = mutableListOf<Vehiculo>(
        Vehiculo("ABC-123", "Toyota", "Hilux", 2023, 800.0, true, "Pickup", 15000),
        Vehiculo("XYZ-789", "Nissan", "Versa", 2022, 500.0, true, "Sedán", 25000),
        Vehiculo("GHI-456", "Honda", "CR-V", 2024, 1200.0, true, "SUV", 5000)
    )
    val listaClientes = mutableListOf<Cliente>(
        Cliente("1010", "Juan Pérez", "555-0101"),
        Cliente("2020", "María García", "555-0202"),
        Cliente("3030", "Carlos López", "555-0303")
    )
    val listaRentas = mutableListOf<Renta>()
}