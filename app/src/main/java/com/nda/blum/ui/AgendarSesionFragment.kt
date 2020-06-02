package com.nda.blum.ui

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

        bindingAgendarSesion.imgAgendarSesionBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        return bindingAgendarSesion.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AgendarSesionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
