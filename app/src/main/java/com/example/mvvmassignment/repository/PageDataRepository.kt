package com.example.mvvmassignment.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmassignment.model.PageModel
import com.example.mvvmassignment.network.BaseService
import retrofit2.HttpException
import java.io.IOException

class PageDataRepository : PagingSource<Int, PageModel>() {
    override fun getRefreshKey(state: PagingState<Int, PageModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PageModel> {
        return try {
            val nextPageNumber = params.key ?: 1
           val result=BaseService().getBaseApi().getData("story", nextPageNumber.toString())
            LoadResult.Page(
                data = result.pageList,
                prevKey = params.key,
                nextKey = params.key?.plus(1) ?: STARTING_PAGE_INDEX.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }


    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}