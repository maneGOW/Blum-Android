package com.nda.blum.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.R
import com.nda.blum.adapters.ChatIndividualAdapter
import com.nda.blum.databinding.ChatWithCoachFragmentBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ChatWithCoachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingChatWithCoach: ChatWithCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.chat_with_coach_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ChatWithCoachViewModelFactory(application)
        val chatWithCoachViewmodel =
            ViewModelProviders.of(this, viewModelFactory).get(ChatWithCoachViewModel::class.java)

        chatWithCoachViewmodel.chatType.value =
            ChatWithCoachFragmentArgs.fromBundle(arguments!!).chatType

        chatWithCoachViewmodel.setChatUrl()

        chatWithCoachViewmodel.getMessagesFromServer()

        val secureStorage = SecureStorage(this.activity!!.applicationContext)
        val urlProfilePic = secureStorage.getObject("userProfilePicture", String::class.java)

        println("ChatType: ${chatWithCoachViewmodel.chatType.value}")

        chatWithCoachViewmodel.coachName.observe(viewLifecycleOwner, Observer {
            bindingChatWithCoach.textView39.text = it
        })

        chatWithCoachViewmodel.coachProfilePic.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it)
                .apply(RequestOptions.circleCropTransform())
                .into(bindingChatWithCoach.imageView34)
        })

        chatWithCoachViewmodel.filledMessageList.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (chatWithCoachViewmodel.messages.value != null) {
                    bindingChatWithCoach.rvChat.layoutManager = LinearLayoutManager(this.context)
                    bindingChatWithCoach.rvChat.adapter = ChatIndividualAdapter(
                        this.context!!,
                        chatWithCoachViewmodel.messages.value!!,
                        chatWithCoachViewmodel.userID.value!!
                    )
                }
            }
        })

        bindingChatWithCoach.imgSendIcon.setOnClickListener {
            if (bindingChatWithCoach.txtUserMessage.text.isNotEmpty() && bindingChatWithCoach.txtUserMessage.text.isNotBlank()) {
                chatWithCoachViewmodel.userMessage.value =
                    bindingChatWithCoach.txtUserMessage.text.toString()
                chatWithCoachViewmodel.sendMessageToServer()
                bindingChatWithCoach.txtUserMessage.setText("")
            }
        }

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.VISIBLE

        bindingChatWithCoach.btnBackCoachChat.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return bindingChatWithCoach.root
    }
}
