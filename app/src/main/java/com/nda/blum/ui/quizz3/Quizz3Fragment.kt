package com.nda.blum.ui.quizz3

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.R
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
            this.findNavController()
               .navigate(Quizz3FragmentDirections.actionQuizz3FragmentToQuizz4Fragment())
            //showDialog(16)
        }

        return bindingQuizz3.root
    }

    private fun showDialog(question: Int) {
        lateinit var dialog: AlertDialog

        var arrayQuestions: Array<String>? = null
        var arrayChecked: BooleanArray? = null

        if(question == 12){
            arrayQuestions = arrayOf("0 - 7 años",
                "8 - 14 años",
                "15 - 21 años",
                "más de 21 años")
            arrayChecked = booleanArrayOf(false, false, false, false)
        }
        if(question == 13){
            arrayQuestions = arrayOf("Mi hijo no duerme.",
                    "Mi hijo tiene pesadillas.",
                    "Mi hijo hace berrinche por todo.",
                    "Mis hijos pelean todo el tiempo.",
                    "Mi hijo se hace pipi/popo.",
                    "Mi hijo no quiere dejar de dormir conmigo.",
                    "Mi hijo no quiere hacer tareas.",
                    "Mi esposo y yo implementamos diferentes tipo de crianza.")
            arrayChecked = booleanArrayOf(false, false, false, false, false, false, false, false)
        }
        if(question == 14){
            arrayQuestions = arrayOf("¿Cuáles son los retos que tienes frente a tí en este momento?",
                    "Mi hijo está triste, pensativo, nostálgico la mayor parte del tiempo.",
                    "Mi hijo pelea con su hermano todo el tiempo.",
                    "Mi hijo no quiere hacer lo que le toca en casa.",
                    "Mi hijo no quiere ir a la escuela.",
                    "Mi hijo solo quiere estar en la tablet.",
                    "Mi hijo está teniendo un despertar sexual prematuro.",
                    "Mi hijo me ignora todo el tiempo.",
                    "Mi esposo y yo implementamos diferentes estilos de crianza.")
            arrayChecked = booleanArrayOf(false, false, false, false, false, false, false, false, false)
        }
        if(question == 15){
            arrayQuestions = arrayOf("Mi hijo está triste, pensativo, nostálgico la mayor parte del tiempo.",
                "Mi hijo pelea con su hermano todo el tiempo.",
                "Mi hijo no quiere hacer lo que le toca en casa.",
                "Mi hijo no quiere ir a la escuela.",
                "Mi hijo solo quiere estar en la tablet.",
                "Mi hijo está teniendo un despertar sexual prematuro.",
                "Mi hijo me ignora todo el tiempo.",
                "Mi esposo y yo implementamos diferentes estilos de crianza.")
            arrayChecked = booleanArrayOf(false, false, false, false, false, false, false, false)
        }
        if(question == 16){
            arrayQuestions = arrayOf("Mi hijo ya quiere tener novia pero aún es pequeño.",
            "Mi hijo es muy rebelde.",
            "Mi hijo se  aísla mucho, no tiene amigos.",
            "Creo que mi hijo está usando drogas.",
            "Los amigos de mi hijo no son una buena influencia.",
            "Mi hijo fuma.",
            "Mi hijo toma.",
            "Mi hijo embarazó a su novia o mi hija está embarazada.",
            "Mi esposo y yo tenemos diferentes tipos de crianza.")
            arrayChecked = booleanArrayOf(false, false, false, false, false, false, false, false, false)
        }

        //val arrayColors = arrayOf("RED", "GREEN", "YELLOW", "BLACK", "MAGENTA", "PINK")

        val builder = AlertDialog.Builder(this.context)

        builder.setTitle("Selecciona las respuestas")

        builder.setMultiChoiceItems(arrayQuestions, arrayChecked) { _, which, isChecked ->
            arrayChecked!![which] = isChecked

            val color = arrayQuestions!![which]

            println("$color clicked.")
        }


        builder.setPositiveButton("OK") { _, _ ->
            println("Seleccionaste..... \n")
            for (i in 0 until arrayQuestions!!.size) {
                val checked = arrayChecked!![i]
                if (checked) {
                    println("${arrayQuestions[i]} \n")
                }
            }
        }

        dialog = builder.create()

        dialog.show()
    }
}
