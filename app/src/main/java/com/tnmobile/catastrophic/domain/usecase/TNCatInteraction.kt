package com.tnmobile.catastrophic.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.data.util.DEFAULT_PAGE_SIZE
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.model.mapper.CatDtoMapper
import com.tnmobile.catastrophic.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class TNCatInteraction
@Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val catDtoMapper: CatDtoMapper
) : TNUseCase {
    override suspend fun getAllCats(): Flow<PagingData<Cat>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { CatPagingDataSource(remoteRepository, catDtoMapper) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}