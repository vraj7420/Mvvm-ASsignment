package com.example.mvvmassignment.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.mvvmassignment.utils.Constant
import com.example.mvvmassignment.repository.PageDataRepository
import com.example.mvvmassignment.model.PageModel


class MainViewModel(private val pageRepository: PageDataRepository) : ViewModel() {
    var pageData=MutableLiveData<PagingData<PageModel>>()
     var pageDataLiveData=(Pager(
         config = PagingConfig(pageSize = Constant.page_size),
         pagingSourceFactory = {PageDataRepository()}, initialKey = Constant.initialKey).flow.cachedIn(viewModelScope).asLiveData())
    var selectedCount=MutableLiveData(0)
    var error=MutableLiveData<String>()
    var loading=MutableLiveData<Boolean>()
    var recyclerViewVisibility=MutableLiveData(error.value?.isEmpty() == true && loading.value ==false)

}