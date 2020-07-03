package com.nda.blum.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.nda.blum.R
import kotlinx.android.synthetic.main.slider1_fragment.view.*

class UserSliderAdapter(val navController: NavController, val directions:  NavDirections) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.slider1_fragment, parent, false))

    override fun getItemCount(): Int = 2

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        if(position == 0){
            txtWelcome.text = "¡Bienvenido!"
            txtDescription.text = "Ahora formas parte de BLUM"
            txtTitle.text = "Lo que estás a punto de descubrir en este viaje\n" +
                    "increíble hacia tu crecimiento personal y\n" +
                    "familiar, será una experiencia transformadora\n" +
                    "para ti."
            txtQuestion.text = "¿Estás listo(a)?"
            btnGoToQuizz.visibility = View.GONE
            ivImage.setImageResource(R.drawable.mujer_sentada)
            container.setBackgroundResource(R.color.backgroundColor)

            val circle1 = layoutCircle1!!.background
            circle1.setTint(Color.parseColor("#FFFFFF"))

            val circle2 = layoutCircle2!!.background
            circle2.setTint(Color.parseColor("#8a93ad"))
        }
        if(position == 1) {
            txtWelcome.visibility = View.INVISIBLE
            txtDescription.text = "¡Queremos conocerte!"
            txtTitle.text = "Para poder conocerte más y asignarte al\n" +
                    "Coach Parental ideal para ti es necesario\n" +
                    "que por favor respondas TODAS las\n" +
                    "preguntas del siguiente cuestionario."
            txtQuestion.visibility = View.INVISIBLE
            ivImage.setImageResource(R.drawable.mujer_pensando)
            btnGoToQuizz.setOnClickListener {
                navController.navigate(directions)
            }
            container.setBackgroundResource(R.color.backgroundColor)

            val circle1 = layoutCircle1!!.background
            circle1.setTint(Color.parseColor("#8a93ad"))

            val circle2 = layoutCircle2!!.background
            circle2.setTint(Color.parseColor("#FFFFFF"))
        }
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)