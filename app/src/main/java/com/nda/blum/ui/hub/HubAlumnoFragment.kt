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
import com.nda.blum.R
import com.nda.blum.databinding.HubAlumnoFragmentBinding
import com.nda.blum.db.BlumDatabase
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
        val viewModelFactory = HubAlumnoViewModelFactory(dataSource,application)
        val hubViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HubAlumnoViewModel::class.java)

        binding.lifecycleOwner = this

        hubViewModel.setUserName()

        binding.btnAgendarSesion.setOnClickListener {
            this.findNavController().navigate(HubAlumnoFragmentDirections.actionHubAlumnoFragmentToAgendarSesionFragment())
        }

        hubViewModel.userName.observe(viewLifecycleOwner, Observer {
            binding.txtWelcomeText.text = "Bienvenid@ ${hubViewModel.userName.value}"
        })

        return binding.root
    }

}
