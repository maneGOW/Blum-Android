package com.nda.blum.ui.sesionescoach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.adapters.SesionesCoachAdapter
import com.nda.blum.databinding.SesionersCoachFragmentBinding
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat

@InternalCoroutinesApi
class SesionersCoachFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.VISIBLE

        val bindingSesionesCoach: SesionersCoachFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.sesioners_coach_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            SesionesCoachViewModelFactory(application)
        val sesionesCoachViewModel = ViewModelProviders.of(this,viewModelFactory).get(SesionersCoachViewModel::class.java)

        bindingSesionesCoach.lifecycleOwner = this

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        sesionesCoachViewModel.diaCita.value =
            sdf.format(bindingSesionesCoach.calendarView2.date)

        bindingSesionesCoach.txtSesionCoachFecha.text = sesionesCoachViewModel.diaCita.value

        bindingSesionesCoach.imgAgendarSesionBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        bindingSesionesCoach.calendarView2.setOnDateChangeListener { _, year, month, dayOfMonth ->



            if (year >= sesionesCoachViewModel.diaCita.value!!.substring(6, 10)
                    .toInt()
            ) {
                if (validateMonth(month) >= sesionesCoachViewModel.diaCita.value!!.substring(3, 5)
                ) {
                    if (dayOfMonth.toInt() >= sesionesCoachViewModel.diaCita.value!!.substring(0, 2)
                            .toInt()
                    ) {
                        println("FECHA: $dayOfMonth ${validateMonth(month)} $year")
                        bindingSesionesCoach.txtSesionCoachFecha.text = "$dayOfMonth/${validateMonth(month)}/$year"
                        sesionesCoachViewModel.getCitasFromServer()
                       // agendarSesionViewModel!!.fecha.value = "$dia/$mes/$anio"
                       // agendarSesionViewModel!!.citasDisponibles()

                    } else {
                        println("el dia es anterior")
                        Toast.makeText(
                            this.context,
                            "El día no es válido",
                            Toast.LENGTH_LONG
                        ).show()
                        //resetHoursDefault(bindingAgendarSesion)
                    }
                } else {
                    println("el mes es anterior")
                    Toast.makeText(
                        this.context,
                        "La fecha no es válida",
                        Toast.LENGTH_LONG
                    ).show()
                    //resetHoursDefault(bindingAgendarSesion)
                }
            } else {
                println("el año es anterior")
                Toast.makeText(
                    this.context,
                    "La fecha no es válida",
                    Toast.LENGTH_LONG
                ).show()
              //  resetHoursDefault(bindingAgendarSesion)
            }
        }

        sesionesCoachViewModel.filledCitasDelServer.observe(viewLifecycleOwner, Observer {
            if(it){
                bindingSesionesCoach.rvCitas.layoutManager = LinearLayoutManager(this.context)
                bindingSesionesCoach.rvCitas.adapter = SesionesCoachAdapter(this.context!!, sesionesCoachViewModel.citasDelServer.value!!)
            }
        })

        return bindingSesionesCoach.root
    }

    private fun validateMonth(month: Int): String {
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
