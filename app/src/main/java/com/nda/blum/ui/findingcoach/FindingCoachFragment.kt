package com.nda.blum.ui.findingcoach

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.FindingCoachFragmentBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FindingCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachFragment: FindingCoachFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.finding_coach_fragment, container, false)

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        val imageViewTarget = bindingCoachFragment.imgLoadingGif
        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        Glide.with(this).load(R.drawable.loading_blum)
            .into(imageViewTarget)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FindingCoachViewModelFactory(application)
        val hubViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FindingCoachViewModel::class.java)

        hubViewModel.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        hubViewModel.sendUserToAsignarCoachService()

        hubViewModel.onCoachFinded.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(FindingCoachFragmentDirections.actionFindingCoachFragmentToCoachResultFragment())
            }
        })
        return bindingCoachFragment.root
    }

}
