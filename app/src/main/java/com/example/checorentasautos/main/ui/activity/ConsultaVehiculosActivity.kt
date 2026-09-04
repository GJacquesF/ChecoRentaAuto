package com.example.checorentasautos.main.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.DataManager
import com.example.checorentasautos.main.data.Vehiculo
import com.example.checorentasautos.main.ui.adapter.VehiculoAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ConsultaVehiculosActivity : AppCompatActivity() {

    private lateinit var adapter: VehiculoAdapter
    private var filtroEtiqueta: String = "Todos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_vehiculos)

        val rvVehiculos: RecyclerView = findViewById(R.id.rvVehiculos)
        val txtBuscarPlaca: EditText = findViewById(R.id.txtBuscarPlaca)
        val chipGroupEtiquetas: ChipGroup = findViewById(R.id.chipGroupEtiquetas)

        // Configuración del RecyclerView
        adapter = VehiculoAdapter(DataManager.listaVehiculos) { vehiculo ->
            if (vehiculo.disponible) {
                val intent = Intent(this, RentaVehiculoActivity::class.java)
                intent.putExtra("PLACA_VEHICULO", vehiculo.placa)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Este vehículo no está disponible para renta", Toast.LENGTH_SHORT).show()
            }
        }
        rvVehiculos.layoutManager = LinearLayoutManager(this)
        rvVehiculos.adapter = adapter

        // Búsqueda por placa
        txtBuscarPlaca.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                aplicarFiltros(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Filtro por etiquetas (Chips)
        chipGroupEtiquetas.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = findViewById(checkedId)
            filtroEtiqueta = chip?.text?.toString() ?: "Todos"
            aplicarFiltros(txtBuscarPlaca.text.toString())
        }
    }

    private fun aplicarFiltros(textoPlaca: String) {
        var listaFiltrada = DataManager.listaVehiculos.filter { 
            it.placa.contains(textoPlaca, ignoreCase = true)
        }

        if (filtroEtiqueta != "Todos") {
            listaFiltrada = listaFiltrada.filter { 
                it.etiqueta.equals(filtroEtiqueta, ignoreCase = true)
            }
        }

        // Usamos el método de filter del adapter para la placa pero aquí lo hacemos manual 
        // para combinar placa y chip fácilmente
        (findViewById<RecyclerView>(R.id.rvVehiculos).adapter as VehiculoAdapter).actualizarLista(listaFiltrada)
    }

    override fun onResume() {
        super.onResume()
        // Refrescar lista al volver
        adapter.actualizarLista(DataManager.listaVehiculos)
    }
}