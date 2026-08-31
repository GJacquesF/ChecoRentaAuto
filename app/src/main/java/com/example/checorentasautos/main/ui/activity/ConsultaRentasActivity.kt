package com.example.checorentasautos.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.DataManager
import com.example.checorentasautos.main.ui.adapter.RentaAdapter

class ConsultaRentasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_rentas)

        val rvRentas: RecyclerView = findViewById(R.id.rvRentas)

        // Configuración del RecyclerView
        rvRentas.layoutManager = LinearLayoutManager(this)
        rvRentas.adapter = RentaAdapter(DataManager.listaRentas)
    }
}