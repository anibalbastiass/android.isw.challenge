package com.anibalbastias.androidisw.presentation.model

import com.anibalbastias.androidisw.R


data class UiWrapperNews(
    val items: List<UiNewsItem>,
    val layoutId: Int? =  R.layout.view_cell_news_item
)