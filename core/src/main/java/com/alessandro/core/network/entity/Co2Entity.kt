package com.alessandro.core.network.entity

import com.google.gson.annotations.SerializedName

data class Co2Entity(
    @SerializedName("id") val id: Int,
    @SerializedName("value") val value: Int,
    @SerializedName("created_at") val createdAt: String
) {
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}