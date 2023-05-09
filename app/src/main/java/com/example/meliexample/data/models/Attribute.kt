package com.example.meliexample.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attribute(
    val id: String,
    val name: String,
    @SerializedName("value_name") val value: String
) : Parcelable
