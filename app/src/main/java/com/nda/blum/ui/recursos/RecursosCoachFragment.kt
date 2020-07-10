package com.nda.blum.ui.recursos

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.adapters.NidosAlumnosAdapter
import com.nda.blum.adapters.NidosListener
import com.nda.blum.databinding.RecuperarPasswordFragmentBinding
import com.nda.blum.databinding.RecursosCoachFragmentBinding
import com.nda.blum.ui.recoverpassword.RecuperarPasswordViewModel
import com.nda.blum.ui.recoverpassword.RecuperarPasswordViewModelFactory


class RecursosCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingRecursosCoach: RecursosCoachFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.recursos_coach_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecursosCoachViewModelFactory(application)
        val recursosCoachViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            RecursosCoachViewModel::class.java
        )

        bindingRecursosCoach.lifecycleOwner = this

        recursosCoachViewModel.userProfilePicture.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()){
                Glide.with(this.requireContext())
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingRecursosCoach.userProfilePicResources)
            }else{
                Glide.with(this.requireContext())
                    .load(R.drawable.profile_imagen)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingRecursosCoach.userProfilePicResources)
            }
        })

     /*   recursosCoachViewModel.recursosListLiveData.observe(viewLifecycleOwner, Observer {
            if(it.size >= 0){
                bindingRecursosCoach..layoutManager = LinearLayoutManager(this.context)
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
        })*/

        bindingRecursosCoach.layoutAudios.setOnClickListener {
            this.findNavController().navigate(
                RecursosCoachFragmentDirections.actionRecursosCoachFragmentToListaRecursosFragment(
                    "audio"
                )
            )
        }

        bindingRecursosCoach.layoutPDF.setOnClickListener {
            this.findNavController().navigate(
                RecursosCoachFragmentDirections.actionRecursosCoachFragmentToListaRecursosFragment(
                    "pdf"
                )
            )
        }


        bindingRecursosCoach.imgResoursesBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        return bindingRecursosCoach.root
    }
}
