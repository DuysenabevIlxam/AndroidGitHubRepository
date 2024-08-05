package com.example.furniq.ui.power

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AllProductsRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PowerVM(private val powerRepository: AllProductsRepository) : ViewModel() {

    private val _powerState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val powerState: StateFlow<SealedClass> = _powerState

    init {
        getAllProducts()
    }

     fun getAllProducts() {
        viewModelScope.launch {
            powerRepository.getAllProducts().collect { result ->
                _powerState.value = result
                Log.d("TTT", "viewmodel:--->${result} ")
            }
        }

    }
}