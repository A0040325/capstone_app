package com.example.capstone.repository

import android.util.Log
import com.example.capstone.model.AccidentDetail
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun getAllData(): ArrayList<AccidentDetail> {
        val resultData = arrayListOf<AccidentDetail>()

        firestore.collection("data")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data: AccidentDetail = document.toObject(AccidentDetail::class.java)
                    resultData.add(data)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("HEHE", "ERROR", exception)
            }
            .await()

        return resultData
    }
}