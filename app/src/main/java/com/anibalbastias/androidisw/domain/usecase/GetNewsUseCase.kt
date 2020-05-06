package com.anibalbastias.androidisw.domain.usecase

import com.anibalbastias.androidisw.domain.model.DomainNewsItem
import com.anibalbastias.androidisw.domain.repository.RemoteRepository
import com.anibalbastias.library.base.coroutines.ResultUseCase
import kotlinx.coroutines.Dispatchers

open class GetNewsUseCase(private val remoteRepository: RemoteRepository) :
    ResultUseCase<String, List<DomainNewsItem>>(
        backgroundContext = Dispatchers.IO,
        foregroundContext = Dispatchers.Main
    ) {
    override suspend fun executeOnBackground(params: String): List<DomainNewsItem>? {
        return remoteRepository.getLatestNews(token = params)
    }
}