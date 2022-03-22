package com.example.mvvmassignment.model


data class PageModel(
     var created_at:String="", var title:String="", var author:String="",var switchSelected :Boolean= false){
   fun setSelectItem(selection:Boolean){
       this.switchSelected=selection
   }

}
