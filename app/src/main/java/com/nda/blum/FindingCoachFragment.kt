package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nda.blum.databinding.FindingCoachFragmentBinding


class FindingCoachFragment : Fragment() {

    companion object {
        fun newInstance() = FindingCoachFragment()
    }

    private lateinit var viewModel: FindingCoachViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachFragment: FindingCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.finding_coach_fragment, container, false)

        val imageViewTarget = bindingCoachFragment.imgLoadingGif

        Glide.with(this).load(R.drawable.loading_nda)
            .into(imageViewTarget)

        return bindingCoachFragment.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FindingCoachViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
