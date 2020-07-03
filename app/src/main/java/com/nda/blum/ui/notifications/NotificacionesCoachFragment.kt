package com.nda.blum.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.NotificacionesCoachFragmentBinding
import com.nda.blum.interfaces.IBackToHub

class NotificacionesCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.VISIBLE

        val bindingNotifications: NotificacionesCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.notificaciones_coach_fragment, container, false
        )

        bindingNotifications.imgNotificationBack.setOnClickListener{
            (activity as IBackToHub?)!!.backToHubFragment()
        }

        return bindingNotifications.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as IBackToHub?)!!.backToHubFragment()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}
