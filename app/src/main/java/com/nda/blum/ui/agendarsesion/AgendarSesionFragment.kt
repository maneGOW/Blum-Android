package com.nda.blum.ui.agendarsesion

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.AgendarSesionFragmentBinding
import com.nda.blum.interfaces.IBackToHub
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat

@InternalCoroutinesApi
class AgendarSesionFragment : Fragment() {

    var agendarSesionViewModel: AgendarSesionViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingAgendarSesion: AgendarSesionFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.agendar_sesion_fragment, container, false
        )

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        val application = requireNotNull(this.activity).application
        val viewModelFactory = AgendarSesionViewModelFactory(application)
        agendarSesionViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AgendarSesionViewModel::class.java)

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        agendarSesionViewModel!!.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        agendarSesionViewModel!!.currentDate.value =
            sdf.format(bindingAgendarSesion.calendarView.date)
        agendarSesionViewModel!!.fecha.value = sdf.format(bindingAgendarSesion.calendarView.date)

        println(agendarSesionViewModel!!.currentDate.value)

        agendarSesionViewModel!!.citasDisponibles()

        agendarSesionViewModel!!.fechasDisponibles.observe(viewLifecycleOwner, Observer { it ->
            if (it!!.code == "0") {
                it.result.forEach {
                    when (it.horaDisponible) {
                        "9" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt9.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt9.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt9.isEnabled = false
                            }
                        }
                        "10" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt10.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt10.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt10.isEnabled = false
                            }
                        }
                        "11" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt11.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt11.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt11.isEnabled = false
                            }
                        }
                        "12" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt12.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt12.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt12.isEnabled = false
                            }
                        }
                        "13" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt13.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt13.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt13.isEnabled = false
                            }
                        }
                        "14" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt14.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt14.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt14.isEnabled = false

                            }
                        }
                        "15" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt15.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt15.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt15.isEnabled = false
                            }
                        }
                        "16" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt16.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt16.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt16.isEnabled = false
                            }
                        }
                        "17" -> {
                            if (it.disponible == "1") {
                                bindingAgendarSesion.txt17.background =
                                    resources.getDrawable(R.drawable.hinglight_rounded_shape)
                                bindingAgendarSesion.txt17.setTextColor(Color.parseColor("#FFFFFF"))
                                bindingAgendarSesion.txt17.isEnabled = false
                            }
                        }
                    }
                }
            }else if(it.code != "1"){
                Toast.makeText(this.context, "No hay citas disponibles", Toast.LENGTH_LONG).show()
                resetHoursDefault(bindingAgendarSesion)
            }
        })

        var dia = ""
        var mes = ""
        var anio = ""

        agendarSesionViewModel!!.sesionAgendadaSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(this.context, "Sesión agendada con éxito", Toast.LENGTH_LONG).show()
                (activity as IBackToHub?)!!.backToHubFragment()
            } else {
                Toast.makeText(
                    this.context,
                    "Ocurrió un error al agendar la sesión",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        bindingAgendarSesion.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            dia = dayOfMonth.toString()

            if (dia.length == 1) {
                dia = "0$dia"
            }

            mes = validateMonth(month)
            anio = year.toString()

            println(agendarSesionViewModel!!.currentDate.value!!.substring(6, 10))
            println(agendarSesionViewModel!!.currentDate.value!!.substring(3, 5))
            println(agendarSesionViewModel!!.currentDate.value!!.substring(0, 2))


            if (anio.toInt() >= agendarSesionViewModel!!.currentDate.value!!.substring(6, 10)
                    .toInt()
            ) {
                if (mes.toInt() >= agendarSesionViewModel!!.currentDate.value!!.substring(3, 5)
                        .toInt()
                ) {
                    if (dia.toInt() >= agendarSesionViewModel!!.currentDate.value!!.substring(0, 2)
                            .toInt()
                    ) {
                        println("FECHA: $dia $mes $anio")
                        agendarSesionViewModel!!.fecha.value = "$dia/$mes/$anio"
                        agendarSesionViewModel!!.citasDisponibles()

                    } else {
                        println("el dia es anterior")
                        Toast.makeText(
                            this.context,
                            "El día no es válido",
                            Toast.LENGTH_LONG
                        ).show()
                        resetHoursDefault(bindingAgendarSesion)
                    }
                } else {
                    println("el mes es anterior")
                    Toast.makeText(
                        this.context,
                        "La fecha no es válida",
                        Toast.LENGTH_LONG
                    ).show()
                    resetHoursDefault(bindingAgendarSesion)
                }
            } else {
                println("el año es anterior")
                Toast.makeText(
                    this.context,
                    "La fecha no es válida",
                    Toast.LENGTH_LONG
                ).show()
                resetHoursDefault(bindingAgendarSesion)
            }
        }

        bindingAgendarSesion.btnAgendar.setOnClickListener {
            try {
                println(agendarSesionViewModel!!.hora.value)
                if (agendarSesionViewModel!!.hora.value != "00") {
                    val sesionID =
                        agendarSesionViewModel!!.fechasDisponibles.value!!.result.last { it.horaDisponible == agendarSesionViewModel!!.hora.value }
                    agendarSesionViewModel!!.idFecha.value = sesionID.idfecha
                    agendarSesionViewModel!!.agendarSesion()
                } else {
                    println("selecciona una fecha valida")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this.context,
                    "La hora no está registrada en el servidor",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        setTextClickable(bindingAgendarSesion)

        bindingAgendarSesion.imgAgendarSesionBack.setOnClickListener {
            (activity as IBackToHub?)!!.backToHubFragment()
        }

        agendarSesionViewModel!!.citasDisponibles.observe(viewLifecycleOwner, Observer {
            if(!it){
                resetHoursDefault(bindingAgendarSesion)
                Toast.makeText(
                    this.context,
                    "No hay citas disponibles",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        return bindingAgendarSesion.root
    }

    private fun resetHoursDefault(binding: AgendarSesionFragmentBinding) {
        binding.txt9.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt9.setTextColor(Color.parseColor("#000000"))
        binding.txt9.isEnabled = true
        binding.txt9.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt10.setTextColor(Color.parseColor("#000000"))
        binding.txt10.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt10.isEnabled = true
        binding.txt11.setTextColor(Color.parseColor("#000000"))
        binding.txt11.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt11.isEnabled = true
        binding.txt12.setTextColor(Color.parseColor("#000000"))
        binding.txt12.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt12.isEnabled = true
        binding.txt13.setTextColor(Color.parseColor("#000000"))
        binding.txt13.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt13.isEnabled = true
        binding.txt14.setTextColor(Color.parseColor("#000000"))
        binding.txt14.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt14.isEnabled = true
        binding.txt15.setTextColor(Color.parseColor("#000000"))
        binding.txt15.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt15.isEnabled = true
        binding.txt16.setTextColor(Color.parseColor("#000000"))
        binding.txt16.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt16.isEnabled = true
        binding.txt17.setTextColor(Color.parseColor("#000000"))
        binding.txt17.background =
            resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
        binding.txt17.isEnabled = true
    }

    private fun setTextClickable(binding: AgendarSesionFragmentBinding) {
        binding.txt9.setOnClickListener {
            if (binding.txt9.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt9.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt9.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "9"
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
        binding.txt10.setOnClickListener {
            if (binding.txt10.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt10.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt10.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "10"
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
        binding.txt11.setOnClickListener {
            if (binding.txt11.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt11.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt11.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "11"
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
        binding.txt12.setOnClickListener {
            if (binding.txt12.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt12.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt12.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "12"
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
        binding.txt13.setOnClickListener {
            if (binding.txt13.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt13.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt13.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "13"
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
        binding.txt14.setOnClickListener {
            if (binding.txt14.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt14.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt14.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "14"
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
        binding.txt15.setOnClickListener {
            if (binding.txt15.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                agendarSesionViewModel!!.hora.value = "00"
                binding.txt15.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "15"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt15.setTextColor(Color.parseColor("#FFFFFF"))
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }

        binding.txt16.setOnClickListener {
            if (binding.txt16.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt16.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt16.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "16"
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt17.isEnabled) {
                    binding.txt17.setTextColor(Color.parseColor("#000000"))
                    binding.txt17.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
        binding.txt17.setOnClickListener {
            if (binding.txt17.currentTextColor == Color.parseColor("#FFFFFF")) {
                it.background = resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                binding.txt17.setTextColor(Color.parseColor("#000000"))
                agendarSesionViewModel!!.hora.value = "00"
            } else {
                it.background = resources.getDrawable(R.drawable.purple_rounded_shape)
                binding.txt17.setTextColor(Color.parseColor("#FFFFFF"))
                agendarSesionViewModel!!.hora.value = "17"
                if (binding.txt9.isEnabled) {
                    binding.txt9.setTextColor(Color.parseColor("#000000"))
                    binding.txt9.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt11.isEnabled) {
                    binding.txt11.setTextColor(Color.parseColor("#000000"))
                    binding.txt11.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt12.isEnabled) {
                    binding.txt12.setTextColor(Color.parseColor("#000000"))
                    binding.txt12.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt13.isEnabled) {
                    binding.txt13.setTextColor(Color.parseColor("#000000"))
                    binding.txt13.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt14.isEnabled) {
                    binding.txt14.setTextColor(Color.parseColor("#000000"))
                    binding.txt14.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt15.isEnabled) {
                    binding.txt15.setTextColor(Color.parseColor("#000000"))
                    binding.txt15.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt16.isEnabled) {
                    binding.txt16.setTextColor(Color.parseColor("#000000"))
                    binding.txt16.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
                if (binding.txt10.isEnabled) {
                    binding.txt10.setTextColor(Color.parseColor("#000000"))
                    binding.txt10.background =
                        resources.getDrawable(R.drawable.white_rounded_shape_purple_stroke)
                }
            }
        }
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as IBackToHub?)!!.backToHubFragment()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}
