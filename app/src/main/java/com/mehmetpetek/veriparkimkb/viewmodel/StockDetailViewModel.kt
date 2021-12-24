package com.mehmetpetek.veriparkimkb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetpetek.veriparkimkb.model.req.ReqStockItem
import com.mehmetpetek.veriparkimkb.model.res.ResSerStockItemDetail
import com.mehmetpetek.veriparkimkb.service.StockService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class StockDetailViewModel : ViewModel() {
    private val dataApiService = StockService()
    private val disposable = CompositeDisposable()

    val data = MutableLiveData<ResSerStockItemDetail>()
    val dataError = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()

    fun refreshData(id: ReqStockItem){
        getDataFromApi(id)
    }

    private fun getDataFromApi(id: ReqStockItem){
        dataLoading.value= true

        disposable.add(
            dataApiService.getStockDetailData(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResSerStockItemDetail>(){
                    override fun onSuccess(t: ResSerStockItemDetail) {

                        print("onSuccess ${t.price} || ${t.difference} || ${t.volume} || ${t.bid} || ${t.offer} || ${t.lowest} || ${t.highest} || ${t.count} || ${t.maximum} || ")

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