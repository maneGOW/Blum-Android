package com.nda.blum.ui.quizz2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.R
import com.nda.blum.databinding.Quizz2FragmentBinding

class Quizz2Fragment : Fragment() {

    private val pregunta6 =
        arrayOf(
            "Sí",
            "No"
        )

    private val pregunta7 =
        arrayOf(
            "Muy buena",
            "Buena",
            "Regular",
            "Mala"
        )

    private val pregunta8 =
        arrayOf(
            "Muy buena",
            "Buena",
            "Regular",
            "Mala"
        )

    private val pregunta9 =
        arrayOf(
            "Sí",
            "No"
        )

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
            this.findNavController()
                .navigate(Quizz2FragmentDirections.actionQuizz2FragmentToQuizz3Fragment())
        }

        return bindingQuizz2.root
    }

}
