package com.example.checorentasautos.main.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.checorentasautos.R
import com.example.checorentasautos.main.data.Vehiculo
import java.util.Locale

class VehiculoAdapter(
    private var listaVehiculos: List<Vehiculo>,
    private val onItemClick: (Vehiculo) -> Unit
) : RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>(), Filterable {

    private var listaCompleta: List<Vehiculo> = listaVehiculos

    class VehiculoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreVehiculo: TextView = itemView.findViewById(R.id.tvNombreVehiculo)
        val tvPlaca: TextView = itemView.findViewById(R.id.tvPlaca)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val tvEtiqueta: TextView = itemView.findViewById(R.id.tvEtiqueta)
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
        holder.tvEtiqueta.text = vehiculo.etiqueta ?: "General"

        if (vehiculo.disponible) {
            holder.tvEstado.text = "Estado: Disponible"
            holder.tvEstado.setTextColor(Color.parseColor("#2CA52C"))
        } else {
            holder.tvEstado.text = "Estado: Rentado / No disponible"
            holder.tvEstado.setTextColor(Color.parseColor("#D32F2F"))
        }

        holder.itemView.setOnClickListener {
            onItemClick(vehiculo)
        }
    }

    override fun getItemCount(): Int = listaVehiculos.size

    fun actualizarLista(nuevaLista: List<Vehiculo>) {
        listaVehiculos = nuevaLista
        listaCompleta = nuevaLista
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.ROOT)
                val filteredList = if (query.isNullOrEmpty()) {
                    listaCompleta
                } else {
                    listaCompleta.filter {
                        it.placa.lowercase(Locale.ROOT).contains(query) ||
                                (it.etiqueta?.lowercase(Locale.ROOT)?.contains(query) ?: false)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listaVehiculos = results?.values as List<Vehiculo>
                notifyDataSetChanged()
            }
        }
    }
}