package com.nda.blum.ui.chat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.*
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

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog : LiveData<Boolean>
    get() = _showDialog

    val secureStorage = SecureStorage(getApplication())

    private var chatUrl = MutableLiveData<String>()

    private var sendMessageUrl = MutableLiveData<String>()

    val chatType = MutableLiveData<String>()

    val coachName = MutableLiveData<String>()
    val coachProfilePic = MutableLiveData<String>()

    val nidoName = MutableLiveData<String>()
    val nidoProfilePic = MutableLiveData<String>()

    val userID = MutableLiveData<String>()

    val nidoID = MutableLiveData<String>()

    private var chatRoomId: String? = null


    val messages = MutableLiveData<RecuperarChatResponse>()
    private val _filledMessageList = MutableLiveData<Boolean>()
    val filledMessageList: LiveData<Boolean>
        get() = _filledMessageList

    val userMessage = MutableLiveData<String>()

    init {
        _showDialog.value = false
        _showProgressDialog.value = false
        _filledMessageList.value = false
        userID.value = secureStorage.getObject("idUsuario", String::class.java)
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
                _showDialog.value = true
                _filledMessageList.value = false
                _showProgressDialog.value = false
            }
        }
    }

    fun sendMessageToServer() {
        coroutineScope.launch {
            val sendMessage = susSendMessage()
            if (sendMessage!!.code == "0") {
                //getMessagesFromServer()
            } else {
                println("error al mandar el mensaje")
            }
        }
    }

    fun getCoachName() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            val coachdata = susGetCoachData()
            println("COACH NAME ${coachdata!!.result.Nombre_Usuario}")
            coachName.value = coachdata.result.Nombre_Usuario
            coachProfilePic.value = coachdata.result.Foto_Usuario
            _showProgressDialog.value = false
        }
    }

    private suspend fun susGetCoachData(): FindUser? {
        var findUserResponse: FindUser? = null
        var idCoach = secureStorage.getObject("idCoach", String::class.java)
        if(chatType.value == "coachUser"){
            idCoach = secureStorage.getObject("idUsuario", String::class.java)
        }
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

    fun getNidoName(){
        coroutineScope.launch {
            _showProgressDialog.value = true
            val nidoData = susGetNidoName()
            if(nidoData!!.code == "0"){
                nidoName.value = nidoData.result.Nombre_Nido
                nidoProfilePic.value = nidoData.result.Foto_Nido
                _showProgressDialog.value = false
            }else{
                _showProgressDialog.value = false
            }
        }
    }

    private suspend fun susGetNidoName(): BuscarNidoResponse?{
        var nidoData: BuscarNidoResponse? = null
        var idNido = secureStorage.getObject("idNido", String::class.java)
        if(idNido == "0" || idNido.isNullOrEmpty()){
            idNido = nidoID.value
        }
        println("Id nido $idNido")
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscaimagennido.php?id_nido=$idNido")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), BuscarNidoResponse::class.java)
                    nidoData = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return nidoData
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
                "https://www.retosalvatucasa.com/ws_app_nda/recuperarchat.php?idchatroom=$idUser-${nidoID.value}-individual"
            sendMessageUrl.value =
                "https://retosalvatucasa.com/ws_app_nda/chatindividual.php?nombrechat=$idUser-${nidoID.value}-individual&sender=$idUser&receiver=$idCoach&timestamp=${System.currentTimeMillis()}"

        } else if (chatType.value == "coachNest") {
            chatUrl.value =
                "https://www.retosalvatucasa.com/ws_app_nda/recuperarchat.php?idchatroom=$idUser-${nidoID.value}-nido"
            println(chatUrl.value)
            sendMessageUrl.value =
                "https://retosalvatucasa.com/ws_app_nda/chatindividual.php?nombrechat=$idUser-${nidoID.value}-nido&sender=$idUser&receiver=$idCoach&timestamp=${System.currentTimeMillis()}"
            println(sendMessageUrl.value)
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
            println("URL CHAT: ${chatUrl.value!!}")
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

    override fun onCleared() {
        super.onCleared()

    }
}
