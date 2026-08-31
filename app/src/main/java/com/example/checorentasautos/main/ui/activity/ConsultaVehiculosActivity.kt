package com.example.checorentasautos.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.DataManager
import com.example.checorentasautos.main.ui.adapter.VehiculoAdapter

class ConsultaVehiculosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_vehiculos)

        val rvVehiculos: RecyclerView = findViewById(R.id.rvVehiculos)

        // Configuración del RecyclerView
        rvVehiculos.layoutManager = LinearLayoutManager(this)
        rvVehiculos.adapter = VehiculoAdapter(DataManager.listaVehiculos)
    }
}