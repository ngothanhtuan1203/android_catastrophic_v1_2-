package com.tnmobile.catastrophic.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.data.util.DEFAULT_PAGE_INDEX
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.model.mapper.CatDtoMapper
import com.tnmobile.catastrophic.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.collect

/**
 * provides the data source for paging lib from api calls
 */
@ExperimentalPagingApi
class CatPagingDataSource(
    private val remoteRepository: RemoteRepository,
    private val catDtoMapper: CatDtoMapper
) :
    PagingSource<Int, Cat>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        var result: LoadResult<Int, Cat> = LoadResult.Error(Exception("Empty data"))

        remoteRepository.getCats(page, params.loadSize).collect {
            val modelList = catDtoMapper.toDomainList(it)
            if (modelList.isEmpty()) {
                result = LoadResult.Error(Exception("Empty data"))
            } else {
                result = LoadResult.Page(
                    modelList, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            }
        }

        return result
    }

}