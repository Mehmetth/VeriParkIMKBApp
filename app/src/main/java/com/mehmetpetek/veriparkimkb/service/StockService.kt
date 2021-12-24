package com.mehmetpetek.veriparkimkb.service

import com.mehmetpetek.veriparkimkb.model.req.ReqStock
import com.mehmetpetek.veriparkimkb.model.req.ReqStockItem
import com.mehmetpetek.veriparkimkb.model.res.ResSerStock
import com.mehmetpetek.veriparkimkb.model.res.ResSerStockItemDetail
import com.mehmetpetek.veriparkimkb.service.api.StockAPI
import com.mehmetpetek.veriparkimkb.utils.Constants
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StockService {
    private val api = Retrofit.Builder()
        .baseUrl(Constants.StockUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(StockAPI::class.java)

    fun getAllStockData(period : ReqStock) : Single<ResSerStock> {
        return api.getStocks(body = period)
    }

    fun getStockDetailData(period : ReqStockItem) : Single<ResSerStockItemDetail> {
        return api.getStockDetail(body = period)
    }
}