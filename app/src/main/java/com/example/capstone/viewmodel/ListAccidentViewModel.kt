package com.example.capstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.model.AccidentDetail
import com.example.capstone.repository.AuthRepository
import com.example.capstone.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAccidentViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    private val auth: AuthRepository
) :
    ViewModel() {
    private val mutableData = MutableLiveData<ArrayList<AccidentDetail>>()
    val data: LiveData<ArrayList<AccidentDetail>> = mutableData

    private val loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = loading

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val result = auth.currentUser?.uid?.let { repository.getAcceptedData(it) }
            result?.addAll(repository.getAllData())

            mutableData.postValue(result)
            loading.postValue(false)
        }
    }

    fun setAccepted(accidentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            auth.currentUser?.uid?.let { repository.setAccepted(accidentId, it) }
            getData()
            loading.postValue(false)
        }
    }

    fun unsetAccepted(accidentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            repository.setAccepted(accidentId, "")
            getData()
            loading.postValue(false)
        }
    }

    fun setResolved(accidentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            repository.setResolved(accidentId)
            getData()
            loading.postValue(false)
        }
    }
}