package com.example.furniq.ui.power.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.auth_repo.LatestRepository
import com.example.furniq.repo.favourites.GetFavouritesRepository
import com.example.furniq.sealedClass.SealedClass
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
}