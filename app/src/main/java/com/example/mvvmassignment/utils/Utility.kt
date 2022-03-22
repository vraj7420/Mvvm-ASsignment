package com.example.mvvmassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object{
    const val dateOldFormat="yyyy-MM-dd'T'hh:mm:ss"
    const val dataFormat="MMM d,yyyy"
        const val story="story"
    }

    fun dateConverter(date:String):String{
        val originalFormat: DateFormat = SimpleDateFormat(dateOldFormat, Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat(dataFormat, Locale.US)
        val origFormatDate = originalFormat.parse(date)
        return targetFormat.format(origFormatDate ?: "")
    }
    fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}