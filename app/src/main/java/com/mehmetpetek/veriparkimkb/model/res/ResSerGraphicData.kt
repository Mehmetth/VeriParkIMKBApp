package com.mehmetpetek.veriparkimkb.model.res

import com.google.gson.annotations.SerializedName

data class ResSerGraphicData(
    @SerializedName("day")
    val day: Int,
    @SerializedName("value")
    val value: Double
)