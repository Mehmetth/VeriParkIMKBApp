package com.mehmetpetek.veriparkimkb.service

import com.mehmetpetek.veriparkimkb.service.api.HandshakeApi
import com.mehmetpetek.veriparkimkb.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HandshakeService {
    private val api : HandshakeApi = Retrofit.Builder()
        .baseUrl(Constants.HandshakeUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HandshakeApi::class.java)

    fun getApiService(): HandshakeApi {
        return api
    }
}