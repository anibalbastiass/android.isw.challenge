package com.anibalbastias.androidisw.data.datasource.remote.model

import com.anibalbastias.androidisw.data.datasource.remote.Constants.DESCRIPTION
import com.anibalbastias.androidisw.data.datasource.remote.Constants.ID
import com.anibalbastias.androidisw.data.datasource.remote.Constants.IMAGE_URL
import com.anibalbastias.androidisw.data.datasource.remote.Constants.TITLE
import com.google.gson.annotations.SerializedName

data class RemoteNewsItem(
    @field:SerializedName(ID) val id: Long?,
    @field:SerializedName(TITLE) val title: String?,
    @field:SerializedName(DESCRIPTION) val description: String?,
    @field:SerializedName(IMAGE_URL) val imageUrl: String?
)