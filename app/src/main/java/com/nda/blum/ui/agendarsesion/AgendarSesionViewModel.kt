package com.nda.blum.ui.agendarsesion

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.CitasDisponiblesResponse
import com.nda.blum.DAO.GuardarCitaResponse
import com.nda.blum.db.dao.UserDao
import com.nda.blum.db.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

// https://retosalvatucasa.com/ws_app_nda/guardarcita.php?idcoach=23&idusuario=35&fecha=12/12/2020&hora=12hr&idfecha=1
class AgendarSesionViewModel(val database: UserDao, application: Application) : BaseViewModel(application) {



    val idCoach = MutableLiveData<String>()
    val fecha = MutableLiveData<String>()
    val hora = MutableLiveData<String>()
    val currentDate = MutableLiveData<String>()
    val fechasDisponibles = MutableLiveData<CitasDisponiblesResponse?>()
    val idFecha = MutableLiveData<String>()

    private val _citasDisponibles = MutableLiveData<Boolean>()
    val citasDisponibles : LiveData<Boolean>
    get() = _citasDisponibles

    private val _sesionAgendadaSuccess = MutableLiveData<Boolean>()
    val sesionAgendadaSuccess : LiveData<Boolean>
    get() = _sesionAgendadaSuccess

    fun citasDisponibles() {
        coroutineScope.launch {
            try{
                val citasDisponiblesResult: CitasDisponiblesResponse? = getCitasDisponibles()
                if (citasDisponiblesResult != null) {
                    if (citasDisponiblesResult.code == "0") {
                        fechasDisponibles.value = citasDisponiblesResult
                        _citasDisponibles.value = true
                    } else {
                        print(citasDisponiblesResult.message)
                        _citasDisponibles.value = false
                    }
                } else {
                    println("Ocurrio un erro al obtener las citas")
                    _citasDisponibles.value = false
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

    fun agendarSesion(){
        coroutineScope.launch {
            try{
                val guardarCitaResult = susAgendarSesion()
                if(guardarCitaResult.code == "0"){
                    println("Se agendó la sesión")
                    _sesionAgendadaSuccess.value = true
                    citasDisponibles()
                }else{
                    println("Ocurrió un error al agendar la sesión")
                    _sesionAgendadaSuccess.value = false
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private suspend fun susAgendarSesion(): GuardarCitaResponse {
        var responseGuardarCita: GuardarCitaResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val userData = susGetUserData()
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/guardarcita.php?idcoach=${idCoach.value}&idusuario=${userData!!.userServerId}&fecha=${fecha.value}&hora=${hora.value}&idfecha=${idFecha.value}")
                .method("POST", body)
                .build()

            println(request.url)

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(response.body!!.string(), GuardarCitaResponse::class.java)
                    println("RESPONSE: $parsedResponse")
                    responseGuardarCita = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return responseGuardarCita!!
    }

    private suspend fun susGetUserData(): User? {
        return withContext(Dispatchers.IO) {
            database.getAllUserData()
        }
    }

    private suspend fun getCitasDisponibles(): CitasDisponiblesResponse {
        var responseCitas: CitasDisponiblesResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscardisponibles.php?idcoach=${idCoach.value}&fecha=${fecha.value}")
                .method("POST", body)
                .build()

            println(request.url)

            val response: Response = client.newCall(request).execute()

            try {
                if (response.code == 200) {
                    val parsedResponse =
                        Gson().fromJson(
                            response.body!!.string(),
                            CitasDisponiblesResponse::class.java
                        )
                    println("RESPONSE: $parsedResponse")
                    responseCitas = parsedResponse
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return responseCitas!!
    }
}
