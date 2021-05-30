package com.example.capstone.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val auth: AuthRepository) : ViewModel() {
    private val loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = loading

    private val result = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = result

    fun login(email: String, pass: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val hasil = auth.signIn(email, pass, context)
            loading.postValue(false)
            result.postValue(hasil)
        }
    }
}