package com.nda.blum.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.nda.blum.R
import kotlinx.android.synthetic.main.slider2_fragment.view.*

class CoachSliderAdapter(val navController: NavController, val directions:  NavDirections) : RecyclerView.Adapter<PagerCoach>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerCoach =
        PagerCoach(LayoutInflater.from(parent.context).inflate(R.layout.slider2_fragment, parent, false))

    override fun getItemCount(): Int = 4

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerCoach, position: Int) = holder.itemView.run {
        if(position == 0){
            imgTitleCoach.setImageResource(R.drawable.icono_calendario)
            coachContainer.setBackgroundResource(R.color.backgroundColor)
            imgFlecha2.visibility = View.INVISIBLE
            imgFlecha3.visibility = View.INVISIBLE
            imgFlecha4.visibility = View.INVISIBLE
            btnGoToHome.visibility = View.INVISIBLE

            txtTitleCoach.text = "CALENDARIO"
            txtDescriptionCoach.text = "Aquí podras ver todas las citas que tienes asignadas."

            imgBarCalendar.setImageResource(R.drawable.blue_calendar_icon)
            val circle1 = circle1!!.background
            circle1.setTint(Color.parseColor("#8a93ad"))
            val circle2 = circle2!!.background
            circle2.setTint(Color.parseColor("#FFFFFF"))
            val circle3 = circle3!!.background
            circle3.setTint(Color.parseColor("#FFFFFF"))
            val circle4 = circle4!!.background
            circle4.setTint(Color.parseColor("#FFFFFF"))
        }
        if(position == 1) {
            imgTitleCoach.setImageResource(R.drawable.chat_nido)
            coachContainer.setBackgroundResource(R.color.backgroundColor)
            imgFlecha1.visibility = View.INVISIBLE
            imgFlecha2.visibility = View.VISIBLE
            imgFlecha3.visibility = View.INVISIBLE
            imgFlecha4.visibility = View.INVISIBLE
            btnGoToHome.visibility = View.INVISIBLE

            txtTitleCoach.text = "CHAT CON MIS NIDOS"
            txtDescriptionCoach.text = "Aquí podrás acceder a tus chats personales con cada uno de los usuarios que les han sido asignados."

            imgNestChat.setImageResource(R.drawable.blue_nest_icon)
            val circle1 = circle1!!.background
            circle1.setTint(Color.parseColor("#FFFFFF"))
            val circle2 = circle2!!.background
            circle2.setTint(Color.parseColor("#8a93ad"))
            val circle3 = circle3!!.background
            circle3.setTint(Color.parseColor("#FFFFFF"))
            val circle4 = circle4!!.background
            circle4.setTint(Color.parseColor("#FFFFFF"))
        }
        if(position == 2){
            imgTitleCoach.setImageResource(R.drawable.chat_alumno)
            coachContainer.setBackgroundResource(R.color.backgroundColor)
            imgFlecha1.visibility = View.INVISIBLE
            imgFlecha2.visibility = View.INVISIBLE
            imgFlecha3.visibility = View.VISIBLE
            imgFlecha4.visibility = View.INVISIBLE
            btnGoToHome.visibility = View.INVISIBLE


            txtTitleCoach.text = "MIS CHATS"
            txtDescriptionCoach.text = "En este botón podrás acceder a tus chats grupales."

            imgChat.setImageResource(R.drawable.blue_chat_coach_icon)
            val circle1 = circle1!!.background
            circle1.setTint(Color.parseColor("#FFFFFF"))
            val circle2 = circle2!!.background
            circle2.setTint(Color.parseColor("#FFFFFF"))
            val circle3 = circle3!!.background
            circle3.setTint(Color.parseColor("#8a93ad"))
            val circle4 = circle4!!.background
            circle4.setTint(Color.parseColor("#FFFFFF"))
        }
        if(position == 3){
            imgTitleCoach.setImageResource(R.drawable.icono_recursos)
            coachContainer.setBackgroundResource(R.color.backgroundColor)
            imgFlecha1.visibility = View.INVISIBLE
            imgFlecha2.visibility = View.INVISIBLE
            imgFlecha3.visibility = View.INVISIBLE
            imgFlecha4.visibility = View.VISIBLE
            btnGoToHome.visibility = View.VISIBLE

            btnGoToHome.setOnClickListener {
                navController.navigate(directions)
            }

            txtTitleCoach.text = "RECURSOS"
            txtDescriptionCoach.text = "En este botón encontraras material que te será de gran ayuda."


            imgChest.setImageResource(R.drawable.blue_chest_icon)
            val circle1 = circle1!!.background
            circle1.setTint(Color.parseColor("#FFFFFF"))
            val circle2 = circle2!!.background
            circle2.setTint(Color.parseColor("#FFFFFF"))
            val circle3 = circle3!!.background
            circle3.setTint(Color.parseColor("#FFFFFF"))
            val circle4 = circle4!!.background
            circle4.setTint(Color.parseColor("#8a93ad"))
        }
    }
}

class PagerCoach(itemView: View) : RecyclerView.ViewHolder(itemView)