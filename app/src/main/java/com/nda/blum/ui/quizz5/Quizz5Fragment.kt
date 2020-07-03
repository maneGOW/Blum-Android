package com.nda.blum.ui.quizz5

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
import com.nda.blum.databinding.Quizz5FragmentBinding


class Quizz5Fragment : Fragment() {

    val question17 = arrayOf("Sí",
        "No"
    )

    val question18 = arrayOf("Sí",
        "No"
    )

    val question19 =
        arrayOf(
            "Lunes",
            "Martes",
            "Miercoles",
            "Jueves",
            "Viernes"
        )

    val question20 =
        arrayOf(
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00"
        )

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

        bindingQuizz5.customProgress.progress = 100

        val secureStorage = SecureStorage(this.activity!!.applicationContext)
        val urlProfilePic = secureStorage.getObject("userProfilePicture", String::class.java)

        Glide.with(this)
            .load(urlProfilePic)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz5.userProfilePic)

        val userName = secureStorage.getObject("nombreUsuario", String::class.java)

        bindingQuizz5.textView7.text = userName

        val question16Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, question17)
        question16Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz5.spinner6.adapter = question16Adapter

        val question17Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, question18)
        question17Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz5.spinner7.adapter = question17Adapter

        val question18Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, question19)
        question18Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz5.spinner8.adapter = question18Adapter

        val question19Adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, question20)
        question19Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        bindingQuizz5.spinner9.adapter = question19Adapter

        bindingQuizz5.button8.setOnClickListener {
            this.findNavController().navigate(Quizz5FragmentDirections.actionQuizz5FragmentToFindingCoachFragment())
        }

        return bindingQuizz5.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Quizz5ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
