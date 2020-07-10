package com.nda.blum.ui.hub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.HubAlumnoFragmentBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class HubAlumnoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: HubAlumnoFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.hub_alumno_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = HubAlumnoViewModelFactory(application)
        val hubViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HubAlumnoViewModel::class.java)

        binding.lifecycleOwner = this

        hubViewModel.setUserName()

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        hubViewModel.userName.observe(viewLifecycleOwner, Observer {
            binding.txtWelcomeText.text = "Bienvenido(a) ${hubViewModel.userName.value}"
        })

        hubViewModel.userProfilePicture.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()){
                Glide.with(this.requireContext())
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgProfile)
            }else{
                Glide.with(this.requireContext())
                    .load(R.drawable.profile_imagen)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgProfile)
            }
        })

        /*    binding.btnCerrarSesion.setOnClickListener {
                val builder = AlertDialog.Builder(this.context!!)
                builder.setTitle("Alerta")
                builder.setMessage("¿Deseas cerrar tu sesión de BLUM?")
                builder.setPositiveButton("Sí") { _, _ ->
                    cerrarSesion()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
            }*/

        hubViewModel.userRol.observe(viewLifecycleOwner, Observer { it ->
            if (it == "Coach") {
                binding.txtSesion.text = "MIS SESIONES"
                binding.txtTitleCoach.text = "MIS BLUMMERS"
                binding.txtDescriptionCoach.text = "MIS NIDOS"
                binding.btnAgendarSesion.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToSesionersCoachFragment())
                }

                binding.btnRecursos.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToRecursosCoachFragment())
                }

                binding.btnChatCoach.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToBlummersCoachFragment())
                }

                binding.btnNotificaciones.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToNotificacionesCoachFragment())
                }

                binding.btnChatNido.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToNidosCoachFragment())
                }
                binding.btnEditProfile.setOnClickListener {
                    println("BOTON presionado")
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToUserProfile())
                }
            } else {
                binding.btnAgendarSesion.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToAgendarSesionFragment())
                }

                binding.btnRecursos.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToRecursosCoachFragment())
                }

                binding.btnChatCoach.setOnClickListener {
                    this.findNavController().navigate(
                        HubAlumnoFragmentDirections.actionHubAlumnoFragmentToChatWithCoachFragment(
                            "userCoach",
                            "",
                            "",
                            ""
                        )
                    )
                }

                binding.btnNotificaciones.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToNotificacionesCoachFragment())
                }

                binding.btnChatNido.setOnClickListener {
                    this.findNavController().navigate(
                        HubAlumnoFragmentDirections.actionHubAlumnoFragmentToChatWithCoachFragment(
                            "userNest",
                            "",
                            "",
                            ""
                        )
                    )
                }

                binding.btnEditProfile.setOnClickListener {
                    println("BOTON presionado")
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToUserProfile())
                }
            }
        })

        return binding.root
    }

    private fun cerrarSesion() {
        println("SE CERRÓ LA SESIÓN")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    createExitDialog()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun createExitDialog() {
        val builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Alerta")
        builder.setMessage("¿Deseas salir de BLUM")
        builder.setPositiveButton("Sí") { _, _ ->
            this.activity!!.finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
