package com.nda.blum.ui.quizz4

import androidx.lifecycle.ViewModelProviders
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
import com.nda.blum.databinding.Quizz4FragmentBinding

class Quizz4Fragment : Fragment() {

    companion object {
        fun newInstance() = Quizz4Fragment()
    }

    private lateinit var viewModel: Quizz4ViewModel

    val arrayQuestions11 = arrayOf("Exploto, le grito, lo amenazo y luego pido perdón.",
    "Me quedo callada, prefiero eso a pelear.",
    "Me salgo de la casa.",
    "Le echo la culpa y lo sermoneo.",
    "Todas las anteriores.")

    val arrayQuestions12 = arrayOf("Siento que soy una parte importantísima en mi familia.",
        "Amo lo que hago, me siento como pez en el agua en mi trabajo.",
        "Me gusta mucho ser yo, me amo y soy capaz de amar así a los demás.",
        "Todas las anteriores",
        "Ninguna de las anteriores.")

    val arrayQuestions13 = arrayOf("Sin problema, todo sale generalmente bien.",
        "Todo es un caos, nada sale bien.",
        "A veces bien, en otros momentos es un caos.")

    val arrayQuestions14 = arrayOf("Nunca",
        "La mitad de los días",
        "Algunos días",
        "Casi todos los días")

    val arrayQuestions15 = arrayOf("Nunca",
        "La mitad de los días",
        "Algunos días",
        "Casi todos los días")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingQuizz4: Quizz4FragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quizz4_fragment, container, false
        )

        bindingQuizz4.customProgress.progress = 75

        val secureStorage = SecureStorage(this.activity!!.applicationContext)
        val urlProfilePic = secureStorage.getObject("userProfilePicture", String::class.java)

        Glide.with(this)
            .load(urlProfilePic)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz4.userProfilePic)

        val userName = secureStorage.getObject("nombreUsuario", String::class.java)

        bindingQuizz4.textView7.text = userName

        val question11Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, arrayQuestions11)
        question11Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz4.spinnerQuestion11.adapter = question11Adapter

        val question12Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, arrayQuestions12)
        question12Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz4.spinnerQuestion12.adapter = question12Adapter

        val question13Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, arrayQuestions13)
        question13Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz4.spinnerQuestion13.adapter = question13Adapter

        val question14Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, arrayQuestions14)
        question14Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz4.spinnerQuestion14.adapter = question14Adapter

        val question15Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, arrayQuestions15)
        question15Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz4.spinnerQuestion15.adapter = question15Adapter

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
