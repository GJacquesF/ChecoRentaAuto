package com.example.checorentasautos.main.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.DataManager
import com.example.checorentasautos.main.data.Renta
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class DevolucionActivity : AppCompatActivity() {

    private var rentaEncontrada: Renta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devolucion)

        // Referencias del layout
        val txtBuscarPlacaFolio: EditText = findViewById(R.id.txtBuscarPlacaFolio)
        val btnBuscarRenta: ImageButton = findViewById(R.id.btnBuscarRenta)
        val cardInfoRenta: MaterialCardView = findViewById(R.id.cardInfoRenta)
        val lblClienteInfo: TextView = findViewById(R.id.lblClienteInfo)
        val lblVehiculoInfo: TextView = findViewById(R.id.lblVehiculoInfo)
        val lblFechaFinInfo: TextView = findViewById(R.id.lblFechaFinInfo)
        val lblAdeudoPendiente: TextView = findViewById(R.id.lblAdeudoPendiente)
        val btnRegistrarDevolucion: MaterialButton = findViewById(R.id.btnRegistrarDevolucion)

        // Ocultar la tarjeta de información hasta realizar una búsqueda válida
        cardInfoRenta.visibility = View.GONE

        // 1. Buscar Renta Activa por Placa o Folio
        btnBuscarRenta.setOnClickListener {
            val query = txtBuscarPlacaFolio.text.toString().trim()

            if (query.isEmpty()) {
                Toast.makeText(this, "Ingresa una placa o número de folio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Buscar coincidencia en la lista de rentas activas
            rentaEncontrada = DataManager.listaRentas.find { renta ->
                renta.activa && (
                        renta.folio.equals(query, ignoreCase = true) ||
                                renta.vehiculo.placa.equals(query, ignoreCase = true)
                        )
            }

            val renta = rentaEncontrada
            if (renta != null) {
                // Poblar la información en la tarjeta
                lblClienteInfo.text = "Cliente: ${renta.cliente.nombre}"
                lblVehiculoInfo.text = "Vehículo: ${renta.vehiculo.marca} ${renta.vehiculo.modelo} [${renta.vehiculo.placa}]"
                lblFechaFinInfo.text = "Días rentados: ${renta.dias}"
                lblAdeudoPendiente.text = "Adeudo Pendiente: $0.00"

                cardInfoRenta.visibility = View.VISIBLE
            } else {
                cardInfoRenta.visibility = View.GONE
                Toast.makeText(this, "No se encontró ninguna renta activa con ese criterio", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Registrar la Devolución
        btnRegistrarDevolucion.setOnClickListener {
            val renta = rentaEncontrada

            if (renta == null) {
                Toast.makeText(this, "Busca y selecciona una renta activa primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Marcar la renta como completada e inhabilitada
            renta.activa = false

            // Liberar el vehículo para futuras rentas
            renta.vehiculo.disponible = true

            Toast.makeText(
                this,
                "Devolución del vehículo ${renta.vehiculo.placa} registrada correctamente",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }
    }
}