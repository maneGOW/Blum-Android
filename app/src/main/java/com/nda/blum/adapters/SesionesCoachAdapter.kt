package com.nda.blum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nda.blum.DAO.BuscarCitasResponse
import com.nda.blum.DAO.CitasEncontradas
import com.nda.blum.R
import kotlinx.android.synthetic.main.coach_citas_asignadas.view.*

class SesionesCoachAdapter (val context: Context, val citasResponse: BuscarCitasResponse) : RecyclerView.Adapter<CitasViewHolder>() {

    override fun getItemCount(): Int {
        return citasResponse.result.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasViewHolder {
           return innerCitasViewHolder(LayoutInflater.from(context).inflate(R.layout.coach_citas_asignadas, parent, false))
    }

    inner class innerCitasViewHolder (view: View) : CitasViewHolder(view) {
        private var userNombre: TextView = view.txtUserNombre
        private var userHour: TextView = view.txtHorarios

        override fun bind(citas: CitasEncontradas) {
            userHour.text = "${citas.horaCita}:00 HRS "
            userNombre.text = citas.nombreAlumno
        }
    }

    override fun onBindViewHolder(holder: CitasViewHolder, position: Int) {
        val citas  = citasResponse.result.get(position)
        holder.bind(citas)
    }
}

open class CitasViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(citas: CitasEncontradas) {}
}