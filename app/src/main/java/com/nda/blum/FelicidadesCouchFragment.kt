package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nda.blum.databinding.FelicidadesCouchFragmentBinding


class FelicidadesCouchFragment : Fragment() {

    companion object {
        fun newInstance() = FelicidadesCouchFragment()
    }

    private lateinit var viewModel: FelicidadesCouchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingFelicidadesCoach : FelicidadesCouchFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.felicidades_couch_fragment, container, false
        )

        bindingFelicidadesCoach.button9.setOnClickListener {
            this.findNavController().navigate(FelicidadesCouchFragmentDirections.actionFelicidadesCouchFragmentToSliderHostFragment("FelicidadesCoachFragment"))
        }

        return bindingFelicidadesCoach.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FelicidadesCouchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
