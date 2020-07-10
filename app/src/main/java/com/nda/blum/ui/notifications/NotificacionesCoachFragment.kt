package com.nda.blum.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.adapters.AlumnosAdapter
import com.nda.blum.adapters.NidosListener
import com.nda.blum.adapters.NotificacionesAdapter
import com.nda.blum.databinding.NotificacionesCoachFragmentBinding
import com.nda.blum.interfaces.IBackToHub
import com.nda.blum.ui.nidosdata.NidosDataViewModel
import com.nda.blum.ui.nidosdata.NidosDataViewModelFactory

class NotificacionesCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        val bindingNotifications: NotificacionesCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.notificaciones_coach_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = NotificacionesCoachViewModelFactory(application)
        val notificacionesViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(NotificacionesCoachViewModel::class.java)

        notificacionesViewModel.getNotificaciones()

        notificacionesViewModel.notificationsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.result.isNotEmpty()) {
                bindingNotifications.rvNotifications.layoutManager =
                    LinearLayoutManager(this.context)
                val manager = GridLayoutManager(activity, 1)
                bindingNotifications.rvNotifications.layoutManager = manager
                val adapter = NotificacionesAdapter(
                    this.requireContext(),
                    it,
                    this
                )
                adapter.addHeaderAndSubmitList(it.result)
                bindingNotifications.rvNotifications.adapter = adapter
                bindingNotifications.txtNoNotificaciones.visibility = View.GONE
            } else {
                bindingNotifications.txtNoNotificaciones.visibility = View.VISIBLE
            }
        })

        bindingNotifications.imgNotificationBack.setOnClickListener {
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
