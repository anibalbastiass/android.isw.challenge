package com.anibalbastias.androidisw.domain.repository


import com.anibalbastias.androidisw.domain.model.DomainNewsItem

interface RemoteRepository {

    suspend fun getLatestNews(token: String): List<DomainNewsItem>

}