package com.example.mvvmassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmassignment.repository.PageDataRepository

class MainViewModelFactory(private val pageDataRepository: PageDataRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
     return MainViewModel(pageDataRepository) as T
    }
}