package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.databinding.CoachResultFragmentBinding


class CoachResultFragment : Fragment() {

    companion object {
        fun newInstance() = CoachResultFragment()
    }

    private lateinit var viewModel: CoachResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachResult: CoachResultFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.coach_result_fragment, container, false)

        val imageViewTarget = bindingCoachResult.profilePic

        Glide.with(this)
            .load(R.drawable.profilepic_sample)
            .apply(RequestOptions.circleCropTransform())
            .into(imageViewTarget)

        bindingCoachResult.button7.setOnClickListener {
            this.findNavController().navigate(CoachResultFragmentDirections.actionCoachResultFragmentToCoachProfileFragment())
        }
        return bindingCoachResult.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CoachResultViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
