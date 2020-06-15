package com.nda.blum.ui.coachprofile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.CoachProfileFragmentBinding
import kotlinx.android.synthetic.main.coach_profile_fragment.*


class CoachProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachProfile: CoachProfileFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.coach_profile_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = CoachProfileViewModelFactory(application)
        val coachProfileViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CoachProfileViewModel::class.java)

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        coachProfileViewModel.coachName.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty() && it.isNotBlank()){
                bindingCoachProfile.txtCoachName.text = it
            }
        })

        coachProfileViewModel.coachProfilePicture.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty() && it.isNotBlank()){
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingCoachProfile.imgCoachPic)
            }
        })

        coachProfileViewModel.coachProfile.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty() && it.isNotBlank()){
                txtCoachProfile.text = it
            }
        })

        bindingCoachProfile.btnShowCoachData.setOnClickListener {
            this.findNavController().navigate(CoachProfileFragmentDirections.actionCoachProfileFragmentToCoachBioFragment())
        }

        bindingCoachProfile.layoutGetSession.setOnClickListener {
            this.findNavController().navigate(CoachProfileFragmentDirections.actionCoachProfileFragmentToHubAlumnoFragment())
        }

        return bindingCoachProfile.root
    }

}
