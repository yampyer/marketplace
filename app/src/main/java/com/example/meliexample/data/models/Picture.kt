package com.example.meliexample.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture(
    val id: String,
    val url: String,
    @SerializedName("secure_url") val secureUrl: String
) : Parcelable
