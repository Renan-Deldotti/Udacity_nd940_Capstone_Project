package com.test.watched.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.getAppSharedPreferences(): SharedPreferences = getSharedPreferences(Constants.WATCHED_APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.hasInternetConnectivity(): Boolean {
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return capabilities?.let {
        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) or
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) or
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } ?: false
}