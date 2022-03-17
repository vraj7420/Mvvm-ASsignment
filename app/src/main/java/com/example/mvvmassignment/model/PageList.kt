package com.example.mvvmassignment.model

import com.google.gson.annotations.SerializedName

data class PageList (@SerializedName("hits")
                    val pageList:List<PageModel>)
