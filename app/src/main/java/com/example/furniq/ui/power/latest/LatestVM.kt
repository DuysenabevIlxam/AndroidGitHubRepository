package com.example.furniq.ui.power.latest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.data.latest_data.LatestData
import com.example.furniq.repo.auth_repo.LatestRepository
import com.example.furniq.repo.auth_repo.PopularRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LatestVM(private val latestRepository: LatestRepository)  : ViewModel() {

    private val _latestState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val latestState: StateFlow<SealedClass> = _latestState

    init {
        getLatest()
    }

    fun getLatest() {
        viewModelScope.launch {
            latestRepository.getLatest().collect { result ->
                _latestState.value = result
                Log.d("RRR", "viewmodel:--->${result} ")
            }
        }

    }
}