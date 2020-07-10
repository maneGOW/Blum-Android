package com.nda.blum.ui.bluumerscoach

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.adapters.AlumnosAdapter
import com.nda.blum.adapters.NidosListener
import com.nda.blum.databinding.BlummersCoachFragmentBinding
import com.nda.blum.databinding.NidosCoachFragmentBinding
import com.nda.blum.ui.nidoscoachfragment.NidosCoachViewModel
import com.nda.blum.ui.nidoscoachfragment.NidosCoachViewModelFactory

class BlummersCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE
        val blummersCoachBinding: BlummersCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.blummers_coach_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            BlummersCoachViewModelFactory(application)

        val blummersCoachViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(BlummersCoachViewModel::class.java)

        blummersCoachViewModel.obtenidoAlumnosList.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (blummersCoachViewModel.alumnosList.value != null) {
                    blummersCoachBinding.rvAlumnosCoach.layoutManager = LinearLayoutManager(this.context)
                    val manager = GridLayoutManager(activity, 1)
                    blummersCoachBinding.rvAlumnosCoach.layoutManager = manager
                    val adapter = AlumnosAdapter(
                        this.requireContext(),
                        blummersCoachViewModel.alumnosList.value!!,
                        NidosListener { nidoId ->
                            blummersCoachViewModel.onAlumnoClicked(nidoId)
                        }, this)
                    adapter.addHeaderAndSubmitList(blummersCoachViewModel.alumnosList.value!!.result)
                    blummersCoachBinding.rvAlumnosCoach.adapter = adapter
                }
            }
        })

        blummersCoachBinding.imgBlummersBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return blummersCoachBinding.root
    }

}
