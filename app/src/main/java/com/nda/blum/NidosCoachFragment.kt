package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class NidosCoachFragment : Fragment() {

    companion object {
        fun newInstance() = NidosCoachFragment()
    }

    private lateinit var viewModel: NidosCoachViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.nidos_coach_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NidosCoachViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
