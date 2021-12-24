package com.mehmetpetek.veriparkimkb.model.res

import com.google.gson.annotations.SerializedName

data class ResSerHandshake(
    @SerializedName("aesIV")
    val aesIV: String,
    @SerializedName("aesKey")
    val aesKey: String,
    @SerializedName("authorization")
    val authorization: String,
    @SerializedName("lifeTime")
    val lifeTime: String,
    @SerializedName("status")
    val resSerStatus: ResSerStatus
)
