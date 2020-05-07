package com.anibalbastias.androidisw.data.datasource.remote.api

import com.anibalbastias.androidisw.data.datasource.remote.Constants.TOKEN
import com.anibalbastias.androidisw.data.datasource.remote.model.RemoteNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface NewsApi {

    @GET("s/{$TOKEN}/download")
    fun getLatestNews(@Path(TOKEN) token: String): Call<RemoteNews>

}