package com.example.furniq.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.categories_repository.CategoriesRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesVM(val categoriesRepository: CategoriesRepository) : ViewModel(){

    private val _searchState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val searchState: StateFlow<SealedClass> = _searchState

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            categoriesRepository.getCategories().collect { result ->
                _searchState.value = result
                Log.d("RRR", "viewmodel:--->${result} ")
            }
        }

    }
}