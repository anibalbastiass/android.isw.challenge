package com.anibalbastias.androidisw.presentation.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class UiNewsItem(
    val id: Long?,
    val title: String?,
    val description: String?,
    val imageUrl: String?
) : Parcelable