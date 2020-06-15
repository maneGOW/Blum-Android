package com.nda.blum.ui.chat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.FindUser
import com.nda.blum.DAO.FindUserResponse
import com.nda.blum.DAO.RecuperarChatResponse
import com.nda.blum.DAO.SendMensajeResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class ChatWithCoachViewModel(application: Application) :
    BaseViewModel(application) {

    val idRoom = MutableLiveData<String>()
    val idSent = MutableLiveData<String>()
    val idRecibe = MutableLiveData<String>()

    val secureStorage = SecureStorage(getApplication())

    private var chatUrl = MutableLiveData<String>()

    private var sendMessageUrl = MutableLiveData<String>()

    val chatType = MutableLiveData<String>()

    val coachName = MutableLiveData<String>()
    val coachProfilePic = MutableLiveData<String>()

    val userID = MutableLiveData<String>()

    val alumnoID = MutableLiveData<String>()

    val nidoID = MutableLiveData<String>()

    private var chatRoomId: String? = null


    val messages = MutableLiveData<RecuperarChatResponse>()
    private val _filledMessageList = MutableLiveData<Boolean>()
    val filledMessageList: LiveData<Boolean>
        get() = _filledMessageList

    val userMessage = MutableLiveData<String>()

    init {
        _filledMessageList.value = false
        userID.value = secureStorage.getObject("idUsuario", String::class.java)
        getCoachName()
    }

    fun getMessagesFromServer() {
        coroutineScope.launch {
            val getMessages = susGetMessages()
            if (getMessages!!.code == "0") {
                messages.value = getMessages
                getMessages.result.forEach {
                    println("MENSAJE: ${it.Mensaje_Chat}")
                    chatRoomId = it.Id_chatroom
                    println("CHAT ROOM: $chatRoomId")
                    _filledMessageList.value = true
                }
            } else {
                println("SIN CHATS")
                _filledMessageList.value = false
            }
        }
    }

    fun sendMessageToServer() {
        coroutineScope.launch {
            val sendMessage = susSendMessage()
            if (sendMessage!!.code == "0") {
                getMessagesFromServer()
            } else {
                println("error al mandar el mensaje")
            }
        }
    }

    private fun getCoachName() {
        coroutineScope.launch {
            val coachdata = susGetCoachData()
            println("COACH NAME ${coachdata!!.result.Nombre_Usuario}")
            coachName.value = coachdata.result.Nombre_Usuario
            coachProfilePic.value = coachdata.result.Foto_Usuario
        }
    }

    private suspend fun susGetCoachData(): FindUser? {
        var findUserResponse: FindUser? = null
        val idCoach = secureStorage.getObject("idCoach", String::class.java)
        println("Id coach $idCoach")
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/datoscoach.php?Id_Coach=$idCoach")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), FindUser::class.java)
                    findUserResponse = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return findUserResponse
    }

    fun setChatUrl() {
        val idCoach = secureStorage.getObject("idCoach", String::class.java)
        val idUser = secureStorage.getObject("idUsuario", String::class.java)
        val idNido = secureStorage.getObject("idNido", String::class.java)

        println("$idUser - $idCoach - $idNido")

        if (chatType.value == "userCoach") {
            println("here")
            chatUrl.value =
                "https://www.retosalvatucasa.com/ws_app_nda/recuperarchat.php?idchatroom=$idCoach-$idUser-individual"
            sendMessageUrl.value =
                "https://retosalvatucasa.com/ws_app_nda/chatindividual.php?nombrechat=$idCoach-$idUser-individual&sender=$idUser&receiver=$idCoach&timestamp=${System.currentTimeMillis()}"
        } else if (chatType.value == "userNest") {
            println("here")
            chatUrl.value =
                "https://www.retosalvatucasa.com/ws_app_nda/recuperarchat.php?idchatroom=$idCoach-$idNido-nido"
            sendMessageUrl.value =
                "https://retosalvatucasa.com/ws_app_nda/chatindividual.php?nombrechat=$idCoach-$idNido-nido&sender=$idUser&receiver=$idCoach&timestamp=${System.currentTimeMillis()}"
        } else if (chatType.value == "coachUser") {
            chatUrl.value =
                "https://www.retosalvatucasa.com/ws_app_nda/recuperarchat.php?idchatroom=$idCoach-${alumnoID.value}-individual"
            sendMessageUrl.value =
                "https://retosalvatucasa.com/ws_app_nda/chatindividual.php?nombrechat=$idCoach-${alumnoID.value}-individual&sender=$idUser&receiver=$idCoach&timestamp=${System.currentTimeMillis()}"

        } else if (chatType.value == "coachNest") {
            chatUrl.value =
                "https://www.retosalvatucasa.com/ws_app_nda/recuperarchat.php?idchatroom=$idCoach-${nidoID.value}-nido"
            sendMessageUrl.value =
                "https://retosalvatucasa.com/ws_app_nda/chatindividual.php?nombrechat=$idCoach-${nidoID.value}-nido&sender=$idUser&receiver=$idCoach&timestamp=${System.currentTimeMillis()}"

        }
    }

    private suspend fun susSendMessage(): SendMensajeResult? {
        var sendMensajeResult: SendMensajeResult? = null
        println(sendMessageUrl.value)
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url(sendMessageUrl.value!!+"&chatmessage=${userMessage.value}")
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
                .url(chatUrl.value!!)
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
