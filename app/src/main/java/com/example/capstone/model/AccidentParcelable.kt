package com.example.capstone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccidentParcelable(
    val user: String,
    val phone: Number,
    val address: String,
    val photo: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable
