package com.example.capstone.viewmodel

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
class SplashViewModel @Inject constructor(private val auth: AuthRepository) : ViewModel() {
    private val isLoginData = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = isLoginData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoginData.postValue(auth.isLogin())
        }
    }

}