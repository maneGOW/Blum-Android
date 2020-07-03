package com.nda.blum.ui.nidoscoachfragment

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
import com.nda.blum.R
import com.nda.blum.adapters.NidosAlumnosAdapter
import com.nda.blum.adapters.NidosListener
import com.nda.blum.databinding.NidosCoachFragmentBinding


class NidosCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val nidosCoachBinding: NidosCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.nidos_coach_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            NidosCoachViewModelFactory(application)

        val nidosCoachViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NidosCoachViewModel::class.java)

        nidosCoachViewModel.navigatetoNidoChat.observe(viewLifecycleOwner, Observer { nido ->
            nido?.let {
                this.findNavController().navigate(
                    NidosCoachFragmentDirections.actionNidosCoachFragmentToChatWithCoachFragment(
                        "", nido, "Prueba de titulo", ""
                    )
                )
                nidosCoachViewModel.onNidoNavigated()
            }
        })

        nidosCoachViewModel.obtenidoNidosList.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (nidosCoachViewModel.nidosList.value != null) {

                    nidosCoachBinding.rvAlumnos.layoutManager = LinearLayoutManager(this.context)
                    val manager = GridLayoutManager(activity, 1)
                    nidosCoachBinding.rvAlumnos.layoutManager = manager
                    val adapter = NidosAlumnosAdapter(
                        this.requireContext(),
                        nidosCoachViewModel.nidosList.value!!,
                        NidosListener { nidoId ->
                            nidosCoachViewModel.onNidoClicked(nidoId)
                        }, this)
                    adapter.addHeaderAndSubmitList(nidosCoachViewModel.nidosList.value!!.result)
                    nidosCoachBinding.rvAlumnos.adapter = adapter
                }
            }
        })

        nidosCoachBinding.imgBackNidos.setOnClickListener {
            this.findNavController().popBackStack()
        }

        return nidosCoachBinding.root
    }
}
