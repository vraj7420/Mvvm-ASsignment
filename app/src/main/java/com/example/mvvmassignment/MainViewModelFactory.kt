package com.example.mvvmassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val pageDataRepository: PageDataRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
     return MainViewModel(pageDataRepository) as T
    }
}