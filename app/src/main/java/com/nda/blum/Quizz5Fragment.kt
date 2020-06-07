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
import com.nda.blum.databinding.Quizz5FragmentBinding


class Quizz5Fragment : Fragment() {

    companion object {
        fun newInstance() = Quizz5Fragment()
    }

    private lateinit var viewModel: Quizz5ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingQuizz5: Quizz5FragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quizz5_fragment, container, false
        )

        bindingQuizz5.customProgress.progress = 68

        Glide.with(this)
            .load(R.drawable.user_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz5.userProfilePic)

        bindingQuizz5.button8.setOnClickListener {
            this.findNavController().navigate(Quizz5FragmentDirections.actionQuizz5FragmentToQuizz6Fragment())
        }

        return bindingQuizz5.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Quizz5ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
