package com.tnmobile.catastrophic.domain.model.mapper

import com.tnmobile.catastrophic.data.remote.entity.CatDto
import com.tnmobile.catastrophic.domain.model.Cat
import javax.inject.Inject

class CatDtoMapper @Inject constructor() : DomainMapper<CatDto, Cat> {

    override fun mapToDomainModel(model: CatDto): Cat {
        return Cat(
            id = model.id,
            url = model.url,
            width = model.width,
            height = model.height
        )
    }

    fun toDomainList(initial: List<CatDto>): List<Cat> {
        return initial.map { mapToDomainModel(it) }
    }

}