package com.example.furniq.ui.power.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AllProductsRepository
import com.example.furniq.repo.auth_repo.PopularRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularVM(private val popularRepository: PopularRepository ) : ViewModel() {

    private val _popularState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val popularState: StateFlow<SealedClass> = _popularState

    init {
        getPopular()
    }

    fun getPopular() {
        viewModelScope.launch {
            popularRepository.getPopular().collect { result ->
                _popularState.value = result
                Log.d("RRR", "viewmodel:--->${result} ")
            }
        }

    }
}