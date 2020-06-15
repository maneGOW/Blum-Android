package com.nda.blum.ui.coachresult

import android.app.ProgressDialog
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
import com.nda.blum.databinding.CoachResultFragmentBinding

class CoachResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachResult: CoachResultFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.coach_result_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        val viewModelFactory = CoachResultViewModelFactory(application)
        val chatWithCoachViewmodel =
            ViewModelProviders.of(this, viewModelFactory).get(CoachResultViewModel::class.java)

        chatWithCoachViewmodel.coachName.observe(viewLifecycleOwner, Observer {
            if (it.isNotBlank() && it.isNotEmpty()) {
                bindingCoachResult.textView28.text = it
            }
        })

        chatWithCoachViewmodel.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        chatWithCoachViewmodel.coachProfilePicture.observe(viewLifecycleOwner, Observer {
            if (it.isNotBlank() && it.isNotEmpty()) {
                val imageViewTarget = bindingCoachResult.profilePic
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageViewTarget)
            }
        })

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        bindingCoachResult.button7.setOnClickListener {
            this.findNavController()
                .navigate(CoachResultFragmentDirections.actionCoachResultFragmentToCoachProfileFragment())
        }
        return bindingCoachResult.root
    }
}
