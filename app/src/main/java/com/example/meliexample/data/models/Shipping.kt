package com.example.meliexample.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shipping(
    @SerializedName("store_pick_up") val storePickUp: Boolean,
    @SerializedName("free_shipping") val freeShipping: Boolean,
    @SerializedName("logistic_type") val logisticType: String,
    val mode: String,
    val tags: ArrayList<String>,
    val promise: String?
) : Parcelable
