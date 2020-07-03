package com.nda.blum.network

import com.nda.blum.DAO.LoginResponse
import com.nda.blum.common.hostBlum
import com.nda.blum.common.urlLogin
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

open class BlumAPI {

    fun getCustomService(): GetBlumService{
        val builder = Retrofit.Builder().baseUrl(hostBlum)
            .addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(
                GsonConverterFactory.create())
            .build()
        return builder.create(GetBlumService::class.java)
    }

    interface GetBlumService{

        @Headers("Content-Type:text/plain; charset=UTF-8")
        @POST(urlLogin)
        fun login(
            @Query(encoded = true, value = "email") email: String,
            @Query(encoded = true, value = "password") password: String
        ): Call<LoginResponse>
    }
}