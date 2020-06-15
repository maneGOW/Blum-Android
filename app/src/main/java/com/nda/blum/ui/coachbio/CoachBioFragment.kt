package com.nda.blum.ui.coachbio

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.CoachBioFragmentBinding

class CoachBioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachResult:  CoachBioFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.coach_bio_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        val viewModelFactory = CoachBioViewModelFactory(application)
        val coachBioViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CoachBioViewModel::class.java)

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        coachBioViewModel.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        coachBioViewModel.coachCedula.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                bindingCoachResult.textView29.text = it
            }
        })

        coachBioViewModel.coachLanguages.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                bindingCoachResult.textView31.text = it
            }
        })

        coachBioViewModel.coachLocation.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                bindingCoachResult.textView30.text = it
            }
        })

        coachBioViewModel.coachName.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                bindingCoachResult.textView9.text = it

            }
        })

        coachBioViewModel.coachProfile.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                bindingCoachResult.textView27.text = it
            }
        })

        coachBioViewModel.coachProfilePicture.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingCoachResult.imgCoachPic)
            }
        })

        coachBioViewModel.coachResume.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank() && it.isNotEmpty()){
                bindingCoachResult.textView32.text = it
            }
        })

        bindingCoachResult.btnSetDate.setOnClickListener {
            this.findNavController().navigate(CoachBioFragmentDirections.actionCoachBioFragmentToHubAlumnoFragment())
        }
        return bindingCoachResult.root
    }
}
