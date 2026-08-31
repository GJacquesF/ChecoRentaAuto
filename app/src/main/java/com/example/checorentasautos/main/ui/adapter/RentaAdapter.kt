package com.example.checorentasautos.main.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.Renta

class RentaAdapter(
    private val listaRentas: List<Renta>
) : RecyclerView.Adapter<RentaAdapter.RentaViewHolder>() {

    class RentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblFolio: TextView = itemView.findViewById(R.id.lblFolio)
        val lblCliente: TextView = itemView.findViewById(R.id.lblCliente)
        val lblVehiculo: TextView = itemView.findViewById(R.id.lblVehiculo)
        val lblFechas: TextView = itemView.findViewById(R.id.lblFechas)
        val lblTotal: TextView = itemView.findViewById(R.id.lblTotal)
        val lblEstadoRenta: TextView = itemView.findViewById(R.id.lblEstadoRenta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_renta, parent, false)
        return RentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RentaViewHolder, position: Int) {
        val renta = listaRentas[position]

        holder.lblFolio.text = "Folio: #${renta.folio}"
        holder.lblCliente.text = "Cliente: ${renta.cliente.nombre}"
        holder.lblVehiculo.text = "Vehículo: ${renta.vehiculo.marca} ${renta.vehiculo.modelo}"
        holder.lblFechas.text = "Días de renta: ${renta.dias}"
        holder.lblTotal.text = "Total: $${renta.costoTotal}"
        holder.lblEstadoRenta.text = if (renta.activa) "ACTIVA" else "FINALIZADA"
    }

    override fun getItemCount(): Int = listaRentas.size
}