package com.nda.blum.ui.agendarsesion

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jcloquell.androidsecurestorage.SecureStorage
import com.nda.blum.BaseViewModel
import com.nda.blum.DAO.CitasDisponiblesResponse
import com.nda.blum.DAO.GuardarCitaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class AgendarSesionViewModel( application: Application) : BaseViewModel(application) {

    val idCoach = MutableLiveData<String>()
    val fecha = MutableLiveData<String>()
    val hora = MutableLiveData<String>()
    val currentDate = MutableLiveData<String>()
    val fechasDisponibles = MutableLiveData<CitasDisponiblesResponse?>()
    val idFecha = MutableLiveData<String>()

    val secureStorage = SecureStorage(getApplication())


    private val _citasDisponibles = MutableLiveData<Boolean>()
    val citasDisponibles : LiveData<Boolean>
    get() = _citasDisponibles

    private val _sesionAgendadaSuccess = MutableLiveData<Boolean>()
    val sesionAgendadaSuccess : LiveData<Boolean>
    get() = _sesionAgendadaSuccess

    fun citasDisponibles() {
        coroutineScope.launch {
            try{
                _showProgressDialog.value = true
                val citasDisponiblesResult: CitasDisponiblesResponse? = getCitasDisponibles()
                if (citasDisponiblesResult != null) {
                    if (citasDisponiblesResult.code == "0") {
                        fechasDisponibles.value = citasDisponiblesResult
                        _citasDisponibles.value = true
                        _showProgressDialog.value = false

                    } else {
                        print(citasDisponiblesResult.message)
                        _citasDisponibles.value = false
                        _showProgressDialog.value = false

                    }
                } else {
                    println("Ocurrio un erro al obtener las citas")
                    _citasDisponibles.value = false
                    _showProgressDialog.value = false

                }
            }catch (e:Exception){
                e.printStackTrace()
                _showProgressDialog.value = false
            }

        }
    }

    fun agendarSesion(){
        coroutineScope.launch {
            _showProgressDialog.value = true
            try{
                val guardarCitaResult = susAgendarSesion()
                if(guardarCitaResult.code == "0"){
                    println("Se agendó la sesión")
                    _sesionAgendadaSuccess.value = true
                    _showProgressDialog.value = false
                    citasDisponibles()
                }else{
                    println("Ocurrió un error al agendar la sesión")
                    _sesionAgendadaSuccess.value = false
                    _showProgressDialog.value = false
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                _showProgressDialog.value = false
            }
        }
    }

    private suspend fun susAgendarSesion(): GuardarCitaResponse {
        var responseGuardarCita: GuardarCitaResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val idCoach = secureStorage.getObject("idCoach", String::class.java)
            val idUsaurio = secureStorage.getObject("idUsuario", String::class.java)
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/guardarcita.php?idcoach=$idCoach&idusuario=$idUsaurio&fecha=${fecha.value}&hora=${hora.value}&idfecha=${idFecha.value}")
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

    private suspend fun getCitasDisponibles(): CitasDisponiblesResponse {
        var responseCitas: CitasDisponiblesResponse? = null
        withContext(Dispatchers.IO) {
            val client = OkHttpClient().newBuilder().build()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "")
            val idCoach = secureStorage.getObject("idCoach", String::class.java)
            val request = Request.Builder()
                .url("https://retosalvatucasa.com/ws_app_nda/buscardisponibles.php?idcoach=$idCoach&fecha=${fecha.value}")
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
