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
import com.nda.blum.databinding.Quizz6FragmentBinding


class Quizz6Fragment : Fragment() {

    companion object {
        fun newInstance() = Quizz6Fragment()
    }

    private lateinit var viewModel: Quizz6ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingQuizz6: Quizz6FragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quizz6_fragment, container, false
        )

        bindingQuizz6.customProgress.progress = 85

        Glide.with(this)
            .load(R.drawable.user_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz6.userProfilePic)

        bindingQuizz6.button8.setOnClickListener {
            this.findNavController().navigate(Quizz6FragmentDirections.actionQuizz6FragmentToQuizz7Fragment())
        }

        return bindingQuizz6.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Quizz6ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
