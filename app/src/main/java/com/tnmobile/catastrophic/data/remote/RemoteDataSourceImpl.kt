package com.tnmobile.catastrophic.data.remote

import android.app.Application
import com.tnmobile.catastrophic.data.remote.entity.BaseRespond
import com.tnmobile.catastrophic.data.remote.entity.CatDto
import com.tnmobile.catastrophic.utilily.Util.Companion.isNetworkConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: APIService,
    private val app: Application
) : RemoteDataSource {

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