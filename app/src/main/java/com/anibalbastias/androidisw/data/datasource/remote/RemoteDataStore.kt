package com.anibalbastias.androidisw.data.datasource.remote

import com.anibalbastias.androidisw.data.datasource.remote.api.NewsApi
import com.anibalbastias.androidisw.domain.mapper.NewsMapper
import com.anibalbastias.androidisw.domain.model.DomainNewsItem
import com.anibalbastias.androidisw.domain.repository.RemoteRepository
import com.anibalbastias.library.base.presentation.extensions.await


open class RemoteDataStore(
    private val newsApi: NewsApi,
    private val mapper: NewsMapper
) : RemoteRepository {

    override suspend fun getLatestNews(token: String): List<DomainNewsItem> {
        val response = newsApi.getLatestNews(token).await()
        with(mapper) {
            return response?.items?.map { item ->
                item.fromRemoteToDomain()
            } ?: listOf()
        }
    }

}