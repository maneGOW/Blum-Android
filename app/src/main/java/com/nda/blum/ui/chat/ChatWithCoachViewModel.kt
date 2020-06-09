package com.nda.blum.ui.chat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.RecuperarChatResponse
import com.nda.blum.DAO.SendMensajeResult
import com.nda.blum.db.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class ChatWithCoachViewModel(private val dataSource: UserDao, application: Application) : BaseViewModel(application) {

    val idRoom = MutableLiveData<String>()
    val idSent = MutableLiveData<String>()
    val idRecibe = MutableLiveData<String>()


    val messages = MutableLiveData<RecuperarChatResponse>()
    private val _filledMessageList = MutableLiveData<Boolean>()
    val filledMessageList : LiveData<Boolean>
    get() = _filledMessageList

    val userMessage = MutableLiveData<String>()

    init {
        _filledMessageList.value = false
    }

    fun getMessagesFromServer() {
        coroutineScope.launch {
            val getMessages = susGetMessages()
            if(getMessages!!.code == "0"){
                messages.value = getMessages
                getMessages.result.forEach {
                    println("MENSAJE: ${it.Mensaje_Chat}")
                    _filledMessageList.value = true
                }
            }else{
                println("SIN CHATS")
                _filledMessageList.value = false
            }
        }
    }

    fun sendMessageToServer() {
        coroutineScope.launch {
            val sendMessage = susSendMessage()
            if(sendMessage!!.code == "0"){
               getMessagesFromServer()
            }else{
                println("error al mandar el mensaje")
            }
        }
    }

    private suspend fun susSendMessage(): SendMensajeResult?{
        var sendMensajeResult: SendMensajeResult? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/chatindividual.php?idchatroom=${idRoom.value}&sender=${idSent.value}&receiver=${idRecibe.value}&chatmessage=${userMessage.value}&timestamp=${System.currentTimeMillis()}")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), SendMensajeResult::class.java)
                    sendMensajeResult = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return sendMensajeResult
    }

    private suspend fun susGetMessages(): RecuperarChatResponse? {
        var recuperarChatResponse: RecuperarChatResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://www.retosalvatucasa.com/ws_app_nda/recuperarchat.php?idchatroom=${idRoom.value}")
                .method("POST", body)
                .build()

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), RecuperarChatResponse::class.java)
                    recuperarChatResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return recuperarChatResponse
    }

}
