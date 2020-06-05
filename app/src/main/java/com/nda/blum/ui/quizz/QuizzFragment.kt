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

    companion object {
        fun newInstance() = QuizzFragment()
    }

    private lateinit var viewModel: QuizzViewModel

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

        bindingQuizz.txtQuestion1Answer1.setOnClickListener {
            if (bindingQuizz.txtQuestion1Answer1.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion1Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer1.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion1Answer1.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion1Answer1.setTextColor(Color.parseColor("#FFFFFF"))
                if (bindingQuizz.txtQuestion1Answer2.currentTextColor == Color.parseColor("#FFFFFF") || bindingQuizz.txtQuestion1Answer3.currentTextColor == Color.parseColor(
                        "#FFFFFF"
                    )
                ) {
                    minusProgress(bindingQuizz)
                }
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion1Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer2.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion1Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion1Answer2.setOnClickListener {
            if (bindingQuizz.txtQuestion1Answer2.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion1Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer2.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion1Answer2.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion1Answer2.setTextColor(Color.parseColor("#FFFFFF"))
                if (bindingQuizz.txtQuestion1Answer1.currentTextColor == Color.parseColor("#FFFFFF") || bindingQuizz.txtQuestion1Answer2.currentTextColor == Color.parseColor(
                        "#FFFFFF"
                    )
                ) {
                    minusProgress(bindingQuizz)
                }
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion1Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer1.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion1Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion1Answer3.setOnClickListener {
            if (bindingQuizz.txtQuestion1Answer3.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion1Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer3.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion1Answer3.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion1Answer3.setTextColor(Color.parseColor("#FFFFFF"))
                if (bindingQuizz.txtQuestion1Answer2.currentTextColor == Color.parseColor("#FFFFFF") || bindingQuizz.txtQuestion1Answer1.currentTextColor == Color.parseColor(
                        "#FFFFFF"
                    )
                ) {
                    minusProgress(bindingQuizz)
                }
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion1Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer1.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion1Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion1Answer2.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion2Answer1.setOnClickListener {
            if (bindingQuizz.txtQuestion2Answer1.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion2Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer1.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion2Answer1.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion2Answer1.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion2Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer2.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion2Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion2Answer2.setOnClickListener {
            if (bindingQuizz.txtQuestion2Answer2.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion2Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer2.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)

            } else {
                bindingQuizz.txtQuestion2Answer2.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion2Answer2.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion2Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer1.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion2Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion2Answer3.setOnClickListener {
            if (bindingQuizz.txtQuestion2Answer3.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion2Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer3.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion2Answer3.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion2Answer3.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion2Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer2.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion2Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion2Answer1.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion3Answer1.setOnClickListener {
            if (bindingQuizz.txtQuestion3Answer1.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion3Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer1.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion3Answer1.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion3Answer1.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion3Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer2.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion3Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion3Answer2.setOnClickListener {
            if (bindingQuizz.txtQuestion3Answer2.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion3Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer2.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion3Answer2.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion3Answer2.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion3Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer1.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion3Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion3Answer3.setOnClickListener {
            if (bindingQuizz.txtQuestion3Answer3.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion3Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer3.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion3Answer3.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion3Answer3.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion3Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer2.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion3Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion3Answer1.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion4Answer1.setOnClickListener {
            if (bindingQuizz.txtQuestion4Answer1.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion4Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer1.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion4Answer1.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion4Answer1.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion4Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer2.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion4Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion4Answer2.setOnClickListener {
            if (bindingQuizz.txtQuestion4Answer2.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion4Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer2.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion4Answer2.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion4Answer2.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion4Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer1.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion4Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer3.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.txtQuestion4Answer3.setOnClickListener {
            if (bindingQuizz.txtQuestion4Answer3.currentTextColor == Color.parseColor("#FFFFFF")) {
                bindingQuizz.txtQuestion4Answer3.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer3.setTextColor(Color.parseColor("#000000"))
                minusProgress(bindingQuizz)
            } else {
                bindingQuizz.txtQuestion4Answer3.setBackgroundResource(R.drawable.purple_rounded_shape)
                bindingQuizz.txtQuestion4Answer3.setTextColor(Color.parseColor("#FFFFFF"))
                addProgress(bindingQuizz)
                bindingQuizz.txtQuestion4Answer1.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer1.setTextColor(Color.parseColor("#000000"))
                bindingQuizz.txtQuestion4Answer2.setBackgroundResource(R.drawable.white_rounded_shape_purple_stroke)
                bindingQuizz.txtQuestion4Answer2.setTextColor(Color.parseColor("#000000"))
            }
        }

        bindingQuizz.button8.setOnClickListener {
            this.findNavController()
                .navigate(QuizzFragmentDirections.actionQuizzFragmentToFindingCoachFragment())
        }

        return bindingQuizz.root
    }

    private fun addProgress(bindingQuizz: QuizzFragmentBinding) {
        progress += (100 / 20)
        bindingQuizz.customProgress.progress = progress
        println(progress)
    }

    private fun minusProgress(bindingQuizz: QuizzFragmentBinding) {
        if (progress < 0){
            progress = 0
            bindingQuizz.customProgress.progress = progress
            } else {
            progress -= (100 / 20)
            bindingQuizz.customProgress.progress = progress
            println(progress)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizzViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
