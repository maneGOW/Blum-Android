package com.nda.blum.ui.userprofile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.ActualizarDatosUsuarioResponse
import com.nda.blum.DAO.CambiarImagenPerfil
import com.nda.blum.DAO.FindUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File

class UserProfileViewModel(application: Application) : BaseViewModel(application) {

    val secureStorage = SecureStorage(this.getApplication())

    val nombreUsuario = MutableLiveData<String>()
    val correoUsuario = MutableLiveData<String>()
    val userProfilePicture = MutableLiveData<String>()
    val numeroTelefono = MutableLiveData<String>()

    private suspend fun susGetUserData(): FindUser? {
        var findUserResponse: FindUser? = null
        val idUsuario = secureStorage.getObject("idUsuario", String::class.java)
        println("Id usuario $idUsuario")
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/datoscoach.php?Id_Coach=$idUsuario")
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

    fun setUserDataValues() {
        coroutineScope.launch {
            _showProgressDialog.value = true
            val userData = susGetUserData()
            if (userData != null) {
                if (userData.code == "0") {
                    nombreUsuario.value = userData.result.Nombre_Usuario
                    correoUsuario.value = userData.result.Correo_Usuario
                    userProfilePicture.value = userData.result.Foto_Usuario
                    numeroTelefono.value = userData.result.Telefono_Usuario
                    _showProgressDialog.value = false
                } else {
                    _showProgressDialog.value = false
                }
            } else {
                _showProgressDialog.value = false
                println("DATOS NULOS")
            }
        }
    }

    fun updateUserData(nombre: String, numeroTelefono: String) {
        coroutineScope.launch {
            val userId = secureStorage.getObject("idUsuario", String::class.java)
            susUpdateUserData(userId!!, nombre, numeroTelefono)
        }
    }

    suspend fun susUpdateUserData(userId: String, nombre: String, numeroTelefono: String){
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/CambiarNombreUsuario.php?id_usuario=$userId&nombre_usuario=$nombre&telefono=$numeroTelefono")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), ActualizarDatosUsuarioResponse::class.java)
                    println(Gson().toJson(parsedResponse))
                    // uploadPictureResponse = parsedResponse
                }else{
                    println(response.code)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun uploadPictureSuspend(imageFile: File, userID: String) {

        // var uploadPictureResponse: CambiarImagenPerfil? = null
        withContext(Dispatchers.IO) {
            val client: OkHttpClient = OkHttpClient().newBuilder()
                .build()
            val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("id_usuario", userID)
                .addFormDataPart(
                    "photo", "user$userID.png",
                    RequestBody.create(
                        "application/octet-stream".toMediaTypeOrNull(),
                        imageFile
                    )
                )
                .build()
            val request: Request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/Documentos/cambiarimagen.php")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()

            val parsedResponse =
                Gson().fromJson(response.body!!.string(), CambiarImagenPerfil::class.java)

            println("Response: $parsedResponse")
         /*   val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/Documentos/cambiarimagen.php?id_usuario=$userID&userfile=$imageFile")
                .method("POST", body)
                .build()
            val response: Response = client.newCall(request).execute()
            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), CambiarImagenPerfil::class.java)
                    println(Gson().toJson(parsedResponse))
                   // uploadPictureResponse = parsedResponse
                }else{
                    println(response.code)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }*/
        }
    }

    fun uploadPicture(imageFile: File) {
        coroutineScope.launch {
            val userId = secureStorage.getObject("idUsuario", String::class.java)
            uploadPictureSuspend(imageFile, userId!!)
        }
    }

}