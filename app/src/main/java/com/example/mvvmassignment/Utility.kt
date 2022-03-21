package com.example.mvvmassignment

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object{
    const val dateOldFormat="yyyy-MM-dd'T'hh:mm:ss"
    const val dataFormat="MMM d,yyyy"
    }

    fun dateConverter(date:String):String{
        val originalFormat: DateFormat = SimpleDateFormat(dateOldFormat, Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat(dataFormat, Locale.US)
        val origFormatDate = originalFormat.parse(date)
        return targetFormat.format(origFormatDate ?: "")
    }
}