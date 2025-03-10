package com.nda.blum.ui.hub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.HubAlumnoFragmentBinding
import com.nda.blum.db.BlumDatabase
import kotlinx.android.synthetic.main.hub_alumno_fragment.view.*
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
        val dataSource = BlumDatabase.getInstance(application).userDao()
        val viewModelFactory = HubAlumnoViewModelFactory(dataSource, application)
        val hubViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HubAlumnoViewModel::class.java)

        binding.lifecycleOwner = this

        hubViewModel.setUserName()

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        hubViewModel.userName.observe(viewLifecycleOwner, Observer {
            binding.txtWelcomeText.text = "Bienvenido(a) ${hubViewModel.userName.value}"
        })

        hubViewModel.userRol.observe(viewLifecycleOwner, Observer {
            if (it == "Coach") {
                binding.txtSesion.text = "MIS SESIONES"
                binding.txtTitleCoach.text = "MIS BLUMMERS"
                binding.txtDescriptionCoach.text = "MIS NIDOS"
                binding.btnAgendarSesion.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToSesionersCoachFragment())
                }

                binding.btnRecursos.setOnClickListener {
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToRecursosCoachFragment())
                }

                binding.btnChatCoach.setOnClickListener{
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToBlummersCoachFragment())
                }

                binding.btnNotificaciones.setOnClickListener {
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToNotificacionesCoachFragment())
                }

                binding.btnChatNido.setOnClickListener {
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToNidosCoachFragment())
                }

            } else {
                binding.btnAgendarSesion.setOnClickListener {
                    this.findNavController()
                        .navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToAgendarSesionFragment())
                }

                binding.btnRecursos.setOnClickListener {
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToRecursosCoachFragment())
                }

                binding.btnChatCoach.setOnClickListener{
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToChatWithCoachFragment())
                }

                binding.btnNotificaciones.setOnClickListener {
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToNotificacionesCoachFragment())
                }

                binding.btnChatNido.setOnClickListener {
                    this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToChatWithCoachFragment())
                }
            }
        })

        return binding.root
    }

}
