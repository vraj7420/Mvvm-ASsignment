package com.example.mvvmassignment.viewmioel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.mvvmassignment.repository.PageDataRepository
import com.example.mvvmassignment.model.PageModel


class MainViewModel(private val pageRepository: PageDataRepository) : ViewModel() {
   private var pageData=MutableLiveData<PagingData<PageModel>>()
    val pageDataLiveData:LiveData<PagingData<PageModel>>
        get()=pageData


    fun getPageData1(): LiveData<PagingData<PageModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pageRepository }).flow.cachedIn(viewModelScope).asLiveData()
    }



  /* fun getPageData(): Flow<PagingData<PageModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pageRepository }).flow.cachedIn(viewModelScope)
    }*/


}