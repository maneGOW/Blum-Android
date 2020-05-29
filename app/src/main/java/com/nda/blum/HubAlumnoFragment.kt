package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class HubAlumnoFragment : Fragment() {

    companion object {
        fun newInstance() = HubAlumnoFragment()
    }

    private lateinit var viewModel: HubAlumnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hub_alumno_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HubAlumnoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
