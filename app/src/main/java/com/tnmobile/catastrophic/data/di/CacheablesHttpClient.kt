package com.tnmobile.catastrophic.data.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.data.util.TIME_OUT
import com.tnmobile.catastrophic.utilily.TNLog
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CacheablesHttpClient  @Inject constructor(
    private val app: Application
) {
    val HEADER_CACHE_CONTROL = "Cache-Control"
    val HEADER_PRAGMA = "Pragma"
    val TAG = CacheablesHttpClient::class.simpleName
    private val cacheSize = 5 * 1024 * 1024 // 5 MB
        .toLong()
    fun provideOkHttpClient(): OkHttpClient {
        fun isNetworkConnected(app: Application): Boolean {
            val cm =
                app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting
        }
        val cache = Cache(File(app.cacheDir, "someIdentifier"), cacheSize)
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d(
                    "ServiceGenerator.TAG",
                    "log: http log: $message"
                )
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
        val networkInterceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                TNLog.d(
                    TAG,
                    "network interceptor: called."
                )
                val response = chain.proceed(chain.request())
                val cacheControl = CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build()
                return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(
                        HEADER_CACHE_CONTROL,
                        cacheControl.toString()
                    )
                    .build()
            }
        }
        val offlineInterceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                TNLog.d(
                    TAG,
                    "offline interceptor: called."
                )
                var request = chain.request()

                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (!isNetworkConnected(app)) {
                    val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                return chain.proceed(request)
            }
        }
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor) // used if network off OR on
            .addNetworkInterceptor(networkInterceptor) // only used when network is on
            .addInterceptor(offlineInterceptor)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }
}