package com.example.checorentasautos.main.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.Cliente
import com.example.checorentasautos.main.data.DataManager
import com.example.checorentasautos.main.data.Renta
import com.example.checorentasautos.main.data.Vehiculo
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class RentaVehiculoActivity : AppCompatActivity() {

    private var clienteSeleccionado: Cliente? = null
    private var vehiculoSeleccionado: Vehiculo? = null

    private var fechaInicioMillis: Long = System.currentTimeMillis()
    private var fechaFinMillis: Long = System.currentTimeMillis() + (86400000L * 4) // +4 días por defecto
    private var diasCalculados: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renta_vehiculo)

        // Referencias del layout
        val spnCliente: AutoCompleteTextView = findViewById(R.id.spnCliente)
        val spnVehiculo: AutoCompleteTextView = findViewById(R.id.spnVehiculo)
        val cardDatePicker: MaterialCardView = findViewById(R.id.cardDatePicker)
        val lblRangoFechas: TextView = findViewById(R.id.lblRangoFechas)
        val lblCostoPorDia: TextView = findViewById(R.id.lblCostoPorDia)
        val lblDias: TextView = findViewById(R.id.lblDias)
        val lblTotal: TextView = findViewById(R.id.lblTotal)
        val btnConfirmar: MaterialButton = findViewById(R.id.btnConfirmar)
        val btnCancelar: MaterialButton = findViewById(R.id.btnCancelar)

        // Nuevos campos de entrega
        val spnGasolina: AutoCompleteTextView = findViewById(R.id.spnGasolina)
        val txtKilometrajeSalida: TextInputEditText = findViewById(R.id.txtKilometrajeSalida)
        val txtObservaciones: TextInputEditText = findViewById(R.id.txtObservaciones)
        val switchCheckRapido: SwitchMaterial = findViewById(R.id.switchCheckRapido)

        // 1. Cargar Clientes
        val clientes = DataManager.listaClientes
        val adapterClientes = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            clientes.map { "${it.nombre} (${it.identificacion})" }
        )
        spnCliente.setAdapter(adapterClientes)

        spnCliente.setOnItemClickListener { _, _, position, _ ->
            clienteSeleccionado = clientes[position]
        }

        // 2. Cargar Vehículos Disponibles
        val vehiculosDisponibles = DataManager.listaVehiculos.filter { it.disponible }
        val adapterVehiculos = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            vehiculosDisponibles.map { "${it.marca} ${it.modelo} [${it.placa}] - $${it.costoRentaDiario}/día" }
        )
        spnVehiculo.setAdapter(adapterVehiculos)

        spnVehiculo.setOnItemClickListener { _, _, position, _ ->
            vehiculoSeleccionado = vehiculosDisponibles[position]
            actualizarDatosVehiculo()
            actualizarResumen(lblCostoPorDia, lblDias, lblTotal)
        }

        // 3. Nivel de Gasolina
        val nivelesGas = arrayOf("Vacío", "1/4", "1/2", "3/4", "Lleno")
        spnGasolina.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nivelesGas))

        // 4. Pre-seleccionar vehículo si viene de Consulta
        val placaIntent = intent.getStringExtra("PLACA_VEHICULO")
        if (placaIntent != null) {
            val vehiculo = DataManager.listaVehiculos.find { it.placa == placaIntent }
            if (vehiculo != null && vehiculo.disponible) {
                vehiculoSeleccionado = vehiculo
                spnVehiculo.setText("${vehiculo.marca} ${vehiculo.modelo} [${vehiculo.placa}] - $${vehiculo.costoRentaDiario}/día", false)
                actualizarDatosVehiculo()
                actualizarResumen(lblCostoPorDia, lblDias, lblTotal)
            }
        }

        // 5. Configurar Selector de Rango de Fechas
        actualizarTextoFechas(lblRangoFechas)
        actualizarResumen(lblCostoPorDia, lblDias, lblTotal)

        cardDatePicker.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Selecciona las fechas de renta")
                .setSelection(androidx.core.util.Pair(fechaInicioMillis, fechaFinMillis))
                .build()

            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                selection.first?.let { fechaInicioMillis = it }
                selection.second?.let { fechaFinMillis = it }

                val diffMillis = fechaFinMillis - fechaInicioMillis
                diasCalculados = (diffMillis / (1000 * 60 * 60 * 24)).toInt()
                if (diasCalculados <= 0) diasCalculados = 1

                actualizarTextoFechas(lblRangoFechas)
                actualizarResumen(lblCostoPorDia, lblDias, lblTotal)
            }

            dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")
        }

        // 6. Botón Confirmar
        btnConfirmar.setOnClickListener {
            val cliente = clienteSeleccionado
            val vehiculo = vehiculoSeleccionado
            val kmSalidaStr = txtKilometrajeSalida.text.toString()
            val gasolina = spnGasolina.text.toString()

            if (cliente == null || vehiculo == null || kmSalidaStr.isEmpty() || gasolina.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos, incluyendo datos de entrega", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val kmSalida = kmSalidaStr.toInt()

            // VALIDACIÓN INTELIGENTE: Kilometraje no puede ser menor al actual
            if (kmSalida < vehiculo.kilometrajeActual) {
                Toast.makeText(this, "ERROR: El kilometraje de salida ($kmSalida) no puede ser menor al kilometraje actual del vehículo (${vehiculo.kilometrajeActual})", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Actualizar datos del vehículo
            vehiculo.disponible = false
            vehiculo.kilometrajeActual = kmSalida

            // Generar Renta
            val nuevaRenta = Renta(
                folio = (DataManager.listaRentas.size + 1001).toString(),
                cliente = cliente,
                vehiculo = vehiculo,
                dias = diasCalculados,
                costoTotal = vehiculo.costoRentaDiario * diasCalculados,
                activa = true,
                gasolinaEntrega = gasolina,
                kilometrajeEntrega = kmSalida,
                observacionesEntrega = txtObservaciones.text.toString(),
                checkRapidoEntrega = switchCheckRapido.isChecked
            )

            DataManager.listaRentas.add(nuevaRenta)
            Toast.makeText(this, "Renta confirmada. Folio #${nuevaRenta.folio}", Toast.LENGTH_LONG).show()
            finish()
        }

        btnCancelar.setOnClickListener { finish() }
    }

    private fun actualizarDatosVehiculo() {
        vehiculoSeleccionado?.let {
            findViewById<TextInputEditText>(R.id.txtKilometrajeSalida).setText(it.kilometrajeActual.toString())
        }
    }

    private fun actualizarTextoFechas(lblRangoFechas: TextView) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val fechaInicioStr = sdf.format(Date(fechaInicioMillis))
        val fechaFinStr = sdf.format(Date(fechaFinMillis))
        lblRangoFechas.text = "$fechaInicioStr - $fechaFinStr"
    }

    private fun actualizarResumen(lblCostoPorDia: TextView, lblDias: TextView, lblTotal: TextView) {
        val vehiculo = vehiculoSeleccionado
        val costoPorDia = vehiculo?.costoRentaDiario ?: 0.0
        val total = costoPorDia * diasCalculados

        lblCostoPorDia.text = "Costo por día: $${String.format("%.2f", costoPorDia)}"
        lblDias.text = "Días: $diasCalculados"
        lblTotal.text = "TOTAL: $${String.format("%.2f", total)}"
    }
}