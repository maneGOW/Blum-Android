package com.nda.blum.ui.chat

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nda.blum.R
import com.nda.blum.adapters.ChatIndividualAdapter
import com.nda.blum.databinding.ChatWithCoachFragmentBinding
import com.nda.blum.interfaces.IBackToHub
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ChatWithCoachFragment : Fragment() {

    var handler : Handler? = null
    var chatWithCoachViewmodel: ChatWithCoachViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindingChatWithCoach: ChatWithCoachFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.chat_with_coach_fragment, container, false
        )

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ChatWithCoachViewModelFactory(application)
        chatWithCoachViewmodel =
            ViewModelProviders.of(this, viewModelFactory).get(ChatWithCoachViewModel::class.java)

        chatWithCoachViewmodel!!.nidoID.value =
            ChatWithCoachFragmentArgs.fromBundle(arguments!!).idRequerido

        chatWithCoachViewmodel!!.chatType.value =
            ChatWithCoachFragmentArgs.fromBundle(arguments!!).chatType

        if (chatWithCoachViewmodel!!.nidoID.value!!.isNotEmpty() && chatWithCoachViewmodel!!.chatType.value != "coachUser") {
            chatWithCoachViewmodel!!.getNidoName()
        } else {
            chatWithCoachViewmodel!!.getCoachName()
        }

        chatWithCoachViewmodel!!.setChatUrl()

        handler = Handler()
        handler!!.postDelayed(object : Runnable {
            override fun run() {
                chatWithCoachViewmodel!!.getMessagesFromServer()
                handler!!.postDelayed(this, 1000)
            }
        }, 0)


        println("ChatType: ${chatWithCoachViewmodel!!.chatType.value}")

        if(chatWithCoachViewmodel!!.chatType.value == "coachUser"){
            bindingChatWithCoach.textView39.text = ChatWithCoachFragmentArgs.fromBundle(arguments!!).tituloChat
            Glide.with(this)
                .load(ChatWithCoachFragmentArgs.fromBundle(arguments!!).urlImagenTitulo)
                .apply(RequestOptions.circleCropTransform())
                .into(bindingChatWithCoach.imageView34)
        }else if(chatWithCoachViewmodel!!.chatType.value == "coachNest"){
            bindingChatWithCoach.textView39.text = ChatWithCoachFragmentArgs.fromBundle(arguments!!).tituloChat
            Glide.with(this)
                .load(ChatWithCoachFragmentArgs.fromBundle(arguments!!).urlImagenTitulo)
                .apply(RequestOptions.circleCropTransform())
                .into(bindingChatWithCoach.imageView34)
        }else if(chatWithCoachViewmodel!!.chatType.value == "userNest"){
            chatWithCoachViewmodel!!.getNidoName()
            bindingChatWithCoach.textView39.text = chatWithCoachViewmodel!!.nidoName.value
        }

        chatWithCoachViewmodel!!.coachName.observe(viewLifecycleOwner, Observer {
            println("TIPO DE CHAT SELECCIONADO : $it")
            if (chatWithCoachViewmodel!!.chatType.value == "userCoach") {
                bindingChatWithCoach.textView39.text = it
            }
        })

        chatWithCoachViewmodel!!.nidoName.observe(viewLifecycleOwner, Observer {
            if (chatWithCoachViewmodel!!.chatType.value == "userNest") {
                bindingChatWithCoach.textView39.text = it
            }
        })

        chatWithCoachViewmodel!!.coachProfilePic.observe(viewLifecycleOwner, Observer {
            println("URL DE IMAGEM $it")
            if (chatWithCoachViewmodel!!.chatType.value == "userCoach") {
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingChatWithCoach.imageView34)
            }
        })

        chatWithCoachViewmodel!!.nidoProfilePic.observe(viewLifecycleOwner, Observer {
            if (chatWithCoachViewmodel!!.chatType.value == "userNest") {
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingChatWithCoach.imageView34)
            }
        })

        chatWithCoachViewmodel!!.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        chatWithCoachViewmodel!!.showDialog.observe(viewLifecycleOwner, Observer {
           // if(it){
           //         handler!!.removeCallbacksAndMessages(null)
           //         createExitDialog()
           // }
        })

        var updateCount = 0
        var currenSize = 0

        chatWithCoachViewmodel!!.filledMessageList.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (updateCount == 0) {
                    if (chatWithCoachViewmodel!!.messages.value != null) {
                        println("USER ID COACH FRAGMENT ${chatWithCoachViewmodel!!.userID.value}")
                        bindingChatWithCoach.rvChat.layoutManager =
                            LinearLayoutManager(this.context)

                        bindingChatWithCoach.rvChat.adapter = ChatIndividualAdapter(
                            this.context!!,
                            chatWithCoachViewmodel!!.messages.value!!,
                            chatWithCoachViewmodel!!.userID.value!!
                        )

                        bindingChatWithCoach.rvChat.itemAnimator = null;

                        currenSize = bindingChatWithCoach.rvChat.adapter!!.itemCount

                        val positon = bindingChatWithCoach.rvChat.adapter!!.itemCount - 1
                        bindingChatWithCoach.rvChat.smoothScrollToPosition(positon)
                        bindingChatWithCoach.rvChat.adapter!!.notifyDataSetChanged()
                        updateCount = 1
                    }
                } else {
                    if (updateCount >= 1 && chatWithCoachViewmodel!!.messages.value!!.result.size > currenSize ) {
                        if (chatWithCoachViewmodel!!.messages.value != null) {
                            println("USER ID COACH FRAGMENT ${chatWithCoachViewmodel!!.userID.value}")
                            bindingChatWithCoach.rvChat.layoutManager =
                                LinearLayoutManager(this.context)

                            bindingChatWithCoach.rvChat.adapter = ChatIndividualAdapter(
                                this.context!!,
                                chatWithCoachViewmodel!!.messages.value!!,
                                chatWithCoachViewmodel!!.userID.value!!
                            )

                            bindingChatWithCoach.rvChat.itemAnimator = null;

                            currenSize = bindingChatWithCoach.rvChat.adapter!!.itemCount

                            val positon = bindingChatWithCoach.rvChat.adapter!!.itemCount - 1
                            bindingChatWithCoach.rvChat.smoothScrollToPosition(positon)
                            bindingChatWithCoach.rvChat.adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        bindingChatWithCoach.imgSendIcon.setOnClickListener {
            if (bindingChatWithCoach.txtUserMessage.text.isNotEmpty() && bindingChatWithCoach.txtUserMessage.text.isNotBlank()) {
                chatWithCoachViewmodel!!.userMessage.value =
                    bindingChatWithCoach.txtUserMessage.text.toString()
                chatWithCoachViewmodel!!.sendMessageToServer()
                bindingChatWithCoach.txtUserMessage.setText("")
            }
        }

        val navView: BottomNavigationView = this.activity!!.findViewById(R.id.bttm_nav)
        navView.visibility = View.GONE

        bindingChatWithCoach.btnBackCoachChat.setOnClickListener {
            (activity as IBackToHub?)!!.backToHubFragment()
        }
        return bindingChatWithCoach.root
    }

    override fun onResume() {
        super.onResume()
        handler!!.postDelayed(object : Runnable {
            override fun run() {
                chatWithCoachViewmodel!!.getMessagesFromServer()
                handler!!.postDelayed(this, 1000)
            }
        }, 0)

    }

    override fun onDestroy() {
        super.onDestroy()
        handler!!.removeCallbacksAndMessages(null)

    }

    override fun onPause() {
        super.onPause()
        handler!!.removeCallbacksAndMessages(null)
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

    fun createExitDialog(){
        val builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Alerta")
        builder.setMessage("Ocurrió un error al cargar tu chat")
        builder.setPositiveButton("Ok") { dialog, _ ->
            (activity as IBackToHub?)!!.backToHubFragment()
            dialog.dismiss()
        }
        builder.show()
    }
}
