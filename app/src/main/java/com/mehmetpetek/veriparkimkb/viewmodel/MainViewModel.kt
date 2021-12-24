package com.mehmetpetek.veriparkimkb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetpetek.veriparkimkb.model.req.ReqHandsake
import com.mehmetpetek.veriparkimkb.model.res.ResSerHandshake
import com.mehmetpetek.veriparkimkb.service.HandshakeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val handshakeService = HandshakeService()
    val handshake = MutableLiveData<ResSerHandshake>()
    val error = MutableLiveData<String>()


    fun getHandshake(queries: ReqHandsake) {
        handshakeService.getApiService().handshake(queries).enqueue(object : Callback<ResSerHandshake> {
            override fun onResponse(call: Call<ResSerHandshake>, response: Response<ResSerHandshake>) {
                if (response.isSuccessful){
                    handshake.value = response.body()
                }else{
                    error.value = response.errorBody().toString()
                }
            }

            override fun onFailure(call: Call<ResSerHandshake>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}