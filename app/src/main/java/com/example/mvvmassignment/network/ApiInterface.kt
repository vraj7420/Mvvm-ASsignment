package com.example.mvvmassignment.network

import com.example.mvvmassignment.model.PageList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search_by_date")
    suspend fun  getData(@Query("tags")tag:String,@Query("page")pageNumber:String): PageList
}