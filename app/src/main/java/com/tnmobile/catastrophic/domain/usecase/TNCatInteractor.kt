package com.tnmobile.catastrophic.domain.usecase

import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.model.mapper.CatDtoMapper
import com.tnmobile.catastrophic.domain.repository.LocalRepository
import com.tnmobile.catastrophic.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class TNCatInteractor @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val catDtoMapper: CatDtoMapper
) : TNUseCase {
    override suspend fun getAllCats(): TNUseCase.Result {
        val catRespond = remoteRepository.getCats()
        var result: TNUseCase.Result = TNUseCase.Result.Success(emptyList<Cat>())

        catRespond.collect {

            result = if (it.count() > 0) {
                val modelList = catDtoMapper.toDomainList(it)
                TNUseCase.Result.Success(modelList)
            } else {
                TNUseCase.Result.Failure("Some thing was wrong")
            }

        }
        return result
    }

    override suspend fun insertCat(title: String, detail: String) {
        localRepository.insertCat(title, detail)
    }
}