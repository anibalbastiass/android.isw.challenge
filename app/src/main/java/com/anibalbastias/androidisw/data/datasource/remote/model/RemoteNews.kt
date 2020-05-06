package com.anibalbastias.androidisw.data.datasource.remote.model

import com.anibalbastias.androidisw.data.datasource.remote.Constants.ITEMS
import com.google.gson.annotations.SerializedName

data class RemoteNews(
    @field:SerializedName(ITEMS) val items: List<RemoteNewsItem>?
)