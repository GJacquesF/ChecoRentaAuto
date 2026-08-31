package com.example.checorentasautos.main.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.checorentasautos.R
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a las tarjetas del menú principal
        val cardRegistroVehiculos: MaterialCardView = findViewById(R.id.cardRegistroVehiculos)
        val cardRegistroClientes: MaterialCardView = findViewById(R.id.cardRegistroClientes)
        val cardConsultaVehiculos: MaterialCardView = findViewById(R.id.cardConsultaVehiculos)
        val cardRentaVehiculo: MaterialCardView = findViewById(R.id.cardRentaVehiculo)
        val cardDevolucion: MaterialCardView = findViewById(R.id.cardDevolucion)
        val cardConsultaRentas: MaterialCardView = findViewById(R.id.cardConsultaRentas)

        // 1. Ir a Registro de Vehículos
        cardRegistroVehiculos.setOnClickListener {
            startActivity(Intent(this, RegVehiculosActivity::class.java))
        }

        // 2. Ir a Registro de Clientes
        cardRegistroClientes.setOnClickListener {
            startActivity(Intent(this, RegClientesActivity::class.java))
        }

        // 3. Ir a Consulta de Vehículos
        cardConsultaVehiculos.setOnClickListener {
            startActivity(Intent(this, ConsultaVehiculosActivity::class.java))
        }

        // 4. Ir a Renta de Vehículo
        cardRentaVehiculo.setOnClickListener {
            startActivity(Intent(this, RentaVehiculoActivity::class.java))
        }

        // 5. Ir a Devolución
        cardDevolucion.setOnClickListener {
            startActivity(Intent(this, DevolucionActivity::class.java))
        }

        // 6. Ir a Consulta de Rentas
        cardConsultaRentas.setOnClickListener {
            startActivity(Intent(this, ConsultaRentasActivity::class.java))
        }
    }
}