package com.example.checorentasautos.main.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.Cliente
import com.example.checorentasautos.main.data.DataManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegClientesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_clientes)

        val txtDocumento: TextInputEditText = findViewById(R.id.txtDocumento)
        val txtNombre: TextInputEditText = findViewById(R.id.txtNombre)
        val txtTelefono: TextInputEditText = findViewById(R.id.txtTelefono)
        val btnGuardar: MaterialButton = findViewById(R.id.btnGuardarCliente)

        btnGuardar.setOnClickListener {
            val documento = txtDocumento.text.toString().trim()
            val nombre = txtNombre.text.toString().trim()
            val telefono = txtTelefono.text.toString().trim()

            if (documento.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cliente = Cliente(
                identificacion = documento,
                nombre = nombre,
                telefono = "+52 $telefono"
            )

            DataManager.listaClientes.add(cliente)
            Toast.makeText(this, "Cliente registrado con éxito", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}