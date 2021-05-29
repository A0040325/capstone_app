package com.example.capstone.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val auth: FirebaseAuth) {

    var currentUser: FirebaseUser? = null

    fun isLogin(): Boolean {
        if (currentUser == null) {
            currentUser = auth.currentUser
        }

        return currentUser != null
    }

    suspend fun signUp(email: String, pass: String): Boolean {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser = auth.currentUser
                }
            }.await()

        return currentUser != null
    }

    suspend fun signIn(email: String, pass: String): Boolean {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser = auth.currentUser
                }
            }.await()

        return currentUser != null
    }
}