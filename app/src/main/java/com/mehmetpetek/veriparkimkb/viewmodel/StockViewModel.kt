package com.mehmetpetek.veriparkimkb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetpetek.veriparkimkb.model.req.ReqStock
import com.mehmetpetek.veriparkimkb.model.res.ResSerStock
import com.mehmetpetek.veriparkimkb.service.StockService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class StockViewModel : ViewModel() {
    private val dataApiService = StockService()
    private val disposable = CompositeDisposable()

    val data = MutableLiveData<ResSerStock>()
    val dataError = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()

    fun refreshData(id: ReqStock){
        getDataFromApi(id)
    }

    private fun getDataFromApi(id: ReqStock){
        dataLoading.value= true

        disposable.add(
            dataApiService.getAllStockData(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResSerStock>(){
                    override fun onSuccess(t: ResSerStock) {
                        data.value=t
                        dataError.value=false
                        dataLoading.value=false
                    }
                    override fun onError(e: Throwable) {
                        dataLoading.value=false
                        dataError.value=true
                        e.printStackTrace()
                    }
                })
        )
    }
}