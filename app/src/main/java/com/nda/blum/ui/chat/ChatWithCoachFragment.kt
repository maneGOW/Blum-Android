package com.nda.blum.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.databinding.ChatWithCoachFragmentBinding

class ChatWithCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingChatWithCoach: ChatWithCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.chat_with_coach_fragment, container, false
        )

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.VISIBLE

        bindingChatWithCoach.btnBackCoachChat.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return bindingChatWithCoach.root
    }

}
