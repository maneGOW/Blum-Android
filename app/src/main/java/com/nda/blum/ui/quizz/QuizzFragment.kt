package com.nda.blum.ui.quizz

import android.graphics.Color
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.R
import com.nda.blum.databinding.QuizzFragmentBinding
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
class QuizzFragment : Fragment() {

    private val pregunta1 =
        arrayOf(
            "Hombre",
            "Mujer"
        )

    private val pregunta2 =
        arrayOf(
            "18-25 años",
            "25-35 años",
            "36-45 años",
            "46-55 años",
            "55+ años"
        )

    private val pregunta3 =
        arrayOf(
            "Sí",
            "No"
        )

    private val pregunta4 =
        arrayOf(
            "Cristiana",
            "Católica",
            "Judía",
            "Otra",
            "Prefiero no decirlo"
        )

    private val pregunta5 =
        arrayOf(
            "Sí",
            "No necesariamente"
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingQuizz: QuizzFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.quizz_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            QuizzViewModelFactory(application)
        val quizzViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(QuizzViewModel::class.java)

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        val secureStorage = SecureStorage(this.activity!!.applicationContext)
        val urlProfilePic = secureStorage.getObject("userProfilePicture", String::class.java)

        Glide.with(this)
            .load(urlProfilePic)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz.userProfilePic)

        val question1Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta1)
        question1Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz.spinnerQuestion1.adapter = question1Adapter

        val question2Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta2)
        question2Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz.spinnerQuestion2.adapter = question2Adapter

        val question3Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta3)
        question3Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz.spinnerQuestion3.adapter = question3Adapter

        val question4Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta4)
        question4Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz.spinnerQuestion4.adapter = question4Adapter

        val question5Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, pregunta5)
        question5Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz.spinnerQuestion5.adapter = question5Adapter

        bindingQuizz.button8.setOnClickListener {
            this.findNavController()
                .navigate(QuizzFragmentDirections.actionQuizzFragmentToQuizz2Fragment())
        }

        return bindingQuizz.root
    }

}
