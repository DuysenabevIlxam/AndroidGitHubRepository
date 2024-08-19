package com.example.furniq.ui.power.latest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.data.latest_data.LatestData
import com.example.furniq.repo.auth_repo.LatestRepository
import com.example.furniq.repo.auth_repo.PopularRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.Dispatchers
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

    private val _postState = MutableStateFlow<SealedClass>(SealedClass.Empty)
    val postState: StateFlow<SealedClass> = _postState

    fun postFavourites(productId :Int) {
        _postState.value = SealedClass.Loading
        viewModelScope.launch(Dispatchers.IO) {
            latestRepository.postFavourites(productId).collect { result ->
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
            latestRepository.deleteFavourites(productId).collect { result ->
                _postState.value = result
                Log.d("CCC", "postFavouritesViewModel:---->${productId} ")

            }
        }
    }

}