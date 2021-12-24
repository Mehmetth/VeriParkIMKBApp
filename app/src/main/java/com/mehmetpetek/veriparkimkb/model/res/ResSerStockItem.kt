package com.mehmetpetek.veriparkimkb.model.res

import com.google.gson.annotations.SerializedName

data class ResSerStockItem(
    @SerializedName("bid")
    val bid: Double,
    @SerializedName("difference")
    val difference: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isDown")
    val isDown: Boolean,
    @SerializedName("isUp")
    val isUp: Boolean,
    @SerializedName("offer")
    val offer: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("volume")
    val volume: Double
)
