package com.mehmetpetek.veriparkimkb.model.res

import com.google.gson.annotations.SerializedName

data class ResSerStatus(
    @SerializedName("error")
    val error: Error,
    @SerializedName("isSuccess")
    val isSuccess: Boolean
)