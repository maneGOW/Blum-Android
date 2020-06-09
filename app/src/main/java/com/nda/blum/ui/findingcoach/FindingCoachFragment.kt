package com.nda.blum.ui.findingcoach

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
import com.nda.blum.db.BlumDatabase
import com.nda.blum.db.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.withContext
import kotlin.random.Random

@InternalCoroutinesApi
class FindingCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachFragment: FindingCoachFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.finding_coach_fragment, container, false)

        val imageViewTarget = bindingCoachFragment.imgLoadingGif
        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        Glide.with(this).load(R.drawable.loading_blum)
            .into(imageViewTarget)

        val application = requireNotNull(this.activity).application
        val dataSource = BlumDatabase.getInstance(application).userDao()
        val viewModelFactory = FindingCoachViewModelFactory(dataSource, application)
        val hubViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FindingCoachViewModel::class.java)

        hubViewModel.nRespuesta.value = (1..3).random().toString()

        hubViewModel.sendUserToAsignarCoachService()

        hubViewModel.onCoachFinded.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(FindingCoachFragmentDirections.actionFindingCoachFragmentToCoachResultFragment())
            }
        })
        return bindingCoachFragment.root
    }

}
