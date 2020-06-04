package com.nda.blum.ui.agendarsesion

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nda.blum.R
import com.nda.blum.databinding.AgendarSesionFragmentBinding
import java.text.SimpleDateFormat

class AgendarSesionFragment : Fragment() {

    companion object {
        fun newInstance() = AgendarSesionFragment()
    }

    private lateinit var viewModel: AgendarSesionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingAgendarSesion: AgendarSesionFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.agendar_sesion_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = AgendarSesionViewModelFactory(application)
        val agendarSesionViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AgendarSesionViewModel::class.java)

        val dateFormat = SimpleDateFormat("yyyy/MM/dd");

        var dia = ""
        var mes = ""
        var anio = ""

        bindingAgendarSesion.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            println("FECHA: $dayOfMonth $month $year}")
            when (month) {
                0 -> {
                    mes = "01"
                }
                1 -> {
                    mes = "02"
                }
                2 -> {
                    mes = "03"
                }
                3 -> {
                    mes = "04"
                }
            }
        }

        bindingAgendarSesion.btnAgendar.setOnClickListener {

            println("HORA: ${bindingAgendarSesion.timePicker.currentHour} MINUTO: ${bindingAgendarSesion.timePicker.currentMinute}" )
        }

        bindingAgendarSesion.imgAgendarSesionBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        return bindingAgendarSesion.root

    }
}
