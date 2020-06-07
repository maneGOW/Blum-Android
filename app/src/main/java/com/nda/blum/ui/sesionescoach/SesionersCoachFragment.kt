package com.nda.blum.ui.sesionescoach

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.adapters.SesionesCoachAdapter
import com.nda.blum.databinding.SesionersCoachFragmentBinding
import com.nda.blum.db.BlumDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SesionersCoachFragment : Fragment() {

    companion object {
        fun newInstance() =
            SesionersCoachFragment()
    }

    private lateinit var viewModel: SesionersCoachViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.VISIBLE

        val bindingLogin: SesionersCoachFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.sesioners_coach_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = BlumDatabase.getInstance(application).userDao()

        val viewModelFactory =
            SesionesCoachViewModelFactory(application,dataSource)
        val sesionesCoachViewModel = ViewModelProviders.of(this,viewModelFactory).get(SesionersCoachViewModel::class.java)

        bindingLogin.lifecycleOwner = this

        bindingLogin.imgAgendarSesionBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        sesionesCoachViewModel.filledCitasDelServer.observe(viewLifecycleOwner, Observer {
            if(it){
                bindingLogin.rvCitas.layoutManager = LinearLayoutManager(this.context)
                bindingLogin.rvCitas.adapter = SesionesCoachAdapter(this.context!!, sesionesCoachViewModel.citasDelServer.value!!)
            }
        })

        return bindingLogin.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SesionersCoachViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
