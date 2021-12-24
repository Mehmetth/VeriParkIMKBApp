package com.mehmetpetek.veriparkimkb.model.res

import com.google.gson.annotations.SerializedName

data class ResSerStock(
    @SerializedName("status")
    val status: ResStatus,
    @SerializedName("stocks")
    val stocks: List<ResSerStockItem>
)