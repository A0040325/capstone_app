package com.example.capstone.repository

import android.content.Context
import android.util.Log
import com.example.capstone.helper.ToastHelper
import com.example.capstone.model.AccidentDetail
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) {

    suspend fun getAcceptedData(uid: String): ArrayList<AccidentDetail>? {
        val resultData = arrayListOf<AccidentDetail>()

        try {
            firestore.collection("data")
                .whereEqualTo("resolved_by", uid)
                .whereEqualTo("resolved", false)
                .get()
                .addOnSuccessListener { data ->
                    for (document in data) {
                        val tempData: AccidentDetail = document.toObject(AccidentDetail::class.java)
                        tempData.isAccepted = true
                        tempData.accidentId = document.id
                        resultData.add(tempData)
                    }
                }
                .await()
        } catch (e: FirebaseFirestoreException) {
            Log.d("HEHE", e.code.toString())
            withContext(Dispatchers.Main) {
                ToastHelper.showToast(e.code.toString(), context)
            }
        }

        return resultData
    }

    suspend fun getHistory(): ArrayList<AccidentDetail> {
        val resultData = arrayListOf<AccidentDetail>()

        try {
            firestore.collection("data")
                .whereEqualTo("resolved", true)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val data: AccidentDetail = document.toObject(AccidentDetail::class.java)
                        data.isAccepted = false
                        data.accidentId = document.id
                        resultData.add(data)
                    }
                }.await()
        } catch (e: FirebaseFirestoreException) {
            Log.d("HEHE", e.code.toString())
            withContext(Dispatchers.Main) {
                ToastHelper.showToast(e.code.toString(), context)
            }
        }

        return resultData
    }

    suspend fun getAllData(): ArrayList<AccidentDetail> {
        val resultData = arrayListOf<AccidentDetail>()

        try {
            firestore.collection("data")
                .whereEqualTo("resolved", false)
                .whereEqualTo("resolved_by", "")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val data: AccidentDetail = document.toObject(AccidentDetail::class.java)
                        data.isAccepted = false
                        data.accidentId = document.id
                        resultData.add(data)
                    }
                }.await()
        } catch (e: FirebaseFirestoreException) {
            Log.d("HEHE", e.code.toString())
            withContext(Dispatchers.Main) {
                ToastHelper.showToast(e.code.toString(), context)
            }
        }

        return resultData
    }

    suspend fun setAccepted(accidentId: String, uid: String) {
        try {
            firestore.collection("data").document(accidentId).update(
                mapOf("resolved_by" to uid)
            ).await()
        } catch (e: FirebaseFirestoreException) {
            Log.d("HEHE", "ERROR", e)
        }
    }

    suspend fun setResolved(accidentId: String) {
        try {
            firestore.collection("data").document(accidentId).update(
                mapOf("resolved" to true)
            ).await()
        } catch (e: FirebaseFirestoreException) {
            Log.d("HEHE", "ERROR", e)
        }
    }
}