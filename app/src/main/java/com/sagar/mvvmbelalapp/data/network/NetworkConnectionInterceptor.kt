package com.sagar.mvvmbelalapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.sagar.mvvmbelalapp.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable())
            throw NoInternetException("No Internet available")

        return chain.proceed(chain.request())

    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo.isAvailable
    }
}