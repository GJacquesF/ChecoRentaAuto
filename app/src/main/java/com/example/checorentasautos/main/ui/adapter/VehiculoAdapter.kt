package com.example.checorentasautos.main.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.Vehiculo

class VehiculoAdapter(private val listaVehiculos: List<Vehiculo>) :
    RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>() {

    class VehiculoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreVehiculo: TextView = itemView.findViewById(R.id.tvNombreVehiculo)
        val tvPlaca: TextView = itemView.findViewById(R.id.tvPlaca)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehiculo, parent, false)
        return VehiculoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) {
        val vehiculo = listaVehiculos[position]

        holder.tvNombreVehiculo.text = "${vehiculo.marca} ${vehiculo.modelo} (${vehiculo.año})"
        holder.tvPlaca.text = "Placa: ${vehiculo.placa}"
        holder.tvPrecio.text = "$${String.format("%.2f", vehiculo.costoRentaDiario)} / día"

        if (vehiculo.disponible) {
            holder.tvEstado.text = "Estado: Disponible"
            holder.tvEstado.setTextColor(Color.parseColor("#2CA52C"))
        } else {
            holder.tvEstado.text = "Estado: Rentado / No disponible"
            holder.tvEstado.setTextColor(Color.parseColor("#D32F2F"))
        }
    }

    override fun getItemCount(): Int = listaVehiculos.size
}