package com.example.mvvmassignment.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmassignment.utils.Utility
import com.example.mvvmassignment.model.PageModel
import com.example.mvvmassignment.network.BaseService
import retrofit2.HttpException
import java.io.IOException

class PageDataRepository : PagingSource<Int, PageModel>() {
    override fun getRefreshKey(state: PagingState<Int, PageModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PageModel> {
        val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {

            val result=BaseService().getBaseApi().getData(Utility.story, nextPageNumber.toString())
            val nextKey =
                if (result.pageList.isEmpty()) {
                    null
                } else {
                    nextPageNumber + 1
                }
            LoadResult.Page(
                data = result.pageList,
                prevKey =null,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            Log.d("error",e.message.toString())
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d("error",e.message.toString())
            LoadResult.Error(e)
        }
    }


    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}