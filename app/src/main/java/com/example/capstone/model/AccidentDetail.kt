package com.example.capstone.model

import com.google.firebase.firestore.GeoPoint

data class AccidentDetail(
    val user: String = "",
    val phone: Number = 0,
    val address: String = "",
    val photo: String = "",
    val coordinate: GeoPoint = GeoPoint(0.0, 0.0)
)
