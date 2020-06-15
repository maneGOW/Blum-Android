package com.nda.blum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nda.blum.DAO.RecuperarChatResponse
import com.nda.blum.DAO.RestultChatMessage
import com.nda.blum.R
import kotlinx.android.synthetic.main.my_message.view.*
import kotlinx.android.synthetic.main.other_message.view.*

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class ChatIndividualAdapter(
    val context: Context,
    val recuperarChatResponse: RecuperarChatResponse,
    val userId: String
) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun getItemCount(): Int {
        return recuperarChatResponse.result.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = recuperarChatResponse.result.get(position)

        println("user ID del fragment $userId --- userID del array ${message.IdUsuarioEmisor_Chat}")
        return if (userId == message.IdUsuarioEmisor_Chat) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.my_message, parent, false)
            )
        } else {
            OtherMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.other_message, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = recuperarChatResponse.result.get(position)

        holder.bind(message)
    }

    inner class MyMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage

        override fun bind(message: RestultChatMessage) {
            messageText.text = message.Mensaje_Chat
        }
    }

    inner class OtherMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage

        override fun bind(message: RestultChatMessage) {
            messageText.text = message.Mensaje_Chat
        }
    }
}

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: RestultChatMessage) {}
}