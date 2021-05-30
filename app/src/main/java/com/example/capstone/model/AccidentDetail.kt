package com.example.capstone.model

import com.google.firebase.firestore.GeoPoint

data class AccidentDetail(
    val user: String = "",
    val phone: String = "",
    val address: String = "",
    val photo: String = "",
    val resolved_by: String = "",
    var isAccepted: Boolean = false,
    var accidentId: String = "",
    val coordinate: GeoPoint = GeoPoint(0.0, 0.0)
)
