package com.example.capstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.model.AccidentDetail
import com.example.capstone.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: FirestoreRepository) :
    ViewModel() {

    private val mutableData = MutableLiveData<ArrayList<AccidentDetail>>()
    val data: LiveData<ArrayList<AccidentDetail>> = mutableData

    private val loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = loading

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            mutableData.postValue(repository.getHistory())
            loading.postValue(false)
        }
    }

}