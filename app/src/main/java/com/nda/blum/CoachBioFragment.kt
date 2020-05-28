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
import com.nda.blum.databinding.CoachBioFragmentBinding


class CoachBioFragment : Fragment() {

    companion object {
        fun newInstance() = CoachBioFragment()
    }

    private lateinit var viewModel: CoachBioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingCoachResult:  CoachBioFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.coach_bio_fragment, container, false)

        Glide.with(this)
            .load(R.drawable.profilepic_sample)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingCoachResult.imgCoachPic)
        return bindingCoachResult.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CoachBioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
