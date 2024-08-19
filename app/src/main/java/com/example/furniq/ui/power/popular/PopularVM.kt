package com.example.furniq.ui.power.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.AllProductsRepository
import com.example.furniq.repo.auth_repo.PopularRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.Dispatchers
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


    private val _postState = MutableStateFlow<SealedClass>(SealedClass.Empty)
    val postState: StateFlow<SealedClass> = _postState

    fun postFavourites(productId :Int) {
        _postState.value = SealedClass.Loading
        viewModelScope.launch(Dispatchers.IO) {
            popularRepository.postFavourites(productId).collect { result ->
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
            popularRepository.deleteFavourites(productId).collect { result ->
                _postState.value = result
                Log.d("CCC", "postFavouritesViewModel:---->${productId} ")

            }
        }
    }

}