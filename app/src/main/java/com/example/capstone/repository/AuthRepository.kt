package com.example.capstone.repository

import android.content.Context
import android.util.Log
import com.example.capstone.helper.ToastHelper
import com.example.capstone.model.UserDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    var currentUser: FirebaseUser? = null
    var userDetail: UserDetail? = null

    fun isLogin(): Boolean {
        if (currentUser == null) {
            currentUser = auth.currentUser
        }

        notifSubscribe()
        return currentUser != null
    }

    suspend fun signUp(
        email: String,
        pass: String,
        phone: Number,
        username: String,
        context: Context
    ): Boolean {
        try {
            auth.createUserWithEmailAndPassword(email, pass).await()
        } catch (e: FirebaseAuthException) {
            Log.d("HEHE", "ERROR", e)
            withContext(Dispatchers.Main) {
                e.localizedMessage?.let { ToastHelper.showToast(it, context) }
            }
        }

        currentUser = auth.currentUser
        notifSubscribe()

        try {
            currentUser?.let {
                val data = hashMapOf(
                    "phone" to phone,
                    "username" to username
                )
                db.collection("user").document(it.uid).set(data).await()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("HEHE", "ERROR", e)
        }

        return currentUser != null
    }

    private suspend fun getUserDetail() {
        val tempData = currentUser?.let { db.collection("user").document(it.uid).get().await() }
        userDetail = tempData?.toObject(UserDetail::class.java)
    }

    private fun notifSubscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("violence")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("HEHE", "Subscirbed")
                }
            }
            .addOnFailureListener { e ->
                Log.d("HEHE", e.toString())
            }
    }

    suspend fun signIn(email: String, pass: String, context: Context): Boolean {
        try {
            auth.signInWithEmailAndPassword(email, pass).await()
        } catch (e: FirebaseAuthException) {
            Log.d("HEHE", "ERROR", e)
            withContext(Dispatchers.Main) {
                e.localizedMessage?.let { ToastHelper.showToast(it, context) }
            }
        }

        currentUser = auth.currentUser
        notifSubscribe()
        CoroutineScope(Dispatchers.IO).launch {
            getUserDetail()
        }

        return currentUser != null
    }
}