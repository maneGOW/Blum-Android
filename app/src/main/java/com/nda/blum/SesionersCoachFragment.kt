package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView


class SesionersCoachFragment : Fragment() {

    companion object {
        fun newInstance() = SesionersCoachFragment()
    }

    private lateinit var viewModel: SesionersCoachViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.VISIBLE
        return inflater.inflate(R.layout.sesioners_coach_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SesionersCoachViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
