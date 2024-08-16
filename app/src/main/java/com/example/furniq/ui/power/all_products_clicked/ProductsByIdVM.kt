package com.example.furniq.ui.power.all_products_clicked

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AllProductsRepository
import com.example.furniq.repo.auth_repo.ProductByIdRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsByIdVM(private val pbiRepository: ProductByIdRepository) :ViewModel() {

    private val _pbiState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val pbiState: StateFlow<SealedClass> = _pbiState
    var productID=0

    init {


        getProductsById(productID)
    }

    fun getProductsById(id :Int) {

        productID=id
        Log.d("QQQ", "ID:${id} ")
        viewModelScope.launch {
            pbiRepository.getProductById(id).collect { result ->
                _pbiState.value = result
               // Log.d("WWW", "viewmodel:--->${result} ")
            }
        }

    }
}