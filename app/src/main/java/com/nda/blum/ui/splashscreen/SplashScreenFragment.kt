package com.nda.blum.ui.splashscreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nda.blum.R
import com.nda.blum.databinding.SplashScreenFragmentBinding


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingSplashScreen: SplashScreenFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.splash_screen_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = SplashScreenViewModelFactory(application)
        val splashScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        bindingSplashScreen.lifecycleOwner = this

        return bindingSplashScreen.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            context?.let{
                this.findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
            }
        }, 2500)
    }

}
