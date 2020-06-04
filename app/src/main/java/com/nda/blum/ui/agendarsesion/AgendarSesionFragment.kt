package com.nda.blum.ui.agendarsesion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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

        val sdf = SimpleDateFormat("dd/MM/yyyy")
// set a string to format your current date
// set a string to format your current date
        val curDate = sdf.format(bindingAgendarSesion.calendarView.date)

        println(curDate)


        var dia = ""
        var mes = ""
        var anio = ""

        bindingAgendarSesion.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            dia = dayOfMonth.toString()
            mes = validateMonth(month)
            anio = year.toString()
            println("FECHA: $dia $mes $anio}")
        }

        bindingAgendarSesion.btnAgendar.setOnClickListener {

        }

        bindingAgendarSesion.imgAgendarSesionBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return bindingAgendarSesion.root
    }

    private fun setFuncional(fecha: String){

    }

    private fun validateMonth(month:Int): String{
        var monthData = ""
        when (month) {
            0 -> {
                monthData = "01"
            }
            1 -> {
                monthData = "02"
            }
            2 -> {
                monthData = "03"
            }
            3 -> {
                monthData = "04"
            }
            4 -> {
                monthData = "05"
            }
            5 -> {
                monthData = "06"
            }
            6 -> {
                monthData = "07"
            }
            7 -> {
                monthData = "08"
            }
            8 -> {
                monthData = "09"
            }
            9 -> {
                monthData = "10"
            }
            11 -> {
                monthData = "11"
            }
            12 -> {
                monthData = "12"
            }
        }
        return monthData
    }
}
