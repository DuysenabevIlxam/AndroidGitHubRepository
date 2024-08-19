package com.example.furniq.ui.power

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AllProductsRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.Dispatchers
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


    private val _postState = MutableStateFlow<SealedClass>(SealedClass.Empty)
    val postState: StateFlow<SealedClass> = _postState

    fun postFavourites(productId :Int) {
        _postState.value = SealedClass.Loading
        viewModelScope.launch(Dispatchers.IO) {
            powerRepository.postFavourites(productId).collect { result ->
                _postState.value = result
                Log.d("CCC", "postFavouritesViewModel:---->${productId} ")

            }
        }
    }


    private val _deleteState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val deleteState: StateFlow<SealedClass> = _deleteState

    fun deleteFavourites(productId :Int) {
        _postState.value = SealedClass.Loading
        viewModelScope.launch(Dispatchers.IO) {
            powerRepository.deleteFavourites(productId).collect { result ->
                _postState.value = result
                Log.d("CCC", "postFavouritesViewModel:---->${productId} ")

            }
        }
    }
}