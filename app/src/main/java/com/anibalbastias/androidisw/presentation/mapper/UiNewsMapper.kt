package com.anibalbastias.androidisw.presentation.mapper

import com.anibalbastias.androidisw.domain.model.DomainNewsItem
import com.anibalbastias.androidisw.presentation.model.UiNewsItem

class UiNewsMapper {

    fun DomainNewsItem.fromDomainToUi() = UiNewsItem(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl
    )

    fun UiNewsItem.fromUiToDomain() = DomainNewsItem(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl
    )
}