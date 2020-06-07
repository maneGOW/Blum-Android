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
import com.nda.blum.databinding.Quizz4FragmentBinding


class Quizz4Fragment : Fragment() {

    companion object {
        fun newInstance() = Quizz4Fragment()
    }

    private lateinit var viewModel: Quizz4ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingQuizz4: Quizz4FragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quizz4_fragment, container, false
        )

        bindingQuizz4.customProgress.progress = 51

        Glide.with(this)
            .load(R.drawable.user_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz4.userProfilePic)

        bindingQuizz4.button8.setOnClickListener {
            this.findNavController().navigate(Quizz4FragmentDirections.actionQuizz4FragmentToQuizz5Fragment())
        }

        return bindingQuizz4.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Quizz4ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
