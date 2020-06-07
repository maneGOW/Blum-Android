package com.nda.blum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nda.blum.databinding.Quizz7FragmentBinding


class Quizz7Fragment : Fragment() {

    companion object {
        fun newInstance() = Quizz7Fragment()
    }

    val dayName =
        arrayOf(
            "Lunes",
            "Martes",
            "Miercoles",
            "Jueves",
            "Viernes"
        )

    val hourTime =
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

    private lateinit var viewModel: Quizz7ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindingQuizz7: Quizz7FragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.quizz7_fragment, container, false)

        val adapterDays = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, dayName)

        bindingQuizz7.customProgress.progress = 100

        adapterDays.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        bindingQuizz7.spinnerDays.adapter = adapterDays

        bindingQuizz7.button8.setOnClickListener {
            this.findNavController().navigate(Quizz7FragmentDirections.actionQuizz7FragmentToFindingCoachFragment())
        }


        val adapterHours = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, hourTime)

        adapterHours.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        bindingQuizz7.spinnerHours.adapter = adapterHours

        return bindingQuizz7.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Quizz7ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
