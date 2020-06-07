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
import com.nda.blum.databinding.Quizz2FragmentBinding


class Quizz2Fragment : Fragment() {

    companion object {
        fun newInstance() = Quizz2Fragment()
    }

    private lateinit var viewModel: Quizz2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindingQuizz2: Quizz2FragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quizz2__fragment, container, false
        )

        bindingQuizz2.customProgress.progress = 17

        Glide.with(this)
            .load(R.drawable.user_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz2.userProfilePic)

        bindingQuizz2.button8.setOnClickListener {
            this.findNavController().navigate(Quizz2FragmentDirections.actionQuizz2FragmentToQuizz3Fragment())
        }

        return bindingQuizz2.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Quizz2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
