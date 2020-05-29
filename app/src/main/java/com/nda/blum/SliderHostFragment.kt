package com.nda.blum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.nda.blum.databinding.SliderHostFragmentBinding

class SliderHostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingSliderHost:  SliderHostFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.slider_host_fragment, container, false)

        return bindingSliderHost.root
    }

}
