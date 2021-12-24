package com.mehmetpetek.veriparkimkb.service.api

import com.mehmetpetek.veriparkimkb.model.req.ReqStock
import com.mehmetpetek.veriparkimkb.model.req.ReqStockItem
import com.mehmetpetek.veriparkimkb.model.res.ResHandshake
import com.mehmetpetek.veriparkimkb.model.res.ResSerStock
import com.mehmetpetek.veriparkimkb.model.res.ResSerStockItemDetail
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface StockAPI {
    @Headers("Content-Type: application/json")
    @POST("list")
    fun getStocks(
        @Header( "X-VP-Authorization") auth:String = ResHandshake.authorization,
        @Body body : ReqStock
    ): Single<ResSerStock>

    @Headers("Content-Type: application/json")
    @POST("detail")
    fun getStockDetail(
        @Header(value = "X-VP-Authorization") auth:String = ResHandshake.authorization,
        @Body body : ReqStockItem
    ): Single<ResSerStockItemDetail>
}