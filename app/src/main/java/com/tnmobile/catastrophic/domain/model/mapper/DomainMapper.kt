package com.tnmobile.catastrophic.domain.model.mapper

interface DomainMapper  <T, DomainModel>{
    fun mapToDomainModel(model: T): DomainModel
}