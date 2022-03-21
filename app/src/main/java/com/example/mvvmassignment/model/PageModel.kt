package com.example.mvvmassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class PageModel(
     var created_at:String="", var title:String="", var author:String=""){
    var isSelected = false
   fun setSelectItem(selection:Boolean){
       isSelected=selection
   }
}
