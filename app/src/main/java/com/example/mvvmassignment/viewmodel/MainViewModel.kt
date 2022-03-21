package com.example.mvvmassignment.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.mvvmassignment.utils.Constant
import com.example.mvvmassignment.repository.PageDataRepository
import com.example.mvvmassignment.model.PageModel


class MainViewModel(private val pageRepository: PageDataRepository) : ViewModel() {
   private var pageData=MutableLiveData<PagingData<PageModel>>()
    val pageDataLiveData:LiveData<PagingData<PageModel>>
        get()=pageData
    var selectedCount=MutableLiveData(0)
    var error=MutableLiveData<String>()
    var loading=MutableLiveData<Boolean>(false)


    fun getPageData(): LiveData<PagingData<PageModel>> {
        return Pager(
            config = PagingConfig(pageSize = Constant.page_size, enablePlaceholders =false),
            pagingSourceFactory = {PageDataRepository()}, initialKey = Constant.initialKey).flow.cachedIn(viewModelScope).asLiveData()
    }
}