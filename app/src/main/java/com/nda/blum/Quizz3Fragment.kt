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
import com.nda.blum.databinding.Quizz3FragmentBinding


class Quizz3Fragment : Fragment() {

    companion object {
        fun newInstance() = Quizz3Fragment()
    }

    private lateinit var viewModel: Quizz3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingQuizz3: Quizz3FragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quizz3_fragment, container, false
        )

        bindingQuizz3.customProgress.progress = 34

        Glide.with(this)
            .load(R.drawable.user_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz3.userProfilePic)

        bindingQuizz3.button8.setOnClickListener {
            this.findNavController().navigate(Quizz3FragmentDirections.actionQuizz3FragmentToQuizz4Fragment())
        }

        return bindingQuizz3.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Quizz3ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
