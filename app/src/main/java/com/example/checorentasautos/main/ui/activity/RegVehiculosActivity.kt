package com.example.checorentasautos.main.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.DataManager
import com.example.checorentasautos.main.data.Vehiculo
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegVehiculosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_vehiculos)

        // Referencias a los componentes de la interfaz
        val txtPlaca: TextInputEditText = findViewById(R.id.txtPlaca)
        val spnMarca: AutoCompleteTextView = findViewById(R.id.spnMarca)
        val txtModelo: TextInputEditText = findViewById(R.id.txtModelo)
        val txtAnio: TextInputEditText = findViewById(R.id.txtAnio)
        val txtPrecio: TextInputEditText = findViewById(R.id.txtPrecio)
        val spnEstado: AutoCompleteTextView = findViewById(R.id.spnEstado)
        val btnGuardar: MaterialButton = findViewById(R.id.btnGuardarVehiculo)

        // Configuración de opciones para los desplegables (Dropdowns)
        val marcas = arrayOf("Toyota", "Nissan", "Ford", "Chevrolet", "Honda", "Hyundai", "Volkswagen")
        val adapterMarcas = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, marcas)
        spnMarca.setAdapter(adapterMarcas)

        val estados = arrayOf("Disponible", "Mantenimiento")
        val adapterEstados = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, estados)
        spnEstado.setAdapter(adapterEstados)

        // Evento del botón Guardar
        btnGuardar.setOnClickListener {
            val placa = txtPlaca.text.toString().trim()
            val marca = spnMarca.text.toString().trim()
            val modelo = txtModelo.text.toString().trim()
            val anioStr = txtAnio.text.toString().trim()
            val precioStr = txtPrecio.text.toString().trim()
            val estadoSeleccionado = spnEstado.text.toString().trim()

            if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || anioStr.isEmpty() || precioStr.isEmpty() || estadoSeleccionado.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoVehiculo = Vehiculo(
                placa = placa,
                marca = marca,
                modelo = modelo,
                año = anioStr.toInt(),
                costoRentaDiario = precioStr.toDouble(),
                disponible = (estadoSeleccionado == "Disponible")
            )

            DataManager.listaVehiculos.add(nuevoVehiculo)
            Toast.makeText(this, "Vehículo registrado con éxito", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}