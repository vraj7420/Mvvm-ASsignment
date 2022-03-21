package com.example.mvvmassignment.view_model

import androidx.lifecycle.*
import androidx.paging.*
import com.example.mvvmassignment.Constant
import com.example.mvvmassignment.Utility
import com.example.mvvmassignment.repository.PageDataRepository
import com.example.mvvmassignment.model.PageModel


class MainViewModel(private val pageRepository: PageDataRepository) : ViewModel() {
   private var pageData=MutableLiveData<PagingData<PageModel>>()
    val pageDataLiveData:LiveData<PagingData<PageModel>>
        get()=pageData


    fun getPageData(): LiveData<PagingData<PageModel>> {
        return Pager(
            config = PagingConfig(pageSize =Constant.page_size, enablePlaceholders =false),
            pagingSourceFactory = {PageDataRepository()}, initialKey =Constant.initialKey).flow.cachedIn(viewModelScope).asLiveData()
    }
}