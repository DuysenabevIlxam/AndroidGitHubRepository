package com.example.furniq.ui.product_by_id_categories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniq.repo.categories_repository.CategoryByIdRepository
import com.example.furniq.sealedClass.SealedClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesByIdVM(val categoryByIdRepository :CategoryByIdRepository ) : ViewModel() {
    var categoryId=1000000000
    private val _categoryState = MutableStateFlow<SealedClass>(SealedClass.Loading)
    val categoryState: StateFlow<SealedClass> = _categoryState


    init {

        getCategories(categoryId)
        Log.d("WWW", "init:${categoryId} ")

    }

    fun getCategories(id : Int) {
        categoryId = id
        Log.d("QQQ", "getCategories---->${categoryId}: ")


        viewModelScope.launch {
            categoryByIdRepository.getCategoriesById(categoryId).collect { result ->
                _categoryState.value = result
                Log.d("RRR", "viewmodel:--->${result} ")

            }
        }

    }
}