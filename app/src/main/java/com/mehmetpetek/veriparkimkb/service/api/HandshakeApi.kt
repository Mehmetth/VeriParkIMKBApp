package com.mehmetpetek.veriparkimkb.service.api

import com.mehmetpetek.veriparkimkb.model.req.ReqHandsake
import com.mehmetpetek.veriparkimkb.model.res.ResSerHandshake
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface HandshakeApi {

    @Headers("Content-Type: application/json")
    @POST("start")
    fun handshake(
        @Body queries : ReqHandsake
    ): Call<ResSerHandshake>

}