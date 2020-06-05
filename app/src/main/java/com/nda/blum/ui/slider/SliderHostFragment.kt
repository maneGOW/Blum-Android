package com.nda.blum.ui.slider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nda.blum.R
import com.nda.blum.adapters.CoachSliderAdapter
import com.nda.blum.adapters.UserSliderAdapter
import com.nda.blum.databinding.SliderHostFragmentBinding

class SliderHostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: SliderHostFragmentArgs by navArgs()

        val bindingSliderHost:  SliderHostFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.slider_host_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = SliderHostViewModelFactory(application)
        val sliderHostViewModel = ViewModelProviders.of(this, viewModelFactory).get(SliderHostViewModel::class.java)

        bindingSliderHost.lifecycleOwner = this

        if(args.fromFragment == "FelicidadesCoachFragment"){
            bindingSliderHost.viewPager2.adapter = CoachSliderAdapter(this.findNavController(), SliderHostFragmentDirections.actionSliderHostFragmentToHubAlumnoFragment())
        }else{
            bindingSliderHost.viewPager2.adapter = UserSliderAdapter(this.findNavController(), SliderHostFragmentDirections.actionSliderHostFragmentToQuizzFragment())
        }

        return bindingSliderHost.root

    }

}
