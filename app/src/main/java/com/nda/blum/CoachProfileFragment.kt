package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.databinding.CoachProfileFragmentBinding


class CoachProfileFragment : Fragment() {

    companion object {
        fun newInstance() = CoachProfileFragment()
    }

    private lateinit var viewModel: CoachProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachProfile: CoachProfileFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.coach_profile_fragment, container, false)


        Glide.with(this)
            .load(R.drawable.profilepic_sample)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingCoachProfile.imgCoachPic)

        return bindingCoachProfile.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CoachProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
