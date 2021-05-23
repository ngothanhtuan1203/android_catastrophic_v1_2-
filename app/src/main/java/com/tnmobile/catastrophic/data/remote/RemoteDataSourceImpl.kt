package com.tnmobile.catastrophic.data.remote

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.tnmobile.catastrophic.data.remote.entity.BaseRespond
import com.tnmobile.catastrophic.data.remote.entity.CatDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: APIService,
    private val app: Application
) : RemoteDataSource {

    private fun isNetworkConnected(context: Application): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    @Throws(IOException::class)
    inline fun readFileResource(
        resourceClass: Class<*>,
        resourcePath: String
    ): ByteArray {
        val `is` =
            resourceClass.classLoader?.getResourceAsStream(resourcePath)
                ?: throw IOException("cannot find resource: $resourcePath")
        return getBytesFromInputStream(`is`)
    }

    @Throws(IOException::class)
    inline fun getBytesFromInputStream(`is`: InputStream): ByteArray {
        val os = ByteArrayOutputStream()
        val buffer = ByteArray(8192)
        var len: Int
        while (`is`.read(buffer).also { len = it } != -1) {
            os.write(buffer, 0, len)
        }
        os.flush()
        return os.toByteArray()
    }

    override suspend fun fetchCats(page: Int, limit: Int): Flow<List<CatDto>> = flow {

        var baseRespond: BaseRespond<List<CatDto>> =
            BaseRespond(null, "Some thing was wrong", false)
        if (!isNetworkConnected(app)) {
            baseRespond = BaseRespond(null, "Your device not connected to internet", false)
        }
        try {
            val respondContainer =
                apiService.fetchCatAPI(page, limit)

            baseRespond = BaseRespond(respondContainer, "Success", true)
        } catch (e: Exception) {
            e.printStackTrace()
            BaseRespond(null, e.localizedMessage, false)
        }

        if (baseRespond.isSuccess) {
            val articleDtoList = baseRespond.data
            emit(articleDtoList!!)
        } else {
            emit(emptyList())
        }
    }
}