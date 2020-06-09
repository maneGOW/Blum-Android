package com.nda.blum.ui.quizz

import android.graphics.Color
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.QuizzFragmentBinding
import com.nda.blum.db.BlumDatabase
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
class QuizzFragment : Fragment() {

    private var progress: Int = 0

    private val pregunta1 =
        arrayOf(
            "hombre",
            "mujer"
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
        val dataSource = BlumDatabase.getInstance(application).userDao()

        val viewModelFactory =
            QuizzViewModelFactory(application, dataSource)
        val quizzViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(QuizzViewModel::class.java)

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        Glide.with(this)
            .load(R.drawable.user_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingQuizz.userProfilePic)


        bindingQuizz.button8.setOnClickListener {
            this.findNavController()
                .navigate(QuizzFragmentDirections.actionQuizzFragmentToQuizz2Fragment())
        }

        return bindingQuizz.root
    }

}
