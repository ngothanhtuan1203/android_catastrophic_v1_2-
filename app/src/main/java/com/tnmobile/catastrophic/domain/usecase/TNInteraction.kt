package com.tnmobile.catastrophic.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tnmobile.catastrophic.utilily.DEFAULT_PAGE_SIZE
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.model.News
import com.tnmobile.catastrophic.domain.model.ReaderViewItem
import com.tnmobile.catastrophic.domain.model.mapper.CatDtoMapper
import com.tnmobile.catastrophic.domain.repository.RemoteRepository
import com.tnmobile.catastrophic.utilily.SmartViewHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class TNInteraction
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

    override suspend fun getAllNews(): Flow<List<News>> {
        return remoteRepository.getAllNews()
    }

    override suspend fun parseData(url: String): Flow<ReaderViewItem?> {
        return SmartViewHelper().parse(url);
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}