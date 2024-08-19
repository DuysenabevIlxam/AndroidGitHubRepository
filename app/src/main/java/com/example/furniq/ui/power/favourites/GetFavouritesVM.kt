package com.example.furniq.ui.power.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.LatestRepository
import com.example.furniq.repo.favourites.GetFavouritesRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GetFavouritesVM(private val getFavouritesRepository: GetFavouritesRepository) : ViewModel() {

    private val _favouriteState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val favouriteState: StateFlow<SealedClass> = _favouriteState

    init {
        getFavourites()
    }

     fun getFavourites() {
        viewModelScope.launch {
            getFavouritesRepository.getFavourites().collect { result ->
                _favouriteState.value = result
                Log.d("LLL", "getFavourites--->:${result} ")
            }
        }

    }

    private val _postState = MutableStateFlow<SealedClass>(SealedClass.Empty)
    val postState: StateFlow<SealedClass> = _postState

    fun postFavourites(productId :Int) {
        _postState.value = SealedClass.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getFavouritesRepository.postFavourites(productId).collect { result ->
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
            getFavouritesRepository.deleteFavourites(productId).collect { result ->
                _postState.value = result
                Log.d("CCC", "postFavouritesViewModel:---->${productId} ")

            }
        }
    }

}