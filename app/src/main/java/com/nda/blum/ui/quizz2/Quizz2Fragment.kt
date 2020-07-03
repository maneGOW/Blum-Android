package com.nda.blum.ui.quizz2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jcloquell.androidsecurestorage.SecureStorage
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
            inflater, R.layout.quizz2_fragment, container, false
        )

        bindingQuizz2.customProgress.progress = 25

        val secureStorage = SecureStorage(this.activity!!.applicationContext)
        val urlProfilePic = secureStorage.getObject("userProfilePicture", String::class.java)
        val userName = secureStorage.getObject("nombreUsuario", String::class.java)

        bindingQuizz2.textView7.text = userName

        Glide.with(this)
            .load(urlProfilePic)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz2.userProfilePic)

        bindingQuizz2.button8.setOnClickListener {
            this.findNavController()
                .navigate(Quizz2FragmentDirections.actionQuizz2FragmentToQuizz3Fragment())
        }

        val question6Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta6)
        question6Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz2.spinnerQuestion6.adapter = question6Adapter

        val question7Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta7)
        question7Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz2.spinnerQuestion7.adapter = question7Adapter

        val question8Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta8)
        question8Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz2.spinnerQuestion8.adapter = question8Adapter

        val question9Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta9)
        question9Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz2.spinnerQuestion9.adapter = question9Adapter

        return bindingQuizz2.root
    }

}
