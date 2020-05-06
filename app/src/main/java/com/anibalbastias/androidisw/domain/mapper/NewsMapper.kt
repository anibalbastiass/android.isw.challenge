package com.anibalbastias.androidisw.domain.mapper

import com.anibalbastias.androidisw.data.datasource.remote.model.RemoteNewsItem
import com.anibalbastias.androidisw.domain.model.DomainNewsItem

class NewsMapper {

    fun RemoteNewsItem.fromRemoteToDomain() = DomainNewsItem(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl
    )
}